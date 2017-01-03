package com.mysandbox.mvnspringhibernatesb.dao;

import java.io.Serializable;

public interface BaseDAO<T, PK extends Serializable>{
    public PK insert(T entity);
    public T find(PK id);
    public void update(T entity);
    public void delete(T entity);
    public void deleteById(PK id);
}
