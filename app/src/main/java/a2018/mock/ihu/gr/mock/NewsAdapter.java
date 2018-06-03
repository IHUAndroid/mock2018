package a2018.mock.ihu.gr.mock;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Thodoris on
 */

public class NewsAdapter extends ArrayAdapter<Article> {

    private ArrayList<Article> articles;
    private Context context;

    public NewsAdapter(Context context, ArrayList<Article> objects) {
        super(context, 0, objects);
        this.articles = objects;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        Article m = articles.get(position);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.list_item_article, null);
        TextView articleTitleView = (TextView)rowView.findViewById(R.id.list_item_article_title);
        articleTitleView.setText(m.getTitle());

        TextView authorView =  (TextView)rowView.findViewById(R.id.list_item_article_author);
        authorView.setText("By "+m.getAuthor());

        TextView descView = (TextView)rowView.findViewById(R.id.list_item_article_description);
        descView.setText(m.getDescription());

        return  rowView;
    }


}
