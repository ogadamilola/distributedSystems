import grpc
import time
import csv
from concurrent.futures import ThreadPoolExecutor
import matplotlib.pyplot as plt
import os
import logging

from chat_pb2 import RoomRequest, UserRoomRequest, MessageRequest
from chat_pb2_grpc import ChatServiceStub

# Configuration
SERVER_ADDRESS = "localhost:8080"
channel = grpc.insecure_channel(SERVER_ADDRESS)
stub = ChatServiceStub(channel)
CSV_OUTPUT_FILE = "grpc_test_results.csv"
GRAPH_OUTPUT_DIR = "graphs/"
logging.basicConfig(level=logging.INFO, format="%(asctime)s - %(message)s")

# Global Variable to Store Chatroom Name
chatroom_name = None

# Function to create a chatroom
def create_chatroom(name):
    global chatroom_name
    print(f"Creating chatroom: {name}...")
    try:
        request = RoomRequest(room_name=name)
        response = stub.CreateRoom(request)  # gRPC call
        chatroom_name = name
        print(f"Chatroom created with name: {chatroom_name}")
    except grpc.RpcError as e:
        print(f"Failed to create chatroom: {e.details()}")
        raise e

# Function to join a chatroom
def join_chatroom(name):
    if name is None:
        raise Exception("Chatroom name is not set. Please create a chatroom first.")
    print(f"Joining chatroom: {name}...")
    try:
        request = UserRoomRequest(room_name=name, user_name="Tester")
        response = stub.JoinRoom(request)  # gRPC call
        print(f"Successfully joined chatroom: {name}")
    except grpc.RpcError as e:
        print(f"Failed to join chatroom: {e.details()}")
        raise e

# Function to send a single message
def send_message(payload):
    if chatroom_name is None:
        raise Exception("Chatroom name is not set. Please create and join a chatroom first.")
    try:
        request = MessageRequest(
            room_name=chatroom_name, user_name=payload["sender"], message=payload["message"]
        )
        start = time.time()
        response = stub.SendMessage(request)  # gRPC call
        latency = time.time() - start
        return {
            "status_code": "OK",
            "response": response.message,
            "latency": latency,
        }
    except grpc.RpcError as e:
        print(f"Error sending message: {e.details()}")
        return {
            "status_code": "ERROR",
            "response": str(e.details()),
            "latency": None,
        }

# Test Scenarios
def test_small_payload(num_requests):
    print("Testing small payloads...")
    payload = {"message": "Test message", "sender": "Tester"}
    results = []
    for i in range(num_requests):
        result = send_message(payload)
        result["test_case"] = "Small Payload"
        result["request_id"] = i + 1
        results.append(result)
    return results

def test_large_payload(num_requests):
    print("Testing large payloads...")
    payload = {"message": "A" * 10000, "sender": "Tester"}
    results = []
    for i in range(num_requests):
        result = send_message(payload)
        result["test_case"] = "Large Payload"
        result["request_id"] = i + 1
        results.append(result)
    return results

def test_concurrent_requests(num_requests, num_threads):
    print("Testing concurrent gRPC requests with independent clients...")
    results = []

    def simulate_client(client_id):
        with grpc.insecure_channel(SERVER_ADDRESS) as channel:
            stub = ChatServiceStub(channel)
            try:
                payload = {
                    "room_name": chatroom_name,
                    "sender": f"gRPC_Client_{client_id}",
                    "message": f"Message from gRPC client {client_id}",
                }
                request = MessageRequest(
                    room_name=payload["room_name"],
                    user_name=payload["sender"],
                    message=payload["message"],
                )
                start = time.time()
                response = stub.SendMessage(request)
                latency = time.time() - start
                return {
                    "status_code": "OK",
                    "response": response.message,
                    "latency": latency,
                }
            except grpc.RpcError as e:
                print(f"Error for gRPC client {client_id}: {e.details()}")
                return {
                    "status_code": "ERROR",
                    "response": str(e.details()),
                    "latency": None,
                }

    with ThreadPoolExecutor(max_workers=num_threads) as executor:
        futures = [executor.submit(simulate_client, i) for i in range(1, num_requests + 1)]
        for i, future in enumerate(futures):
            result = future.result()
            result["test_case"] = "Concurrent Requests GRPC"
            result["request_id"] = i + 1
            results.append(result)

    return results



def test_arrival_rates(num_requests, rate):
    print(f"Testing arrival rates at {rate} requests per second...")
    payload = {"message": "Test message", "sender": "Tester"}
    results = []
    for i in range(num_requests):
        result = send_message(payload)
        result["test_case"] = f"Arrival Rate: {rate} rps"
        result["request_id"] = i + 1
        results.append(result)
        time.sleep(1 / rate)  # Delay to control arrival rate
    return results

def test_data_types():
    print("Testing different data types...")
    results = []

    # Text message
    payload = {"message": "Simple text message", "sender": "Tester"}
    result = send_message(payload)
    result["test_case"] = "Data Type: Text"
    result["request_id"] = 1
    results.append(result)

    # JSON with nested data (as a string since gRPC expects strings)
    payload = {"message": '{"text": "Nested JSON", "meta": {"key": "value"}}', "sender": "Tester"}
    result = send_message(payload)
    result["test_case"] = "Data Type: Nested JSON"
    result["request_id"] = 2
    results.append(result)

    # Binary data (encoded as a string)
    binary_data = "01101000 01100101 01101100 01101100 01101111".replace(" ", "")
    payload = {"message": binary_data, "sender": "Tester"}
    result = send_message(payload)
    result["test_case"] = "Data Type: Binary"
    result["request_id"] = 3
    results.append(result)

    return results

