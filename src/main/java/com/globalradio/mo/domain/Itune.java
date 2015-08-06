package com.globalradio.mo.domain;

import org.jdom.Namespace;

public class Itune {

    // TODO move the URL to application.yml
    private static final String URI = "http://www.itunes.com/dtds/podcast-1.0.dtd";
    public static final Namespace ITUNE_NS = Namespace.getNamespace("itunes", URI);

    private String subtitle;
    private String author;
    private String summary;
    private String image;
    private String isExplicit;
    private String newFeedUrl;
    private String duration;
    private Owner owner;
    private String category;

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String isExplicit() {
        return isExplicit;
    }

    public void setIsExplicit(String isExplicit) {
        this.isExplicit = isExplicit;
    }

    public String getNewFeedUrl() {
        return newFeedUrl;
    }

    public void setNewFeedUrl(String newFeedUrl) {
        this.newFeedUrl = newFeedUrl;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
