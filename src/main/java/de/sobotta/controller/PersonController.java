package de.sobotta.controller;

import de.sobotta.DTO.PersonDTO;
import de.sobotta.model.Person;
import de.sobotta.repository.PersonRepository;
import de.sobotta.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping
    public List<PersonDTO> getAll() {
        return personService.findAll();
    }

    @GetMapping("{personId}")
    public PersonDTO getById(@PathVariable long personId) {
        return personService.findById(personId);
    }

    @PostMapping
    public PersonDTO create(@RequestBody PersonDTO personDTO) {
        return personService.save(personDTO);
    }

    @PutMapping("{personId}")
    public PersonDTO update(@PathVariable long personId, @RequestBody PersonDTO personDTO) {
        return personService.update(personId, personDTO);
    }

    @DeleteMapping("{personId}")
    public void delete(@PathVariable long personId) {
        personService.delete(personId);
    }
}
