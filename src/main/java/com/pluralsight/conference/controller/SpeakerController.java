package com.pluralsight.conference.controller;

import com.pluralsight.conference.ServiceError;
import com.pluralsight.conference.model.Speaker;
import com.pluralsight.conference.service.SpeakerService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SpeakerController {

    private SpeakerService speakerService;

    public SpeakerController(SpeakerService speakerService) {
        this.speakerService = speakerService;
    }

    @PostMapping("/speaker")
    public Speaker createSpeaker(@RequestBody Speaker speaker) {
        System.out.println("Name: " + speaker.getName());
        return speakerService.create(speaker);
    }

    @PutMapping("/speaker")
    public Speaker updateSpeaker(@RequestBody Speaker speaker) {
        System.out.println("Name: " + speaker.getName());
        return speakerService.update(speaker);
    }

    @GetMapping("/speaker")
    public List<Speaker> getSpeakers() {
        return speakerService.findAll();
    }

    @GetMapping("/speaker/{id}")
    public Speaker getSpeaker(@PathVariable(value = "id") int id) {
        return speakerService.getSpeaker(id);
    }

    @GetMapping("/speaker/batch")
    public Object batch() {
        speakerService.batch();
        return null;
    }

    @DeleteMapping("/speaker/delete/{id}")
    public Object deleteSpeaker(@PathVariable(value = "id") int id) {
        speakerService.delete(id);
        return null;
    }

    @GetMapping("/speaker/test")
    public void test() {
        throw new DataAccessException("Testing exception thrown") {};
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ServiceError> handleException(RuntimeException ex) {
        ServiceError error = new ServiceError(HttpStatus.OK.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.OK);
    }
}
