package com.mysandbox.mvnspringhibernatesb.dao.impl;

import com.mysandbox.mvnspringhibernatesb.dao.SandboxUserDAO;
import com.mysandbox.mvnspringhibernatesb.entity.model.SandboxUser;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;

import org.springframework.stereotype.Repository;

@Repository
public class SandboxUserDAOImpl extends BaseDAOImpl<SandboxUser,Long> implements SandboxUserDAO{

    @Override
    public SandboxUser getSandboxUser(String username) {
        Query query = getCurrentSession().createQuery("from SandboxUser where username = :username");
        query.setParameter("username", username);
        return (SandboxUser) query.list().get(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SandboxUser> getSandboxUsers() {
        Criteria criteria = getCurrentSession().createCriteria(SandboxUser.class);
        return criteria.list();
    }
}