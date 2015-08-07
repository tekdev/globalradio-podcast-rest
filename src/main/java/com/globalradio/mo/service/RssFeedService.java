package com.globalradio.mo.service;


import com.globalradio.mo.data.InvalidDateFormatException;
import com.globalradio.mo.data.InvalidRssFeedException;
import com.globalradio.mo.domain.Feed;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface RssFeedService {
    Feed getRssFeedPodcastById(String id, boolean alternate) throws ParserConfigurationException, InvalidDateFormatException, SAXException, IOException, InvalidRssFeedException, NotFoundException;

    Feed getRssFeedPodcasts(boolean latest) throws ParserConfigurationException, InvalidDateFormatException, SAXException, IOException, InvalidRssFeedException;
}
