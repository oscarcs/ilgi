package ilgi;

public abstract class Module implements Runnable {
    
    private Thread runner;

    public Module() {
        runner = new Thread(this);
        runner.start();

        System.out.println("Created instance of module '" + this.getClass().getName() + "'");
    }

    /**
     * Entry point of the module's thread.
     */
    public abstract void run();
}