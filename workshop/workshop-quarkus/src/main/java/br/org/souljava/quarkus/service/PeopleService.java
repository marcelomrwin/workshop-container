package br.org.souljava.quarkus.service;

import java.util.List;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.org.souljava.quarkus.model.People;

@Path("/api/people")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PeopleService {

	@Inject
	EntityManager em;

	private final Logger log = LoggerFactory.getLogger(PeopleService.class);

	@GET
	public List<People> get() {
		log.info("Getting all People");
		
		return em.createQuery("select p from People p order by p.id").getResultList();
	}

	@GET
	@Path("{id}")
	public People getSingle(@PathParam("id") Long id) {
		log.info("Getting a single People");
		
		People entity = em.find(People.class, id);
		if (entity == null) {
			throw new WebApplicationException("People with id of " + id + " does not exist.", 404);
		}
		return entity;
	}

	@POST
	@Transactional
	public Response create(People people) {

		if (Objects.isNull(people)) {
			throw new WebApplicationException("Invalid payload!", 415);
		}

		if (people.getId() != null) {
			throw new WebApplicationException("Id was invalidly set on request.", 422);
		}

		em.persist(people);
		return inputHeaders(Response.ok(people)).status(201).build();
	}

	@PUT
	@Path("{id}")
	@Transactional
	public Response update(@PathParam("id") Long id, People people) {
		if (Objects.isNull(people)) {
			throw new WebApplicationException("Invalid payload!", 415);
		}

		if (Objects.isNull(people.getName())) {
			throw new WebApplicationException("People Name was not set on request.", 422);
		}

		People entity = em.find(People.class, id);

		if (entity == null) {
			throw new WebApplicationException("People with id of " + id + " does not exist.", 404);
		}
		people.setId(id);
		em.merge(people);
		em.flush();

		return inputHeaders(Response.ok(entity)).build();

	}

	@DELETE
	@Path("{id}")
	@Transactional
	public Response delete(@PathParam("id") Long id) {
		People entity = em.find(People.class, id);
		if (entity == null) {
			throw new WebApplicationException("People with id of " + id + " does not exist.", 404);
		}
		em.remove(entity);
		return inputHeaders(Response.status(204)).build();
	}

	@Provider
	public static class ErrorMapper implements ExceptionMapper<Exception> {

		@Override
		public Response toResponse(Exception exception) {

			int code = 500;
			if (exception instanceof WebApplicationException) {
				code = ((WebApplicationException) exception).getResponse().getStatus();
			}

			return Response.status(code)
					.entity(Json.createObjectBuilder().add("error", exception.getMessage()).add("code", code).build())
					.build();
		}
	}

	// UGLY WORKAROUND
	@OPTIONS
	@Path("{path : .*}")
	public Response options() {
		return Response.ok("").header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Max-Age", "1209600").build();
	}

	private ResponseBuilder inputHeaders(ResponseBuilder rb) {
		rb.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Max-Age", "1209600");

		return rb;
	}

}
