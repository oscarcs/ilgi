package ilgi.core;

import ilgi.Module;
import java.io.IOException;

public class Core extends Module {

    public Core() {
    }

    public void run() {
        startServer(CoreServer.class);
    }

    public static <T extends NanoHTTPD> void startServer(Class<T> serverClass) {
        try {
            NanoHTTPD server = serverClass.newInstance();

            try {
                server.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
            }
            catch (IOException ioe) {
                System.err.println("Couldn't start server:\n" + ioe);
                System.exit(-1);
            }

            System.out.println("Server started, press enter to stop.");

            try {
                System.in.read();
            } 
            catch (Throwable ignored) {
                
            }

            server.stop();
            System.out.println("Server stopped.");
        } 
        catch (Exception e) {

        }
    }
}