package a2018.mock.ihu.gr.mock;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Thodoris
 */

public class FetchNewsTask extends AsyncTask<String,Void,ArrayList<Article>> {

    private final String LOG_TAG = FetchNewsTask.class.getSimpleName();
    private NewsAdapter newsAdapter;
    public static final String SERVICE_BASE_URL = "https://newsapi.org/v1/articles";

    public FetchNewsTask(NewsAdapter newsAdapter){
        this.newsAdapter = newsAdapter;

    }

    private ArrayList<Article> getMerchantsFromJson(String responseJsonStr) throws JSONException {
        ArrayList<Article> articles = new ArrayList<>();
        try{
            JSONObject response = new JSONObject(responseJsonStr);
            JSONArray articlesArray = response.getJSONArray("articles");
            for(int i=0; i<articlesArray.length(); i++){
                JSONObject articleObject = articlesArray.getJSONObject(i);
                String author = articleObject.getString("author");
                String title = articleObject.getString("title");
                String description = articleObject.getString("description");
                String url = articleObject.getString("url");
                articles.add(new Article(author, title, description, url));
            }
            Log.d(LOG_TAG, "Article Fetching Complete. " + articles.size() + "articles inserted");
            return  articles;
        }catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
            return  articles;
        }
    }

    @Override
    protected ArrayList<Article> doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String articleJsonString = null;

        try {
            final String MASHABLE_NEWS_URL =
                    "?source=mashable&sortBy=top&apiKey=0c41975ad9374d79a904203a7d4dd66f";

            Uri builtUri = Uri.parse(SERVICE_BASE_URL + MASHABLE_NEWS_URL);

            URL url = new URL(builtUri.toString());

            // Create the request to Yummy Wallet server, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            articleJsonString = buffer.toString();
            return  getMerchantsFromJson(articleJsonString);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Article> articles) {
        if(articles.size() > 0){
            this.newsAdapter.clear();
            for(Article m : articles){
                this.newsAdapter.add(m);
            }
        }
    }
}
