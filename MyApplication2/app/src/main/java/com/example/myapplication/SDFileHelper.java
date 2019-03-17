package com.example.myapplication;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;

public class SDFileHelper {

    private Context context;

    public SDFileHelper() {
    }

    public SDFileHelper(Context context) {
        super();
        this.context = context;
    }

    //往SD卡写入文件的方法
    public void saveFileToSD(String path, String filename, String file) throws Exception {
        //如果手机已插入sd卡,且app具有读写sd卡的权限
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filepath = Environment.getExternalStorageDirectory().getCanonicalPath() + "/" + path;
            this.createdir(filepath);
            filepath = filepath + "/" + filename;
            //这里就不要用openFileOutput了,那个是往手机内存中写数据的
            FileOutputStream output = new FileOutputStream(filepath);
            output.write(file.getBytes());
            //将String字符串以字节流的形式写入到输出流中
            output.close();
            //关闭输出流
        } else Toast.makeText(context, "SD卡不存在或者不可读写", Toast.LENGTH_SHORT).show();
    }

    //读取SD卡中文件的方法
    //定义读取文件的方法:
    public String readstrFromSD(String path, String filename) throws IOException {
        StringBuilder sb = new StringBuilder("");
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            filename = Environment.getExternalStorageDirectory().getCanonicalPath() + "/" + path + "/" + filename;
            //打开文件输入流
            FileInputStream input = new FileInputStream(filename);
            byte[] temp = new byte[1024];

            int len = 0;
            //读取文件内容:
            while ((len = input.read(temp)) > 0) {
                sb.append(new String(temp, 0, len));
            }
            //关闭输入流
            input.close();
        }
        return sb.toString();
    }

    public static byte[] readimgFromSD(String path, String filename) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            filename = Environment.getExternalStorageDirectory().getCanonicalPath() + "/" + path + "/" + filename;
            //打开文件输入流
            FileInputStream input = new FileInputStream(filename);
            byte[] temp = new byte[1024];

            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = input.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            //关闭输入流
            input.close();
        }
        return outStream.toByteArray();
    }

    public boolean createdir(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                //按照指定的路径创建文件夹
                file.mkdirs();
            } catch (Exception e) {
                // TODO: handle exception
                return false;
            }
        }
        return true;
    }

   /* private static String getfilepath() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        return path;
    }

    public void writerSD() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

        } else {
            Toast.makeText(MainActivity.this, "SD卡不存在", Toast.LENGTH_SHORT).show();
        }
        File file = Environment.getExternalStorageDirectory();
        File newfile = new File(file, "1.txt");
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(newfile);
            outputStream.write("123".getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

}