package com.mysandbox.mvnspringhibernatesb.service;

import java.io.Serializable;

public interface BaseService<T, PK extends Serializable>{
    public PK insert(T entity);
    public T find(PK id);
    public void update(T entity);
    public void delete(T entity);
    public void deleteById(PK id);
}
