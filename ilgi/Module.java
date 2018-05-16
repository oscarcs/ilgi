package ilgi;

import java.util.logging.Logger;

import java.io.IOException;
import java.io.Writer;
import java.io.Reader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.net.Socket;
import java.net.ConnectException;

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

        this.serverPort = 10789;
    }

    protected void connect() {
        
        // Try 3 times to connect.
        for (int attempts = 3; attempts > 0; attempts--) {
            try {
                Socket socket = new Socket("localhost", serverPort);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()
                ));
                
                // Send the name of this module. If that was successful, prevent connection retries.
                out.println(name);
                attempts = 0;

                handleConnection(in, out);

                socket.close();
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

    protected void handleConnection(BufferedReader in, PrintWriter out) throws Exception {

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