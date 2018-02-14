import 'dart:io';
import 'dart:async';

Future main() async {
    var server = await HttpServer.bind(
        InternetAddress.LOOPBACK_IP_V4,
        8080,
    );

    print('Listening on localhost:${server.port}');

    await for (HttpRequest request in server) {
        request.response
        ..write('Hello, world!')
        ..close();
    }
}