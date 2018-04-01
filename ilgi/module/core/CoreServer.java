package ilgi.module.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.List;

import ilgi.module.core.NanoHTTPD.Response;
import ilgi.module.core.NanoHTTPD.Response.Status;

public class CoreServer extends NanoHTTPD {

    private boolean loggingOn = true;
    private String base = System.getProperty("user.dir");

    public CoreServer() {
        super(8080);
    }

    @Override
    public Response serve(IHTTPSession session) {

        Response r = null;
        Map<String, String> headers = session.getHeaders();
        Map<String, List<String>> parms = session.getParameters();
        String uri = session.getUri();
        Method method = session.getMethod();

        if (loggingOn) {
            System.out.println(method + ": '" + uri + "'");
        }

        // Check to see if the request is a CORS request:
        if (Method.OPTIONS.equals(method)) {
            r = newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT, "");
        }
        // Otherwise, do a normal response:
        else {
            // Ensure clients can't escape the sandbox:
            if (uri.contains("../")) {
                return forbiddenResponse("Forbidden.");
            }

            if (canServeUri(uri)) {
                File file = new File(base, uri);
                String mime = getMimeTypeForFile(uri); 
                r = fileResponse(uri, headers, file, mime);
            }
            else {
                return notFoundResponse("File not found.");
            }
        }

        return r != null ? r : notFoundResponse("Not found.");
    }

    private Response fileResponse(String uri, Map<String, String> headers, File file, String mime) {
        Response r = null;

        try {
            String etag = Integer.toHexString(
                (file.getAbsolutePath() + file.lastModified() + "" + file.length()).hashCode()
            );

            long fileLen = file.length();

            r = newFixedLengthResponse(Status.OK, mime, new FileInputStream(file), (int) fileLen);
            r.addHeader("Content-Length", "" + fileLen);
            r.addHeader("ETag", etag);
        }
        catch (IOException e) {
            return notFoundResponse("File not found.");
        }

        return r != null ? r : notFoundResponse("Not found.");
    }

    private Response notFoundResponse(String msg) {
        return newFixedLengthResponse(Status.NOT_FOUND, MIME_PLAINTEXT, msg);
    }

    private Response forbiddenResponse(String msg) {
        Response r = newFixedLengthResponse(Status.FORBIDDEN, MIME_PLAINTEXT, msg);
        r.addHeader("Content-Type", "text/html; charset=utf-8");
        return r;
    } 

    private boolean canServeUri(String uri) {
        boolean canServeUri = false;

        File f = new File(base, uri);
        canServeUri = f.exists();

        return canServeUri;
    }
}