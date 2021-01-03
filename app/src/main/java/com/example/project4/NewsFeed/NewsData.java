package com.example.project4.NewsFeed;

public class NewsData {
    private String title,description,category,image,key;

    public NewsData() {
    }

    public NewsData(String title, String description, String category, String image, String key) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.image = image;
        this.key = key;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
