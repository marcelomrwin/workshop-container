package br.gov.souljava.boot.repository;

import org.springframework.data.repository.CrudRepository;

import br.gov.souljava.boot.model.People;

public interface PeopleRepository extends CrudRepository<People, Integer> {
}
