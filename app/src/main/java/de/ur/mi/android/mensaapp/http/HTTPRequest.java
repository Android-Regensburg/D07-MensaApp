package de.ur.mi.android.mensaapp.http;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class HTTPRequest implements Runnable {

    private static final String LINE_BREAK_DELIMITER = "\n";

    private URL url;
    private HTTPRequestListener listener;

    private HTTPRequest(URL url, HTTPRequestListener listener) {
       this.url = url;
       this.listener = listener;
    }

    public static void get(URL url, HTTPRequestListener listener) {
        HTTPRequest request = new HTTPRequest(url, listener);
        Executors.newSingleThreadExecutor().submit(request);
    }

    @Override
    public void run() {
        try {
            HttpURLConnection connection = (HttpURLConnection) this.url.openConnection();
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            InputStreamReader inputReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader((inputReader));
            String result = bufferedReader.lines().collect(Collectors.joining(LINE_BREAK_DELIMITER));
            inputStream.close();
            connection.disconnect();
            listener.onResult(result);
        } catch (Exception e) {
            listener.onError();
        }
    }
}
