package grpc;

// ChatClient.java
import chat.ChatServiceGrpc;
import chat.ChatServiceOuterClass.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class ChatClient {
    private final ChatServiceGrpc.ChatServiceBlockingStub chatStub;

    public ChatClient(String host, int port) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        chatStub = ChatServiceGrpc.newBlockingStub(channel);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Connected to gRPC Chat Service");

        while (true) {
            System.out.println("1. Create Room\n2. List Rooms\n3. Join Room\n4. Leave Room\n5. Send Message\n6. View Messages\n0. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1 -> {
                        System.out.print("Room name: ");
                        String roomName = scanner.nextLine();
                        RoomResponse response = chatStub.createRoom(RoomRequest.newBuilder().setRoomName(roomName).build());
                        System.out.println(response.getMessage());
                    }
                    case 2 -> {
                        RoomList rooms = chatStub.listRooms(Empty.newBuilder().build());
                        System.out.println("Rooms: " + rooms.getRoomsList());
                    }
                    case 3 -> {
                        System.out.print("Room name: ");
                        String roomName = scanner.nextLine();
                        System.out.print("User name: ");
                        String userName = scanner.nextLine();
                        RoomResponse response = chatStub.joinRoom(UserRoomRequest.newBuilder().setRoomName(roomName).setUserName(userName).build());
                        System.out.println(response.getMessage());
                    }
                    case 4 -> {
                        System.out.print("Room name: ");
                        String roomName = scanner.nextLine();
                        System.out.print("User name: ");
                        String userName = scanner.nextLine();
                        RoomResponse response = chatStub.leaveRoom(UserRoomRequest.newBuilder().setRoomName(roomName).setUserName(userName).build());
                        System.out.println(response.getMessage());
                    }
                    case 5 -> {
                        System.out.print("Room name: ");
                        String roomName = scanner.nextLine();
                        System.out.print("User name: ");
                        String userName = scanner.nextLine();
                        System.out.print("Message: ");
                        String message = scanner.nextLine();
                        MessageResponse response = chatStub.sendMessage(MessageRequest.newBuilder().setRoomName(roomName).setUserName(userName).setMessage(message).build());
                        System.out.println(response.getMessage());
                    }
                    case 6 -> {
                        System.out.print("Room name: ");
                        String roomName = scanner.nextLine();
                        MessageList messages = chatStub.getMessages(RoomRequest.newBuilder().setRoomName(roomName).build());
                        System.out.println("Messages: " + messages.getMessagesList());
                    }
                    case 0 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid option");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient("chat-server", 8080);
        client.start();
    }
}

