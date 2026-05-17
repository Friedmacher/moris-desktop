package com.moris.desktop.security.login;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
@Slf4j
public class PasswordResetEvent extends ApplicationEvent {
	private final String userName;

	/**
	 * Create a new {@code ApplicationEvent} with its {@link #getTimestamp() timestamp}
	 * set to the value returned by {@link Clock#millis()} in the provided {@link Clock}.
	 * <p>This constructor is typically used in testing scenarios.
	 *
	 * @param source the object on which the event initially occurred or with
	 *               which the event is associated (never {@code null})
	 * @param userName  the name of the user who triggers the event
	 */
	public PasswordResetEvent(Object source, String userName) {
		super(source);
		this.userName = userName;
		log.info(String.format("Password reset event for user '%s'", userName));
	}

}
