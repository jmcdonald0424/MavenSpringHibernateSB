package com.mysandbox.mvnspringhibernatesb.service;

import com.mysandbox.mvnspringhibernatesb.entity.model.SandboxUser;
import java.util.List;

public interface SandboxUserService extends BaseService<SandboxUser, Long>{	
    public SandboxUser getSandboxUser(String username);	
    public List<SandboxUser> getSandboxUsers();
}