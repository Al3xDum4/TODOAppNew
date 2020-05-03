package com.Alex.repository;

import com.Alex.model.Project;
import com.Alex.model.User;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class ProjectsRepository implements CrudRepository<Project, Integer> {
    private EntityManager entityManager;

    public ProjectsRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<Project> findAll() {
        return entityManager.createQuery("SELECT p FROM Project p").getResultList();
    }

    @Override
    public void save(Project project) {
        entityManager.getTransaction().begin();
        entityManager.persist(project);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteById(Integer id) {
        Optional<Project> project = findById(id);
        if (project.isPresent()) {
            entityManager.getTransaction().begin();
            entityManager.remove(project);
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public Optional<Project> findById(Integer id) {
        try {
            Project project = entityManager.find(Project.class, id);
            return Optional.of(project);
        } catch (Exception e) {
            System.out.println("Something went wrong...");
        }
        return Optional.empty();
    }

    public Project findByName(String name) {
        try {
            Project project = (Project) entityManager
                    .createQuery("SELECT u  FROM Project u WHERE u.name = :name")
                    .setParameter("name", name)
                    .getResultList()
                    .get(0);
            return project;
        } catch (Exception e) {
            return null;
        }
    }
}
