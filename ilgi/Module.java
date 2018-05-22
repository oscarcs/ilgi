package ilgi;

import java.util.logging.Logger;

import java.io.IOException;
import java.io.Writer;
import java.io.Reader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.Socket;
import java.net.ConnectException;

public abstract class Module implements Runnable {
    
    protected Thread runner;
    protected String name;
    protected Logger logger;
    protected int modulePort;
    protected int serverPort;

    private volatile String message = "";
    private volatile Boolean newMessage = false;

    public Module() {
        runner = new Thread(this);
        runner.start();

        name = this.getClass().getName();

        logger = Logger.getLogger(name);
        logger.info("Creating instance of module '" + name + "'.");

        this.serverPort = 10789;
    }

    protected void connect() {
        
        // Try 3 times to connect.
        for (int attempts = 3; attempts > 0; attempts--) {
            try {
                Socket socket = new Socket("localhost", serverPort);
                attempts = 0;

                handleConnection(socket);
            }
            catch (ConnectException e) {
                // Ignore the 'connection refused' message and try again.
                // There may be other relevant exception messages that need adding here.
                if (e.getMessage().equals("Connection refused (Connection refused)")) {
                    if (attempts - 1 > 0) {
                        // Print the number of attempts remaining after this one.
                        logger.warning("Connection failed (" + name + "). " + 
                            (attempts - 1) + " attempts remaining.");
                    }
                    else {
                        logger.severe("Connection failed (" + name + "). ");
                    }
                }
                else {
                    logger.severe(e.toString());
                    break;
                }
            }
            catch (Exception e) {
                logger.severe("Socket on module '" + name + "': " + e.toString());
            }
        }
    }

    /**
     * Handle the connection to the Ilgi instance; this is a default implementation that can
     * be overriden.
     */
    protected void handleConnection(Socket socket) {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()
            ));
            
            new Thread(new RequestHandler(in, out)).start();
        }
        catch (IOException e) {

        }

    }

    /**
     * Send a message to the Ilgi instance.
     */
    protected void sendMessage(String msg) {
        message = msg; 
        newMessage = true;
    }

    protected class RequestHandler implements Runnable {
        private BufferedReader in;
        private PrintWriter out;

        public RequestHandler(BufferedReader in, PrintWriter out) {
            this.in = in;
            this.out = out;
        }
        
        public void run() {
            while (true) {
                if (newMessage) {
                    out.println(message);
                    newMessage = false;
                }
            }
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