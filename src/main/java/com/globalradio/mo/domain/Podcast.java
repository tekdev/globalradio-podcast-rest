package com.globalradio.mo.domain;


import java.util.Date;

public class Podcast {
    private String id;
    private boolean isPermaLink;
    private String title;
    private String description;
    private Date pubDate;
    private Enclosure enclosure;
    private Itune itune;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isPermaLink() {
        return isPermaLink;
    }

    public void setIsPermaLink(boolean isPermaLink) {
        this.isPermaLink = isPermaLink;
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

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public Enclosure getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(Enclosure enclosure) {
        this.enclosure = enclosure;
    }

    public Itune getItune() {
        return itune;
    }

    public void setItune(Itune itune) {
        this.itune = itune;
    }
}
