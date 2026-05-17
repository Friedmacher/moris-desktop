package com.moris.desktop.security.audit;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
	/**
	 * Returns the current auditor of the application.
	 *
	 * @return the current auditor.
	 */
	@Override
	public Optional<String> getCurrentAuditor() {
		return Optional.of("Administrator");
	}
}
