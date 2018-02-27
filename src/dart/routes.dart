import 'data_store.dart';
import 'dart:convert';

Map<String, Function> routes = {};

void setupRoutes() {
    addRoute('/entries/list', (String data) {
        String str = '[';
        for (Entry entry in entries) {
            str += '{"ID":${entry.ID},"title":"${entry.title}"},';
        }
        str = str.substring(0, str.length - 1); // remove trailing ','.
        str += ']';
        return str;
    });

    addRoute('/entries/create', (String data) {
        var newEntry = JSON.decode(data);
        addEntry(newEntry['title']);
        return '';
    });
}

void addRoute(String path, String handler(String data)) {
    routes[path] = handler;
}

Function getRoute(String path) {
    return routes[path];
}

bool validRoute(String path) {
    return routes.containsKey(path);
}