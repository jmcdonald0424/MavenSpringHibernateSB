package com.mysandbox.mvnspringhibernatesb.dao;

import com.mysandbox.mvnspringhibernatesb.entity.model.SandboxUser;
import java.util.List;

public interface SandboxUserDAO extends BaseDAO<SandboxUser, Long>{	
    public SandboxUser getSandboxUser(String username);	
    public List<SandboxUser> getSandboxUsers();
}
