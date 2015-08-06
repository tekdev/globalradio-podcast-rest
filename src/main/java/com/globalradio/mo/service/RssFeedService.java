package com.globalradio.mo.service;


import com.globalradio.mo.data.InvalidRssFeedException;
import com.globalradio.mo.domain.Rss;
import com.sun.syndication.io.FeedException;

import java.io.IOException;

public interface RssFeedService {

    public Rss getRssFeedPodcastById(String id, boolean alternate) throws InvalidRssFeedException, IOException, FeedException;

    public Rss getRssFeedPodcasts(boolean latest) throws InvalidRssFeedException, IOException, FeedException;
}
