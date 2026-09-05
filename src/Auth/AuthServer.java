/**
* Student Name: Mohammed Tahar Souida
* Student Number:  041200233
* Course: Course name - cst 8108 networking Labs
* Program/Level: CET-CS - Level 2
* Lab Professor: Prof name - mike -shavit
*/
package Auth;

/**
 * 
 */
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class AuthServer {

    public static final int PORT = 18991;
    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "1234";

    public static void main(String[] args) {

        System.out.println("Server starting on port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT);
             BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in))) {

            boolean serverRunning = true;

            while (serverRunning) {

                System.out.println("Waiting for client...");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected.");

                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                boolean authenticated = false;
                int attempts = 0;

                while (attempts < 3 && !authenticated) {

                    String username = in.readUTF();
                    String password = in.readUTF();

                    if (username.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD)) {
                        out.writeUTF("LOGIN_SUCCESS");
                        authenticated = true;
                        System.out.println("Client authenticated.");
                    } else {
                        attempts++;
                        if (attempts < 3) {
                            out.writeUTF("LOGIN_FAILED");
                        } else {
                            out.writeUTF("DISCONNECT");
                            System.out.println("Client failed authentication 3 times.");
                            socket.close();
                        }
                    }
                }

                if (!authenticated) {
                    continue;
                }

                // Messaging phase
                while (true) {

                    System.out.print("Enter message to send: ");
                    String msg = keyboard.readLine();

                    out.writeUTF(msg);

                    if (msg.equalsIgnoreCase("goodbye")) {
                        System.out.println("Closing client connection.");
                        socket.close();
                        break;
                    }

                    if (msg.equalsIgnoreCase("shutdown")) {
                        System.out.println("Shutting down server.");
                        socket.close();
                        serverRunning = false;
                        break;
                    }
                }
            }

            System.out.println("Server stopped.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}