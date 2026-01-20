package rmi;

// ChatRoomClient.java
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class ChatRoomClient {
    private ChatRoomService chatService;

    public ChatRoomClient(String host) {
        try {
            Registry registry = LocateRegistry.getRegistry(host, 1099);
            chatService = (ChatRoomService) registry.lookup("ChatRoomService");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Chat Room!");

        while (true) {
            System.out.println("1. Create Room");
            System.out.println("2. List Rooms");
            System.out.println("3. Join Room");
            System.out.println("4. Leave Room");
            System.out.println("5. Send Message");
            System.out.println("6. View Messages");
            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            try {
                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter room name: ");
                        String roomName = scanner.nextLine();
                        chatService.createRoom(roomName);
                    }
                    case 2 -> {
                        List<String> rooms = chatService.listRooms();
                        System.out.println("Available rooms: " + rooms);
                    }
                    case 3 -> {
                        System.out.print("Enter room name: ");
                        String roomName = scanner.nextLine();
                        System.out.print("Enter your username: ");
                        String userName = scanner.nextLine();
                        chatService.joinRoom(roomName, userName);
                    }
                    case 4 -> {
                        System.out.print("Enter room name: ");
                        String roomName = scanner.nextLine();
                        System.out.print("Enter your username: ");
                        String userName = scanner.nextLine();
                        chatService.leaveRoom(roomName, userName);
                    }
                    case 5 -> {
                        System.out.print("Enter room name: ");
                        String roomName = scanner.nextLine();
                        System.out.print("Enter your username: ");
                        String userName = scanner.nextLine();
                        System.out.print("Enter message: ");
                        String message = scanner.nextLine();
                        chatService.sendMessage(roomName, userName, message);
                    }
                    case 6 -> {
                        System.out.print("Enter room name: ");
                        String roomName = scanner.nextLine();
                        List<String> messages = chatService.getMessages(roomName);
                        messages.forEach(System.out::println);
                    }
                    case 0 -> {
                        System.out.println("Exiting chat client...");
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        ChatRoomClient client = new ChatRoomClient("localhost");
        client.start();
    }
}

