package com.globalradio.mo.web;

import com.globalradio.mo.data.InvalidDateFormatException;
import com.globalradio.mo.data.InvalidRssFeedException;
import com.globalradio.mo.domain.Feed;
import com.globalradio.mo.service.*;
import com.globalradio.mo.service.NotFoundException;
import com.globalradio.mo.utils.RestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Component
@Path("/rssfeed")
public class RSSFeedResourceService {
    private static Logger logger = Logger.getLogger(RSSFeedResourceService.class);

    @Autowired
    private RssFeedService rssFeedService;

    // useful monitoring the service
    @GET
    @Path("/health")
    @Produces(MediaType.TEXT_PLAIN)
    public String health() {
        return "podcast-service-rest: Up and Running!";
    }

    @GET
    @Path("/latest")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRssResourcesByLatest() {
        Feed rss = null;
        try {
            rss = rssFeedService.getRssFeedPodcasts(true);
        } catch (InvalidRssFeedException | ParserConfigurationException | InvalidDateFormatException | SAXException | IOException e) {
            getErrorResponse(e);
        }
        return Response
                .status(Response.Status.OK)
                .entity(RestUtils.getRssfeedRepresentation(rss)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRssResources() {
        Feed rss = null;
        try {
            rss = rssFeedService.getRssFeedPodcasts(false);
        } catch (InvalidRssFeedException | ParserConfigurationException | InvalidDateFormatException | SAXException | IOException e) {
            getErrorResponse(e);
        }
        return Response
                .status(Response.Status.OK)
                .entity(RestUtils.getRssfeedRepresentation(rss)).build();
    }

    @GET
    @Path("{id}/alternate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRssResourcesAlternate(@PathParam("id") String id) {
        Feed rss = null;
        RssRepresentation rssRepresentation;
        try {
            rss = rssFeedService.getRssFeedPodcastById(id, true);
        } catch (InvalidRssFeedException | ParserConfigurationException | InvalidDateFormatException | SAXException | NotFoundException | IOException e) {
            getErrorResponse(e);
        }
        if (rss.getPodcasts() == null || rss.getPodcasts().size() == 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            rssRepresentation = RestUtils.getRssfeedRepresentation(rss);
        }
        return Response
                .status(Response.Status.OK)
                .entity(rssRepresentation).build();

    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRssResourceById(@PathParam("id") String id) {
        Feed rss = null;
        RssRepresentation rssRepresentation;
        try {
            rss = rssFeedService.getRssFeedPodcastById(id, false);
        } catch (InvalidRssFeedException | ParserConfigurationException | InvalidDateFormatException | SAXException | IOException | NotFoundException e) {
            getErrorResponse(e);
        }
        if (rss.getPodcasts() == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            rssRepresentation = RestUtils.getRssfeedRepresentation(rss);
        }
        return Response
                .status(Response.Status.OK)
                .entity(rssRepresentation).build();
    }

    private Response getErrorResponse(Exception e) {
        if (e.getClass().isInstance(NotFoundException.class)) {
            logger.info(e.getMessage(), e);
            ServiceErrorRepresentation errorRepresentation = new ServiceErrorRepresentation(
                    Response.Status.NOT_FOUND.getStatusCode(),
                    e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(errorRepresentation).build();

        } else {
            logger.error(e.getMessage(), e);
            ServiceErrorRepresentation errorRepresentation = new ServiceErrorRepresentation(
                    Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "something went horrible wrong on the server side");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRepresentation).build();
        }
    }

}