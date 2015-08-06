package com.globalradio.mo.data;


import org.jdom.Namespace;

import static com.globalradio.mo.domain.Itune.ITUNE_NS;

public class Validator {

    public static boolean validateNamespace(Namespace actualNamespace) throws InvalidRssFeedException {
        if (actualNamespace.getURI() == ITUNE_NS.getURI() && actualNamespace.getPrefix() == ITUNE_NS.getPrefix()) {
            return true;
        } else {
            throw new InvalidRssFeedException("Unknown namespace: " + actualNamespace.getURI());
        }
    }
}
