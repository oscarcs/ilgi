package ilgi.core;

import spark.Spark;

public class Core {
    public Core() {
        Spark.port(8080);
        Spark.get("/", (req, res) -> "Hello, World!");
        Spark.init();  
    }
}