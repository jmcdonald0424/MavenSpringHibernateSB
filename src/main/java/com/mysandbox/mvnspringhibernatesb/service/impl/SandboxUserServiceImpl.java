package com.mysandbox.mvnspringhibernatesb.service.impl;

import com.mysandbox.mvnspringhibernatesb.dao.SandboxUserDAO;
import com.mysandbox.mvnspringhibernatesb.entity.model.SandboxUser;
import com.mysandbox.mvnspringhibernatesb.service.SandboxUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SandboxUserServiceImpl extends BaseServiceImpl<SandboxUser,Long> implements SandboxUserService{

    @Autowired
    private SandboxUserDAO sandboxUserDAO;

    @Override
    @Transactional
    public SandboxUser getSandboxUser(String username) {
        return sandboxUserDAO.getSandboxUser(username);
    }

    @Override
    @Transactional
    public List<SandboxUser> getSandboxUsers() {
        return sandboxUserDAO.getSandboxUsers();
    }
}
