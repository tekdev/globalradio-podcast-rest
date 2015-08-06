package com.globalradio.mo.data;


import com.sun.syndication.feed.CopyFrom;
import com.sun.syndication.feed.module.Module;

import java.util.Date;

public interface DateModule extends Module, CopyFrom {

    // TODO move the URL to application.yml
    String URI = "http://purl.org/dc/elements/1.1/";

    Date getDate();

    void setDate(Date date);
}
