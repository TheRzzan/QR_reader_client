package com.morozov.qr_reader_v2_0;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest extends AsyncTask<Void, Void, Void> {
    private TextView textView_result;
    private HttpURLConnection connection = null;
    private String query = "http://194.58.104.228/polit/api.php?page=checkin_ticket&moderator=12&ticket=TmVLbmZCRy9KQlBrOGROSGFSbEFTZzhWN1FkVFZOVUVJSG54NVZlOGVqTU9QSGtCeVZHZ0htaEl6UUljNDRRaVF1Q0cxQ2o3VytyWm1MT1VFM1NvWlRHMDd4azJ3STNmbnhWU01KTXJ3R0hGbXBEUk1HMW4wYXNqalc4bGVWMDNlVklqTUVCUzVFOEV0eklvR3dWMHB5";
    private String MESS_STR = "Empty now";

    public HttpRequest(TextView textView_result_){
        textView_result = textView_result_;
    }

    private void makeConnection() throws IOException {
        connection = (HttpURLConnection) new URL(query).openConnection();

        connection.setRequestMethod("GET");
        connection.setUseCaches(false);
        connection.setConnectTimeout(250);
        connection.setReadTimeout(250);

        connection.connect();

        StringBuilder sb = new StringBuilder();

        if (HttpURLConnection.HTTP_OK == connection.getResponseCode()){
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

            String line;
            while ((line = in.readLine()) != null){
                sb.append(line);
                sb.append('\n');
            }

            MESS_STR = sb.toString();
        } else {
            MESS_STR = "fail: " + connection.getResponseCode() + ", " + connection.getRequestMethod();
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            makeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        textView_result.setText(MESS_STR);
    }
}
