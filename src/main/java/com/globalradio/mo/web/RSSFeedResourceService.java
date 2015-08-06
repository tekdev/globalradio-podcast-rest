package com.globalradio.mo.web;

import com.globalradio.mo.data.InvalidRssFeedException;
import com.globalradio.mo.domain.Rss;
import com.globalradio.mo.service.RssFeedService;
import com.sun.syndication.io.FeedException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
        Rss rss = null;
        try {
            rss = rssFeedService.getRssFeedPodcasts(true);
        } catch (InvalidRssFeedException e) {
            getErrorResponse(e);
        } catch (IOException e) {
            getErrorResponse(e);
        } catch (FeedException e) {
            getErrorResponse(e);
        }
        return Response
                .status(Response.Status.OK)
                .entity(RestUtils.getRssfeedRepresentation(rss)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRssResources() {
        Rss rss = null;
        try {
            rss = rssFeedService.getRssFeedPodcasts(false);
        } catch (InvalidRssFeedException e) {
            getErrorResponse(e);
        } catch (IOException e) {
            getErrorResponse(e);
        } catch (FeedException e) {
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
        Rss rss = null;
        try {
            rss = rssFeedService.getRssFeedPodcastById(id, true);
        } catch (InvalidRssFeedException e) {
            getErrorResponse(e);
        } catch (IOException e) {
            getErrorResponse(e);
        } catch (FeedException e) {
            getErrorResponse(e);
        }
        return Response
                .status(Response.Status.OK)
                .entity(RestUtils.getRssfeedRepresentation(rss)).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRssResourceById(@PathParam("id") String id) {
        Rss rss = null;
        try {
            rss = rssFeedService.getRssFeedPodcastById(id, false);
        } catch (InvalidRssFeedException e) {
            getErrorResponse(e);
        } catch (IOException e) {
            getErrorResponse(e);
        } catch (FeedException e) {
            getErrorResponse(e);
        }
        return Response
                .status(Response.Status.OK)
                .entity(RestUtils.getRssfeedRepresentation(rss)).build();
    }

    private Response getErrorResponse(Exception e) {
        logger.error(e.getMessage(), e);
        ServiceErrorRepresentation errorRepresentation = new ServiceErrorRepresentation(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                "something went horrible wrong on the server side");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRepresentation).build();
    }

}

