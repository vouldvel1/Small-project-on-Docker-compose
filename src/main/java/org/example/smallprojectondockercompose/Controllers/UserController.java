package org.example.smallprojectondockercompose.Controllers;

import org.example.smallprojectondockercompose.Models.Tutorial;
import org.example.smallprojectondockercompose.Repositories.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/nrt")
public class UserController {

    @Autowired
    TutorialRepository tutorialRepository;

    @GetMapping
    public ResponseEntity<List<Tutorial>> findAll() {
        List<Tutorial> tutorials = tutorialRepository.findAll();
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tutorial> getUser(@PathVariable String id) {
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

        return tutorialData.map(tutorial -> new ResponseEntity<>(tutorial, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public ResponseEntity<Tutorial> createUser(@RequestBody Tutorial tutorial) {
        try {
            Tutorial _tutorial = tutorialRepository.save(new Tutorial(tutorial.getLogin(), tutorial.getPassword(), tutorial.getName(), tutorial.getSurName()));
            return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Tutorial> updateUser(@PathVariable String id, @RequestBody Tutorial tutorial) {
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

        if (tutorialData.isPresent()) {
            Tutorial _tutorial = tutorialData.get();

            _tutorial.setName(tutorial.getName());
            _tutorial.setSurName(tutorial.getSurName());

            _tutorial.setLogin(tutorial.getLogin());
            _tutorial.setPassword(tutorial.getPassword());
            return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Tutorial> deleteUser(@PathVariable String id, @RequestParam(required = false) String login, @RequestParam(required = false) String password) {
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
        if (tutorialData.isPresent()) {
            if(tutorialData.get().getLogin().equals(login) && tutorialData.get().getPassword().equals(password)) {
                tutorialRepository.delete(tutorialData.get());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
