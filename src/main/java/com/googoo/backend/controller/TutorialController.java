package com.googoo.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.googoo.backend.dto.EmptyResponse;
import com.googoo.backend.dto.ResponseCode;
import com.googoo.backend.dto.TutorialListResponse;
import com.googoo.backend.dto.TutorialResponse;
import com.googoo.backend.model.Tutorial;
import com.googoo.backend.repository.TutorialRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TutorialController {

    @Autowired
    TutorialRepository tutorialRepository;

    @GetMapping("/tutorials")
    public ResponseEntity<TutorialListResponse> getAllTutorials(@RequestParam(required = false) String title) {
        try {
            List<Tutorial> tutorials = new ArrayList<Tutorial>();

            if (title == null) {
                tutorialRepository.findAll().forEach(tutorials::add);
            } else {
                tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);
            }

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(new TutorialListResponse(), HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(new TutorialListResponse(tutorials), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new TutorialListResponse(e.getMessage(),ResponseCode.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<TutorialResponse> getTutorialById(@PathVariable("id") long id) {
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

        if (tutorialData.isPresent()) {
            return new ResponseEntity<>(new TutorialResponse(tutorialData.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new TutorialResponse("Not found",ResponseCode.ERROR), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/tutorials")
    public ResponseEntity<TutorialResponse> createTutorial(@RequestBody Tutorial tutorial) {
        try {
            Tutorial entity = tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
            return new ResponseEntity<>(new TutorialResponse(entity), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new TutorialResponse(e.getMessage(),ResponseCode.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<TutorialResponse> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

        if (tutorialData.isPresent()) {
            Tutorial entry = tutorialData.get();
            entry.setTitle(tutorial.getTitle());
            entry.setDescription(tutorial.getDescription());
            entry.setPublished(tutorial.isPublished());
            return new ResponseEntity<>(new TutorialResponse(tutorialRepository.save(entry)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new TutorialResponse("Not found...", ResponseCode.ERROR), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<EmptyResponse> deleteTutorial(@PathVariable("id") long id) {
        try {
            tutorialRepository.deleteById(id);
            return new ResponseEntity<>(new EmptyResponse(), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new EmptyResponse(e.getMessage(), ResponseCode.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/tutorials")
    public ResponseEntity<EmptyResponse> deleteAllTutorials() {
        try {
            tutorialRepository.deleteAll();
            return new ResponseEntity<>(new EmptyResponse(), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new EmptyResponse(e.getMessage(), ResponseCode.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<TutorialListResponse> findByPublished() {
        try {
            List<Tutorial> tutorials = tutorialRepository.findByPublished(true);

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(new TutorialListResponse(), HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(new TutorialListResponse(tutorials), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new TutorialListResponse(e.getMessage(), ResponseCode.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @GetMapping("/populate")
    public ResponseEntity<EmptyResponse> populate() {
        try {
            for(int i =1; i< 100; i++) {
                Tutorial t = new Tutorial();
                t.setDescription("Sample tutorial " + i);
                t.setTitle("Title is " + UUID.randomUUID().toString() );
                t.setPublished( i % 2 ==0);
                tutorialRepository.save(t);
            }

            return new ResponseEntity<>(new EmptyResponse(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new EmptyResponse(e.getMessage(), ResponseCode.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
