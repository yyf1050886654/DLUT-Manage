package com.yyf.dao;

import com.yyf.pojo.Role;

import java.util.Set;

public interface RoleDao {
    public Set<Role> findByUserId(int id);
}