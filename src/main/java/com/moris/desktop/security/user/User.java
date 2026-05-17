package com.moris.desktop.security.user;

import com.moris.desktop.security.audit.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.Audited;

import java.sql.Date;

@Entity
@Table(name = "users", schema = "moris")
@Audited
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Slf4j
public class User extends AuditableEntity {
	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "password")
	private String password;

	@Column(name = "password_initial")
	private boolean passwordInitial;

	@Column(name = "last_login")
	private Date lastLogin;

	@Column(name = "user_type")
	private UserType userType;

	@PrePersist
	void beforeSave() {
		// Called before INSERT
		log.info("User '{}' will be created", this.userName);
	}

	@PostPersist
	void afterSave() {
		// Called after INSERT
		log.info("User '{}' has been created", this.userName);
	}

	@PreUpdate
	void beforeUpdate() {
		// Called before UPDATE
		log.info("User '{}' will be updated", this.userName);
	}

	@PostUpdate
	void afterUpdate() {
		// Called after UPDATE
		log.info("User '{}' has been updated", this.userName);
	}

	@PreRemove
	void beforeRemove() {
		// Called before DELETE
		log.info("User '{}' will be deleted", this.userName);
	}

	@PostRemove
	void afterRemove() {
		// Called after DELETE
		log.info("User '{}' has been deleted", this.userName);
	}

}
