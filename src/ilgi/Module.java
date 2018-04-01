package ilgi;

public abstract class Module implements Runnable {
    
    private Thread runner;

    public Module() {
        runner = new Thread(this);
        runner.start();
    }

    /**
     * Entry point of the module's thread.
     */
    public abstract void run();
}