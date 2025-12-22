package com.example.pokepmdex;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class NetUtils {
    public static String getURLText(String url) throws IOException {
        URL website = new URL(url);
        HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        StringBuilder response = new StringBuilder();
        String line;

        // Concatenamos la respuesta de la llamada
        while((line = reader.readLine()) != null) {
            response.append(line);
        }

        reader.close();

        return response.toString();
    }

    public static Bitmap getURLBitmap(String url) throws IOException {
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        return BitmapFactory.decodeStream(connection.getInputStream());
    }
}
