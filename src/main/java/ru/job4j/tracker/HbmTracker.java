package ru.job4j.tracker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class HbmTracker implements ITracker, AutoCloseable {
    private static final Logger LOG = LogManager.getLogger(HbmTracker.class.getName());

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public Item add(Item item) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            LOG.error(e.getMessage());
        }
        return item;
    }

    @Override
    public boolean replace(Integer id, Item item) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("update Item set name = :newName where id = :id");
            query.setParameter("newName", item.getName());
            query.setParameter("id", id);
            query.executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            LOG.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Item item = new Item(null);
            item.setId(id);
            session.delete(item);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            LOG.error(e.getMessage());
        }
        return false;
    }

    @Override
    public List<Item> findAll() {
        List<Item> result = new ArrayList<>();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            result = session.createQuery("from ru.job4j.tracker.Item").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            LOG.error(e.getMessage());
        }
        return result;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> list = new ArrayList<>();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from ru.job4j.tracker.Item where name = :name");
            query.setParameter("name", key);
            list = query.list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            LOG.error(e.getMessage());
        }
        return list;
    }

    @Override
    public Item findById(Integer id) {
        Item result = null;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            result = session.get(Item.class, id);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            LOG.error(e.getMessage());
        }
        return result;
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
