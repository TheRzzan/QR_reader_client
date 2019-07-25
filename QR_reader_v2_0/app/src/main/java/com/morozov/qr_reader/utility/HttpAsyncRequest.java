package com.morozov.qr_reader.utility;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.morozov.qr_reader.interfaces.DataLoader;
import com.morozov.qr_reader.interfaces.OnDataLoadedListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpAsyncRequest extends AsyncTask<String, Void, String> implements DataLoader {
    private Ticket ticket = new Ticket();
    private WeakReference<OnDataLoadedListener> listener;

    @Override
    protected String doInBackground(String... strings) {
        if (strings.length < 1)
            return null;

        URL url = null;
        try {
            url = new URL("http://194.58.104.228/polit/api.php?page=checkin_ticket&moderator=12&ticket=" + strings[0]);
        } catch (java.net.MalformedURLException e) {
            e.printStackTrace();
        }

        if (url == null)
            return null;

        return readJsonFromHttp(url);
    }

//    private String readJsonFromUrl(URL url) {
//        StringBuilder sb = new StringBuilder();
//        try {
//            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
//            String ls = System.getProperty("line.separator");
//            String tempStr;
//            while ((tempStr = in.readLine()) != null) {
//                sb.append(tempStr).append(ls);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return sb.toString();
//    }

    private String readJsonFromHttp(URL url) {
        StringBuilder sb = new StringBuilder();
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(250);
            connection.setReadTimeout(250);

            connection.connect();

            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()){
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

                String line;
                while ((line = in.readLine()) != null){
                    sb.append(line);
                    sb.append('\n');
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
        }

        return sb.toString();
    }

    @Override
    protected void onPostExecute(String json) {
        super.onPostExecute(json);

        if (json == null) {
            ticket = null;
            return;
        }

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(json);

            if (jsonObject.has("content")) {
                Gson gson = new GsonBuilder().create();
                Content content = gson.fromJson(json, Content.class);
                ticket = content.getContent();
            } else {
                ticket = null;
            }

            if (listener != null) {
                OnDataLoadedListener l = listener.get();
                if (l != null)
                    l.onDataLoaded(ticket);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadData(String token, OnDataLoadedListener listener) {
        this.listener = new WeakReference<>(listener);
        this.execute(token);
    }
}
