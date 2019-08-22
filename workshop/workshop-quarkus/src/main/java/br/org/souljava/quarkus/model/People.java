package br.org.souljava.quarkus.model;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDateTime;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class People {

	public People() {
		super();
	}

	@Id
	@SequenceGenerator(name = "peopleseq", sequenceName = "peopleseq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "peopleseq")
	private Long id;

	@NotNull
	@Size(min = 3, max = 100)
	private String name;

	@NotNull
	@Size(min = 3, max = 100)
	private String surname;

	@Max(100)
	private Integer age;

	private LocalDateTime datein;

	private LocalDateTime updated;

	@Email
	private String email;

	public String toJson() {
		String jsonString = null;

		JsonObjectBuilder objectBuilder = Json.createObjectBuilder().add("name", name).add("surname", surname)
				.add("age", age).add("email", email);

		if (datein != null)
			objectBuilder.add("datein", datein.toString());
		if (updated != null)
			objectBuilder.add("updated", updated.toString());

		JsonObject jsonObject = objectBuilder.build();

		try (Writer writer = new StringWriter()) {
			Json.createWriter(writer).write(jsonObject);
			jsonString = writer.toString();
		} catch (IOException e) {
			jsonString = "";
		}

		return jsonString;
	}

	public static People getSimpleUser() {
		People people = new People();
		people.name = "Debora";
		people.surname = "Sales";
		people.age = 42;
		people.datein = LocalDateTime.now();
		people.email = "mail@mail.com.br";

		return people;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("People [");
		if (name != null) {
			builder.append("name=");
			builder.append(name);
			builder.append(", ");
		}
		if (surname != null) {
			builder.append("surname=");
			builder.append(surname);
			builder.append(", ");
		}
		if (age != null) {
			builder.append("age=");
			builder.append(age);
			builder.append(", ");
		}
		if (datein != null) {
			builder.append("datein=");
			builder.append(datein);
			builder.append(", ");
		}
		if (updated != null) {
			builder.append("updated=");
			builder.append(updated);
			builder.append(", ");
		}
		if (email != null) {
			builder.append("email=");
			builder.append(email);
		}
		builder.append("]");
		return builder.toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public LocalDateTime getDatein() {
		return datein;
	}

	public void setDatein(LocalDateTime datein) {
		this.datein = datein;
	}

	public LocalDateTime getUpdated() {
		return updated;
	}

	public void setUpdated(LocalDateTime updated) {
		this.updated = updated;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
