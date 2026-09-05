/**
* Student Name: Mohammed Tahar Souida
* Student Number:  041200233
* Course: Course name - 8108 networking Labs
* Program/Level: CET-CS - Level 2
* Lab Professor: Prof name - MIke
*/
package Auth;

import java.io.*;
import java.net.Socket;

public class AuthClient {

    public static final String SERVER_IP = "localhost";
    public static final int PORT = 18991;

    public static void main(String[] args) {

        System.out.println("Connecting to server...");

        try (Socket socket = new Socket(SERVER_IP, PORT);
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in))) {

            boolean loggedIn = false;
            int attempts = 0;

            while (!loggedIn && attempts < 3) {

                System.out.print("Username: ");
                String username = keyboard.readLine();

                System.out.print("Password: ");
                String password = keyboard.readLine();

                out.writeUTF(username);
                out.writeUTF(password);

                String response = in.readUTF();

                if (response.equals("LOGIN_SUCCESS")) {
                    System.out.println("Login successful!");
                    loggedIn = true;
                } else if (response.equals("LOGIN_FAILED")) {
                    attempts++;
                    System.out.println("Login failed. Attempts left: " + (3 - attempts));
                } else if (response.equals("DISCONNECT")) {
                    System.out.println("Too many failed attempts. Disconnected.");
                    return;
                }
            }

            // Messaging phase
            while (true) {

                String msg = in.readUTF();
                System.out.println("Server: " + msg);

                if (msg.equalsIgnoreCase("goodbye") ||
                    msg.equalsIgnoreCase("shutdown")) {

                    System.out.println("Connection closed.");
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}