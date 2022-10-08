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

    // find by Name, Email, Address. Sort by Name, Email, Address (asc, desc)
    public List<Contact> search(String name, String email, String address, String sortValue, String sortDirection) {
        String jpql = "select c from Contact c where 1=1";

        if (name != null && !name.isEmpty()) {
            jpql += " and c.name like :name";
        }
        if (email != null && !email.isEmpty()) {
            jpql += " and c.email like :email";
        }
        if (address != null && !address.isEmpty()) {
            jpql += " and c.address like :address";
        }

        if(sortDirection == null || sortDirection.isEmpty()) {
            sortDirection = "desc";
        }

        if (sortValue == null || sortValue.isEmpty()) {
            sortValue = "id";
        }

        jpql += " order by c." + sortValue + " " + sortDirection;

        System.out.println("jpql: " + jpql);
        TypedQuery<Contact> query = entityManager.createQuery(jpql, Contact.class);
        if (name != null && !name.isEmpty()) {
            query.setParameter("name", "%" + name + "%");
        }
        if (email != null && !email.isEmpty()) {
            query.setParameter("email", "%" + email + "%");
        }
        if (address != null && !address.isEmpty()) {
            query.setParameter("address", "%" + address + "%");
        }

        return query.getResultList();
    }

}
