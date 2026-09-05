TCP Part2
# TCP Authentication Server & Client  
Mohammed Tahar Souida – 041200233  


---

## 📘 Overview

This project implements a **TCP-based authentication system** using Java sockets.  
It includes two programs:

- **AuthServer** – handles client connections, login attempts, and messaging  
- **AuthClient** – connects to the server, sends login credentials, and receives messages

The server validates a username and password, allows up to **3 attempts**, and then either authenticates the client or disconnects it.

---

## 🧠 Features

### 🔐 Authentication
- Client sends username + password  
- Server checks credentials (`admin` / `1234`)  
- Up to **3 login attempts**  
- Server responds with:
  - `LOGIN_SUCCESS`
  - `LOGIN_FAILED`
  - `DISCONNECT` (after 3 failed attempts)

### 💬 Messaging Phase
After successful login:
- Server sends messages typed by the professor/user  
- Client prints messages from the server  
- Special commands:
  - `goodbye` → closes client connection  
  - `shutdown` → shuts down the server completely  

---

## 🏗️ Technologies Used

- **Java**
- **TCP Sockets**
- `ServerSocket`
- `Socket`
- `DataInputStream`
- `DataOutputStream`

This project is **NOT UDP**.  
UDP would use `DatagramSocket` and `DatagramPacket`.

---

## 📂 Project Structure
Auth/
├── AuthServer.java
└── AuthClient.java
