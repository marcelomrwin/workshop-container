package br.gov.souljava.boot.service;

import java.util.List;

import br.gov.souljava.boot.model.People;

public interface PeopleService {

	People findPeopleById(Integer peopleId);

	List<People> getAllPeople();
	
	Boolean peopleExists(Integer peopleId);

	People savePeople(People people);
	
	void deleteAllPeople();

	void deletePeopleById(Integer peopleId);

	People updatePeople(People people);

}