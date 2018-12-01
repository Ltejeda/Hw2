package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {
    private NewsRepository mRepository;

    private LiveData<List<NewsItem>> mAllNews;

    public NewsViewModel (Application application){
        super(application);
        mRepository = new NewsRepository(application);
        mAllNews = mRepository.loadAllNewsItems();
    }

    public LiveData<List<NewsItem>> loadAllNewsItems(){
        return mAllNews;
    }

    public void update() {
        mRepository.makeNewsSearchQuery();
    }
}
