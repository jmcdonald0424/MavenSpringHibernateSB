package com.mysandbox.mvnspringhibernatesb.service.impl;

import com.mysandbox.mvnspringhibernatesb.dao.BaseDAO;
import com.mysandbox.mvnspringhibernatesb.service.BaseService;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public abstract class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK>{
    
    @Autowired
    private BaseDAO<T, PK> baseDAO;

    @Override
    @Transactional
    public PK insert(T entity) {
        return baseDAO.insert(entity);
    }

    @Override
    @Transactional
    public T find(PK id) {
        return baseDAO.find(id);
    }

    @Override
    @Transactional
    public void update(T entity) {
        baseDAO.update(entity);
    }

    @Override
    @Transactional
    public void delete(T entity) {
        baseDAO.delete(entity);
    }   
    
    @Override
    @Transactional
    public void deleteById(PK id){
        baseDAO.deleteById(id);
    }
}