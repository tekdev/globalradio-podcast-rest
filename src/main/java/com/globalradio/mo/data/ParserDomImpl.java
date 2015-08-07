package com.globalradio.mo.data;

import com.globalradio.mo.domain.*;
import com.globalradio.mo.utils.DateUtils;
import org.jdom.Namespace;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ParserDomImpl implements RSSFeedParser {
    private final URL URL;
    private final Namespace NAMESPACE;
    private ParserUtils parserUtils;
    private Document document;

    public ParserDomImpl(URL URL, Namespace NAMESPACE) {
        this.URL = URL;
        this.NAMESPACE = NAMESPACE;
        parserUtils = new ParserUtils();
    }

    public void setParserUtils(ParserUtils parserUtils) {
        this.parserUtils = parserUtils;
    }

    @Override
    public Feed parse() throws ParserConfigurationException, IOException, SAXException, InvalidDateFormatException, InvalidRssFeedException {
        document = parserUtils.read(URL);
        final String[] TAG_NAMES = {"item", "channel"};
        Feed feed = new Feed();

        for (int i = 0; i < TAG_NAMES.length; i++) {
            parseChildNodes(document.getElementsByTagName(TAG_NAMES[i]), TAG_NAMES[i].equals("channel"), feed);
        }
        return feed;
    }

    /**
     * iterates through the children nodes for a given parent node
     *
     * @param nodeList
     * @param isChannelTag
     * @param feed
     * @throws InvalidDateFormatException
     */
    public void parseChildNodes(NodeList nodeList, boolean isChannelTag, Feed feed) throws InvalidDateFormatException, InvalidRssFeedException {
        List<Podcast> podcasts = null;
        Podcast p = null;
        Itune itune;

        // if isChannelTag= true this will iterates through nodelist at root level otherwise  will iterates through nodelist of items.
        for (int i = 0; i < nodeList.getLength(); i++) {
            NodeList childNodeList = nodeList.item(i).getChildNodes();
            itune = new Itune();
            if (!isChannelTag) p = new Podcast();

            // if isChannelTag = true this will contain children node for root channel otherwise will contain children node of 1 item node
            for (int j = 0; j < childNodeList.getLength(); j++) {
                Node childNode = childNodeList.item(j);

                if (childNode.getPrefix() != null && childNode.getPrefix().equals("itunes")) {
                    Validator.validateNamespace(childNode.getNamespaceURI(), NAMESPACE.getURI());
                }

                switch (childNode.getNodeName()) {
                    case "guid":
                        if (!isChannelTag) p.setId(childNode.getTextContent());
                        p.setIsPermaLink(Boolean.valueOf(childNode.getAttributes().item(0).getTextContent()));
                        break;
                    case "author":
                        if (!isChannelTag) p.setAuthor(childNode.getTextContent());
                        break;
                    case "title":
                        if (isChannelTag) feed.setTitle(childNode.getTextContent());
                        else p.setTitle(childNode.getTextContent());
                        break;
                    case "description":
                        if (isChannelTag) feed.setDescription(childNode.getTextContent());
                        else p.setDescription(childNode.getTextContent());
                        break;
                    case "pubDate":
                        if (!isChannelTag) p.setPubDate(DateUtils.stringToDate(childNode.getTextContent()));
                        break;
                    case "enclosure":
                        if (!isChannelTag) {
                            Enclosure e = new Enclosure();
                            Map<String, String> attrs = parseAttributes(childNode);
                            e.setUrl(attrs.get("url"));
                            e.setLength(Long.valueOf(attrs.get("length")));
                            e.setType(attrs.get("type"));
                            p.setEnclosure(e);
                        }
                        break;
                    case "link":
                        feed.setLink(childNode.getTextContent());
                        break;
                    case "language":
                        feed.setLanguage(childNode.getTextContent());
                        break;
                    case "copyright":
                        feed.setCopyright(childNode.getTextContent());
                        break;

                    case "itunes:subtitle":
                        itune.setSubtitle(childNode.getTextContent());
                        break;
                    case "itunes:author":
                        itune.setAuthor(childNode.getFirstChild().getTextContent());
                        break;
                    case "itunes:summary":
                        itune.setSummary(childNode.getTextContent());
                        break;
                    case "itunes:image":
                        if (isChannelTag) {
                            Map<String, String> iAttrs = parseAttributes(childNode);
                            itune.setImage(iAttrs.get("href"));
                        }
                        break;
                    case "itunes:explicit":
                        itune.setIsExplicit(childNode.getTextContent());
                        break;
                    case "itunes:new-feed-url":
                        itune.setNewFeedUrl(childNode.getTextContent());
                        break;
                    case "itunes:category":
                        Map<String, String> cAttrs = parseAttributes(childNode);
                        itune.setCategory(cAttrs.get("text"));
                        break;
                    case "itunes:duration":
                        itune.setDuration(childNode.getTextContent());
                        break;
                    case "itunes:owner":
                        Owner owner = new Owner();
                        boolean foundChildren = false;
                        for (int f = 0; f < childNode.getChildNodes().getLength(); f++) {
                            Node ownerNodeChildren = childNode.getChildNodes().item(f);
                            if (ownerNodeChildren.getNodeName().equals("itunes:email")) {
                                owner.setEmail(ownerNodeChildren.getTextContent());
                                foundChildren = true;
                            }
                            if (ownerNodeChildren.getNodeName().equals("itunes:name")) {
                                owner.setName(ownerNodeChildren.getTextContent());
                                foundChildren = true;
                            }
                        }
                        if (foundChildren) itune.setOwner(owner);
                        break;
                }
            }
            if (isChannelTag) feed.setItune(itune);
            else p.setItune(itune);
            {
                if (podcasts == null) {
                    podcasts = new ArrayList<>();
                }
                podcasts.add(p);
            }
        }
        if (!isChannelTag) feed.setPodcasts(podcasts);
    }

    /**
     * collects any found attributes for a given node
     *
     * @param node
     * @return Map<String, String>
     */
    private Map<String, String> parseAttributes(Node node) {
        Map<String, String> attributes = new HashMap<>();
        NamedNodeMap attributesNode = node.getAttributes();

        for (int i = 0; i < attributesNode.getLength(); i++) {
            String childNodeName = attributesNode.item(i).getNodeName();

            switch (childNodeName) {
                case "url":
                    attributes.put("url", attributesNode.item(i).getTextContent());
                    break;
                case "type":
                    attributes.put("type", attributesNode.item(i).getTextContent());
                    break;
                case "length":
                    attributes.put("length", attributesNode.item(i).getTextContent());
                    break;
                case "href":
                    attributes.put("href", attributesNode.item(i).getTextContent());
                    break;
                case "text":
                    attributes.put("text", attributesNode.item(i).getTextContent());
                    break;
            }
        }
        return attributes;
    }
}