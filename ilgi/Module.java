package ilgi;

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

    public void broadcast(String s) {

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