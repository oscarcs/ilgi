import 'dart:io';

String getExtension(String path) {
    int index = path.lastIndexOf('.');
    if (index < 0 || index + 1 >= path.length) {
        return path;
    }
    
    return path.substring(index + 1).toLowerCase();
}

ContentType getContentType(String extension) {
    String mime = extensionMap[extension];
    String primaryType = mime.split('/')[0];
    String subType = mime.split('/')[1];
    return new ContentType(primaryType, subType);
}

Map<String, String> extensionMap = <String, String>{
    // Basic formats
    'html': 'text/html',
    'js': 'application/javascript',
    'json': 'application/json',
    'css': 'text/css',

    // Image formats
    'jpe': 'image/jpeg',
    'jpeg': 'image/jpeg',
    'jpg': 'image/jpeg',
    'png': 'image/png',
    'gif': 'image/gif',
    'ico': 'image/x-icon',
};