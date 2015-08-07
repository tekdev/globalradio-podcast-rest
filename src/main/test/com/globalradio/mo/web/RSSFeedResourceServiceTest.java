package com.globalradio.mo.web;

import com.globalradio.mo.Boot;
import com.globalradio.mo.data.InvalidDateFormatException;
import com.globalradio.mo.data.InvalidRssFeedException;
import com.globalradio.mo.domain.*;
import com.globalradio.mo.service.NotFoundException;
import com.globalradio.mo.service.RssFeedService;
import com.globalradio.mo.service.RssFeedServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Boot.class)
@WebAppConfiguration
@IntegrationTest("server.port=9004")
public class RSSFeedResourceServiceTest {
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

    //setting up injectable autowired mock
    @Mock
    RssFeedService rssFeedService;

    @Autowired
    private EmbeddedWebApplicationContext webApplicationContext;

    @InjectMocks
    private RSSFeedResourceService rssFeedResourceService;

    private MockMvc mockMvc;

    private RestTemplate restTemplate;
    final String START_DATE = "Thu, 30 Jul 2015 10:00:00 GMT";
    final String DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";

    @Before
    public void init() throws ParserConfigurationException, InvalidRssFeedException, InvalidDateFormatException, SAXException, IOException, ParseException {
        // Process mock annotations
        MockitoAnnotations.initMocks(this);

        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        restTemplate = new TestRestTemplate();
    }

    @Test
    public void testHealthEndpoint() {
        ResponseEntity<String> entity =
                restTemplate.getForEntity("http://localhost:9004/rssfeed/health", String.class);

        assertTrue(entity.getStatusCode().is2xxSuccessful());
        assertEquals("podcast-service-rest should be reach from /rssfeed/health ", entity.getBody(), "podcast-service-rest: Up and Running!");
    }

    @Test
    public void testGetRssResourceById() throws Exception {
        //TODO use the mockMvc  a build  a request and get a response
        // TODO get the response as string and convert the json string to Object
        // TODO assert the expected object against the actual
    }

    @Test
    public void testGetRssResources() throws Exception {
        //TODO use the mockMvc  a build  a request and get a response
        // TODO get the response as string and convert the json string to Object
        // TODO assert the expected object against the actual
    }

    @Test
    public void testGetRssResourcesTheMostRecent() throws Exception {
        //TODO use the mockMvc  a build  a request and get a response
        // TODO get the response as string and convert the json string to Object
        // TODO assert the expected object against the actual
    }

    @Test
    public void testGetRssResourcesAlternate() throws Exception {
        //TODO use the mockMvc  a build  a request and get a response
        // TODO get the response as string and convert the json string to Object
        // TODO assert the expected object against the actual
    }

    @Test
    public void testGetRssResourcesByIdNotFount() throws Exception {
        //TODO use the mockMvc  a build  a request and get a response
        // TODO assertthat response code is 404
    }

    @Test
    public void testGetRssResourcesNotFound() throws Exception {
        //TODO use the mockMvc  a build  a request and get a response
        // TODO assertthat response code is 404
    }

    @Test
    public void testGetRssResourcesServerError() throws Exception {
        //TODO use the mockMvc  a build  a request and get a response
        // TODO assertthat response code is 500
    }

    private Feed getExpectedFeed(int numberOfItems) throws ParseException {
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

        for (int i = 0; i < numberOfItems; i++) {
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