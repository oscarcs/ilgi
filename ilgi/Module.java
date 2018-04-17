package ilgi;

import java.util.logging.Logger;

public abstract class Module implements Runnable {
    
    protected Thread runner;
    protected String name;
    protected Logger logger;
    protected int modulePort;
    protected int hostPort;

    public Module(int srcPort, int destPort) {
        runner = new Thread(this);
        runner.start();

        name = this.getClass().getName();

        logger = Logger.getLogger(name);
        logger.info("Creating instance of module '" + name + "'.");

        this.modulePort = modulePort;
        this.hostPort = hostPort;
    }

    public void broadcast(String s) {
        try {
            Socket s = new Socket(null, hostPort);
            DataOutputStream os = new DataOutputStream(s.getOutputStream());
            os.writeUTF(s);
        }
        catch (Exception e) {
            
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