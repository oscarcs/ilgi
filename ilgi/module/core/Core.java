package ilgi.module.core;

import ilgi.Module;
import java.io.IOException;

public class Core extends Module {

    private NanoHTTPD server; 

    public void run() {
        startServer(CoreServer.class);

        connect();
        sendMessage("lol");
    }

    public void stop() {
        server.stop();
        logger.info("Server stopped.");
    }

    /**
     * Start the core webserver on a new thread.
     */
    public <T extends NanoHTTPD> void startServer(Class<T> serverClass) {
        try {
            server = serverClass.newInstance();

            try {
                server.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
            }
            catch (IOException ioe) {
                logger.severe("Couldn't start server:\n" + ioe);
            }

            logger.info("Server started.");
        } 
        catch (Exception e) {

        }
    }
}