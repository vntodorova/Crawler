package com.example.venetatodorova.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Fragmentche.DownloadListener {
    ListView listView;
    ArrayList<String> html_list;
    String page = "https://developer.android.com/reference/android/os/AsyncTask.html";
    Fragmentche fragmentche;
    ArrayAdapter<String> itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);

        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragmentche = (Fragmentche) fragmentManager.findFragmentByTag("Tag");

        if (fragmentche != null) {
            html_list = fragmentche.getHtmlList();
        } else {
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentche = Fragmentche.newInstance();
            fragmentTransaction.add(android.R.id.content,fragmentche,"Tag");
            fragmentTransaction.commit();
            html_list = new ArrayList<>();
            fragmentche.setHtmlList(html_list);
        }

        itemsAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, html_list);
        listView.setAdapter(itemsAdapter);

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentche.doStuff(page);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),Activity2.class);
                String clicked_html = html_list.get(i);
                intent.putExtra("html",clicked_html);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        fragmentche.setListener(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        fragmentche.setListener(null);
        super.onResume();
    }

    @Override
    public void onDownloadFinished(ArrayList<String> list) {
        //fragmentche.setHtmlList(html_list);
    }

    @Override
    public void onDownloadProgress(String value) {
        Log.v("tag", "portokal " + html_list.size() + " " + itemsAdapter.getCount() + " " + this );
        html_list.add(value);
        itemsAdapter.notifyDataSetChanged();
    }
}
