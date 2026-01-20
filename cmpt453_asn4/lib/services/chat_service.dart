import 'package:http/http.dart' as http;
import 'dart:convert';

class ChatService {
  final String baseUrl = 'http://localhost:8080';

  // Fetch the list of available chat rooms
  Future<List<String>> getChatRooms() async {
    final response = await http.get(Uri.parse('$baseUrl/rooms'));
    if (response.statusCode == 200) {
      return List<String>.from(json.decode(response.body));
    } else {
      throw Exception('Failed to load chat rooms');
    }
  }

  // Create a new chat room
  Future<void> createChatRoom(String name) async {
    final response = await http.post(
      Uri.parse('$baseUrl/rooms'),
      body: jsonEncode({'name': name}),
      headers: {'Content-Type': 'application/json'},
    );
    if (response.statusCode != 201) {
      throw Exception('Failed to create chat room');
    }
  }

  // Join an existing chat room
  Future<void> joinChatRoom(String roomId) async {
    final response = await http.post(
      Uri.parse('$baseUrl/rooms/$roomId/join'),
    );
    if (response.statusCode != 200) {
      throw Exception('Failed to join chat room');
    }
  }

  // Leave a chat room
  Future<void> leaveChatRoom(String roomId) async {
    final response = await http.post(
      Uri.parse('$baseUrl/rooms/$roomId/leave'),
    );
    if (response.statusCode != 200) {
      throw Exception('Failed to leave chat room');
    }
  }

  // Fetch all messages in a chat room
  Future<List<Map<String, dynamic>>> getChatRoomMessages(String roomId) async {
    final response = await http.get(
      Uri.parse('$baseUrl/rooms/$roomId/messages'),
    );
    if (response.statusCode == 200) {
      return List<Map<String, dynamic>>.from(json.decode(response.body));
    } else {
      throw Exception('Failed to load messages');
    }
  }

  // Send a message to a chat room
  Future<void> sendMessage(String roomName, String sender, String message) async {
    // final String baseUrl = 'http://localhost:8080';
    try {
      final response = await http.post(
        Uri.parse('$baseUrl/rooms/$roomName/messages'),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({
          'message': message,
          'sender': sender,
        }),
      );

      if (response.statusCode == 201) {
        print('Message sent successfully: ${response.body}');
      } else {
        print('Failed to send message: ${response.statusCode} ${response.body}');
      }
    } catch (e) {
      print('Error sending message: $e');
    }
  }

}

