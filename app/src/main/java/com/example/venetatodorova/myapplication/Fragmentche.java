package com.example.venetatodorova.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Fragmentche extends Fragment {

    public STATE state = STATE.NEUTRAL;
    private DownloadListener listener;
    DownloadTask downloadTask;
    ArrayList<String> html_list;

    public ArrayList<String> getHtmlList() {
        return html_list;
    }

    public void setHtmlList(ArrayList<String> list){
        html_list = list;
    }

    interface DownloadListener {
        void onDownloadFinished(ArrayList<String> html_list);

        void onDownloadProgress(String value);
    }

    public enum STATE {
        NEUTRAL,
        DOWNLOADING,
        DOWNLOADED
    }

    public static Fragmentche newInstance() {
        Fragmentche fragmentche = new Fragmentche();
        return fragmentche;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void doStuff(String page){
        state = STATE.DOWNLOADING;
        downloadTask = new DownloadTask();
        downloadTask.setListener(new DownloadListener() {
            @Override
            public void onDownloadFinished(ArrayList<String> html_list) {
                state = STATE.DOWNLOADED;
                listener.onDownloadFinished(html_list);
            }

            @Override
            public void onDownloadProgress(String value) {
                listener.onDownloadProgress(value);
            }
        });
        downloadTask.execute(page);
    }

    public void setListener(DownloadListener listener) {
        this.listener = listener;
    }


}
