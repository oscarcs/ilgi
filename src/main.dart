import 'dart:io';
import 'dart:async';
import 'package:sqlite/sqlite.dart';

import 'dart/http.dart';

Directory root = new File(Platform.script.toFilePath()).parent;
Database database = new Database.inMemory();

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