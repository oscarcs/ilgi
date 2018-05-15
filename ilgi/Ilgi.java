package ilgi;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URI;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketException;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ilgi {
    
    /**
     * Entry point of the program.
     */
    public static void main(String[] args) {
        new Ilgi();
    }

    private String executionDir;
    private ArrayList<Module> localModules;
    private Logger logger;

    public Ilgi() {
        System.out.println("\nStarting Ilgi...\n");

        executionDir = System.getProperty("user.dir");
        localModules = new ArrayList<Module>();
        logger = Logger.getLogger(this.getClass().getName());

        // @@TODO: Load settings:

        // Look for modules:
        searchClassesInFolder("/bin");

        // Create and maintain a thread:
        runServer();

        stop();
    }

    private void runServer() {
        ExecutorService threadPool = Executors.newFixedThreadPool(10); 

        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(10789);
            while (true) {
                Socket clientSocket;
                try {
                    clientSocket = serverSocket.accept();
                    threadPool.execute(new RequestHandler(clientSocket, logger));
                } 
                catch (Exception e) {
                    logger.severe(e.toString()); 
                }
            }
        }
        catch (Exception e) {
            System.out.println("Can't start server.");
        }
    }

    class RequestHandler implements Runnable {

        private Socket socket;
        private Logger logger;

        public RequestHandler(Socket socket, Logger logger) {
            this.socket = socket;
            this.logger = logger;
        }

        public void run() {
            try (
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(this.socket.getInputStream()), 1000
                );
                PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
            ) {

                String inputLine = "";
                while (true) {
                    if (socket.isClosed()) { }

                    inputLine = in.readLine();
                    if (inputLine == null) break;

                    System.out.println("message: " + inputLine);
                }
            }
            catch (SocketException e) {
                if (e.getMessage().equals("Connection reset")) {
                    logger.info("Connection closed.");
                }
                else {
                    logger.severe(e.toString());
                }
            }
            catch (Exception e) {
                logger.severe(e.toString());
            }
        }
    }

    /**
     * Stop the modules that are currently running.
     */
    public void stop() {
        for (Module m : localModules) {
            m.stop();
        }

        System.exit(0);
    }

    /**
     * Search the given directory, relative to the execution directory, for .class modules
     * in a subdirectory with the correct package (ilgi.module.*). 
     */
    protected void searchClassesInFolder(String dir) {
        logger.info("Searching for modules in '" + executionDir + dir + "'.");

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
                        logger.severe(".class file for module '" + className + "' not found.");
                        classes[i] = null;
                    }
                }

                // Load the classes:
                URLClassLoader classLoader = new URLClassLoader(urls);
                for (int i = 0; i < classes.length; i++) {
                    try {
                        Class<?> c = classLoader.loadClass(classes[i]);
                        Constructor<?> constructor = c.getConstructor();  
                        
                        Module module = (Module) constructor.newInstance(); 
                        localModules.add(module);
                    }
                    catch (Exception e) {
                        logger.severe("Failed to create instance of '" + classes[i] + "'.");
                    }
                }
            }
        }
    }
}