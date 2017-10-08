package com.dao.user;

import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

public class Role implements GrantedAuthority{
    private Integer id;
    private String name;
    private Set<User> users = new HashSet<>();

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
