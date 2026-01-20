package grpc;

// ChatServer.java
import chat.ChatServiceGrpc;
import chat.ChatServiceOuterClass.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatServer extends ChatServiceGrpc.ChatServiceImplBase {

    private final Map<String, List<String>> rooms = new HashMap<>();
    private final Map<String, List<String>> messages = new HashMap<>();

    @Override
    public void createRoom(RoomRequest request, StreamObserver<RoomResponse> responseObserver) {
        try {
            String roomName = request.getRoomName();
            if (!rooms.containsKey(roomName)) {
                rooms.put(roomName, new ArrayList<>());
                messages.put(roomName, new ArrayList<>());
                responseObserver.onNext(RoomResponse.newBuilder().setSuccess(true).setMessage("Room created: " + roomName).build());
            } else {
                responseObserver.onNext(RoomResponse.newBuilder().setSuccess(false).setMessage("Room already exists").build());
            }
        } catch (Exception e) {
            responseObserver.onError(Status.UNKNOWN.withDescription("Server error: " + e.getMessage()).asRuntimeException());
        }
        responseObserver.onCompleted();
    }


    @Override
    public void listRooms(Empty request, StreamObserver<RoomList> responseObserver) {
        RoomList roomList = RoomList.newBuilder().addAllRooms(rooms.keySet()).build();
        responseObserver.onNext(roomList);
        responseObserver.onCompleted();
    }

    @Override
    public void joinRoom(UserRoomRequest request, StreamObserver<RoomResponse> responseObserver) {
        String roomName = request.getRoomName();
        String userName = request.getUserName();
        if (rooms.containsKey(roomName)) {
            rooms.get(roomName).add(userName);
            responseObserver.onNext(RoomResponse.newBuilder().setSuccess(true).setMessage(userName + " joined room: " + roomName).build());
        } else {
            responseObserver.onNext(RoomResponse.newBuilder().setSuccess(false).setMessage("Room does not exist").build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void leaveRoom(UserRoomRequest request, StreamObserver<RoomResponse> responseObserver) {
        String roomName = request.getRoomName();
        String userName = request.getUserName();
        if (rooms.containsKey(roomName) && rooms.get(roomName).remove(userName)) {
            responseObserver.onNext(RoomResponse.newBuilder().setSuccess(true).setMessage(userName + " left room: " + roomName).build());
        } else {
            responseObserver.onNext(RoomResponse.newBuilder().setSuccess(false).setMessage("Room or user does not exist").build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void sendMessage(MessageRequest request, StreamObserver<MessageResponse> responseObserver) {
        String roomName = request.getRoomName();
        String message = request.getUserName() + ": " + request.getMessage();
        if (rooms.containsKey(roomName)) {
            messages.get(roomName).add(message);
            responseObserver.onNext(MessageResponse.newBuilder().setSuccess(true).setMessage("Message sent").build());
        } else {
            responseObserver.onNext(MessageResponse.newBuilder().setSuccess(false).setMessage("Room does not exist").build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void getMessages(RoomRequest request, StreamObserver<MessageList> responseObserver) {
        String roomName = request.getRoomName();
        List<String> roomMessages = messages.getOrDefault(roomName, new ArrayList<>());
        MessageList messageList = MessageList.newBuilder().addAllMessages(roomMessages).build();
        responseObserver.onNext(messageList);
        responseObserver.onCompleted();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8080).addService(new ChatServer()).build();
        System.out.println("Chat gRPC server started...");
        server.start();
        server.awaitTermination();
    }
}
