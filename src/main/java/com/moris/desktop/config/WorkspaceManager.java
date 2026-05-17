package com.moris.desktop.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WorkspaceManager {
	@Autowired
	Environment environment;

	private final String                    applicationHome;

	public WorkspaceManager(String applicationHome) {
		this.applicationHome = applicationHome;
	}

	public void showHomePath() {
		log.info("Application Home: {}", applicationHome);
	}
}
