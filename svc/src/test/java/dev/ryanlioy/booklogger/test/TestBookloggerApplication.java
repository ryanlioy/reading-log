package dev.ryanlioy.booklogger.test;

import dev.ryanlioy.booklogger.BookloggerApplication;
import org.springframework.boot.SpringApplication;

public class TestBookloggerApplication {
	public static void main(String[] args) {
		SpringApplication.from(BookloggerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}
}
