package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class NewsRepository {
    private NewsItemDao mNewsDao;
    private LiveData<List<NewsItem>> mAllNews;
    List<NewsItem> mArticles;

    public NewsRepository(Application application){
        NewsRoomDatabase db = NewsRoomDatabase.getDatabase(application.getApplicationContext());
        mNewsDao = db.newsItemDao();
        mAllNews = mNewsDao.loadAllNewsItems();
    }

    LiveData<List<NewsItem>> loadAllNewsItems(){
        return mAllNews;
    }

    public void insert (List<NewsItem> newsItem) {
        new insertAsyncTask(mNewsDao).execute(newsItem);
    }

    public void delete(){
        new deleteAsyncTask(mNewsDao).execute() ;
    }

    private static class insertAsyncTask extends AsyncTask<List<NewsItem>, Void, Void> {
        private NewsItemDao mAsyncTaskDao;
        insertAsyncTask(NewsItemDao dao){
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final List<NewsItem>... params){

            for(int i = 0; i < params.length; i++) {
                mAsyncTaskDao.insert(params[i]);
            }
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<List<NewsItem>,Void, Void>{
        private NewsItemDao mAsyncTaskDao;
        deleteAsyncTask(NewsItemDao dao){
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final List<NewsItem>... params){

            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
    public void makeNewsSearchQuery(){
        URL newsUrl = NetworkUtils.buildUrl();
        new NewsQueryTask().execute(newsUrl);
    }
    public class NewsQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            delete();
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String newsSearchResults = null;
            try {
                newsSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newsSearchResults;
        }

        @Override
        protected void onPostExecute(String newsSearchResult) {
            super.onPostExecute(newsSearchResult);
            mArticles = JsonUtils.parseNews(newsSearchResult);
            insert(mArticles);
        }

    }
}
