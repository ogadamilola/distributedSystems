import requests
import time
import csv
from concurrent.futures import ThreadPoolExecutor
import matplotlib.pyplot as plt
import os
import logging

# Configuration
SERVER_URL = "http://localhost:8080"
CREATE_CHATROOM_ENDPOINT = f"{SERVER_URL}/rooms"
JOIN_CHATROOM_ENDPOINT = lambda room_id: f"{SERVER_URL}/rooms/{room_id}/join"
SEND_MESSAGE_ENDPOINT = lambda room_id: f"{SERVER_URL}/rooms/{room_id}/messages"
CSV_OUTPUT_FILE = "rest_test_results.csv"
GRAPH_OUTPUT_DIR = "graphs/"

# Global Variable to Store Chatroom ID
chatroom_id = None

# Function to create a chatroom
def create_chatroom(chatroom_name):
    global chatroom_id
    print(f"Creating chatroom: {chatroom_name}...")
    payload = {"name": chatroom_name}
    try:
        response = requests.post(CREATE_CHATROOM_ENDPOINT, json=payload, headers={"Content-Type": "application/json"})
        print(f"Response Status Code: {response.status_code}")
        print(f"Response Text: {response.text}")  # For debugging
        if response.status_code == 201:
            # Use the room name directly
            chatroom_id = chatroom_name
            print(f"Chatroom created with name: {chatroom_id}")
        else:
            print(f"Failed to create chatroom: {response.status_code}, {response.text}")
            raise Exception("Chatroom creation failed")
    except Exception as e:
        print(f"Error creating chatroom: {str(e)}")
        raise e


# Function to join a chatroom
# def join_chatroom():
#     if chatroom_id is None:
#         raise Exception("Chatroom ID is not set. Please create a chatroom first.")
#     print(f"Joining chatroom: {chatroom_id}...")
#     try:
#         response = requests.post(JOIN_CHATROOM_ENDPOINT(chatroom_id))
#         if response.status_code == 200:
#             print("Successfully joined chatroom.")
#         else:
#             print(f"Failed to join chatroom: {response.status_code}, {response.text}")
#             raise Exception("Chatroom join failed")
#     except Exception as e:
#         print(f"Error joining chatroom: {str(e)}")
#         raise e

# Function to send a single message
def send_message(payload):
    if chatroom_id is None:
        raise Exception("Chatroom name is not set. Please create and join a chatroom first.")
    try:
        payload["roomName"] = chatroom_id
        start = time.time()
        response = requests.post(SEND_MESSAGE_ENDPOINT(chatroom_id), json=payload, headers={"Content-Type": "application/json"})
        latency = time.time() - start
        return {
            "status_code": response.status_code,
            "response": response.text,
            "latency": latency,
        }
    except Exception as e:
        return {
            "status_code": "ERROR",
            "response": str(e),
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
    print("Testing concurrent REST requests with independent clients...")
    results = []

    def simulate_client(client_id):
        logging.info("Simulating new client...")
        with requests.Session() as session:
            try:
                payload = {
                    "message": f"Message from REST client {client_id}",
                    "sender": f"REST_Client_{client_id}",
                }
                start = time.time()
                url = SEND_MESSAGE_ENDPOINT(chatroom_id)
                response = session.post(url, json=payload, headers={"Content-Type": "application/json"})
                latency = time.time() - start
                return {
                    "status_code": response.status_code,
                    "response": response.text,
                    "latency": latency,
                }
            except Exception as e:
                print(f"Error for REST client {client_id}: {str(e)}")
                return {
                    "status_code": "ERROR",
                    "response": str(e),
                    "latency": None,
                }

    with ThreadPoolExecutor(max_workers=num_threads) as executor:
        futures = [executor.submit(simulate_client, i) for i in range(1, num_requests + 1)]
        for i, future in enumerate(futures):
            result = future.result()
            result["test_case"] = "Concurrent Requests REST"
            result["request_id"] = i + 1
            results.append(result)

    return results

def send_message_with_retry(payload, retries=3):
    url = SEND_MESSAGE_ENDPOINT(chatroom_id)
    for attempt in range(retries):
        try:
            response = requests.post(url, json=payload, headers={"Content-Type": "application/json"})
            if response.status_code == 200:
                return {"status_code": response.status_code, "response": response.text}
            print(f"Attempt {attempt + 1} failed: {response.status_code}")
        except requests.exceptions.RequestException as e:
            print(f"Network error: {str(e)}")
        time.sleep(2 ** attempt)  # Exponential backoff
    return {"status_code": "ERROR", "response": "Max retries reached"}

def send_message_with_server_check(payload):
    try:
        response = requests.post(SEND_MESSAGE_ENDPOINT(chatroom_id), json=payload, timeout=5)
        if response.status_code == 503:
            print("Server unavailable, retrying...")
        return {"status_code": response.status_code, "response": response.text}
    except requests.exceptions.RequestException as e:
        print(f"Server unreachable: {str(e)}")
        return {"status_code": "ERROR", "response": "Server unreachable"}



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
    result["request_id"] = 1  # Assign a unique request_id
    results.append(result)

    # JSON with nested data
    payload = {"message": {"text": "Nested JSON", "meta": {"key": "value"}}, "sender": "Tester"}
    result = send_message(payload)
    result["test_case"] = "Data Type: Nested JSON"
    result["request_id"] = 2  # Assign a unique request_id
    results.append(result)

    # Binary data (encoded as string)
    binary_data = "01101000 01100101 01101100 01101100 01101111".replace(" ", "")
    payload = {"message": binary_data, "sender": "Tester"}
    result = send_message(payload)
    result["test_case"] = "Data Type: Binary"
    result["request_id"] = 3  # Assign a unique request_id
    results.append(result)

    return results


def test_data_volume():
    print("Testing data volume...")
    results = []

    # Very small payload
    payload = {"message": "A", "sender": "Tester"}
    result = send_message(payload)
    result["test_case"] = "Data Volume: Very Small"
    result["request_id"] = 1  # Assign a unique request_id
    results.append(result)

    # Normal payload
    payload = {"message": "This is a normal-sized message.", "sender": "Tester"}
    result = send_message(payload)
    result["test_case"] = "Data Volume: Normal"
    result["request_id"] = 2  # Increment request_id
    results.append(result)

    # Extremely large payload
    payload = {"message": "A" * 1000000, "sender": "Tester"}  # 1 MB payload
    result = send_message(payload)
    result["test_case"] = "Data Volume: Extremely Large"
    result["request_id"] = 3  # Increment request_id
    results.append(result)

    return results


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
    # Ensure the graphs directory exists
    if not os.path.exists(run_dir):
        os.makedirs(run_dir)

    print("Generating graphs...")
    test_cases = set(result["test_case"] for result in results)
    for test_case in test_cases:
        case_results = [r for r in results if r["test_case"] == test_case and r.get("latency") is not None]
        for result in case_results:
            if "request_id" not in result:
                print(f"Missing 'request_id' in result: {result}")  # Debug output
            if "latency" not in result:
                print(f"Missing 'latency' in result: {result}")
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
        payload = {"message": "Test after network failure", "sender": "Tester"}
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
            payload = {"message": "Test after node failure", "sender": "Tester"}
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

