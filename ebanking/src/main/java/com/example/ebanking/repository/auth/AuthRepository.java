package com.example.ebanking.repository.auth;

import com.example.ebanking.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AuthRepository {

    private EntityManager entityManager;

    @Autowired
    public AuthRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<User> login(String email, String password) {
        try {
            return Optional.ofNullable((User) entityManager.createQuery("SELECT u FROM User u WHERE email = :email AND password = :password")
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void register(User user) {
        EntityManager em = entityManager.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error creating user: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public Optional<User> findByEmail(String email) {
        try {
            return Optional.ofNullable((User) entityManager.createQuery("SELECT u FROM User u WHERE email = :email")
                    .setParameter("email", email)
                    .getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
