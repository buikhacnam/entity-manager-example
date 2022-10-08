package com.buinam.entitymanagerexample;

import com.buinam.entitymanagerexample.entity.Contact;
import com.buinam.entitymanagerexample.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class EntityManagerExampleApplication implements CommandLineRunner {

	@Autowired
	private ContactRepository contactRepository;

	public static void main(String[] args) {
		SpringApplication.run(EntityManagerExampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		saveContact();
//		findAll();
//		findById();
//		update();
//		deleteById();
		search();
	}

	private void saveContact() {
		Contact contact = new Contact();
		contact.setName("casey bui");
		contact.setEmail("caseybui@gmail.com");
		contact.setAddress("New York");
		contactRepository.saveContact(contact);
	}

	private void findAll() {
		contactRepository.findAll().forEach(System.out::println);
	}

	private  void findById() {
		Contact contact = contactRepository.findById(1L);
		System.out.println(contact);
	}

	private void update() {
		Contact contact = contactRepository.findById(1L);
		contact.setName("Nam Bui");
		contactRepository.update(contact);
	}

	private void deleteById() {
		contactRepository.deleteById(1L);
	}

	private void search() {
		List<Contact> result = contactRepository.search("", "", "", "", "desc");
		System.out.println("result");
		result.forEach(System.out::println);

		List<Contact> result1 = contactRepository.search("drogba", "", "", "name", "asc");
		System.out.println("result1");
		result1.forEach(System.out::println);

		List<Contact> result2 = contactRepository.search("", "", "", "email", "desc");
		System.out.println("result2");
		result2.forEach(System.out::println);

		List<Contact> result3 = contactRepository.search("casey", "caseybui", "new york", "", "");
		System.out.println("result3");
		result3.forEach(System.out::println);
	}

}
