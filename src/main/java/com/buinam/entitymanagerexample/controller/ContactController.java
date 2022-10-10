package com.buinam.entitymanagerexample.controller;

import com.buinam.entitymanagerexample.entity.Contact;
import com.buinam.entitymanagerexample.repository.ContactRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @GetMapping // http://localhost:8080/contact?name=&email=&address=&sortValue=&sortDirection=&page=1&size=4
    public Page<Contact> getContact(String name, String email, String address, String sortValue, String sortDirection, Pageable pageable) {
        try {
            return contactRepository.search(name, email, address, sortValue, sortDirection, pageable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}


