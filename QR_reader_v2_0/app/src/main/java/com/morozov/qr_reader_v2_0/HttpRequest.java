package com.morozov.qr_reader_v2_0;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest extends AsyncTask<Void, Void, Void> {
    private TextView textView_result;
    private HttpURLConnection connection = null;
    private String query = "http://194.58.104.228/polit/api.php?page=checkin_ticket&moderator=12&ticket=";
    private String MESS_STR = "Empty now";

    private final String RESPONSE = "response";
    private final String ERROR = "error";

    private String RESULT_MESSAGE = "NONE RESULT";

    public HttpRequest(TextView textView_result_, String ticket_){
        textView_result = textView_result_;
        query += ticket_;
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

    public void jsonParse(String mess) throws JSONException {
        if (mess.equals(RESPONSE)){
            JSONObject jsonObject = new JSONObject(MESS_STR);
            RESULT_MESSAGE = ("Response is: " + jsonObject.get(RESPONSE) + '\n');

            JSONObject jsonObject2 = jsonObject.getJSONObject("content");
            RESULT_MESSAGE += ("Name: " + jsonObject2.get("name") + '\n');
            RESULT_MESSAGE += ("Age: " + jsonObject2.get("age") + '\n');
            RESULT_MESSAGE += ("Event: " + jsonObject2.get("event") + '\n');
            RESULT_MESSAGE += ("Phone number: " + jsonObject2.get("mob_phone"));
        } else if (mess.equals(ERROR)){
            JSONObject jsonObject = new JSONObject(MESS_STR);

            if (jsonObject.get(ERROR).equals("3")){
                RESULT_MESSAGE = ("No such ticket.");
            } else {
                RESULT_MESSAGE = ("Internal error!");
            }
        } else {
            RESULT_MESSAGE = "Wrong input message from server.";
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            makeConnection();
            RESULT_MESSAGE = MESS_STR;
            jsonParse(RESPONSE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            try {
                jsonParse(ERROR);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        textView_result.setText(RESULT_MESSAGE);
    }
}
