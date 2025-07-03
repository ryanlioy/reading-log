package dev.ryanlioy.bookloger.test;

import dev.ryanlioy.bookloger.BookloggerApplication;
import org.springframework.boot.SpringApplication;

public class TestBookloggerApplication {
	public static void main(String[] args) {
		SpringApplication.from(BookloggerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}
}
