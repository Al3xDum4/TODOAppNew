package com.Alex.repository;

import com.Alex.model.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

//TODO: UserRepository: add implementation

public class UserRepository implements CrudRepository<User, String> {
    private EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void save(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteById(String id) {
        Optional<User> user = findById(id);
        if (user.isPresent()) {
            entityManager.getTransaction().begin();
            entityManager.remove(user);
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public Optional<User> findById(String id) {
        // DONE: add try catch
        try {
            User user = entityManager.find(User.class, id);
            return Optional.of(user);
        } catch (Exception e) {
            System.out.println("Something went wrong...");
        }
        return Optional.empty();
    }

    public User findByUsername(String username) {
        try {
            User user = (User) entityManager
                    .createQuery("SELECT u  FROM User u WHERE u.username = :username")
                    .setParameter("username", username)
                    .getResultList()
                    .get(0);
            return user;
        } catch (Exception e) {
            return null;
        }
    }
}
