package com.globalradio.mo.web;

import com.globalradio.mo.Boot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Boot.class)
@WebAppConfiguration
@IntegrationTest("server.port=9000")
public class RSSFeedResourceServiceTest {

    private RestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void health() {
        ResponseEntity<String> entity =
                restTemplate.getForEntity("http://localhost:9000/health", String.class);

        assertTrue(entity.getStatusCode().is2xxSuccessful());
        assertEquals("podcast-service-rest should be running", entity.getBody().toString(), "podcast-service-rest: Up and Running!");
    }
}