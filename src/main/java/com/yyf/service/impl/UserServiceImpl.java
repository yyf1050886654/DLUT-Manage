
/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.yyf.service.impl;

import com.yyf.dao.PermissionDao;
import com.yyf.dao.RoleDao;
import com.yyf.dao.UserDao;
import com.yyf.pojo.Permission;
import com.yyf.pojo.Role;
import com.yyf.pojo.User;
import com.yyf.service.UserService;
import com.yyf.utils.BCryptPasswordEncoderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    public User findByUsername(String username) {
        User user = userDao.findByUsername(username);
        if(user == null){
            return null;
        }
        Integer userId = user.getId();
        Set<Role> roles = roleDao.findByUserId(userId);
        if(roles != null && roles.size() > 0){
            for(Role role : roles){
                Integer roleId = role.getId();
                Set<Permission> permissions = permissionDao.findByRoleId(roleId);
                if(permissions != null && permissions.size() > 0){
                    role.setPermissions(permissions);
                }
            }
            user.setRoles(roles);
        }
        return user;
    }

    @Override
    public void add(User user) {
        String encode = BCryptPasswordEncoderUtils.encodePassword(user.getPassword());
        user.setPassword(encode);
        userDao.add(user);

    }
    public void addRole(int userId,int roleId){
        userDao.addRole(userId,roleId);
    }

    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

    @Override
    public void deleteRole(int id) {
        userDao.deleteRole(id);
    }

    @Override
    public void changePassword(String password, int uid) {
        String encode = BCryptPasswordEncoderUtils.encodePassword(password);
        userDao.changePassword(encode,uid);
    }
}