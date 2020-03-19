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

    public void onClickText(View v) {

    }

    public void onClickImage(View v) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkNetwork();


    }
}
