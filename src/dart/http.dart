import 'dart:io';

import 'content_type.dart';

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

void handleGet(HttpRequest request) {
    final HttpResponse response = request.response;
    
    String path = processFilePath(request.uri.path);
    File file = new File(path);

    print('GET: ' + path);

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

void handlePost(HttpRequest request) {
    final HttpResponse response = request.response;

    String path = request.uri.path;
    print('POST: ' + path);


}

String processFilePath(String path) {
    if (path == '/') {
        path = '/index.html';
    }
    path = root.path + path; 
    return path;
}