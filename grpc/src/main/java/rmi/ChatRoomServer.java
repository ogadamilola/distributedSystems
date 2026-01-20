package rmi;

// ChatRoomServer.java
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatRoomServer implements ChatRoomService {

    private final Map<String, List<String>> rooms = new HashMap<>();
    private final Map<String, List<String>> messages = new HashMap<>();

    public ChatRoomServer() {}

    @Override
    public void createRoom(String roomName) throws RemoteException {
        if (!rooms.containsKey(roomName)) {
            rooms.put(roomName, new ArrayList<>());
            messages.put(roomName, new ArrayList<>());
            System.out.println("Room created: " + roomName);
        }
    }

    @Override
    public List<String> listRooms() throws RemoteException {
        return new ArrayList<>(rooms.keySet());
    }

    @Override
    public void joinRoom(String roomName, String userName) throws RemoteException {
        if (rooms.containsKey(roomName)) {
            rooms.get(roomName).add(userName);
            System.out.println(userName + " joined room: " + roomName);
        }
    }

    @Override
    public void leaveRoom(String roomName, String userName) throws RemoteException {
        if (rooms.containsKey(roomName)) {
            rooms.get(roomName).remove(userName);
            System.out.println(userName + " left room: " + roomName);
        }
    }

    @Override
    public List<String> getMessages(String roomName) throws RemoteException {
        return messages.getOrDefault(roomName, new ArrayList<>());
    }

    @Override
    public void sendMessage(String roomName, String userName, String message) throws RemoteException {
        if (rooms.containsKey(roomName)) {
            String formattedMessage = userName + ": " + message;
            messages.get(roomName).add(formattedMessage);
            System.out.println("Message sent to " + roomName + ": " + formattedMessage);
        }
    }

    public static void main(String[] args) {
        try {
            ChatRoomServer server = new ChatRoomServer();
            ChatRoomService stub = (ChatRoomService) UnicastRemoteObject.exportObject(server, 0);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("ChatRoomService", stub);
            System.out.println("Chat Room Server started...");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
