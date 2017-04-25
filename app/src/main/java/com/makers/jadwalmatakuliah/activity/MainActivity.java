package com.makers.jadwalmatakuliah.activity;

/**
 * Created by Firman on 11/27/2016.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.makers.jadwalmatakuliah.R;
import com.makers.jadwalmatakuliah.adapter.AdapterArticle;
import com.makers.jadwalmatakuliah.helper.RealmHelper;
import com.makers.jadwalmatakuliah.model.ArticleModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "HomeActivity";
    private Toolbar toolbar;
    private ImageView toolbarTitle;
    private RecyclerView recyclerView;
    private RealmHelper helper;
    private ArrayList<ArticleModel> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);
        //bind view
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbarTitle = (ImageView) findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        data = new ArrayList<>();
        helper = new RealmHelper(MainActivity.this);


        recyclerView = (RecyclerView) findViewById(R.id.rvArticle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddActivity.class));
                finish();
            }
        });


        setRecyclerView();
    }


    /**
     * set recyclerview with try get data from realm
     */
    public void setRecyclerView() {
        try {
            data = helper.findAllArticle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        AdapterArticle adapter = new AdapterArticle(data, new AdapterArticle.OnItemClickListener() {
            @Override
            public void onClick(ArticleModel item) {
                Intent i = new Intent(getApplicationContext(), EditActivity.class);
                i.putExtra("id", item.getId());
                i.putExtra("hari", item.getHari());
                i.putExtra("keterangan", item.getKeterangan());
                startActivity(i);
                finish();
            }
        });
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            data = helper.findAllArticle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //data = helper.findAllArticle();
        setRecyclerView();
    }

    /**
     * Menangani perintah saat tombol back ditekan
     */
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit")
                .setMessage("Apakah Kamu Ingin Keluar?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_info:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                Intent i = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


