package com.example.demoproject.api;

import com.example.demoproject.model.Person;
import com.example.demoproject.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

// @RequestMapping : General purpose mapping we can provide method like GET, POST etc.
// @GetMapping : Handles HTTP Get request.
// @PostMapping : Handles HTTP Post request.
// @DeleteMapping : Handles HTTP Delete request.
// @PutMapping : Handles HTTP Put request. // Updating
// @PatchMapping : Handles HTTP Patch request.


@RequestMapping("/api/v1/person")
@RestController
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public void addPerson(@RequestBody Person person){
        personService.addPerson(person);
    }

    @GetMapping
    public List<Person> getAllPeople(){
        return personService.getAllPeople();
    }

    // getting id from url.
    // we have to type id in url for e.g. /api/v1/person/iAmId
    @GetMapping(path = "{id}")
    public Person getPersonById(@PathVariable("id") UUID id){
        return personService.getPersonById(id)
                .orElse(null);
    }

    @DeleteMapping(path = "{id}")
    public void deletePersonById(@PathVariable("id") UUID id){
         personService.deletePersonById(id);
    }

    @PutMapping(path = "{id}")
    public void updatePersonById(@PathVariable("id") UUID id, @Valid @NotNull @RequestBody Person newPerson){
        personService.updatePersonById(id, newPerson);
    }
}
