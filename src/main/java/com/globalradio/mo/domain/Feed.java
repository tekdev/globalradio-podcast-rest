package com.globalradio.mo.domain;


import java.util.List;

public class Feed {
    private String title;
    private String link;
    private String description;
    private String language;
    private String copyright;
    private Itune itune;
    private List<Podcast> podcasts;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public List<Podcast> getPodcasts() {
        return podcasts;
    }

    public void setPodcasts(List<Podcast> podcasts) {
        this.podcasts = podcasts;
    }

    public Itune getItune() {
        return itune;
    }

    public void setItune(Itune itune) {
        this.itune = itune;
    }
}
