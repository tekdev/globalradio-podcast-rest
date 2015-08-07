package com.globalradio.mo.utils;


import com.globalradio.mo.utils.DateUtils;
import com.globalradio.mo.domain.Feed;
import com.globalradio.mo.domain.Itune;
import com.globalradio.mo.domain.Podcast;
import com.globalradio.mo.web.ItuneRepresentation;
import com.globalradio.mo.web.PodcastRepresentation;
import com.globalradio.mo.web.RssRepresentation;

import java.util.ArrayList;
import java.util.List;

public class RestUtils {
    public static RssRepresentation getRssfeedRepresentation(Feed feed) {
        RssRepresentation rssRepresentation = new RssRepresentation();
        rssRepresentation.setCopyright(feed.getCopyright());
        rssRepresentation.setDescriptions(feed.getDescription());
        rssRepresentation.setLanguage(feed.getLanguage());
        rssRepresentation.setLink(feed.getLink());
        rssRepresentation.setTitle(feed.getTitle());
        rssRepresentation.setItuneRepresentation(getItuneRepresentation(feed.getItune()));
        rssRepresentation.setPodcastRepresentations(getPodcastRepresentations(feed.getPodcasts()));
        return rssRepresentation;
    }

    private static List<PodcastRepresentation> getPodcastRepresentations(List<Podcast> podcasts) {
        List<PodcastRepresentation> podcastRepresentations = new ArrayList<>();
        if (podcasts != null) {
            for (Podcast p : podcasts) {
                PodcastRepresentation podcastRepresentation = new PodcastRepresentation();
                podcastRepresentation.setGuid(p.getId());
                podcastRepresentation.setIsPermaLink(p.isPermaLink());
                podcastRepresentation.setTitle(p.getTitle());
                podcastRepresentation.setDescription(p.getDescription());
                podcastRepresentation.setPubDate(DateUtils.convertDateToGMTString(p.getPubDate()));
                PodcastRepresentation.Enclosure enclosure = podcastRepresentation.new Enclosure(p.getEnclosure().getUrl(), p.getEnclosure().getType(), p.getEnclosure().getLength());
                podcastRepresentation.setEnclosure(enclosure);
                podcastRepresentation.setItuneRepresentation(getItuneRepresentation(p.getItune()));
                podcastRepresentations.add(podcastRepresentation);
            }
        }
        return podcastRepresentations;
    }

    private static ItuneRepresentation getItuneRepresentation(Itune itune) {
        ItuneRepresentation ituneRepresentation = new ItuneRepresentation();
        ituneRepresentation.setSubtitle(itune.getSubtitle());
        ituneRepresentation.setAuthor(itune.getAuthor());
        ituneRepresentation.setSummary(itune.getSummary());
        ituneRepresentation.setIsExplicit(itune.isExplicit());

        // setting up optional values
        if (itune.getImage() != null) {
            ituneRepresentation.setImage(itune.getImage());
        }

        if (itune.getDuration() != null) {
            ituneRepresentation.setDuration(itune.getDuration());
        }
        if (itune.getOwner() != null) {
            ituneRepresentation.setOwnerName(itune.getOwner().getName());
            ituneRepresentation.setOwnerEmail(itune.getOwner().getEmail());
        }
        if (itune.getCategory() != null) {
            ituneRepresentation.setCategory(itune.getCategory());
        }
        if (itune.getNewFeedUrl() != null) {
            ituneRepresentation.setNewFeedUrl(itune.getNewFeedUrl());
        }
        return ituneRepresentation;
    }
}
