package ilgi.module.journal;

import ilgi.Module;

public class Journal extends Module {
    
    public void run() {
        while (true) {
            System.out.println("Guten Abend!");
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) { } 
        }
    }

    public void stop() {

    }
}