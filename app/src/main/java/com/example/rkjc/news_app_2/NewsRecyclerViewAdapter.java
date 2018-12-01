package com.example.rkjc.news_app_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {
    private List<NewsItem> mNewsArticles;
    final private ListItemClickListener mListItemClickListener;
    private NewsViewModel viewModel;

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

    public NewsRecyclerViewAdapter(NewsViewModel viewModel, ListItemClickListener mListItemClickListener){
        this.viewModel = viewModel;
        this.mListItemClickListener = mListItemClickListener;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutOfItems = R.layout.news_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachToParent = false;

        View view = inflater.inflate(layoutOfItems, viewGroup, attachToParent);
        NewsViewHolder viewHolder = new NewsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position){
        holder.bind(position);
    }

    @Override
    public int getItemCount(){
        if(mNewsArticles != null) {
            return mNewsArticles.size();
        } else {
            return 0;
        }
    }

    void setNewsArticle(List<NewsItem> newsItems) {
        mNewsArticles = newsItems;
        notifyDataSetChanged();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        TextView title;
        TextView description;
        TextView date;
        ImageView image;

        public NewsViewHolder(View itemView){
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            date = (TextView) itemView.findViewById(R.id.date);
            image = (ImageView) itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        void bind(int listIndex){
            Uri urlImage = Uri.parse(mNewsArticles.get(listIndex).getUrlToImage());

            if(urlImage != null) {
                Picasso.get().load(urlImage).into(image);
            }
            title.setText("Title: " + mNewsArticles.get(listIndex).getTitle());
            description.setText("Description: " + mNewsArticles.get(listIndex).getDescription());
            date.setText("Date: " + mNewsArticles.get(listIndex).getPublishedAt());
        }
        @Override
        public void onClick(View v){
            int clickedArticlePosition = getAdapterPosition();
            mListItemClickListener.onListItemClick(clickedArticlePosition);
        }
    }
}
