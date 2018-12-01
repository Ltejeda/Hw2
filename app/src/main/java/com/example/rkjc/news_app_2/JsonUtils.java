package com.example.rkjc.news_app_2;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JsonUtils {
    public static ArrayList<NewsItem> parseNews(String JsonString) {
        ArrayList<NewsItem> newsItems = new ArrayList<>();
        try {
            JSONObject result = new JSONObject(JsonString);
            JSONArray articles = result.getJSONArray("articles");

            for(int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                String author = article.getString("author");
                String title = article.getString("title");
                String description = article.getString("description");
                String url = article.getString("url");
                String urlToImage = article.getString("urlToImage");
                String publishedAt = article.getString("publishedAt");

                newsItems.add(new NewsItem(author, title, description, url, urlToImage, publishedAt));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsItems;
    }

}


