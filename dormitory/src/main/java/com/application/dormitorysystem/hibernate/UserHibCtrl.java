package com.application.dormitorysystem.hibernate;

import com.application.dormitorysystem.model.Student;
import com.application.dormitorysystem.model.User;
import com.application.dormitorysystem.model.Administrator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserHibCtrl {
    private EntityManagerFactory emf = null;

    public UserHibCtrl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void createUser(User user) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void editUser(User user) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void removeUser(int id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            //Before deleting, brake the relations with other entities
            User user = null;
            try {
                user = em.getReference(User.class, id);
                user.getUserId();
            } catch (Exception e) {
                System.out.println("No such user by given Id");
            }
            em.remove(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> getAllUsers() {
        return getAllUsers(true, -1, -1);
    }

    public List<User> getAllUsers(boolean all, int resMax, int resFirst) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(User.class));
            Query q = em.createQuery(query);

            if (!all) {
                q.setMaxResults(resMax);
                q.setFirstResult(resFirst);
            }

            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }

    public User getUserById(int id) {
        EntityManager em = null;
        User user = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            user = em.getReference(User.class, id);
            user.getUserId();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such user by given Id");
        }
        return user;
    }
    public Administrator getAdminById(int id) {
        EntityManager em = null;
        Administrator user = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            user = em.find(Administrator.class, id);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such user by given Id");
        }
        return user;
    }
    public Student getStudentById(int id) {
        EntityManager em = null;
        Student user = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            user = em.getReference(Student.class, id);
            user.getUserId();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such user by given Id");
        }
        return user;
    }

    public User getUserByLoginData(String login, String psw) {
        EntityManager em = getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root).where(cb.like(root.get("login"), login));//the fields/columns by which to filter
        query.select(root).where(cb.like(root.get("password"), psw));
        Query q = em.createQuery(query);
        try {
            return (User) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    public User getUserByLogin(String login) {
        EntityManager em = getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root).where(cb.like(root.get("login"), login));//the fields/columns by which to filter
        Query q = em.createQuery(query);
        try {
            return (User) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    public List<Student> getAllStudents() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(Student.class));
            Query q = em.createQuery(query);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }
    public List<Administrator> getAllAdmins() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(Administrator.class));
            Query q = em.createQuery(query);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }

}
