package ilgi;

import ilgi.core.Core;

public class Ilgi {
    
    /**
     * Entry point of the program.
     */
    public static void main(String[] args) {
        new Ilgi();
    }

    private String executionDir = System.getProperty("user.dir");

    public Ilgi() {
        new Core();  
    }


}