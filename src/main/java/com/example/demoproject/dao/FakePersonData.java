package com.example.demoproject.dao;

import com.example.demoproject.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// This class needs to be instantiated as a bean so that we can inject it on all the classes is by using
// @Repository annotation

@Repository("fakeDao")
public class FakePersonData implements PersonDao{

    private  static List<Person> db = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        db.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return db;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return db.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> checkPerson = selectPersonById(id);
        if(checkPerson.isEmpty()){
            return 0;
        }
        db.remove(checkPerson.get());
        return 1;
    }

    @Override
    public int updatePersonById(UUID id, Person newPerson) {
        return selectPersonById(id)
                .map(person -> {
                    int indexOfPersonToUpdate = db.indexOf(person);
                    if(indexOfPersonToUpdate >= 0){
                        db.set(indexOfPersonToUpdate, new Person(id, newPerson.getName()));
                        return 1;
                    }
                    return 0;
                })
                .orElse(0);
    }
}
