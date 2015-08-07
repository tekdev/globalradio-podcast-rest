package com.globalradio.mo.data;

import com.globalradio.mo.domain.Feed;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface RSSFeedParser {
    Feed parse() throws ParserConfigurationException, IOException, SAXException, InvalidDateFormatException, InvalidRssFeedException;
}