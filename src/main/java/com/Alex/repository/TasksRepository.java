package com.Alex.repository;

import com.Alex.model.Task;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class TasksRepository implements CrudRepository<Task, Integer> {
    private EntityManager entityManager;

    public TasksRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Task> findAll() {
        return entityManager.createQuery("SELECT t FROM Task t").getResultList();
    }

    @Override
    public void save(Task task) {
        entityManager.getTransaction().begin();
        entityManager.persist(task);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteById(Integer id) {
        Optional<Task> task = findById(id);
        if (task.isPresent()) {
            entityManager.getTransaction().begin();
            entityManager.remove(task);
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public Optional<Task> findById(Integer id) {
        try {
            Task task = entityManager.find(Task.class, id);
            return Optional.of(task);
        } catch (Exception e) {
            System.out.println("Something went wrong...");
        }
        return Optional.empty();
    }

    public Task findByName(String name) {
        try {
            Task task = (Task) entityManager
                    .createQuery("SELECT u  FROM Tasks u WHERE u.name = :name")
                    .setParameter("name", name)
                    .getResultList()
                    .get(0);
            return task;
        } catch (Exception e) {
            return null;
        }
    }
}
