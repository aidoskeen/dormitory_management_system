package com.application.dormitorysystem.hibernate;

import com.application.dormitorysystem.model.Comments;
import com.application.dormitorysystem.model.Dormitory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class CommentsHibCtrl {
    private EntityManagerFactory emf = null;

    public CommentsHibCtrl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comments task) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(task);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comments task) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(task);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void remove(int id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comments task = null;
            try {
                task = em.getReference(Comments.class, id);
                task.getId();
            } catch (Exception e) {
                System.out.println("No such user by given Id");
            }

            Dormitory project = task.getDorm();
            if (project != null) {
                project.getDormitoryComments().remove(task);
                em.merge(project);
            }

            for (Comments t : task.getReply()) {
                remove(t.getId());
            }

            task.getReply().clear();
            em.merge(task);



            em.remove(task);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comments> getAllTasks() {
        return getAllTasks(true, -1, -1);
    }

    public List<Comments> getAllTasks(boolean all, int resMax, int resFirst) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(Comments.class));
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

    public Comments getTaskById(int id) {
        EntityManager em;
        Comments task = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            task = em.find(Comments.class, id);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such user by given Id");
        }
        return task;
    }


}
