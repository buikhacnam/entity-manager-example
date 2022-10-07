package com.buinam.entitymanagerexample.repository;
import com.buinam.entitymanagerexample.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ContactRepository  {
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void saveContact(Contact contact) {
        // persist method is used to save the entity. It is used to insert the data into the database.
        // The persist method returns void.
        // https://www.baeldung.com/hibernate-save-persist-update-merge-saveorupdate
        entityManager.persist(contact);
    }

    public List<Contact> findAll() {
        String jpql = "select c from Contact c";
        TypedQuery<Contact> query = entityManager.createQuery(jpql, Contact.class);
        return query.getResultList();
    }

    public Contact findById(Long id) {
        return entityManager.find(Contact.class, id);
    }

    @Transactional
    public Contact update(Contact contact) {
        return entityManager.merge(contact);
    }

    @Transactional
    public void deleteById(Long id) {
        Contact contact = findById(id);
        entityManager.remove(contact);
    }

}
