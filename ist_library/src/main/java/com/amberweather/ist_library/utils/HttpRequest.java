package com.amberweather.ist_library.utils;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by jsen on 2016/4/18.
 */
public class HttpRequest {
    public void request(final String url1, final HttpRequestListener listener) {
        final String url = url1.replace(" ", "");
        Log.d("wzw", "URL :" + url);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpGet httpRequest = new HttpGet(url);
                HttpClient httpclient = new DefaultHttpClient();
                try {
                    HttpResponse httpResponse = httpclient.execute(httpRequest);
                    if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                        HttpEntity httpEntity = httpResponse.getEntity();
                        InputStream is = httpEntity.getContent();
                        listener.onComplete(inputStream2String(is));
                        is.close();
                    } else listener.onError();

                } catch (Exception e) {
                    e.printStackTrace();
                    listener.onError();
                }
            }
        }).start();
    }

    String inputStream2String(InputStream is) {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "---";
        }
    }

    public interface HttpRequestListener {
        void onComplete(String data);

        void onError();
    }

}
