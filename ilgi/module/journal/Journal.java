package ilgi.module.journal;

import ilgi.Module;

import java.net.Socket;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Journal extends Module {
    
    public void run() {
        startListening();
    }

    public void stop() {

    }

    private void startListening() {
        try (
            Socket socket = new Socket("localhost", 10789);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );
            BufferedReader stdIn = new BufferedReader(
                new InputStreamReader(System.in)
            );
        )
        {
            String userInput;
            while (true) {
                userInput = stdIn.readLine();
                out.println(userInput);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}