package com.globalradio.mo.data;

import com.globalradio.mo.domain.Rss;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.jdom.Element;

import javax.xml.bind.annotation.XmlElement;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class RssReader {

    final String SOURCE_URL = "http://mediaweb.musicradio.com/RSSFeed.xml?Channel=9172";

    public Rss read() throws FeedException, IOException, InvalidRssFeedException {



        URL feedSource = new URL(SOURCE_URL);
        SyndFeedInput input = new SyndFeedInput();
        input.setPreserveWireFeed(true);
        SyndFeed feed = input.build(new XmlReader(feedSource));

        Rss rss = null;
        RssFeedParser parser;
        if (feed != null && feed.getEntries() != null) {
            rss = new Rss();
            parser = new RssFeedParser();
            rss.setTitle(feed.getTitle());
            rss.setLink(feed.getLink());
            rss.setDescription(feed.getDescription());
            rss.setLanguage(feed.getLanguage());
            rss.setCopyright(feed.getCopyright());
            rss.setItune(parser.parseItune((List<Element>) feed.getForeignMarkup()));
            rss.setPodcasts(parser.parsePodcasts((List<SyndEntry>) feed.getEntries()));
        }
        return rss;
    }
}
