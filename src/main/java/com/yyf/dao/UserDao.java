package com.yyf.dao;

import com.github.pagehelper.Page;
import com.yyf.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
    public User findByUsername(String username);

    public Page<User> findByCondition(@Param(value = "value") String query,
                                      @Param(value = "adminKind")int adminKind);
    public void add(User user);
    public void addRole(@Param(value = "userId") int userId,
                        @Param(value = "roleId") int roleId);
    public void delete(int id);
    public void deleteRole(int id);
    public void changePassword(@Param(value = "password")String password,
                               @Param(value = "id")int id);
    public int getUserNum(@Param(value = "roleId")int roleId);
}