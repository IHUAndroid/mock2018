package a2018.mock.ihu.gr.mock;

/**
 * Created by Thodoris
 */

public class Article {

    private String author;
    private String title;
    private String description;
    private String url;

    public Article(String author, String title, String description, String url) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String toString(){
        return title +" - "+ description +" - "+ url;
    }
}
