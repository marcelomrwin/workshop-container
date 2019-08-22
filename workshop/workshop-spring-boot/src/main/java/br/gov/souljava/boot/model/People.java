package br.gov.souljava.boot.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class People {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotNull
	@Size(min = 3, max = 100)
	private String name;
	@NotNull
	@Size(min = 3, max = 100)
	private String surname;
	@Max(100)
	private Integer age;

	private Date datein;
	private Date updated;

	@Email
	private String email;

	public Map<String, String> toMap() {
		Map<String, String> map = new HashMap<>();
		map.put("id", String.valueOf(getId()));
		map.put("name", getName());
		map.put("surname", getSurname());
		map.put("age", String.valueOf(getAge()));
		return map;
	}
}
