package com.vince.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootInventoryApplication.class, args);
//		LocalDate datenow = LocalDate.now();
//		System.out.println(datenow);
		
		//JSON WITH INNER JSON
//		JsonObject person = new JsonObject();
//		person.addProperty("firstName", "Sergey");
//		person.addProperty("lastName", "Kargopolov");
//		// Create Inner JSON Object 
//		JsonObject address = new JsonObject();
//		address.addProperty("country", "ru");
//		address.addProperty("city", "Moscow");
//		person.add("address", address);
//		System.out.println(person.toString());
		
//		JsonObject respo = new JsonObject();
//		respo.addProperty("asd",1);
//		List<Object> list = new ArrayList<>();
	}

}
