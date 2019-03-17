package com.example.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Bitmap bitmap;
    private ImageView imageView;
    private static int REQUEST_EXTERNAL_STRONGE = 1;
    private SDFileHelper a = new SDFileHelper();
    private Downloader downloader;
    private String Dirpath;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            textView.setText("你好呀");
            switch (msg.what) {
                case 0x001:
                    imageView.setImageBitmap(bitmap);
                    try {
                        a.saveFileToSD("EC/question/5", "img.jpg", bitmap.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 0x002:
                    imageView.setImageBitmap(null);
                    break;
                default:
                    break;
            }
        }

        ;
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//根据请求是否通过的返回码进行判断，然后进一步运行程序
       /* if (grantResults.length > 0 && requestCode == REQUEST_EXTERNAL_STRONGE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        }*/

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);
        findViewById(R.id.send_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        try {
                            byte[] data = GetData.getImage("http://10.0.2.2:8000/resources/question-12-Choice-1_1_2.jpg");
                            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

                        } catch (
                                Exception e) {
                            e.printStackTrace();
                        }

                        handler.sendEmptyMessage(0x001);

                    }
                }.start();
                String filepath = "";
                try {
                    Dirpath = Environment.getExternalStorageDirectory().getCanonicalPath() + "/EC/question";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                filepath = Dirpath + "/16.zip";
                DownLoadFile downLoadFile = new DownLoadFile(MainActivity.this, "http://10.0.2.2:8000/question/16", filepath);
                downLoadFile.downLoad();
                try {
                    ZipUtils.UnZipFolder(filepath, Dirpath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
               /* new Thread(){
                    public void run(){
                        try{
                            downloader=new Downloader("http://10.0.2.2:8000/question/14","EC/question","14.zip");
                            downloader.download();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(0x002);
                    }
                }.start();

            }
        });
            */
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STRONGE);
                }//REQUEST_EXTERNAL_STRONGE是自定义个的一个对应码，用来验证请求是否通过
                else {

                    try {
                        a.saveFileToSD("AC/chioce", "2.txt", "123456");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        textView.setText(a.readstrFromSD("EC/quetion", "2.txt"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                //textView = (TextView) findViewById(R.id.response_data);
            }


        });
    }
}