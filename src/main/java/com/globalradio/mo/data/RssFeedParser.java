package com.globalradio.mo.data;


import com.globalradio.mo.domain.Enclosure;
import com.globalradio.mo.domain.Itune;
import com.globalradio.mo.domain.Owner;
import com.globalradio.mo.domain.Podcast;
import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndEntry;
import org.jdom.Element;

import java.util.ArrayList;
import java.util.List;

public class RssFeedParser {

    public Itune parseItune(List<Element> elements) throws InvalidRssFeedException {
        Itune itune = new Itune();

        for (Element e : elements) {
            if (Validator.validateNamespace(e.getNamespace())) {
                if (e.getName().equals("subtitle")) {
                    itune.setSubtitle(e.getText());
                }

                if (e.getName() == "author") {
                    itune.setAuthor(e.getText());
                }

                if (e.getName() == "summary") {
                    itune.setSummary(e.getText());
                }

                if (e.getName() == "image") {
                    itune.setImage(e.getAttributeValue("href"));
                }

                if (e.getName() == "explicit") {
                    itune.setIsExplicit(e.getText());
                }

                if (e.getName() == "new-feed-url") {
                    itune.setNewFeedUrl(e.getText());
                }

                if (e.getName() == "owner") {

                    Owner newOwner = new Owner();
                    List<Element> ownerElements = (List<Element>) e.getContent();
                    for (int i = 0; i < ownerElements.size(); i++) {
                        if (ownerElements.get(i).getName() == "name") {
                            newOwner.setName(ownerElements.get(i).getText());
                        }

                        if (ownerElements.get(i).getName() == "email") {
                            newOwner.setEmail(ownerElements.get(i).getText());
                        }
                    }

                    itune.setOwner(newOwner);
                }

                if (e.getName() == "category") {
                    itune.setCategory(e.getAttributeValue("text"));
                }

                if (e.getName() == "duration") {
                    itune.setDuration(e.getText());
                }
            }
        }

        return itune;
    }

    public List<Podcast> parsePodcasts(List<SyndEntry> entries) throws InvalidRssFeedException {
        List<Podcast> podcasts = new ArrayList<>();
        for (SyndEntry entry : entries) {
            Podcast p = new Podcast();
            p.setId(entry.getUri());
            // TODO  p.setIsPermaLink();
            p.setTitle(entry.getTitle());
            p.setDescription(entry.getDescription().getValue());
            DateModule dateModule = new DateModuleImpl();
            dateModule.copyFrom(entry.getModule(DateModule.URI));
            p.setPubDate(dateModule.getDate());

            // getting the enclosure
            SyndEnclosure enc = (SyndEnclosure) entry.getEnclosures().get(0);
            Enclosure enclosure = new Enclosure();
            enclosure.setLength(enc.getLength());
            enclosure.setType(enc.getType());
            enclosure.setUrl(enc.getUrl());
            p.setEnclosure(enclosure);
            p.setItune(parseItune((List<Element>) entry.getForeignMarkup()));

            podcasts.add(p);
        }
        return podcasts;
    }
}
