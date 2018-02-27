import 'data_store.dart';

Map<String, Function> routes = {};

void setupRoutes() {
    addRoute('/entries/list', () {
        String str = '[';
        for (Entry entry in entries) {
            str += '{"ID":${entry.ID},"title":"${entry.title}"},';
        }
        str = str.substring(0, str.length - 1);
        str += ']';
        return str;
    });
}

void addRoute(String path, String handler()) {
    routes[path] = handler;
}

Function getRoute(String path) {
    return routes[path];
}

bool validRoute(String path) {
    return routes.containsKey(path);
}