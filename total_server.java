import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;

public class total_server {
    static int PORT = 5050;
    static int REAP = 1;
    public static void main(String argv[]) throws Exception {
        ServerSocket server = new ServerSocket(PORT);
        for(int i=0; i<REAP; i++) {
            System.out.printf("Listening...\n");
            Socket socket = server.accept();
            Thread thread = new Network(socket);
            thread.start();
            thread.join();
        }
        server.close();
    }
    private static class Network extends Thread {
        Socket socket = null;
        private Network(Socket socket) {
            this.socket = socket;
        }
        public void send(String message) {
            String HOST = "YOUR_IP_ADDRESS";
            int    PORT = 8080;
            try {
                Socket socket = new Socket(HOST, PORT);
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.write(message.getBytes()); // Sending data as bytes
                out.close();
                socket.close();
            } catch(Exception error) {
                System.out.print(error + "\n");
            }
        }
        public void run() {
            try {
                BufferedReader conn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while(true) {
                    String data = (String)conn.readLine();
                    if(data==null)
                        break;
                    System.out.print("recieve data : " + data + "\n");
                }
                // Insert your code here!
                send("Hello World!");
                socket.close();
            } catch(Exception error) {
                System.out.print(error + "\n");
            }
        }
    }
}
