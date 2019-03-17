package com.example.myapplication;

import android.os.Environment;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader {
    //添加@Test标记是表示该方法是Junit测试的方法,就可以直接运行该方法了
    public String path;
    public String filepath;
    public String filename;

    public Downloader(String url, String filepath, String filename) {
        this.path = url;
        try {
            this.filepath = Environment.getExternalStorageDirectory().getCanonicalPath() + "/" + filepath + "/" + filename;
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.filename = filename;
    }

    public void download() throws Exception {
        //设置URL的地址和下载后的文件名
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept-Encoding", "identity");
        conn.setRequestProperty("CONTENT_TYPE", "text/plain");
        conn.setRequestProperty("User-Agent", " Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36");

        conn.connect();
        //获得需要下载的文件的长度(大小)
        int filelength = conn.getContentLength();
        System.out.println("要下载的文件长度" + filelength);
        //生成一个大小相同的本地文件
        String name = conn.getURL().getFile();
        File file = new File(filepath);
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
        randomAccessFile.setLength(filelength);
        randomAccessFile.close();
        conn.disconnect();
        //设置有多少条线程下载
        int threadsize = 3;
        //计算每个线程下载的量
        int threadlength = filelength % 3 == 0 ? filelength / 3 : filelength / 3 + 1;
        for (int i = 0; i < threadsize; i++) {
            //设置每条线程从哪个位置开始下载
            int startposition = i * threadlength;
            //从文件的什么位置开始写入数据
            RandomAccessFile threadfile = new RandomAccessFile(file, "rwd");
            threadfile.seek(startposition);
            //启动三条线程分别从startposition位置开始下载文件
            new DownLoadThread(i, startposition, threadfile, threadlength, path).start();
        }
        int quit = System.in.read();
        while ('q' != quit) {
            Thread.sleep(2000);
        }
    }


    private class DownLoadThread extends Thread {
        private int threadid;
        private int startPosition;
        private int endPosition;
        private RandomAccessFile threadfile;
        private int threadlength;
        private String path;

        public DownLoadThread(int threadid, int startPosition,
                              RandomAccessFile threadfile, int threadlength, String path) {
            this.threadid = threadid;
            this.startPosition = startPosition;
            this.threadfile = threadfile;
            this.threadlength = threadlength;
            this.path = path;
        }

        public DownLoadThread() {
        }

        @Override
        public void run() {
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                //指定从什么位置开始下载
                conn.setRequestProperty("Range", "bytes=" + startPosition + "-" + endPosition);
                //System.out.println(conn.getResponseCode());
                if (conn.getResponseCode() == 206) {
                    InputStream is = conn.getInputStream();
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    int length = 0;
                    while (length < threadlength && (len = is.read(buffer)) != -1) {
                        threadfile.write(buffer, 0, len);
                        //计算累计下载的长度
                        length += len;
                    }
                    threadfile.close();
                    is.close();
                    System.out.println("线程" + (threadid + 1) + "已下载完成");
                }
            } catch (Exception ex) {
                System.out.println("线程" + (threadid + 1) + "下载出错" + ex);
            }
        }

    }
}