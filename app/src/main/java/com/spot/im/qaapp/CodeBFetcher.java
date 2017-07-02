package com.spot.im.qaapp;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by nissimpardo on 01/07/2017.
 */

public class CodeBFetcher extends AsyncTask <String, Void, String> {

    Listener mListener;



    public interface Listener {
        void onCodeB(String codeB);
    }

    private void setListener(Listener listener) {
        mListener = listener;
    }

    public static void fetch(String url, Listener listener) {
        CodeBFetcher fetcher = new CodeBFetcher();
        fetcher.setListener(listener);
        fetcher.execute(url);
    }

    @Override
    protected String doInBackground(String... params) {
        StringBuilder stringBuilder = null;
        try {
            stringBuilder = new StringBuilder();
            URL url = new URL(params[0]);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            InputStream inputStream = httpConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            char[] buffer = new char[1024];
            int charsRead;
            while ((charsRead = reader.read(buffer)) != -1) {
                stringBuilder.append(buffer, 0, charsRead);
            }
            reader.close();
            inputStream.close();
            httpConnection.disconnect();

        } catch (MalformedURLException e) {
            return null;
        } catch (ProtocolException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
        return stringBuilder.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        mListener.onCodeB(s);
    }
}
