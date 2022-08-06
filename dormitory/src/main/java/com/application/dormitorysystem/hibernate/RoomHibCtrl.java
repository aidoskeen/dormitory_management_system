package com.application.dormitorysystem.hibernate;

import com.application.dormitorysystem.model.Dormitory;
import com.application.dormitorysystem.model.Room;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class RoomHibCtrl {
    private EntityManagerFactory emf = null;

    public RoomHibCtrl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void addRoom(Room room) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(room);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void editRoom(Room room) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(room);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void removeRoom(int id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Room room = null;
            try {
                room = em.getReference(Room.class, id);
                room.getRoom_num();
            } catch (Exception e) {
                System.out.println("No such room");
            }

            assert room != null;
            Dormitory dormitory = room.getDormitory();
            if (dormitory != null) {
                dormitory.getRooms().remove(room);
                em.merge(dormitory);
            }

            em.remove(room);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Room> getAllRooms() {
        return getAllRooms(true, -1, -1);
    }

    public List<Room> getAllRooms(boolean all, int resMax, int resFirst) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(Room.class));
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

    public Room getRoomByNum(int room_num) {
        EntityManager em = null;
        Room room = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            room = em.find(Room.class, room_num);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such Room");
        }
        return room;
    }


}
