package com.globalradio.mo.service;

import com.globalradio.mo.data.InvalidDateFormatException;
import com.globalradio.mo.data.InvalidRssFeedException;
import com.globalradio.mo.data.ParserDomImpl;
import com.globalradio.mo.data.RSSFeedParser;
import com.globalradio.mo.domain.Feed;
import com.globalradio.mo.domain.Podcast;
import org.jdom.Namespace;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("rssFeedService")
public class RssFeedServiceImpl implements RssFeedService {
    //TODO move this hardcoded URL's to application.yml
    final String STRING_URL = "http://mediaweb.musicradio.com/RSSFeed.xml?Channel=9172";
    final static String NAMESPACE_URI = "http://www.itunes.com/dtds/podcast-1.0.dtd";
    private static Namespace ITUNE_NS = Namespace.getNamespace("itunes", NAMESPACE_URI);
    private RSSFeedParser parser;
    private URL URL;

    public RssFeedServiceImpl() throws MalformedURLException {
        this.URL = new URL(STRING_URL);
        parser = new ParserDomImpl(URL, ITUNE_NS);
    }

    public void setParser(RSSFeedParser parser) {
        this.parser = parser;
    }

    @Override
    public Feed getRssFeedPodcastById(String id, boolean alternate) throws ParserConfigurationException, InvalidDateFormatException, SAXException, IOException, InvalidRssFeedException, NotFoundException {
        Feed feed = parser.parse();
        if (alternate) {
            List<Podcast> podcasts = feed.getPodcasts().stream().filter(p -> !p.getId().equals(id)).collect(Collectors.toList());
            feed.setPodcasts(podcasts);
        } else {
            Optional<Podcast> podcast = feed.getPodcasts().stream().filter(p -> p.getId().equals(id)).findFirst();
            if (podcast.isPresent()) {
                List<Podcast> podcasts = new ArrayList<>();
                podcasts.add(podcast.get());
                feed.setPodcasts(podcasts);
            } else {
                throw new NotFoundException("Not found resource id: " + id);
            }
        }
        return feed;
    }

    @Override
    public Feed getRssFeedPodcasts(boolean latest) throws ParserConfigurationException, InvalidDateFormatException, SAXException, IOException, InvalidRssFeedException {
        Feed feed = parser.parse();
        if (latest) {
            List<Podcast> podcasts = new ArrayList<>();
            Date mostRecentDate = feed.getPodcasts().stream().map(Podcast::getPubDate).max(Date::compareTo).get();
            Optional<Podcast> podcast = feed.getPodcasts().stream().filter(p -> p.getPubDate() == mostRecentDate).findFirst();
            podcasts.add(podcast.get());
            feed.setPodcasts(podcasts);
        }
        return feed;
    }
}