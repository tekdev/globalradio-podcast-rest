package com.globalradio.mo.web;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "itune")
@XmlType(propOrder = {"subtitle", "author", "summary", "image", "isExplicit", "newFeedUrl", "duration", "ownerName", "ownerEmail", "category"})
public class ItuneRepresentation {
    private String subtitle;
    private String author;
    private String summary;
    private String image;
    private String isExplicit;
    private String duration;
    private String ownerName;
    private String ownerEmail;
    private String category;
    private String newFeedUrl;

    @XmlElement(name = "subtitle")
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    @XmlElement(name = "author")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @XmlElement(name = "summary")
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @XmlElement(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @XmlElement(name = "explicit")
    public String getIsExplicit() {
        return isExplicit;
    }

    public void setIsExplicit(String isExplicit) {
        this.isExplicit = isExplicit;
    }

    @XmlElement(name = "duration")
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @XmlElement(name = "ownerName")
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @XmlElement(name = "ownerEmail")
    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    @XmlElement(name = "category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @XmlElement(name = "new-feed-url")
    public String getNewFeedUrl() {
        return newFeedUrl;
    }

    public void setNewFeedUrl(String newFeedUrl) {
        this.newFeedUrl = newFeedUrl;
    }
}
