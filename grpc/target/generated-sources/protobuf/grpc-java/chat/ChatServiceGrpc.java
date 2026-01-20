package chat;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Define the ChatService
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.57.0)",
    comments = "Source: chat.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ChatServiceGrpc {

  private ChatServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "chat.ChatService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<chat.ChatServiceOuterClass.RoomRequest,
      chat.ChatServiceOuterClass.RoomResponse> getCreateRoomMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateRoom",
      requestType = chat.ChatServiceOuterClass.RoomRequest.class,
      responseType = chat.ChatServiceOuterClass.RoomResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<chat.ChatServiceOuterClass.RoomRequest,
      chat.ChatServiceOuterClass.RoomResponse> getCreateRoomMethod() {
    io.grpc.MethodDescriptor<chat.ChatServiceOuterClass.RoomRequest, chat.ChatServiceOuterClass.RoomResponse> getCreateRoomMethod;
    if ((getCreateRoomMethod = ChatServiceGrpc.getCreateRoomMethod) == null) {
      synchronized (ChatServiceGrpc.class) {
        if ((getCreateRoomMethod = ChatServiceGrpc.getCreateRoomMethod) == null) {
          ChatServiceGrpc.getCreateRoomMethod = getCreateRoomMethod =
              io.grpc.MethodDescriptor.<chat.ChatServiceOuterClass.RoomRequest, chat.ChatServiceOuterClass.RoomResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateRoom"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chat.ChatServiceOuterClass.RoomRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chat.ChatServiceOuterClass.RoomResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ChatServiceMethodDescriptorSupplier("CreateRoom"))
              .build();
        }
      }
    }
    return getCreateRoomMethod;
  }

  private static volatile io.grpc.MethodDescriptor<chat.ChatServiceOuterClass.Empty,
      chat.ChatServiceOuterClass.RoomList> getListRoomsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListRooms",
      requestType = chat.ChatServiceOuterClass.Empty.class,
      responseType = chat.ChatServiceOuterClass.RoomList.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<chat.ChatServiceOuterClass.Empty,
      chat.ChatServiceOuterClass.RoomList> getListRoomsMethod() {
    io.grpc.MethodDescriptor<chat.ChatServiceOuterClass.Empty, chat.ChatServiceOuterClass.RoomList> getListRoomsMethod;
    if ((getListRoomsMethod = ChatServiceGrpc.getListRoomsMethod) == null) {
      synchronized (ChatServiceGrpc.class) {
        if ((getListRoomsMethod = ChatServiceGrpc.getListRoomsMethod) == null) {
          ChatServiceGrpc.getListRoomsMethod = getListRoomsMethod =
              io.grpc.MethodDescriptor.<chat.ChatServiceOuterClass.Empty, chat.ChatServiceOuterClass.RoomList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListRooms"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chat.ChatServiceOuterClass.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chat.ChatServiceOuterClass.RoomList.getDefaultInstance()))
              .setSchemaDescriptor(new ChatServiceMethodDescriptorSupplier("ListRooms"))
              .build();
        }
      }
    }
    return getListRoomsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<chat.ChatServiceOuterClass.UserRoomRequest,
      chat.ChatServiceOuterClass.RoomResponse> getJoinRoomMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "JoinRoom",
      requestType = chat.ChatServiceOuterClass.UserRoomRequest.class,
      responseType = chat.ChatServiceOuterClass.RoomResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<chat.ChatServiceOuterClass.UserRoomRequest,
      chat.ChatServiceOuterClass.RoomResponse> getJoinRoomMethod() {
    io.grpc.MethodDescriptor<chat.ChatServiceOuterClass.UserRoomRequest, chat.ChatServiceOuterClass.RoomResponse> getJoinRoomMethod;
    if ((getJoinRoomMethod = ChatServiceGrpc.getJoinRoomMethod) == null) {
      synchronized (ChatServiceGrpc.class) {
        if ((getJoinRoomMethod = ChatServiceGrpc.getJoinRoomMethod) == null) {
          ChatServiceGrpc.getJoinRoomMethod = getJoinRoomMethod =
              io.grpc.MethodDescriptor.<chat.ChatServiceOuterClass.UserRoomRequest, chat.ChatServiceOuterClass.RoomResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "JoinRoom"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chat.ChatServiceOuterClass.UserRoomRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chat.ChatServiceOuterClass.RoomResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ChatServiceMethodDescriptorSupplier("JoinRoom"))
              .build();
        }
      }
    }
    return getJoinRoomMethod;
  }

  private static volatile io.grpc.MethodDescriptor<chat.ChatServiceOuterClass.UserRoomRequest,
      chat.ChatServiceOuterClass.RoomResponse> getLeaveRoomMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "LeaveRoom",
      requestType = chat.ChatServiceOuterClass.UserRoomRequest.class,
      responseType = chat.ChatServiceOuterClass.RoomResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<chat.ChatServiceOuterClass.UserRoomRequest,
      chat.ChatServiceOuterClass.RoomResponse> getLeaveRoomMethod() {
    io.grpc.MethodDescriptor<chat.ChatServiceOuterClass.UserRoomRequest, chat.ChatServiceOuterClass.RoomResponse> getLeaveRoomMethod;
    if ((getLeaveRoomMethod = ChatServiceGrpc.getLeaveRoomMethod) == null) {
      synchronized (ChatServiceGrpc.class) {
        if ((getLeaveRoomMethod = ChatServiceGrpc.getLeaveRoomMethod) == null) {
          ChatServiceGrpc.getLeaveRoomMethod = getLeaveRoomMethod =
              io.grpc.MethodDescriptor.<chat.ChatServiceOuterClass.UserRoomRequest, chat.ChatServiceOuterClass.RoomResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "LeaveRoom"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chat.ChatServiceOuterClass.UserRoomRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chat.ChatServiceOuterClass.RoomResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ChatServiceMethodDescriptorSupplier("LeaveRoom"))
              .build();
        }
      }
    }
    return getLeaveRoomMethod;
  }

  private static volatile io.grpc.MethodDescriptor<chat.ChatServiceOuterClass.MessageRequest,
      chat.ChatServiceOuterClass.MessageResponse> getSendMessageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendMessage",
      requestType = chat.ChatServiceOuterClass.MessageRequest.class,
      responseType = chat.ChatServiceOuterClass.MessageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<chat.ChatServiceOuterClass.MessageRequest,
      chat.ChatServiceOuterClass.MessageResponse> getSendMessageMethod() {
    io.grpc.MethodDescriptor<chat.ChatServiceOuterClass.MessageRequest, chat.ChatServiceOuterClass.MessageResponse> getSendMessageMethod;
    if ((getSendMessageMethod = ChatServiceGrpc.getSendMessageMethod) == null) {
      synchronized (ChatServiceGrpc.class) {
        if ((getSendMessageMethod = ChatServiceGrpc.getSendMessageMethod) == null) {
          ChatServiceGrpc.getSendMessageMethod = getSendMessageMethod =
              io.grpc.MethodDescriptor.<chat.ChatServiceOuterClass.MessageRequest, chat.ChatServiceOuterClass.MessageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SendMessage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chat.ChatServiceOuterClass.MessageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chat.ChatServiceOuterClass.MessageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ChatServiceMethodDescriptorSupplier("SendMessage"))
              .build();
        }
      }
    }
    return getSendMessageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<chat.ChatServiceOuterClass.RoomRequest,
      chat.ChatServiceOuterClass.MessageList> getGetMessagesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetMessages",
      requestType = chat.ChatServiceOuterClass.RoomRequest.class,
      responseType = chat.ChatServiceOuterClass.MessageList.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<chat.ChatServiceOuterClass.RoomRequest,
      chat.ChatServiceOuterClass.MessageList> getGetMessagesMethod() {
    io.grpc.MethodDescriptor<chat.ChatServiceOuterClass.RoomRequest, chat.ChatServiceOuterClass.MessageList> getGetMessagesMethod;
    if ((getGetMessagesMethod = ChatServiceGrpc.getGetMessagesMethod) == null) {
      synchronized (ChatServiceGrpc.class) {
        if ((getGetMessagesMethod = ChatServiceGrpc.getGetMessagesMethod) == null) {
          ChatServiceGrpc.getGetMessagesMethod = getGetMessagesMethod =
              io.grpc.MethodDescriptor.<chat.ChatServiceOuterClass.RoomRequest, chat.ChatServiceOuterClass.MessageList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetMessages"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chat.ChatServiceOuterClass.RoomRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chat.ChatServiceOuterClass.MessageList.getDefaultInstance()))
              .setSchemaDescriptor(new ChatServiceMethodDescriptorSupplier("GetMessages"))
              .build();
        }
      }
    }
    return getGetMessagesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ChatServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChatServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChatServiceStub>() {
        @java.lang.Override
        public ChatServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChatServiceStub(channel, callOptions);
        }
      };
    return ChatServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ChatServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChatServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChatServiceBlockingStub>() {
        @java.lang.Override
        public ChatServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChatServiceBlockingStub(channel, callOptions);
        }
      };
    return ChatServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ChatServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChatServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChatServiceFutureStub>() {
        @java.lang.Override
        public ChatServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChatServiceFutureStub(channel, callOptions);
        }
      };
    return ChatServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Define the ChatService
   * </pre>
   */
  public interface AsyncService {

    /**
     */
    default void createRoom(chat.ChatServiceOuterClass.RoomRequest request,
        io.grpc.stub.StreamObserver<chat.ChatServiceOuterClass.RoomResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateRoomMethod(), responseObserver);
    }

    /**
     */
    default void listRooms(chat.ChatServiceOuterClass.Empty request,
        io.grpc.stub.StreamObserver<chat.ChatServiceOuterClass.RoomList> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListRoomsMethod(), responseObserver);
    }

    /**
     */
    default void joinRoom(chat.ChatServiceOuterClass.UserRoomRequest request,
        io.grpc.stub.StreamObserver<chat.ChatServiceOuterClass.RoomResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getJoinRoomMethod(), responseObserver);
    }

    /**
     */
    default void leaveRoom(chat.ChatServiceOuterClass.UserRoomRequest request,
        io.grpc.stub.StreamObserver<chat.ChatServiceOuterClass.RoomResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLeaveRoomMethod(), responseObserver);
    }

    /**
     */
    default void sendMessage(chat.ChatServiceOuterClass.MessageRequest request,
        io.grpc.stub.StreamObserver<chat.ChatServiceOuterClass.MessageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendMessageMethod(), responseObserver);
    }

    /**
     */
    default void getMessages(chat.ChatServiceOuterClass.RoomRequest request,
        io.grpc.stub.StreamObserver<chat.ChatServiceOuterClass.MessageList> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMessagesMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ChatService.
   * <pre>
   * Define the ChatService
   * </pre>
   */
  public static abstract class ChatServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ChatServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ChatService.
   * <pre>
   * Define the ChatService
   * </pre>
   */
  public static final class ChatServiceStub
      extends io.grpc.stub.AbstractAsyncStub<ChatServiceStub> {
    private ChatServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChatServiceStub(channel, callOptions);
    }

    /**
     */
    public void createRoom(chat.ChatServiceOuterClass.RoomRequest request,
        io.grpc.stub.StreamObserver<chat.ChatServiceOuterClass.RoomResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateRoomMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listRooms(chat.ChatServiceOuterClass.Empty request,
        io.grpc.stub.StreamObserver<chat.ChatServiceOuterClass.RoomList> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListRoomsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void joinRoom(chat.ChatServiceOuterClass.UserRoomRequest request,
        io.grpc.stub.StreamObserver<chat.ChatServiceOuterClass.RoomResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getJoinRoomMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void leaveRoom(chat.ChatServiceOuterClass.UserRoomRequest request,
        io.grpc.stub.StreamObserver<chat.ChatServiceOuterClass.RoomResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLeaveRoomMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void sendMessage(chat.ChatServiceOuterClass.MessageRequest request,
        io.grpc.stub.StreamObserver<chat.ChatServiceOuterClass.MessageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendMessageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getMessages(chat.ChatServiceOuterClass.RoomRequest request,
        io.grpc.stub.StreamObserver<chat.ChatServiceOuterClass.MessageList> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMessagesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ChatService.
   * <pre>
   * Define the ChatService
   * </pre>
   */
  public static final class ChatServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ChatServiceBlockingStub> {
    private ChatServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChatServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public chat.ChatServiceOuterClass.RoomResponse createRoom(chat.ChatServiceOuterClass.RoomRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateRoomMethod(), getCallOptions(), request);
    }

    /**
     */
    public chat.ChatServiceOuterClass.RoomList listRooms(chat.ChatServiceOuterClass.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListRoomsMethod(), getCallOptions(), request);
    }

    /**
     */
    public chat.ChatServiceOuterClass.RoomResponse joinRoom(chat.ChatServiceOuterClass.UserRoomRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getJoinRoomMethod(), getCallOptions(), request);
    }

    /**
     */
    public chat.ChatServiceOuterClass.RoomResponse leaveRoom(chat.ChatServiceOuterClass.UserRoomRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLeaveRoomMethod(), getCallOptions(), request);
    }

    /**
     */
    public chat.ChatServiceOuterClass.MessageResponse sendMessage(chat.ChatServiceOuterClass.MessageRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendMessageMethod(), getCallOptions(), request);
    }

    /**
     */
    public chat.ChatServiceOuterClass.MessageList getMessages(chat.ChatServiceOuterClass.RoomRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMessagesMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ChatService.
   * <pre>
   * Define the ChatService
   * </pre>
   */
  public static final class ChatServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<ChatServiceFutureStub> {
    private ChatServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChatServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<chat.ChatServiceOuterClass.RoomResponse> createRoom(
        chat.ChatServiceOuterClass.RoomRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateRoomMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<chat.ChatServiceOuterClass.RoomList> listRooms(
        chat.ChatServiceOuterClass.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListRoomsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<chat.ChatServiceOuterClass.RoomResponse> joinRoom(
        chat.ChatServiceOuterClass.UserRoomRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getJoinRoomMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<chat.ChatServiceOuterClass.RoomResponse> leaveRoom(
        chat.ChatServiceOuterClass.UserRoomRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLeaveRoomMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<chat.ChatServiceOuterClass.MessageResponse> sendMessage(
        chat.ChatServiceOuterClass.MessageRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendMessageMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<chat.ChatServiceOuterClass.MessageList> getMessages(
        chat.ChatServiceOuterClass.RoomRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMessagesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_ROOM = 0;
  private static final int METHODID_LIST_ROOMS = 1;
  private static final int METHODID_JOIN_ROOM = 2;
  private static final int METHODID_LEAVE_ROOM = 3;
  private static final int METHODID_SEND_MESSAGE = 4;
  private static final int METHODID_GET_MESSAGES = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_ROOM:
          serviceImpl.createRoom((chat.ChatServiceOuterClass.RoomRequest) request,
              (io.grpc.stub.StreamObserver<chat.ChatServiceOuterClass.RoomResponse>) responseObserver);
          break;
        case METHODID_LIST_ROOMS:
          serviceImpl.listRooms((chat.ChatServiceOuterClass.Empty) request,
              (io.grpc.stub.StreamObserver<chat.ChatServiceOuterClass.RoomList>) responseObserver);
          break;
        case METHODID_JOIN_ROOM:
          serviceImpl.joinRoom((chat.ChatServiceOuterClass.UserRoomRequest) request,
              (io.grpc.stub.StreamObserver<chat.ChatServiceOuterClass.RoomResponse>) responseObserver);
          break;
        case METHODID_LEAVE_ROOM:
          serviceImpl.leaveRoom((chat.ChatServiceOuterClass.UserRoomRequest) request,
              (io.grpc.stub.StreamObserver<chat.ChatServiceOuterClass.RoomResponse>) responseObserver);
          break;
        case METHODID_SEND_MESSAGE:
          serviceImpl.sendMessage((chat.ChatServiceOuterClass.MessageRequest) request,
              (io.grpc.stub.StreamObserver<chat.ChatServiceOuterClass.MessageResponse>) responseObserver);
          break;
        case METHODID_GET_MESSAGES:
          serviceImpl.getMessages((chat.ChatServiceOuterClass.RoomRequest) request,
              (io.grpc.stub.StreamObserver<chat.ChatServiceOuterClass.MessageList>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getCreateRoomMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              chat.ChatServiceOuterClass.RoomRequest,
              chat.ChatServiceOuterClass.RoomResponse>(
                service, METHODID_CREATE_ROOM)))
        .addMethod(
          getListRoomsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              chat.ChatServiceOuterClass.Empty,
              chat.ChatServiceOuterClass.RoomList>(
                service, METHODID_LIST_ROOMS)))
        .addMethod(
          getJoinRoomMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              chat.ChatServiceOuterClass.UserRoomRequest,
              chat.ChatServiceOuterClass.RoomResponse>(
                service, METHODID_JOIN_ROOM)))
        .addMethod(
          getLeaveRoomMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              chat.ChatServiceOuterClass.UserRoomRequest,
              chat.ChatServiceOuterClass.RoomResponse>(
                service, METHODID_LEAVE_ROOM)))
        .addMethod(
          getSendMessageMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              chat.ChatServiceOuterClass.MessageRequest,
              chat.ChatServiceOuterClass.MessageResponse>(
                service, METHODID_SEND_MESSAGE)))
        .addMethod(
          getGetMessagesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              chat.ChatServiceOuterClass.RoomRequest,
              chat.ChatServiceOuterClass.MessageList>(
                service, METHODID_GET_MESSAGES)))
        .build();
  }

  private static abstract class ChatServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ChatServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return chat.ChatServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ChatService");
    }
  }

  private static final class ChatServiceFileDescriptorSupplier
      extends ChatServiceBaseDescriptorSupplier {
    ChatServiceFileDescriptorSupplier() {}
  }

  private static final class ChatServiceMethodDescriptorSupplier
      extends ChatServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ChatServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ChatServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ChatServiceFileDescriptorSupplier())
              .addMethod(getCreateRoomMethod())
              .addMethod(getListRoomsMethod())
              .addMethod(getJoinRoomMethod())
              .addMethod(getLeaveRoomMethod())
              .addMethod(getSendMessageMethod())
              .addMethod(getGetMessagesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
