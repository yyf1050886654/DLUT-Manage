package com.yyf.dao;

import com.yyf.pojo.Menu;
import com.yyf.pojo.Permission;

import java.util.Set;

public interface MenuDao {
    public Set<Menu> findByRoleId(int roleId);
}
