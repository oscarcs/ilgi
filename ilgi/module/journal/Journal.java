package ilgi.module.journal;

import ilgi.Module;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Journal extends Module {
    
    private volatile String lastMessage = "";
    private volatile String message = "";

    public void run() {
        // Connect the remote socket.
        connect();

        // Handle input on the main thread.
        handleInput();
    }

    public void stop() {

    }

    protected void handleInput() {
        while (true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                message = br.readLine();
            } catch (Exception e) { }
        }
    }

    @Override 
    protected void handleConnection(BufferedReader in, PrintWriter out) {
        new Thread(new RequestHandler(in, out)).start();
    }

    class RequestHandler implements Runnable {
        private BufferedReader in;
        private PrintWriter out;

        public RequestHandler(BufferedReader in, PrintWriter out) {
            this.in = in;
            this.out = out;
        }
        
        public void run() {
            while (true) {
                if (!lastMessage.equals(message)) {
                    out.println(message);
                    lastMessage = message;
                }
            }
        }
    }
}