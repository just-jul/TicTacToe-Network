# 🎮 TicTacToe-Network

A two-player **Tic-Tac-Toe** game played over a network using Java sockets. One player runs the server, the other connects as a client - and the game is managed entirely server-side.

---

## 📓 How It Works

The server listens for exactly two client connections. Once both players are connected, the game begins. The server tracks the board state, validates moves, enforces turn order, detects wins and draws, and broadcasts updates to both clients.

---

## 🚀 Getting Started

**Prerequisites:**
- Java 8 or higher
- Both players on the same local network (or localhost for testing)

**Running the Game**
Compile all source files and run App:
```
javac src/*.java
java -cp src App
```
A window will open. Click Start, enter your player name, and the app handles the rest:

The first player to click Start automatically starts the server on port 12345 and connects as Player X.
The second player runs the same app on another machine (or the same machine), clicks Start, and connects as Player O.
Once both players are connected, the game begins.

---

## 🔨Built With
- Java with Swing (javax.swing) — graphical game window and board UI
- TCP Sockets (java.net) — client-server communication
- Multithreading — each client is handled by a dedicated ClientHandler thread; UI updates run on the Swing Event Dispatch Thread via SwingUtilities.invokeLater
