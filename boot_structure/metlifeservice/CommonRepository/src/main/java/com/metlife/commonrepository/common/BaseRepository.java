package com.metlife.commonrepository.common;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class BaseRepository<T> {

    @Autowired
    @Qualifier(value = "sessionFactory")
    private SessionFactory sessionFactory;

    private final int batchSize = 50;

    private Class<T> modelClass;

    protected Criteria getCriteria() {
        return getSession().createCriteria(getModelClass());
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    protected void setModelClass (Class<T> modelClass) {

        this.modelClass = modelClass;
    }

    @SuppressWarnings("unchecked")
    private Class<T> getModelClass() {
        if (modelClass == null) {
            ParameterizedType thisType = (ParameterizedType) this.getClass().getGenericSuperclass();
            this.modelClass = (Class<T>) thisType.getActualTypeArguments()[0];
        }
        return modelClass;
    }

    private String getDomainClassName() {
        return this.getModelClass().getName();
    }

    public void create(T t) {
        this.getSession().save(t);
    }

    public void createAndCommit(T t) {
        Session session = this.getSession();
        Transaction tx = session.beginTransaction();

        session.save(t);

        session.flush();
        session.clear();
        tx.commit();
    }

    public void create(List<T> tList) {
        Session session = this.getSession();
        for (int i = 0; i < tList.size(); i++) {
            session.save(tList.get(i));
            if (i % batchSize == 0) {
                flushSession();
                clearSession();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public T get(Serializable id) {
        return (T) this.getSession().get(getModelClass(), id);
    }

    @SuppressWarnings("unchecked")
    public T load(Serializable id) {
        return (T) this.getSession().load(getModelClass(), id);
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return this.getSession()
                .createQuery("from " + getDomainClassName())
                .list();
    }

    public void update(T t) {
        this.getSession().update(t);
    }

    public void updateAndCommit(T t) {
        Session session = this.getSession();
        Transaction tx = session.beginTransaction();

        this.getSession().update(t);

        session.flush();
        session.clear();
        tx.commit();
    }

    public void update(List<T> tList) {
        Session session = this.getSession();
        for (int i = 0; i < tList.size(); i++) {
            session.update(tList.get(i));
            if (i % batchSize == 0) {
                this.flushSession();
                this.clearSession();
            }
        }
    }

    public void delete(T t) {
        this.getSession().delete(t);
    }

    public void deleteById(Serializable id) {
        this.delete(load(id));
    }

    public void deleteAll() {
        this.getSession()
                .createQuery("delete from " + getDomainClassName())
                .executeUpdate();
    }

    public long count() {
        return (Long) this.getSession()
                .createQuery("select count(*) from " + getDomainClassName())
                .uniqueResult();
    }

    public boolean exists(Serializable id) {
        return (this.get(id) != null);
    }

    public void flushSession() {
        this.getSession().flush();
    }

    public void clearSession() {
        this.getSession().clear();
    }

    public void setTypeClass(Class<T> typeClass) {
        this.setModelClass(typeClass);
    }


}
