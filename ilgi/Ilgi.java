package ilgi;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URI;
import java.net.MalformedURLException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Ilgi {
    
    /**
     * Entry point of the program.
     */
    public static void main(String[] args) {
        new Ilgi();
    }

    private String executionDir = System.getProperty("user.dir");

    public Ilgi() {
        System.out.println("Starting Ilgi...");
        searchClassesInFolder("/bin");
    }

    /**
     * Search the given directory, relative to the execution directory, for .class modules
     * in a subdirectory with the correct package (ilgi.module.*). 
     */
    public void searchClassesInFolder(String dir) {
        System.out.println("Searching for modules in '" + executionDir + dir + "'.");

        Path path = Paths.get(executionDir + dir);
        
        if (Files.isDirectory(path)) {
            // Check the appropriate package:
            path = Paths.get(executionDir + dir + "/ilgi/module");
            
            if (Files.isDirectory(path)) {
                
                // Get the modules and load them:
                String[] dirs = path.toFile().list();
                URL[] urls = new URL[dirs.length];
                String[] classes = new String[dirs.length];
                
                for (int i = 0; i < dirs.length; i++) {
                    String className = dirs[i].substring(0, 1).toUpperCase() + dirs[i].substring(1);

                    // Check if the .class file exists
                    File f = new File(path.toFile(), dirs[i] + "/" + className + ".class"); 
                    if (f.exists()) {
                        classes[i] = "ilgi.module." + dirs[i] + "." + className;
                        
                        try {
                            urls[i] = f.toURI().toURL();
                        }
                        catch (MalformedURLException e) { }
                    }
                    else {
                        System.out.println(".class file for module '" + className + "' not found.");
                        classes[i] = null;
                    }
                }

                // Load the classes:
                URLClassLoader classLoader = new URLClassLoader(urls);
                for (int i = 0; i < classes.length; i++) {
                    try {
                        Class<?> c = classLoader.loadClass(classes[i]);
                        Constructor<?> constructor = c.getConstructor();  
                        Object obj = constructor.newInstance();   
                    }
                    catch (Exception e) {
                        System.out.println("Failed to create instance of '" + classes[i] + "'.");
                    }
                }
            }
        }
    }
}