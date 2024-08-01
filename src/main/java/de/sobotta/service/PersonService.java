package de.sobotta.service;

import de.sobotta.DTO.PersonDTO;
import de.sobotta.model.Person;
import de.sobotta.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public List<PersonDTO> findAll() {
        List<Person> persons = (List<Person>) personRepository.findAll();
        List<PersonDTO> personDTOs = new ArrayList<>();
        for (Person p : persons) {
            PersonDTO personDTO = mapToDTO(p);
            personDTOs.add(personDTO);
        }
        return personDTOs;
    }

    public PersonDTO findById(long personId) {
        Optional<Person> optionalPerson = personRepository.findById(personId);
        if (optionalPerson.isPresent()) {
            return mapToDTO(optionalPerson.get());
        } else {
            throw new RuntimeException("Person not found with id: " + personId);
        }
    }

    public PersonDTO save(PersonDTO personDTO) {
        Person person = mapToPerson(personDTO);
        person.setPersonNr(generatedPersonNr());
        personRepository.save(person);
        return mapToDTO(person);
    }

    public PersonDTO update(long personId, PersonDTO personDTO) {
        Optional<Person> optionalPerson = personRepository.findById(personId);
        if (optionalPerson.isPresent()) {
            Person toUpdate = optionalPerson.get();
            toUpdate.setFirstName(personDTO.getFirstName());
            toUpdate.setLastName(personDTO.getLastName());
            toUpdate.setBirthDay(personDTO.getBirthDay());
            toUpdate.setStreet(personDTO.getStreet());
            toUpdate.setCity(personDTO.getCity());
            personRepository.save(toUpdate);
            return mapToDTO(toUpdate);
        } else {
            throw new RuntimeException("Person not found with id: " + personId);
        }
    }

    public void delete(long personId) {
        if (personRepository.existsById(personId)) {
            personRepository.deleteById(personId);
        } else {
            throw new RuntimeException("Person not found with id: " + personId);
        }
    }

    private String generatedPersonNr() {
        int numberPart = new Random().nextInt(99999);
        return "P" + String.format("%05d", numberPart);
    }

    private PersonDTO mapToDTO(Person person) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setPersonId(person.getPersonId());
        personDTO.setPersonNr(person.getPersonNr());
        personDTO.setFirstName(person.getFirstName());
        personDTO.setLastName(person.getLastName());
        personDTO.setBirthDay(person.getBirthDay());
        personDTO.setStreet(person.getStreet());
        personDTO.setCity(person.getCity());
        return personDTO;
    }

    private Person mapToPerson(PersonDTO personDTO) {
        Person person = new Person();
        person.setPersonId(personDTO.getPersonId());
        person.setPersonNr(personDTO.getPersonNr());
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setBirthDay(personDTO.getBirthDay());
        person.setStreet(personDTO.getStreet());
        person.setCity(personDTO.getCity());
        return person;
    }
}
