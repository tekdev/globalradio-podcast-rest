package com.globalradio.mo.data;


import com.globalradio.mo.domain.Feed;
import com.globalradio.mo.domain.Podcast;
import org.apache.log4j.Logger;
import org.jdom.Namespace;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParserDomImplTest {
    //setting the expected feed values
    final String CHANNEL_TITLE = "channel title";
    final String CHANNEL_LINK = "http://www.xfm.co.uk/";
    final String CHANNEL_DESCRIPTION = "channel description";
    final String CHANNEL_LANGUAGE = "en";
    final String CHANNEL_COPYRIGHT = "XFM";
    final String CHANNEL_ITUNES_SUBTITLE = "channel itune subtitle";
    final String CHANNEL_ITUNES_AUTHOR = "Xfm";
    final String CHANNEL_ITUNES_SUMMARY = "some summary";
    final String CHANNEL_ITUNES_IMAGE = "http://dummy-url.com/me.jpeg";
    final String CHANNEL_ITUNES_EXPICIT = "no";
    final String CHANNEL_ITUNES_NEW_FEED_URL = "http://dummy-url.com";
    final String CHANNEL_ITUNES_OWNER_NAME = "Xfm";
    final String CHANNEL_ITUNES_OWNER_EMAIL = "dummy@xfm.co.uk";
    final String CHANNEL_ITUNES_CATEGORY = "Comedy";
    final String ITEM_AUTHOR = "item author";
    final String ITEM_TITLE = "item title";
    final String ITEM_DESCRIPTION = "item description";
    final String ITEM_PUB_DATE = "Thu, 30 Jul 2015 23:00:00 GMT";
    final String ITEM_ENCLOSURE_URL = "http://dummy-url.com";
    final String ITEM_ENCLOSURE_TYPE = "audio/mpeg";
    final long ITEM_ENCLOSURE_LENGHT = 38746591;
    final String ITEM_ITUNE_SUBTITLE = "item itune subtitle";
    final String ITEM_ITUNE_EXPLICIT = "yes";
    final String ITEM_ITUNE_SUMMARY = "item itune summary";
    final String ITEM_ITUNE_DURATION = "0:26:53";


    //setting test resources
    ParserUtils parserUtils = mock(ParserUtils.class);
    static Logger logger = Logger.getLogger(ParserDomImplTest.class);
    final String TEST_FILE_PATH = "src/main/test/resources/feeds.xml";
    final String STRING_URL = "http://mediaweb.musicradio.com/RSSFeed.xml?Channel=9172";
    final static String NAMESPACE_URI = "http://www.itunes.com/dtds/podcast-1.0.dtd";
    static Namespace ITUNE_NS = Namespace.getNamespace("itunes", NAMESPACE_URI);
    File xmlFile = null;
    ParserDomImpl parser = null;
    URL URL = null;
    Document document;

    @Before
    public void init() throws IOException, SAXException, ParserConfigurationException {
        // creading the test file
        xmlFile = new File(TEST_FILE_PATH);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        document = db.parse(xmlFile);

        //  setting up the parser. Only testing the parsing so we are mocking the reader Utils class
        this.URL = new URL(STRING_URL);
        parser = new ParserDomImpl(URL, ITUNE_NS);
        parser.setParserUtils(parserUtils);
        when(parserUtils.read(URL)).thenReturn(document);
    }

    @Test
    public void parserTest() throws ParserConfigurationException, InvalidRssFeedException, InvalidDateFormatException, SAXException, IOException, ParseException {

        Feed feed = parser.parse();
        assertEquals(CHANNEL_TITLE, feed.getTitle());
        assertEquals(CHANNEL_LINK, feed.getLink());
        assertEquals(CHANNEL_DESCRIPTION, feed.getDescription());
        assertEquals(CHANNEL_LANGUAGE, feed.getLanguage());
        assertEquals(CHANNEL_COPYRIGHT, feed.getCopyright());
        assertEquals(CHANNEL_ITUNES_SUBTITLE, feed.getItune().getSubtitle());
        assertEquals(CHANNEL_ITUNES_AUTHOR, feed.getItune().getAuthor());
        assertEquals(CHANNEL_ITUNES_SUMMARY, feed.getItune().getSummary());
        assertEquals(CHANNEL_ITUNES_IMAGE, feed.getItune().getImage());
        assertEquals(CHANNEL_ITUNES_EXPICIT, feed.getItune().isExplicit());
        assertEquals(CHANNEL_ITUNES_NEW_FEED_URL, feed.getItune().getNewFeedUrl());
        assertEquals(CHANNEL_ITUNES_OWNER_NAME, feed.getItune().getOwner().getName());
        assertEquals(CHANNEL_ITUNES_OWNER_EMAIL, feed.getItune().getOwner().getEmail());
        assertEquals(CHANNEL_ITUNES_CATEGORY, feed.getItune().getCategory());

        for (int i = 0; i < feed.getPodcasts().size(); i++) {
            String id = String.valueOf(i+1);
            Podcast p = feed.getPodcasts().get(i);
            assertEquals(id, p.getId());
            assertEquals(ITEM_AUTHOR + id, p.getAuthor());
            assertEquals(ITEM_TITLE + id, p.getTitle());
            assertEquals(ITEM_DESCRIPTION + id, p.getDescription());
            assertEquals(ITEM_ENCLOSURE_TYPE, p.getEnclosure().getType());
            assertEquals(ITEM_ENCLOSURE_URL + id, p.getEnclosure().getUrl());
            assertEquals(ITEM_ENCLOSURE_LENGHT, p.getEnclosure().getLength());
            assertEquals(ITEM_ITUNE_SUBTITLE + id, p.getItune().getSubtitle());
            assertEquals(ITEM_ITUNE_EXPLICIT, p.getItune().isExplicit());
            assertEquals(ITEM_ITUNE_SUMMARY + id, p.getItune().getSummary());
            assertEquals(ITEM_ITUNE_DURATION, p.getItune().getDuration());
        }
    }

    //TODO Test parsing an xml with the wrong namepace
    public void testWrongNamespace() {

    }

}
