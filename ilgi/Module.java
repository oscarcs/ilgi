package ilgi;

import java.net.Socket;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.logging.Logger;

public abstract class Module implements Runnable {
    
    protected Thread runner;
    protected String name;
    protected Logger logger;
    protected int modulePort;
    protected int serverPort;

    public Module() {
        runner = new Thread(this);
        runner.start();

        name = this.getClass().getName();

        logger = Logger.getLogger(name);
        logger.info("Creating instance of module '" + name + "'.");

        // this.modulePort = modulePort;
        this.serverPort = 10789;
    }

    protected void connect() {
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
            while (true) {
                out.println(name);
                // Wait for acknowledgement
                String response = in.readLine();

                Thread.sleep(1000);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Entry point of the module's thread.
     */
    public abstract void run();

    /**
     * Clean up when server is stopped.
     */
    public abstract void stop();
}