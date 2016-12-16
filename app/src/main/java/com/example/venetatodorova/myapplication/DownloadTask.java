package com.example.venetatodorova.myapplication;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DownloadTask extends AsyncTask<String,String,String>{

    private Fragmentche.DownloadListener listener;
    ArrayList<String> htmls;
    ArrayList<String> htmls_names;

    @Override
    protected String doInBackground(String... params) {
        String page = params[0];
        String line;
        htmls = new ArrayList<>();
        int count =0;
        htmls_names = new ArrayList<>();
        try {
            downloadURL(page);
            String html_path = "storage/emulated/0/Download/AsyncTask.html";
            BufferedReader br = new BufferedReader(new FileReader(html_path));

            while((line = br.readLine()) != null && count<20) {
                Pattern p = Pattern.compile("href=\"(.*?)\"");
                Matcher matcher = p.matcher(line);

                if(matcher.find()){
                    String current_html = matcher.group(1);
                    if(current_html.startsWith("#")) htmls.add(page+current_html);
                    else if(current_html.startsWith("//")) htmls.add("https:"+current_html);
                    else if(current_html.startsWith("/")) htmls.add("https:/"+current_html);
                    else if(current_html.length()!=0) htmls.add(current_html);
                    count++;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String html: htmls) {
            downloadURL(html);
            File file = new File(html);
            String name = file.getName();
            publishProgress(name);
        }
        return "Download complete";
    }

    private void downloadURL(String page) {

        File file = new File(page);
        String name = file.getName();
        htmls_names.add(name);

        String html_path = "storage/emulated/0/Download/";
        File input_file = new File(html_path+name);
        int count;
        try {
            URL url = new URL(page);
            InputStream inputStream = new BufferedInputStream(url.openStream());
            byte[] data = new byte[1024];
            OutputStream outputStream = new FileOutputStream(input_file);

            while ((count = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, count);
            }

            inputStream.close();
            outputStream.close();
            } catch(IOException e){
                e.printStackTrace();
            }

    }

    public void setListener(Fragmentche.DownloadListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute (String aVoid){
        listener.onDownloadFinished(htmls_names);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        //super.onProgressUpdate(values);
        listener.onDownloadProgress(values[0]);
    }
}

