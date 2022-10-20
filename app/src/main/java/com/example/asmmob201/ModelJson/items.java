package com.example.asmmob201.ModelJson;

public class items {
    String title;
    descriptions description;
    String pubDate;
    String link;
    String guid;
    int comments;

    public items(String title, descriptions description, String pubDate, String link, String guid, int comments) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.link = link;
        this.guid = guid;
        this.comments = comments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public descriptions getDescription() {
        return description;
    }

    public void setDescription(descriptions description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "items{" +
                "title='" + title + '\'' +
                '}';
    }
}
