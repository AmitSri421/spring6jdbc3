package com.pluralsight.conference;

import com.pluralsight.conference.model.Speaker;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Spring6jdbc3ApplicationTests {

    @Test
    public void testCreateSpeaker() {
        RestTemplate restTemplate = new RestTemplate();
        Speaker speaker = new Speaker();
        speaker.setName("Bobby Smith");
        speaker = restTemplate.postForObject("http://localhost:8080/speaker", speaker, Speaker.class);
        System.out.println("Speaker name: " + speaker.getName());
    }

    @Test
    void testGetSpeakers() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Speaker>> speakersResponse = restTemplate.exchange(
                "http://localhost:8080/speaker", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Speaker>>() {
                });

        assertTrue(speakersResponse.getBody() != null, "Body is null");

        List<Speaker> speakers = speakersResponse.getBody();

        for (Speaker speaker : speakers) {
            System.out.println("Speaker name: " + speaker.getName());
        }
    }

    // No change except String concat
    @Test
    void testGetSpeakersWithSkill() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Speaker>> speakersResponse = restTemplate.exchange(
                "http://localhost:8080/speaker", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Speaker>>() {
                });

        assertTrue(speakersResponse.getBody() != null, "Body is null");

        List<Speaker> speakers = speakersResponse.getBody();

        for (Speaker speaker : speakers) {
            System.out.println("Speaker name: " + speaker.getName() + " Skill:" + speaker.getSkill());
        }
    }

    @Test
    void testGetSpeaker() {
        RestTemplate restTemplate = new RestTemplate();
        Speaker speaker = restTemplate.getForObject("http://localhost:8080/speaker/{id}", Speaker.class, 28);
        System.out.println(speaker.getName());
    }

    @Test
    void testUpdateSpeaker() {
        RestTemplate restTemplate = new RestTemplate();

        //Fetch speaker
        Speaker speaker = restTemplate.getForObject("http://localhost:8080/speaker/{id}", Speaker.class, 29);
        System.out.println("Speaker name before update: " + speaker.getName());

        //Update
        speaker.setName(speaker.getName() + " Sr.");
        restTemplate.put("http://localhost:8080/speaker", speaker);
        System.out.println("Speaker name after update: " + speaker.getName());
    }

    @Test
    void testUpdateBatch() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject("http://localhost:8080/speaker/batch", Object.class);
    }

    @Test
    void testDeleteSpeaker() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete("http://localhost:8080/speaker/delete/{id}", 25);
    }

    @Test
    void testException() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject("http://localhost:8080/speaker/test", Speaker.class);
    }
}
