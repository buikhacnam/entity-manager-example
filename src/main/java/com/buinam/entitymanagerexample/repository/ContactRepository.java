package com.buinam.entitymanagerexample.repository;
import com.buinam.entitymanagerexample.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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


    // find by Name, Email, Address. Sort by Name, Email, Address (asc, desc) with Pageable
    public Page<Contact> search(String name, String email, String address, String sortValue, String sortDirection, Pageable pageable) {
        String jpql = "select c from Contact c where 1=1";
        String countJpql = "select count(c) from Contact c where 1=1";

        if (name != null && !name.isEmpty()) {
            jpql += " and c.name like :name";
            countJpql += " and c.name like :name";
        }
        if (email != null && !email.isEmpty()) {
            jpql += " and c.email like :email";
            countJpql += " and c.email like :email";
        }
        if (address != null && !address.isEmpty()) {
            jpql += " and c.address like :address";
            countJpql += " and c.address like :address";
        }

        if(sortDirection == null || sortDirection.isEmpty()) {
            sortDirection = "desc";
        }

        if (sortValue == null || sortValue.isEmpty()) {
            sortValue = "id";
        }

        jpql += " order by c." + sortValue + " " + sortDirection;
        countJpql += " order by c." + sortValue + " " + sortDirection;

        System.out.println("jpql: " + jpql);
        System.out.println("countJpql: " + countJpql);

        TypedQuery<Contact> query = entityManager.createQuery(jpql, Contact.class);
        Query queryCount = entityManager.createQuery(countJpql);

        if (name != null && !name.isEmpty()) {
            query.setParameter("name", "%" + name + "%");
            queryCount.setParameter("name", "%" + name + "%");
        }

        if (email != null && !email.isEmpty()) {
            query.setParameter("email", "%" + email + "%");
            queryCount.setParameter("email", "%" + email + "%");
        }

        if (address != null && !address.isEmpty()) {
            query.setParameter("address", "%" + address + "%");
            queryCount.setParameter("address", "%" + address + "%");
        }

        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        List<Contact> contacts = query.getResultList();

        long count = (long) queryCount.getSingleResult();

        return new PageImpl<>(contacts, pageable, count);
    }

    // TODO: example of finding by Name, Email, Address. Sort by Name, Email, Address (asc, desc) with Pageable and JOIN other tables
    // TODO: retrieve data from table that does not exist in entity folder

}
