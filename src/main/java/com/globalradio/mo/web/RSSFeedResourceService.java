package com.globalradio.mo.web;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Component
@Path("/")
public class RSSFeedResourceService {

    // useful monitoring the service
    @GET
    @Path("/health")
    @Produces("plain/text")
    public String health() {
        return "podcast-service-rest: Up and Running!";
    }
}

