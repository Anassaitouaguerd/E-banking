package com.example.ebanking.repository.crud;

import com.example.ebanking.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Read operations - use SUPPORTS or REQUIRED_NEW for better performance
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        try {
            User user = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.ofNullable(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    // Write operations - use REQUIRED (default) for data modification
    @Transactional
    public User save(User user) {
        try {
            entityManager.persist(user);
            return user;
        } catch (PersistenceException e) {
            throw new RuntimeException("Error creating user: " + e.getMessage(), e);
        }
    }

    @Transactional
    public User update(User user) {
        try {
            return entityManager.merge(user);
        } catch (PersistenceException e) {
            throw new RuntimeException("Error updating user: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            User user = entityManager.find(User.class, id);
            entityManager.remove(user);
        } catch (PersistenceException e) {
            throw new RuntimeException("Error deleting user: " + e.getMessage(), e);
        }
    }
}