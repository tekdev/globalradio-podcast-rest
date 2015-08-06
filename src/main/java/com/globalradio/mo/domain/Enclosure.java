package com.globalradio.mo.domain;

public class Enclosure {
    private String url;
    private String type;
    private long length;

    public Enclosure() {
    }

    public Enclosure(String url, String type, long length) {
        this.url = url;
        this.type = type;
        this.length = length;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }
}

