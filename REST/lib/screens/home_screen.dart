import 'package:flutter/material.dart';
import '../services/chat_service.dart';
import 'chat_room_screen.dart';

class HomeScreen extends StatefulWidget {
  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  final ChatService chatService = ChatService();
  List<String> chatRooms = [];

  @override
  void initState() {
    super.initState();
    fetchChatRooms();
  }

  void fetchChatRooms() async {
    try {
      final rooms = await chatService.getChatRooms();
      setState(() {
        chatRooms = rooms;
      });
    } catch (e) {
      print('Error loading chat rooms: $e');
    }
  }

  void createRoom() async {
    TextEditingController roomController = TextEditingController();
    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: Text('Create Chat Room'),
          content: TextField(
            controller: roomController,
            decoration: InputDecoration(hintText: 'Enter room name'),
          ),
          actions: [
            TextButton(
              onPressed: () async {
                if (roomController.text.isNotEmpty) {
                  await chatService.createChatRoom(roomController.text);
                  fetchChatRooms();
                  Navigator.pop(context);
                }
              },
              child: Text('Create'),
            ),
            TextButton(
              onPressed: () => Navigator.pop(context),
              child: Text('Cancel'),
            ),
          ],
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Chat Rooms')),
      body: chatRooms.isEmpty
          ? Center(child: CircularProgressIndicator())
          : ListView.builder(
        itemCount: chatRooms.length,
        itemBuilder: (context, index) {
          return ListTile(
            title: Text(chatRooms[index]),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) =>
                      ChatRoomScreen(roomId: chatRooms[index], roomName: chatRooms[index]),
                ),
              );
            },
          );
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: createRoom,
        child: Icon(Icons.add),
      ),
    );
  }
}
