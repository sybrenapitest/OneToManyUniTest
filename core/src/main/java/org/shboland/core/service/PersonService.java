package org.shboland.core.service;

import org.shboland.persistence.db.repo.CarRepository;
import org.shboland.persistence.db.hibernate.bean.Car;
import java.util.List;
import org.shboland.persistence.criteria.PersonSearchCriteria;
import java.util.Optional;
import org.shboland.persistence.db.hibernate.bean.Person;
import org.shboland.persistence.db.repo.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class PersonService {

    private final PersonRepository personRepository;
    private final CarRepository carRepository;
    // @FieldInput

    @Autowired
    public PersonService(CarRepository carRepository, PersonRepository personRepository) {
        this.personRepository = personRepository;
        this.carRepository = carRepository;
        // @ConstructorInput
    }
    
    // @Input

    public Person fetchPersonForCar(long carId) {
        Optional<Car> carOptional = carRepository.findById(carId);
        return carOptional.isPresent() && carOptional.get().getPerson() != null ? carOptional.get().getPerson() : null;
    }
  
    public int findNumberOfPerson(PersonSearchCriteria sc) {
        return personRepository.findNumberOfPersonBySearchCriteria(sc);
    }
    

    public List<Person> findBySearchCriteria(PersonSearchCriteria sc) {
        return personRepository.findBySearchCriteria(sc);
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public Optional<Person> fetchPerson(long personId) {
        return personRepository.findById(personId);
    }

    public boolean deletePerson(long personId) {
        Optional<Person> person = personRepository.findById(personId);

        if (person.isPresent()) {
            personRepository.delete(person.get());
            return true;
        } else {
            return false;
        }
    }
    
}