def test_data_volume():
    print("Testing data volume...")
    results = []

    # Very small payload
    payload = {"message": "A", "sender": "Tester"}
    result = send_message(payload)
    result["test_case"] = "Data Volume: Very Small"
    result["request_id"] = 1
    results.append(result)

    # Normal payload
    payload = {"message": "This is a normal-sized message.", "sender": "Tester"}
    result = send_message(payload)
    result["test_case"] = "Data Volume: Normal"
    result["request_id"] = 2
    results.append(result)

    # Extremely large payload
    payload = {"message": "A" * 1000000, "sender": "Tester"}
    result = send_message(payload)
    result["test_case"] = "Data Volume: Extremely Large"
    result["request_id"] = 3
    results.append(result)

    return results

def send_message_with_retry(payload, retries=3):
    for attempt in range(retries):
        try:
            request = MessageRequest(**payload)
            response = stub.SendMessage(request)
            return {"status_code": "OK", "response": response.message}
        except grpc.RpcError as e:
            print(f"Attempt {attempt + 1} failed: {e.details()}")
        time.sleep(2 ** attempt)
    return {"status_code": "ERROR", "response": "Max retries reached"}

def send_message_with_server_check(payload):
    try:
        request = MessageRequest(**payload)
        response = stub.SendMessage(request)
        return {"status_code": "OK", "response": response.message}
    except grpc.RpcError as e:
        if e.code() == grpc.StatusCode.UNAVAILABLE:
            print("Server unavailable, retrying...")
        else:
            print(f"gRPC error: {e.details()}")
        return {"status_code": "ERROR", "response": str(e.details())}



# Save results to a CSV file
def save_results_to_csv(results):
    print(f"Saving results to {CSV_OUTPUT_FILE}...")
    with open(CSV_OUTPUT_FILE, "w", newline="") as csvfile:
        fieldnames = ["test_case", "request_id", "status_code", "response", "latency"]
        writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
        writer.writeheader()
        writer.writerows(results)
    print("Results saved.")

def generate_graphs(results, run_dir="graphs/"):
    if not os.path.exists(run_dir):
        os.makedirs(run_dir)
    print("Generating graphs...")
    test_cases = set(result["test_case"] for result in results)
    for test_case in test_cases:
        case_results = [r for r in results if r["test_case"] == test_case and r.get("latency") is not None]
        if not case_results:
            continue
        request_ids = [r["request_id"] for r in case_results]
        latencies = [r["latency"] for r in case_results]
        plt.figure()
        plt.plot(request_ids, latencies, marker="o")
        plt.title(f"Latency for {test_case}")
        plt.xlabel("Request ID")
        plt.ylabel("Latency (seconds)")
        plt.grid(True)
        graph_file = os.path.join(run_dir, f"{test_case.replace(' ', '_').lower()}_latency.png")
        plt.savefig(graph_file)
        plt.close()
        print(f"Graph saved: {graph_file}")
    print("All graphs generated.")

# Main Function
if __name__ == "__main__":
    NUM_REQUESTS = 50
    NUM_THREADS = 10
    RATES = [1, 10, 50]

    try:
        # Step 1: Create Chatroom
        create_chatroom("TestChatroom")
        join_chatroom("TestChatroom")

        # Step 2: Run Regular Tests
        all_results = []
        all_results.extend(test_small_payload(NUM_REQUESTS))
        all_results.extend(test_large_payload(NUM_REQUESTS))
        all_results.extend(test_concurrent_requests(NUM_REQUESTS, NUM_THREADS))
        for rate in RATES:
            all_results.extend(test_arrival_rates(NUM_REQUESTS, rate))
        all_results.extend(test_data_types())
        all_results.extend(test_data_volume())

        # Step 3: Test Network Failures
        print("\n--- Testing Network Failures ---")
        payload = {
            "room_name": chatroom_name,
            "message": "Test after network failure",
        }
        network_results = send_message_with_retry(payload, retries=3)
        print(f"Network Failure Test Results: {network_results}")
        all_results.append({
            "test_case": "Network Failure",
            "request_id": len(all_results) + 1,
            **network_results
        })

        # Step 4: Simulate Node Failures
        print("\n--- Simulating Node Failure ---")
        try:
            # Stop the server manually or via a script
            print("Simulating server crash...")
            payload["message"] = "Test after node failure"
            node_failure_result = send_message_with_server_check(payload)
            print(f"Node Failure Test Results: {node_failure_result}")
            all_results.append({
                "test_case": "Node Failure",
                "request_id": len(all_results) + 1,
                **node_failure_result
            })
        except Exception as e:
            print(f"Node failure test failed: {str(e)}")

        # Step 5: Save Results and Generate Graphs
        save_results_to_csv(all_results)
        generate_graphs(all_results, "graphs/")
        print("All tests completed.")
    except Exception as e:
        print(f"Testing terminated due to error: {str(e)}")

