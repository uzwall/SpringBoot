package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class fakePersonDataAccessService implements PersonDao {

    private static List<Person> DB = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return DB;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return DB.stream().filter(person -> person.getId().equals(id)).findFirst();
    }

    @Override
    public int updatePerson(UUID id, Person update) {

        return selectPersonById(id).map(
                person -> {
            int indexOfPerson = DB.indexOf(person);
            if (indexOfPerson >= 0) {
                DB.set(indexOfPerson, new Person(id, update.getName()));
                return 1;
            } else {
                return 0;
            }

        }).orElse(0);
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> personmaybe = selectPersonById(id);

        if (personmaybe.isEmpty()) {
            return 0;
        } else {
            DB.remove(personmaybe.get());
            System.out.println("ID" + id + " is deleted");
            return 1;

        }

    }
}
