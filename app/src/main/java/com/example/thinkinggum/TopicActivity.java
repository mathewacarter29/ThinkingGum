package com.example.thinkinggum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TopicActivity extends AppCompatActivity implements TopicAdapter.ViewHolder.onTopicListener{

    RecyclerView topicsRv;
    final static String URL = "https://www.aconsciousrethink.com/6907/thought-provoking-questions/";
    final static String TAG = "TopicActivity";
    final static String TOPIC_TAG = "Topic Tag";
    List<String> topics;
    TopicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        topicsRv = (RecyclerView) findViewById(R.id.topicsRv);
        topics = new ArrayList<>();

        //Putting "this" as the second parameter refers to the onTopicListener this class implements
        adapter = new TopicAdapter(topics, this);
        topicsRv.setAdapter(adapter);
        topicsRv.setLayoutManager(new LinearLayoutManager(this));

        TopicTask asyncTask = new TopicTask();
        asyncTask.execute();
    }

    @Override
    public void onTopicClick(int position) {
        Log.d(TAG, "Button clicked");
        String topic = topics.get(position);
        Intent intent = new Intent(this, PopUp.class);
        intent.putExtra(TOPIC_TAG, topic);
        startActivity(intent);
    }

    private class TopicTask extends AsyncTask<Void, Void, List<String>> {


        @Override
        protected List<String> doInBackground(Void... voids) {
            String title;
            Document document;
            List<String> entries = new ArrayList<>();
            try {
                int i = 0;
                document = Jsoup.connect(URL).get();
                Elements content = document.getElementsByTag("p");
                for (Element text: content) {
                    String line = text.toString();
                    if (line.contains("</b>")) {
                        String[] tokens = line.split("</b>*");
                        String entry = tokens[tokens.length - 1];
                        //Takes the </p off the end
                        entry = entry.substring(1, entry.length() - 4);
                        entries.add(entry);
                        i++;
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return entries;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            topics.addAll(result);
            topics.remove(96);
            topics.remove(79);
            topics.remove(78);
            topics.remove(72);
            topics.remove(53);
            topics.remove(44);
            topics.remove(43);
            topics.remove(28);
            topics.remove(24);
            topics.remove(8);
            topics.remove(4);

            adapter.notifyDataSetChanged();
        }
    }
}