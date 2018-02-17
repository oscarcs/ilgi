import 'dart:io';
import 'dart:async';

import 'dart/content_type.dart';

Directory root = new File(Platform.script.toFilePath()).parent;

Future main() async {
    var server = await HttpServer.bind(
        InternetAddress.LOOPBACK_IP_V4,
        8080,
    );

    print('Listening on localhost:${server.port}');

    await for (HttpRequest request in server) {
        handleRequest(request);
    }
}

void handleRequest(HttpRequest request) {
    if (request.method == 'GET') {
      handleGet(request);
    } 
    else {
        request.response
            ..statusCode = HttpStatus.METHOD_NOT_ALLOWED
            ..write('Unsupported request: ${request.method}.')
            ..close();
    }
}

void handleGet(HttpRequest request) {
    final response = request.response;
    
    String path = processPath(request.uri.path);
    File file = new File(path);

    print('GET: ' + path);

    file.exists().then((bool exists) {
        if (exists) {
            response.statusCode = HttpStatus.OK;
            response.headers.contentType = getContentType(getExtension(path));
            file.openRead().pipe(response);
        }
        else {
            response.statusCode = 404;
            response.close();
        }
    });
}

String processPath(String path) {
    if (path == '/') {
        path = "/index.html";
    }
    path = root.path + path; 
    return path;
}