package com.globalradio.mo.web;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement(name = "channel")
@XmlType(propOrder = {"title", "link", "descriptions", "language", "copyright", "ituneRepresentation", "podcastRepresentations"})
public class RssRepresentation {
    private String title;
    private String link;
    private String descriptions;
    private String language;
    private String copyright;
    private ItuneRepresentation ituneRepresentation;
    private List<PodcastRepresentation> podcastRepresentations;

    @XmlElement(name="title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @XmlElement(name="link")
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @XmlElement(name="descriptions")
    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    @XmlElement(name="language")
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @XmlElement(name="copyright")
    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    @XmlElement(name="itune")
    public ItuneRepresentation getItuneRepresentation() {
        return ituneRepresentation;
    }

    public void setItuneRepresentation(ItuneRepresentation ituneRepresentation) {
        this.ituneRepresentation = ituneRepresentation;
    }

    @XmlElement(name="items")
    public List<PodcastRepresentation> getPodcastRepresentations() {
        return podcastRepresentations;
    }

    public void setPodcastRepresentations(List<PodcastRepresentation> podcastRepresentations) {
        this.podcastRepresentations = podcastRepresentations;
    }
}
