package com.moris.desktop;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MorisMain {

	public static void main(String[] args) {
		Application.launch(MorisFX.class, args);
	}

}
