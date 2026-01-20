package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatRoomService extends Remote {
    void createRoom(String roomName) throws RemoteException;
    List<String> listRooms() throws RemoteException;
    void joinRoom(String roomName, String userName) throws RemoteException;
    void leaveRoom(String roomName, String userName) throws RemoteException;
    List<String> getMessages(String roomName) throws RemoteException;
    void sendMessage(String roomName, String userName, String message) throws RemoteException;
}