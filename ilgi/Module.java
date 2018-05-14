package ilgi;

import java.net.Socket;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.Reader;
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
            Socket socket = new Socket("localhost", serverPort);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );
        )
        {
            handleConnection(in, out);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    protected void handleConnection(Reader in, Writer out) {
        
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