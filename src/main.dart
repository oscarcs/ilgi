import 'dart:io';
import 'dart:async';

import 'dart/http.dart';

Future main() async {
    var server = await HttpServer.bind(
        InternetAddress.LOOPBACK_IP_V4,
        8080,
    );

    // Create data directory relative to launch location.
    Directory dataDir = new Directory('./data');
    dataDir.exists().then((exists) {
        if (!exists) {
            dataDir.createSync();
            print('Creating data directory.');
        }
    });

    print('Listening on localhost:${server.port}');

    await for (HttpRequest request in server) {
        handleRequest(request);
    }
}