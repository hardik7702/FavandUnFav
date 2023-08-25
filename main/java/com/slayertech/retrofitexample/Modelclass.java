package com.slayertech.retrofitexample;

public class Modelclass {
    int albumId;
    int id;
    String title;
    String url;
    String bookmark;

//
    public Modelclass(int albumId, int id, String title, String url, String bookmark) {
        this.albumId = albumId;
        this.id = id;
        this.title = title;
        this.url = url;
        this.bookmark=bookmark;

    }



    public String getBookmark() {
        return bookmark;
    }

    public void setBookmark(String bookmark) {
        this.bookmark = bookmark;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
