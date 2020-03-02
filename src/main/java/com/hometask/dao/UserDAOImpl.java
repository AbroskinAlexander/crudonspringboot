package com.hometask.dao;

import com.hometask.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void deleteUser(User user) {
        entityManager.remove(user);
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    public void updateUser(User updateUserold) {
        entityManager.merge(updateUserold);
    }

    @Override
    public boolean existUser(User user) {
        TypedQuery<User> query = entityManager.createQuery("select u From User u where u.email=:email", User.class);
        query.setParameter("email", user.getUsername());
        List<User> list = query.getResultList();
        return list.size() == 0;
    }

    @Override
    public User getUserByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery("select u From User u where u.email=:email", User.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }
}
