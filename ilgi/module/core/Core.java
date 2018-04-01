package ilgi.module.core;

import ilgi.Module;
import java.io.IOException;

public class Core extends Module {

    private NanoHTTPD server; 

    public Core() {
    }

    public void run() {
        startServer(CoreServer.class);
    }

    public void stop() {
        server.stop();
        System.out.println("[CORE] Server stopped.");
    }

    public <T extends NanoHTTPD> void startServer(Class<T> serverClass) {
        try {
            server = serverClass.newInstance();

            try {
                server.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
            }
            catch (IOException ioe) {
                System.err.println("[CORE] Couldn't start server:\n" + ioe);
            }

            System.out.println("[CORE] Server started.");
        } 
        catch (Exception e) {

        }
    }
}