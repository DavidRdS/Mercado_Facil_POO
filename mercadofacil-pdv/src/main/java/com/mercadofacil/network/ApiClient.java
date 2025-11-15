package com.mercadofacil.network;

import com.google.gson.Gson;
import com.mercadofacil.model.Venda;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ApiClient {

    private static final Gson gson = new Gson();

    public static String get(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        try (Scanner sc = new Scanner(url.openStream())) {
            return sc.useDelimiter("\\A").hasNext() ? sc.next() : "";
        }
    }

    public static boolean postVenda(String urlStr, Venda venda) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);

        String json = gson.toJson(venda);
        byte[] bytes = json.getBytes("UTF-8");

        conn.setFixedLengthStreamingMode(bytes.length);
        conn.connect();
        try (OutputStream os = conn.getOutputStream()) {
            os.write(bytes);
        }

        int code = conn.getResponseCode();
        return code == 201 || code == 200;
    }
}
