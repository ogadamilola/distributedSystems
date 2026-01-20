import 'dart:convert';
import 'package:flutter/material.dart';
import '../services/chat_service.dart';
import 'package:uuid/uuid.dart';

class ChatRoomScreen extends StatefulWidget {
  final String roomId;
  final String roomName;

  ChatRoomScreen({required this.roomId, required this.roomName});

  @override
  _ChatRoomScreenState createState() => _ChatRoomScreenState();
}

class _ChatRoomScreenState extends State<ChatRoomScreen> {
  final ChatService chatService = ChatService();
  List<Map<String, dynamic>> messages = [];
  final TextEditingController messageController = TextEditingController();
  final String senderName = Uuid().v4().substring(0, 8); // Generate a random user ID

  @override
  void initState() {
    super.initState();
    fetchMessages(); // Fetch existing messages on initialization
    startPollingMessages(); // Start polling for new messages
  }

  // Fetch messages from the server
  void fetchMessages() async {
    try {
      final fetchedMessages = await chatService.getChatRoomMessages(widget.roomId);
      setState(() {
        messages = fetchedMessages;
      });
    } catch (e) {
      print('Error fetching messages: $e');
    }
  }

  // Start polling for new messages
  void startPollingMessages() {
    Future.delayed(Duration(seconds: 2), () {
      fetchMessages();
      if (mounted) {
        startPollingMessages(); // Repeat polling if the widget is still mounted
      }
    });
  }

  // Send a message to the server
  void sendMessage() async {
    if (messageController.text.isNotEmpty) {
      try {
        await chatService.sendMessage(
          widget.roomId,               // Room ID
          senderName,                  // Sender (unique per terminal)
          messageController.text,      // Message text
        );
        messageController.clear();
        fetchMessages(); // Fetch updated messages after sending
      } catch (e) {
        print('Error sending message: $e');
      }
    }
  }

  @override
  void dispose() {
    messageController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text(widget.roomName)),
      body: Column(
        children: [
          Expanded(
            child: ListView.builder(
              itemCount: messages.length,
              itemBuilder: (context, index) {
                final message = messages[index];
                return ListTile(
                  title: Text(message['message']),
                  subtitle: Text('Sent by: ${message['sender']}'),
                );
              },
            ),
          ),
          Padding(
            padding: EdgeInsets.all(8.0),
            child: Row(
              children: [
                Expanded(
                  child: TextField(
                    controller: messageController,
                    decoration: InputDecoration(hintText: 'Type a message...'),
                  ),
                ),
                IconButton(
                  icon: Icon(Icons.send),
                  onPressed: sendMessage,
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
