package com.example.rkjc.news_app_2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.example.rkjc.news_app_2.ScheduleUtils;

import android.view.Menu;
import android.view.MenuItem;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NewsRecyclerViewAdapter.ListItemClickListener {

    NewsViewModel mNewsViewModel;
    RecyclerView mNumbersList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNumbersList = (RecyclerView) findViewById(R.id.news_recyclerview);
        mNewsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);

        final NewsRecyclerViewAdapter adapter = new NewsRecyclerViewAdapter(mNewsViewModel,MainActivity.this);
        mNumbersList.setAdapter(adapter);
        mNumbersList.setLayoutManager(new LinearLayoutManager(this));
        mNewsViewModel.loadAllNewsItems().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(@Nullable List<NewsItem> newsItems) {
                adapter.setNewsArticle(newsItems);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNumbersList.setLayoutManager(layoutManager);
        mNumbersList.setHasFixedSize(true);
        ScheduleUtils.scheduleRefresh(this);
        //mAdapter = new NewsRecyclerViewAdapter(mNewsViewModel, MainActivity.this);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //mNumbersList.setLayoutManager(layoutManager);
        //mNewsViewModel.loadAllNewsItems().observe(this, new Observer<List<NewsItem>>() {
        //    @Override
        //    public void onChanged(@Nullable List<NewsItem> newsItems) {
         //       mAdapter.setNewsArticle(newsItems);
         //   }
        //});
        //mNumbersList.setHasFixedSize(true);

        //newsQuery();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();

        if (itemId == R.id.action_search) {
            mNewsViewModel.update();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onListItemClick(int clickedItemIndex){
        Uri uri = Uri.parse(mNewsViewModel.loadAllNewsItems().getValue().get(clickedItemIndex).getUrl());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
