package com.example.rkjc.news_app_2;

import android.content.Context;
import android.os.AsyncTask;
import com.firebase.jobdispatcher.JobService;
import com.example.rkjc.news_app_2.NewsRepository;

public class FirebaseJobScheduler extends JobService {
    private AsyncTask mBackgroundAsyncTask;
    NewsRepository mNewsRepository;

    @Override
    public boolean onStartJob(final com.firebase.jobdispatcher.JobParameters job) {
        mBackgroundAsyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Context context = FirebaseJobScheduler.this;
                ReminderTask.executeTask(context, ReminderTask.UPDATE_ARTICLES);
                mNewsRepository = new NewsRepository(getApplication());
                mNewsRepository.makeNewsSearchQuery();
                return null;
            }
            @Override
            protected void onPostExecute(Object object) {
                jobFinished(job, false);
            }
        };
        mBackgroundAsyncTask.execute();
        return true;
    }
    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
        if(mBackgroundAsyncTask != null)
            mBackgroundAsyncTask.cancel(true);
        return true;
    }
}
