package com.example.utils;

import android.content.Context;

import com.example.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

public class GetData {

    private Context context;
    private Handler handler;
    public int ConnectFail=0;
    public String LoginResult;
    public Bitmap bitmap;
    public Object result;
    public GetData(){

    }
    public GetData(Context context,final Handler handler){
        this.context=context;
        this.handler=handler;
    }
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

    //检查登陆信息
    //activity,主线程的Handler,返回的message，和返回的结果，名字，密码
    public void login(final int message ,final String name, final String password) {
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
                        handler.sendEmptyMessage(0x000);
                    }
                    else if (conn.getResponseCode() == 200) {
                        InputStream inStream = conn.getInputStream();
                        String a = null;
                        try {
                            byte[] inputdata = read(inStream);
                            inStream.close();
                            a = new String(inputdata);
                            JSONObject jar =new JSONObject(a);
                            LoginResult=jar.getString("result");
                            handler.sendEmptyMessage(message);
                        } catch (Exception e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }.start();
    }
    //获取图片
    public void getImage(final int message,final String path){
        new Thread() {
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    // 设置连接超时为5秒
                    conn.setConnectTimeout(5000);
                    // 设置请求类型为Get类型
                    conn.setRequestMethod("GET");
                    // 判断请求Url是否成功
                    if (conn.getResponseCode() != 200) {
                        handler.sendEmptyMessage(0x000);
                    }
                    InputStream inStream = conn.getInputStream();
                    byte[] data = read(inStream);
                    inStream.close();
                    bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    handler.sendEmptyMessage(message);
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    //获取章节

    public void getGroups(final int message){
        new Thread(){
            public void run(){
                try {

                    // 创建url资源
                    String Url = context.getResources().getString(R.string.groups_url);
                    StringBuilder params=new StringBuilder();
                    params.append("groups");
                    params.append("=");
                    params.append("0000");//all
                    //params.append("&");
                    URL url = new URL(Url+(params.length()>0 ? "?"+params.toString() : ""));
                    // 建立http连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //GET请求
                    conn.setRequestMethod("GET");
                    // 设置连接超时为5秒
                    conn.setConnectTimeout(5000);
                    // 开始连接请求
                    conn.connect();
                    if (conn.getResponseCode() != 200) {
                        handler.sendEmptyMessage(0x000);
                    }
                    else if (conn.getResponseCode() == 200) {
                        InputStream inStream = conn.getInputStream();
                        String a = null;
                        try {
                            byte[] inputdata = read(inStream);
                            inStream.close();
                            a = new String(inputdata);
                            JSONObject jar =new JSONObject(a);
                            Bundle bundle=new Bundle();
                            Message msg=handler.obtainMessage();
                            Iterator iter = jar.keys();
                            while(iter.hasNext()){
                                String key = (String)iter.next();
                                String value = jar.getString(key);
                                bundle.putString(key,value);
                            }
                            msg.what=message;
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        } catch (Exception e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }.start();
    }
    //获取题目
    public void getQuestion(final String path){

    }

}


