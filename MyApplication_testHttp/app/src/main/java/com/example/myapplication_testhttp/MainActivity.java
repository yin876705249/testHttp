package com.example.myapplication_testhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b=findViewById(R.id.button);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Http连接代码

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{

                            URL url = new URL("http://172.20.10.12/80");
                            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                            //connection.setRequestMethod("GET");
                            connection.setRequestMethod("POST");

                            connection.setDoOutput(true);//post请求改为TRUE
                            connection.setDoInput(true);

                            //connection.setUseCaches(false);
                            //connection.setInstanceFollowRedirects(true);
                            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
                            connection.setRequestProperty("Charset", "utf-8");
                            connection.setRequestProperty("Accept-Charset", "utf-8");

                            connection.setConnectTimeout(6000);
                            connection.setReadTimeout(6000);
                            InputStream in = connection.getInputStream();//服务端响应耗时操作
                            //OutputStream out = connection.getOutputStream();//客户端请求耗时操作，最终数据写入实在out.flush()中完成

                            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                            String data ="abcaa";
                            out.write(data.getBytes(StandardCharsets.UTF_8));
                            out.flush();
                            out.close();

                            BufferedReader reader = null;
                            reader = new BufferedReader(new InputStreamReader(in));
                            StringBuilder response = new StringBuilder();
                            String line = null;
                            while ((line = reader.readLine()) != null) {
                                response.append(line);
                             }

                        }
                         catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

            }
            ).start();




            }
        });



    }


}