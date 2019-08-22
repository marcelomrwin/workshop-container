package br.gov.souljava.boot.controller;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.gov.souljava.boot.exception.NotFoundException;
import br.gov.souljava.boot.exception.UnprocessableEntityException;
import br.gov.souljava.boot.exception.UnsupportedMediaTypeException;
import br.gov.souljava.boot.model.People;
import br.gov.souljava.boot.service.PeopleService;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/people")
public class PeopleController {

	private final PeopleService service;

	public PeopleController(PeopleService service) {
		this.service = service;
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Get a people", notes = "Recovery a people from the database ", response = People.class, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public People get(@PathVariable("id") Integer id) {
		try {
			return service.findPeopleById(id);
		} catch (EntityNotFoundException e) {
			throw new NotFoundException(String.format("People with id=%d was not found", id));
		}
	}

	@GetMapping
	@ApiOperation(value = "Get all people", notes = "Recovery all people from the database ", response = People.class, produces = MediaType.APPLICATION_JSON)
	public List<People> getAll() {
		return service.getAllPeople();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	@ApiOperation(value = "Insert a new people", notes = "Save a new people in the database ", response = People.class, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public People post(@Valid @RequestBody(required = false) People people) {
		verifyCorrectPayload(people);

		return service.savePeople(people);
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{id}")
	@ApiOperation(value = "Update people", notes = "Update a people in the database ", response = People.class, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public People put(@PathVariable("id") Integer id, @Valid @RequestBody(required = false) People people) {
		verifyPeopleExist(id);
		verifyCorrectPayload(people);

		people.setId(id);
		return service.updatePeople(people);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete a people", notes = "Delete a people with the passed ID in the database ", response = People.class, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public void delete(@PathVariable("id") Integer id) {
		verifyPeopleExist(id);

		service.deletePeopleById(id);
	}

	private void verifyPeopleExist(Integer id) {
		if (!service.peopleExists(id)) {
			throw new NotFoundException(String.format("People with id=%d was not found", id));
		}
	}

	private void verifyCorrectPayload(People people) {
		if (Objects.isNull(people)) {
			throw new UnsupportedMediaTypeException("Invalid payload!");
		}

		if (!Objects.isNull(people.getId())) {
			throw new UnprocessableEntityException("Id was invalidly set on request.");
		}
	}

}
