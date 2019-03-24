package com.example.utils;

import android.content.Context;

import com.example.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetData {

    private Context context;
    private Listener mListener;
    private String Loginresult;
    public GetData(){

    }
    public GetData(Context context){
        this.context=context;
    }
    public void setOnListener(Listener mListener) {
        this.mListener = mListener;
    }
    //接口
    public interface Listener {
        void result(String result);
    }

    //获取图片
    public static byte[] getImage(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置连接超时为5秒
        conn.setConnectTimeout(5000);
        // 设置请求类型为Get类型
        conn.setRequestMethod("GET");
        // 判断请求Url是否成功
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("请求url失败");
        }
        InputStream inStream = conn.getInputStream();
        byte[] bt = StreamTool.read(inStream);
        inStream.close();
        return bt;
    }
    //检查登陆信息
    public void login(final Context activity, final String name, final String password) {
        context=activity;
        new Thread(){
            public void run(){
                try {
                    //post json数据
                    JSONObject obj = new JSONObject();
                    obj.put("name", name);
                    obj.put("password", password);
                    System.out.println(obj);
                    // 创建url资源
                    String Url = context.getResources().getString(R.string.login_url);
                    URL url = new URL(Url);
                    // 建立http连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    // 设置连接超时为5秒
                    conn.setConnectTimeout(5000);
                    // 设置允许输出
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    // 设置不用缓存
                    conn.setUseCaches(false);
                    //设置请求类型为POST类型
                    conn.setRequestMethod("POST");
                    // 设置维持长连接
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    // 设置文件字符集:
                    conn.setRequestProperty("Charset", "UTF-8");
                    //转换为字节数组
                    byte[] outputdata = (obj.toString()).getBytes();
                    // 设置文件长度
                    conn.setRequestProperty("Content-Length", String.valueOf(outputdata.length));
                    // 设置文件类型:
                    conn.setRequestProperty("contentType", "application/json");
                    // 开始连接请求
                    conn.connect();
                    OutputStream out = conn.getOutputStream();
                    // 写入请求的字符串
                    out.write((obj.toString()).getBytes());
                    out.flush();
                    out.close();
                    if (conn.getResponseCode() != 200) {
                        handler.sendEmptyMessage(0x002);
                    }
                    else if (conn.getResponseCode() == 200) {
                        InputStream inStream = conn.getInputStream();
                        String a = null;
                        try {
                            byte[] inputdata = StreamTool.read(inStream);
                            inStream.close();
                            a = new String(inputdata);
                            JSONObject jar =new JSONObject(a);
                            Loginresult=jar.getString("result");
                            handler.sendEmptyMessage(0x001);
                        } catch (Exception e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                }catch (RuntimeException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }.start();
    }

    // 获取网页的html源代码
    public static String getHtml(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200) {
            InputStream in = conn.getInputStream();
            byte[] data = StreamTool.read(in);
            String html = new String(data, "UTF-8");
            return html;
        }
        return null;
    }

    private Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x001:
                    mListener.result(Loginresult);
                    break;
                default:
                    break;
            }
        };
    };
}


class StreamTool {
    //从流中读取数据
    public static byte[] read(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}
