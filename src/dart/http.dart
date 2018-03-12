import 'dart:io';
import 'dart:convert';
import 'dart:async';

import 'content_type.dart';
import 'routes.dart';

Directory root = new File(Platform.script.toFilePath()).parent;

void handleRequest(HttpRequest request) {
    if (request.method == 'GET') {
        handleGet(request);
    }
    else if (request.method == 'POST') {
        handlePost(request);
    } 
    else {
        request.response
            ..statusCode = HttpStatus.METHOD_NOT_ALLOWED
            ..write('Unsupported request: ${request.method}.')
            ..close();
    }
}

Future handleGet(HttpRequest request) async {
    final HttpResponse response = request.response;
    
    String path = request.uri.path;
    print('GET: ' + path);

    Route route = parseRoute(path);

    if (validRoute(route)) {
        String content = await request.transform(UTF8.decoder).join();

        var data = getRoute(path)(path, content);

        response.statusCode = HttpStatus.OK;
        response.headers.contentType = getContentType('json');
        response
            ..write(data)
            ..close();
    }
    else {
        path = processFilePath(path);
        File file = new File(path);

        file.exists().then((bool exists) {
            if (exists) {
                response.statusCode = HttpStatus.OK;
                response.headers.contentType = getContentType(getExtension(path));
                file.openRead().pipe(response);
            }
            // Redirect the browser to the root; let the client handle routing.
            else {
                path = processFilePath('/');
                file = new File(path);
                response.statusCode = HttpStatus.OK;
                response.headers.contentType = getContentType(getExtension(path));
                file.openRead().pipe(response);
            }
        });
    }
}

Future handlePost(HttpRequest request) async {
    final HttpResponse response = request.response;

    String path = request.uri.path;
    print('POST: ' + path);

    if (validRoute(path)) {
        String content = await request.transform(UTF8.decoder).join();
        
        var data = getRoute(path)(path, content);

        response.statusCode = HttpStatus.OK;
        response.headers.contentType = getContentType('json');
        response
            ..write(data)
            ..close();
    }

}

String processFilePath(String path) {
    if (path == '/') {
        path = '/index.html';
    }
    path = root.path + path; 
    return path;
}