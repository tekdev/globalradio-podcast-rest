package com.globalradio.mo.web;


import com.globalradio.mo.domain.Itune;
import com.globalradio.mo.domain.Rss;
import com.globalradio.mo.domain.Podcast;

import java.util.ArrayList;
import java.util.List;

public class RestUtils {
    public static RssRepresentation getRssfeedRepresentation(Rss rss) {
        RssRepresentation rssRepresentation = new RssRepresentation();
        rssRepresentation.setCopyright(rss.getCopyright());
        rssRepresentation.setDescriptions(rss.getDescription());
        rssRepresentation.setLanguage(rss.getLanguage());
        rssRepresentation.setLink(rss.getLink());
        rssRepresentation.setTitle(rss.getTitle());
        rssRepresentation.setItuneRepresentation(getItuneRepresentation(rss.getItune()));
        rssRepresentation.setPodcastRepresentations(getPodcastRepresentations(rss.getPodcasts()));
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
                podcastRepresentation.setPubDate(p.getPubDate());
                PodcastRepresentation.Enclosure enclosure = podcastRepresentation.new Enclosure(p.getEnclosure().getUrl(), p.getEnclosure().getUrl(), p.getEnclosure().getLength());
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
