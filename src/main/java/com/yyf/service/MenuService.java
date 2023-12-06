package com.yyf.service;

import com.yyf.entity.PageResult;
import com.yyf.pojo.Menu;
import com.yyf.pojo.Role;

import java.util.List;
import java.util.Set;


public interface MenuService {
    public List<Menu> getMenuByRoles(Set<Role> roles);
}
