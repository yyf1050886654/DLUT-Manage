package com.yyf.dao;

import com.yyf.pojo.Permission;

import java.util.Set;

public interface PermissionDao {
    public Set<Permission> findByRoleId(int roleId);
}