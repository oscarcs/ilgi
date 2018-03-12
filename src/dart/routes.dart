import 'data_store.dart';
import 'dart:convert';

class Route {
    String path;

}

Map<String, Function> routes = {};

void setupRoutes() {
    addRoute('/entries/list', (Route route, String data) {
        String str = '[';
        for (Entry entry in getEntries()) {
            str += '{"ID":${entry.ID},"title":"${entry.title}"},';
        }
        str = str.substring(0, str.length - 1); // remove trailing ','.
        str += ']';
        return str;
    });

    addRoute('/entries/create', (Route route, String data) {
        var newEntry = JSON.decode(data);
        addEntry(newEntry['title']);
        return '';
    });

    addRoute('/entries/get/:id', (Route route, String data) {
        return '';
    });
}

void addRoute(String path, String handler(Route route, String data)) {
    routes[path] = handler;
}

Function getRoute(String path) {
    return routes[path];
}

Route parseRoute(String path) {
    Route r = new Route();
    
    for 
    segments = path.split('/');


    return r;
}

bool validRoute(Route route) {
    return routes.containsKey(route.path);
}