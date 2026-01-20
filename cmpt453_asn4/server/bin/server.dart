import 'dart:convert';
import 'package:shelf/shelf.dart';
import 'package:shelf/shelf_io.dart' as io;
import 'package:shelf_router/shelf_router.dart';

// In-memory storage for simplicity (replace with a database in production)
final List<String> chatRooms = [];
final Map<String, List<Map<String, String>>> messages = {};

// Fetch list of chat rooms
Response getChatRooms(Request request) {
  return Response.ok(
    jsonEncode(chatRooms),
    headers: {'Content-Type': 'application/json'},
  );
}

// Create a new chat room
Future<Response> createChatRoom(Request request) async {
  try {
    final payload = jsonDecode(await request.readAsString());
    final roomName = payload['name'];

    if (roomName == null || roomName.isEmpty) {
      return Response(
        400,
        body: jsonEncode({'error': 'Room name is required'}),
        headers: {'Content-Type': 'application/json'},
      );
    }

    if (chatRooms.contains(roomName)) {
      return Response(
        400,
        body: jsonEncode({'error': 'Room already exists'}),
        headers: {'Content-Type': 'application/json'},
      );
    }

    chatRooms.add(roomName);
    messages[roomName] = []; // Initialize empty message list for the room

    return Response(
      201,
      body: jsonEncode({'message': 'Room created'}),
      headers: {'Content-Type': 'application/json'},
    );
  } catch (e) {
    print('Error creating chat room: $e');
    return Response(
      500,
      body: jsonEncode({'error': 'Internal server error'}),
      headers: {'Content-Type': 'application/json'},
    );
  }
}


// Fetch messages for a specific chat room
Response getMessages(Request request, String roomName) {
  if (!chatRooms.contains(roomName)) {
    return Response(
      404,
      body: jsonEncode({'error': 'Room not found'}),
      headers: {'Content-Type': 'application/json'},
    );
  }

  final roomMessages = messages[roomName] ?? [];
  return Response.ok(
    jsonEncode(roomMessages),
    headers: {'Content-Type': 'application/json'},
  );
}

// Send a message to a specific chat room
Future<Response> sendMessage(Request request, String roomName) async {
  try {
    if (!chatRooms.contains(roomName)) {
      return Response(
        404,
        body: jsonEncode({'error': 'Room not found'}),
        headers: {'Content-Type': 'application/json'},
      );
    }

    final payload = jsonDecode(await request.readAsString());
    final message = payload['message'];
    final sender = payload['sender'];

    if (message == null || message.isEmpty || sender == null || sender.isEmpty) {
      return Response(
        400,
        body: jsonEncode({'error': 'Message and sender are required'}),
        headers: {'Content-Type': 'application/json'},
      );
    }

    messages[roomName]!.add({'sender': sender, 'message': message});

    return Response(
      201,
      body: jsonEncode({'message': 'Message sent'}),
      headers: {'Content-Type': 'application/json'},
    );
  } catch (e) {
    print('Error sending message: $e');
    return Response(
      500,
      body: jsonEncode({'error': 'Internal server error'}),
      headers: {'Content-Type': 'application/json'},
    );
  }
}


Middleware handleCors() {
  return (Handler handler) {
    return (Request request) async {
      final response = await handler(request);
      return response.change(headers: {
        ...response.headers,
        'Access-Control-Allow-Origin': '*', // Allow all origins
        'Access-Control-Allow-Methods': 'GET, POST, OPTIONS',
        'Access-Control-Allow-Headers': 'Content-Type',
      });
    };
  };
}

// Set up routing
Router setupRouter() {
  final router = Router();

  router.get('/rooms', getChatRooms); // List chat rooms
  router.post('/rooms', createChatRoom); // Create a new chat room
  router.get('/rooms/<roomName>/messages', getMessages); // Get messages for a room
  router.post('/rooms/<roomName>/messages', sendMessage); // Send a message to a room

  // Handle preflight OPTIONS requests
  router.all('/<ignored|.*>', (Request request) {
    if (request.method == 'OPTIONS') {
      return Response.ok(
        '',
        headers: {
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Methods': 'GET, POST, OPTIONS',
          'Access-Control-Allow-Headers': 'Content-Type',
        },
      );
    }
    return Response.notFound('Route not found: ${request.requestedUri}');
  });

  return router;
}


// Main entry point for the server
void main() async {
  final handler = Pipeline()
      .addMiddleware(logRequests()) // Log all HTTP requests
      .addMiddleware(handleCors())
      .addHandler(setupRouter().call);

  const port = 8080; // Port for the server
  final server = await io.serve(handler, 'localhost', port);

  print('Server running on http://${server.address.host}:$port');
}
