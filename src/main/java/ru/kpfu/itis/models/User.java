package ru.kpfu.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@SequenceGenerator(name = AbstractEntity.GENERATOR, sequenceName = "users_seq", allocationSize = 1)
public class User extends AbstractAuditableDeletableEntity {

	private String firsName;
	private String lastName;
	private String email;
	private String passwordHash;

	@Enumerated(EnumType.STRING)
	private Role role;

	public enum Role {
		CLIENT, ADMIN
	}
}