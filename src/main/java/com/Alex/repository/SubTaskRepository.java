package com.Alex.repository;

import com.Alex.model.Project;
import com.Alex.model.SubTask;

import javax.persistence.EntityManager;
import java.util.List;

public class SubTaskRepository implements CrudRepository<SubTask, Integer> {
    private EntityManager entityManager;

    public SubTaskRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public void save(SubTask subTask) {
        entityManager.getTransaction().begin();
        entityManager.persist(subTask);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteById(Integer id) {
        SubTask subTask = findById(id);
        if (subTask != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(subTask);
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public SubTask findById(Integer id) {
        try {
            SubTask subTask = entityManager.find(SubTask.class, id);
            return subTask;
        }catch (Exception e){
            System.out.println("Something went wrong...");
        }
        return null;
    }

    public SubTask findByName(String name) {
        try {
            SubTask subTask = (SubTask) entityManager
                    .createQuery("SELECT u  FROM SubTask u WHERE u.name = :name")
                    .setParameter("name", name)
                    .getResultList()
                    .get(0);
            return subTask;
        } catch (Exception e) {
            return null;
        }
    }
}
