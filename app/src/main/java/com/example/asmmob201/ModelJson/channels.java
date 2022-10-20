package com.example.asmmob201.ModelJson;
import java.util.ArrayList;

public class channels {
     private String title;
     private String description;
     private images image;
     private String pubDate;
     private String generator;
     private String link;
     private ArrayList<items> item;

    public channels(String title, String description, images image, String pubDate, String generator, String link, ArrayList<items> item) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.pubDate = pubDate;
        this.generator = generator;
        this.link = link;
        this.item = item;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public images getImage() {
        return image;
    }

    public void setImage(images image) {
        this.image = image;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ArrayList<items> getItem() {
        return item;
    }

    public void setItem(ArrayList<items> item) {
        this.item = item;
    }


}
