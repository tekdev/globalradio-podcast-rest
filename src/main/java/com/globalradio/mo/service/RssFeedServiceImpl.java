package com.globalradio.mo.service;

import com.globalradio.mo.data.RssReader;
import com.globalradio.mo.data.InvalidRssFeedException;
import com.globalradio.mo.domain.Podcast;
import com.globalradio.mo.domain.Rss;
import com.sun.syndication.io.FeedException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("rssFeedService")
public class RssFeedServiceImpl implements RssFeedService {

    RssReader reader = new RssReader();

    @Override
    public Rss getRssFeedPodcastById(String id, boolean alternate) throws InvalidRssFeedException, IOException, FeedException {
        Rss allFeed = reader.read();
        Rss reducedFeed = copyRootChannelInfo(allFeed);

        if (alternate) {
            List<Podcast> podcasts = allFeed.getPodcasts().stream().filter(p -> !p.getId().equals(id)).collect(Collectors.toList());
            reducedFeed.setPodcasts(podcasts);
        } else {
            Optional<Podcast> podcast = allFeed.getPodcasts().stream().filter(p -> p.getId().equals(id)).findFirst();
            if (podcast.isPresent()) {
                List<Podcast> podcasts = new ArrayList<>();
                podcasts.add(podcast.get());
                reducedFeed.setPodcasts(podcasts);
            }
        }
        return reducedFeed;
    }

    @Override
    public Rss getRssFeedPodcasts(boolean latest) throws InvalidRssFeedException, IOException, FeedException {
        return reader.read();
    }

    private Rss copyRootChannelInfo(Rss allFeeds) {
        Rss reducedFeed = new Rss();
        reducedFeed.setTitle(allFeeds.getTitle());
        reducedFeed.setItune(allFeeds.getItune());
        reducedFeed.setDescription(allFeeds.getDescription());
        reducedFeed.setCopyright(allFeeds.getCopyright());
        reducedFeed.setLanguage(allFeeds.getLanguage());
        return reducedFeed;
    }
}