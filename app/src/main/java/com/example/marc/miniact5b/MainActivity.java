package com.example.marc.miniact5b;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import android.os.AsyncTask;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private ImageView iv;
    private TextView tv;
    private Button btn1;
    private Button btn2;
    private ConnectivityManager cM;
    private NetworkInfo nI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = findViewById(R.id.imageView);
        tv = findViewById(R.id.textView1);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);

        cM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        nI = cM.getActiveNetworkInfo();
        checkNetwork();

    }

    private void checkNetwork(){
        String text;
        if (nI != null) {
            if (nI.getType() == ConnectivityManager.TYPE_WIFI && nI.isConnected()) {
                text = "Wifi connected!";
            } else text = "Mobile connected!";
            btn1.setEnabled(true);
            btn2.setEnabled(true);
        } else {
            text = "No network operating!";
            btn1.setEnabled(false);
            btn2.setEnabled(false);
        }
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }

    class AsynTaskRunner extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {


            InputStream stream;
            HttpsURLConnection connection;

            String myurl = "https://www.google.es/";
            try {
                URL url = new URL(myurl);
                connection = (HttpsURLConnection) url.openConnection();
                connection.setReadTimeout(3000);
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                int response = connection.getResponseCode();
                if(response != HttpsURLConnection.HTTP_OK)
                    throw new IOException(("HTTP error code: " + response));
                stream = connection.getInputStream();

                return readStream(stream, 500);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tv.setText(s);
        }
    }

    public void onClickText(View v) {

        AsynTaskRunner runner = new AsynTaskRunner();
        runner.execute();
    }

    private String readStream(InputStream stream, int i) throws IOException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] rawBuffer = new char[i];
        int readSize;
        StringBuffer buffer = new StringBuffer();

        while(((readSize = reader.read(rawBuffer)) != -1) && i >0){
            if(readSize > 500) {
                readSize = i;
            }
            buffer.append(rawBuffer, 0, readSize);
            i -= readSize;
        }
        return  buffer.toString();
    }

    public void onClickImage(View v) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkNetwork();
    }
}
