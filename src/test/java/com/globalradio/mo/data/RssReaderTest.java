package com.globalradio.mo.data;


import com.globalradio.mo.domain.Rss;
import com.sun.syndication.io.FeedException;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RssReaderTest {

    @Test
    public void testFeedTitle() throws InvalidRssFeedException, IOException, FeedException {


        RssReader reader = new RssReader();

        Rss rss = reader.read();


        // assertEquals("The Xfm Breakfast Show Podcast with Jon Holmes", title);

        SimpleDateFormat formatter = new SimpleDateFormat("EE, dd MM yyyy HH:mm:ss");
        String dateInString = "Thu, 30 Jul 2015 23:00:00 GMT";
        String[] parts = dateInString.split(" ");
        int i = parts.length;

//        try {
//            Date date = formatter.parse(dateInString);
//            System.out.println(date);
//            System.out.println(formatter.format(date));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }


    }


}
