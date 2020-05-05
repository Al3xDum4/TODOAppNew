package com.Alex.repository;

import com.Alex.model.SubTask;
import com.Alex.model.Task;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class SubTaskRepository implements CrudRepository<SubTask, Integer> {
    private EntityManager entityManager;

    public SubTaskRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<SubTask> findAll() {
        return entityManager.createQuery("SELECT s FROM SubTask s").getResultList();
    }

    @Override
    public void save(SubTask subTask) {
        entityManager.getTransaction().begin();
        entityManager.persist(subTask);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteById(Integer id) {
        Optional<SubTask> subTask = findById(id);
        if (subTask.isPresent()) {
            entityManager.getTransaction().begin();
            entityManager.remove(subTask);
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public Optional<SubTask> findById(Integer id) {
        try {
            SubTask subTask = entityManager.find(SubTask.class, id);
            return Optional.of(subTask);
        }catch (Exception e){
            System.out.println("Something went wrong...");
        }
        return Optional.empty();
    }

    public SubTask findByName(String name) {
        try {
            SubTask subTask = (SubTask) entityManager
                    .createQuery("SELECT u  FROM SubTasks u WHERE u.name = :name")
                    .setParameter("name", name)
                    .getResultList()
                    .get(0);
            return subTask;
        } catch (Exception e) {
            return null;
        }
    }

    public SubTask findAllSubTaskAssign(Integer id) {
        try {
            SubTask subTask = (SubTask) entityManager.createQuery("SELECT st FROM SubTask st WHERE st.task_task_id = :id")
                    .setParameter("id", id)
                    .getResultList()
                    .get(0);
            return subTask;
        } catch (Exception e) {
            return null;
        }
    }
}
