package com.globalradio.mo.web;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.text.SimpleDateFormat;
import java.util.Date;

@XmlRootElement(name = "podcast")
@XmlType(propOrder = {"guid", "isPermaLink", "title", "description", "pubDate", "enclosure", "ituneRepresentation"})
public class PodcastRepresentation {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("EEE, d MMM YYYY HH:mm:ss z ");

    @XmlRootElement(name = "enclosure")
    @XmlType(propOrder = {"url", "type", "length"})
    class Enclosure {
        private String url;
        private String type;
        private long length;

        public Enclosure(String url, String type, long length) {
            this.url = url;
            this.type = type;
            this.length = length;
        }

        @XmlElement(name = "url")
        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @XmlElement(name = "type")
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @XmlElement(name = "length")
        public long getLength() {
            return length;
        }

        public void setLength(long length) {
            this.length = length;
        }
    }


    private String guid;
    private boolean isPermaLink;
    private String title;
    private String description;
    private String pubDate;
    private Enclosure enclosure;
    private ItuneRepresentation ituneRepresentation;

    @XmlElement(name = "guid")
    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @XmlElement(name = "isPermaLink")
    public boolean isPermaLink() {
        return isPermaLink;
    }

    public void setIsPermaLink(boolean isPermaLink) {
        this.isPermaLink = isPermaLink;
    }

    @XmlElement(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement(name = "pubdate")
    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = SIMPLE_DATE_FORMAT.format(pubDate);
    }

    @XmlElement(name = "enclosure")
    public Enclosure getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(Enclosure enclosure) {
        this.enclosure = enclosure;
    }

    @XmlElement(name = "itune")
    public ItuneRepresentation getItuneRepresentation() {
        return ituneRepresentation;
    }

    public void setItuneRepresentation(ItuneRepresentation ituneRepresentation) {
        this.ituneRepresentation = ituneRepresentation;
    }
}