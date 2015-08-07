package com.globalradio.mo.service;


import com.globalradio.mo.utils.DateUtils;
import com.globalradio.mo.data.InvalidDateFormatException;
import com.globalradio.mo.data.InvalidRssFeedException;
import com.globalradio.mo.data.RSSFeedParser;
import com.globalradio.mo.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RssFeedServiceImplTest {
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

    RSSFeedParser parser = mock(RSSFeedParser.class);
    RssFeedServiceImpl rssFeedService;
    Feed expectedFeed;
    final String START_DATE = "Thu, 30 Jul 2015 10:00:00 GMT";
    final String ACTUAL_MOST_RECENT_DATE = "Mon, 03 Aug 2015 10:00:00 GMT";
    final String DATE_FORMAT= "EEE, dd MMM yyyy HH:mm:ss z";

    @Before
    public void init() throws IOException, ParserConfigurationException, InvalidRssFeedException, InvalidDateFormatException, SAXException, ParseException {
        rssFeedService = new RssFeedServiceImpl();
        rssFeedService.setParser(parser);
        expectedFeed = getExpectedFeed();

        when(parser.parse()).thenReturn(expectedFeed);
    }

    @Test
    public void testGetAllPodcasts() throws IOException, ParserConfigurationException, InvalidRssFeedException, InvalidDateFormatException, SAXException, NotFoundException {
        Feed actualFeed = rssFeedService.getRssFeedPodcasts(false);
        assertNotNull("Feed Object should not be null ", actualFeed);
        assertEquals(5, actualFeed.getPodcasts().size());
        assertEquals("1", actualFeed.getPodcasts().get(0).getId());
    }

    @Test
    public void testGetPodcastById() throws IOException, ParserConfigurationException, InvalidRssFeedException, InvalidDateFormatException, SAXException, NotFoundException {
        Feed actualFeed = rssFeedService.getRssFeedPodcastById("3", false);
        assertNotNull("Feed Object should not be null ", actualFeed);
        assertEquals(1, actualFeed.getPodcasts().size());
        assertEquals("3", actualFeed.getPodcasts().get(0).getId());
    }

    @Test
            (expected = NotFoundException.class)
    public void testGetPodcastByIdNotFound() throws NotFoundException, InvalidRssFeedException, SAXException, InvalidDateFormatException, ParserConfigurationException, IOException {
        rssFeedService.getRssFeedPodcastById("10", false);
    }

    @Test
    public void testGetPodcastAlternateTrue() throws NotFoundException, InvalidRssFeedException, SAXException, InvalidDateFormatException, ParserConfigurationException, IOException {
        Feed actualFeed = rssFeedService.getRssFeedPodcastById("3", true);

        assertNotNull("Feed Object should not be null ", actualFeed);
        assertEquals(4, actualFeed.getPodcasts().size());
        boolean isPresent = false;
        for (int i = 0; i < actualFeed.getPodcasts().size(); i++) {
            if (actualFeed.getPodcasts().get(i).getId().equals("3"))
                isPresent = true;
        }
        assertFalse("podcast with id=3 should not be included in the result", isPresent);
    }

    @Test
    public void testGetMostRecentPodcast() throws IOException, ParserConfigurationException, InvalidRssFeedException, InvalidDateFormatException, SAXException, NotFoundException {
        Feed actualFeed = rssFeedService.getRssFeedPodcasts(true);
        assertNotNull("Feed Object should not be null ", actualFeed);
        assertEquals(1, actualFeed.getPodcasts().size());
        String actualMostRecentDate = DateUtils.convertDateToGMTString(actualFeed.getPodcasts().get(0).getPubDate());
        assertEquals(ACTUAL_MOST_RECENT_DATE, actualMostRecentDate);
    }


    private Feed getExpectedFeed() throws ParseException {
        Feed feed = new Feed();
        List<Podcast> podcasts = new ArrayList<>();

        feed.setTitle(CHANNEL_TITLE);
        feed.setLink(CHANNEL_LINK);
        feed.setDescription(CHANNEL_DESCRIPTION);
        feed.setLanguage(CHANNEL_LANGUAGE);
        feed.setCopyright(CHANNEL_COPYRIGHT);

        Itune itune = new Itune();
        itune.setSubtitle(CHANNEL_ITUNES_SUBTITLE);
        itune.setAuthor(CHANNEL_ITUNES_AUTHOR);
        itune.setSummary(CHANNEL_ITUNES_SUMMARY);
        itune.setImage(CHANNEL_ITUNES_IMAGE);
        itune.setIsExplicit(CHANNEL_ITUNES_EXPICIT);
        itune.setNewFeedUrl(CHANNEL_ITUNES_NEW_FEED_URL);
        itune.setOwner(new Owner(CHANNEL_ITUNES_OWNER_NAME, CHANNEL_ITUNES_OWNER_EMAIL));
        itune.setCategory(CHANNEL_ITUNES_CATEGORY);
        feed.setItune(itune);

        for (int i = 0; i < 5; i++) {
            String id = String.valueOf(i + 1);
            Podcast p = new Podcast();
            p.setId(id);
            p.setAuthor(ITEM_AUTHOR + id);
            p.setTitle(ITEM_TITLE + id);
            p.setDescription(ITEM_DESCRIPTION + id);
            Date d = getNewDate(i);
            p.setPubDate(getNewDate(i));
            p.setEnclosure(new Enclosure(ITEM_ENCLOSURE_URL + id, ITEM_ENCLOSURE_TYPE, ITEM_ENCLOSURE_LENGHT));

            Itune it = new Itune();
            it.setSubtitle(ITEM_ITUNE_SUBTITLE + id);
            it.setIsExplicit(ITEM_ITUNE_EXPLICIT);
            it.setSummary(ITEM_ITUNE_SUMMARY + id);
            it.setDuration(ITEM_ITUNE_DURATION);
            p.setItune(it);
            podcasts.add(p);
        }
        feed.setPodcasts(podcasts);
        return feed;
    }

    private Date getNewDate(int increment) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT"));
        c.setTime(sdf.parse(START_DATE));
        c.add(Calendar.DATE, increment);
        return c.getTime();
    }
}
