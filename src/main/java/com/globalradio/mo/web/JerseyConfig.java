package com.globalradio.mo.web;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(RSSFeedResourceService.class);
    }




}