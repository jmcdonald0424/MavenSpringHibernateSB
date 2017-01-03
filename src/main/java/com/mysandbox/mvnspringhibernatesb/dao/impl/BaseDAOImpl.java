package com.mysandbox.mvnspringhibernatesb.dao.impl;

import com.mysandbox.mvnspringhibernatesb.dao.BaseDAO;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public abstract class BaseDAOImpl <T, PK extends Serializable> implements BaseDAO<T, PK>{

    @Autowired
    private SessionFactory sessionFactory;    
    private final Class<T> type;
    
    public BaseDAOImpl(){
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType)t;
        this.type = (Class) pt.getActualTypeArguments()[0];
    }

    @Override
    public PK insert(T entity) {
        return (PK)getCurrentSession().save(entity);
    }

    @Override
    public T find(PK id) {
        return (T)getCurrentSession().get(type, id);
    }

    @Override
    public void update(T entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public void delete(T entity) {
        getCurrentSession().delete(entity);
    }
    
    @Override
    public void deleteById(PK id){
        getCurrentSession().delete(find(id));
    }
    
    protected Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
}
