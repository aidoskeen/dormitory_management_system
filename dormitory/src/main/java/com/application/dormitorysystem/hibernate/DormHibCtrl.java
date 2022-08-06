package com.application.dormitorysystem.hibernate;

import com.application.dormitorysystem.model.Administrator;
import com.application.dormitorysystem.model.Dormitory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class DormHibCtrl {
    private EntityManagerFactory emf = null;

    public DormHibCtrl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    public void editDorm(Dormitory dorm) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(dorm);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public void addnewDorm(Dormitory dorm) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(dorm);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public void removeDorm(int dorm_num) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            //Before deleting, brake the relations with other entities
            Dormitory dorm = null;
            try {
                dorm = em.getReference(Dormitory.class, dorm_num);
                dorm.getDorm_num();
            } catch (Exception e) {
                System.out.println("No such Dorm");
            }
            em.remove(dorm);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Dormitory> getAllDorms() {
        return getAllDorms(true, -1, -1);
    }

    public List<Dormitory> getAllDorms(boolean all, int resMax, int resFirst) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(Dormitory.class));
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
    public Dormitory getDormByNum(int dorm_num) {
        EntityManager em = null;
        Dormitory dorm = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            dorm = em.find(Dormitory.class, dorm_num);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such Dormitory");
        }
        return dorm;
    }
    public List<Dormitory> getDormByAdminId(int id) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Dormitory> query = cb.createQuery(Dormitory.class);

        Root<Dormitory> root = query.from(Dormitory.class);

        CriteriaBuilder.In<Integer> inClause = cb.in(root.get("dorm_num"));
        Administrator user = em.getReference(Administrator.class, id);
        for (Dormitory d : user.getDorms()) {
            inClause.value(d.getDorm_num());
        }
        query.select(root).where(inClause);
        Query q;
        try {
            q = em.createQuery(query);
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
