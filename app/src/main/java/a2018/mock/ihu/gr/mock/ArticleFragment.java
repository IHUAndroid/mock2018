package a2018.mock.ihu.gr.mock;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class ArticleFragment extends Fragment {

    NewsAdapter newsAdapter;

    public ArticleFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchArticles();
    }

    private void fetchArticles(){
        FetchNewsTask fetchNewsTask = new FetchNewsTask(newsAdapter);
        fetchNewsTask.execute();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        newsAdapter = new NewsAdapter(getActivity(),new ArrayList<Article>());
        ListView newsListView = (ListView)rootView.findViewById(R.id.listview_articles);
        newsListView.setAdapter(newsAdapter);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = newsAdapter.getItem(position);
                if(o instanceof  Article){
                    Article selectedArticle = (Article)o;
                    String url = selectedArticle.getUrl();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
            }
        });

        return rootView;
    }
}
