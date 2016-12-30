package com.mysandbox.mvnspringhibernatesb.service;

public interface BaseService<T, PK>{
    public PK insert(T entity);
    public T find(PK id);
    public void update(T entity);
    public void delete(T entity);
}
