import 'dart:io';
import 'dart:async';

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
    response.statusCode = HttpStatus.OK;

    response
        ..writeln('lol')
        ..close();
}