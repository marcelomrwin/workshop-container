package br.gov.souljava.boot.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.stereotype.Service;

import br.gov.souljava.boot.exception.UnprocessableEntityException;
import br.gov.souljava.boot.model.People;
import br.gov.souljava.boot.repository.PeopleRepository;

@Service
public class PeopleServiceImpl implements PeopleService {

	private final PeopleRepository repository;

	private final Validator validator;

	public PeopleServiceImpl(PeopleRepository repository, Validator validator) {
		this.repository = repository;
		this.validator = validator;
	}

	@Override
	public People findPeopleById(Integer peopleId) {
		return repository.findById(peopleId).orElseThrow(() -> new EntityNotFoundException());
	}

	@Override
	public List<People> getAllPeople() {
		Spliterator<People> people = repository.findAll().spliterator();
		return StreamSupport.stream(people, false).collect(Collectors.toList());
	}

	@Override
	public Boolean peopleExists(Integer peopleId) {
		return repository.findById(peopleId).isPresent();
	}

	@Override
	public People savePeople(People people) {
		validatePeople(people);
		people.setDatein(new Date());
		people.setUpdated(new Date());
		return repository.save(people);
	}
	
	@Override
	public People updatePeople(People people) {
		validatePeople(people);
		people.setUpdated(new Date());
		return repository.save(people);
	}

	@Override
	public void deletePeopleById(Integer peopleId) {
		repository.deleteById(peopleId);
	}

	@Override
	public void deleteAllPeople() {
		repository.deleteAll();
	}

	private void validatePeople(People people) throws UnprocessableEntityException, RuntimeException {
		Set<ConstraintViolation<People>> violations = validator.validate(people);
		if (!violations.isEmpty()) {
			try (StringWriter sw = new StringWriter()) {
				try (PrintWriter pw = new PrintWriter(sw)) {
					for (ConstraintViolation<People> violation : violations) {
						pw.println(violation.getMessage());
					}
				}
				throw new UnprocessableEntityException(sw.toString());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
