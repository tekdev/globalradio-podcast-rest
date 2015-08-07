package com.globalradio.mo.data;


import org.jdom.Namespace;

public class Validator {

    public static boolean validateNamespace(String actualNamespace, String expectedNamespace) throws InvalidRssFeedException {
        if (actualNamespace.equals(expectedNamespace)) {
            return true;
        } else {
            throw new InvalidRssFeedException("Unknown namespace: " + actualNamespace);
        }
    }
}
