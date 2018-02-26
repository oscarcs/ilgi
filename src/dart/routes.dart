

Map<String, Function> routes = {};

void setupRoutes() {
    addRoute('/entries/get', () => '{ "1": "test", "2": "Hello, World!" }');
}

void addRoute(String path, Function handler) {
    routes[path] = handler;
}

Function getRoute(String path) {
    return routes[path];
}

bool validRoute(String path) {
    return routes.containsKey(path);
}