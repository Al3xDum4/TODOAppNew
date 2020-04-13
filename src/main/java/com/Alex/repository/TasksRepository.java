package com.Alex.repository;

import com.Alex.model.SubTask;
import com.Alex.model.Task;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class TasksRepository implements CrudRepository<Task, Integer> {
    private EntityManager entityManager;

    public TasksRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Task> findAll() {
        return null;
    }

    @Override
    public void save(Task task) {
        entityManager.getTransaction().begin();
        entityManager.persist(task);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteById(Integer id) {
        Task task = findById(id);
        if (task != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(task);
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public Task findById(Integer id) {
        try {
            Task task = entityManager.find(Task.class, id);
            return task;
        }catch (Exception e){
            System.out.println("Something went wrong...");
        }
        return null;
    }

    public Task findByName(String name) {
        try {
            Task task = (Task) entityManager
                    .createQuery("SELECT u  FROM Task u WHERE u.name = :name")
                    .setParameter("name", name)
                    .getResultList()
                    .get(0);
            return task;
        } catch (Exception e) {
            return null;
        }
    }
}