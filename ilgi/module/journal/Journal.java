package ilgi.module.journal;

import ilgi.Module;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Journal extends Module {

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
                String message = br.readLine();
                sendMessage(message);
            } catch (Exception e) { }
        }
    }
}