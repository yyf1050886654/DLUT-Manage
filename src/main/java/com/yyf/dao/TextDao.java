package com.yyf.dao;

import org.apache.ibatis.annotations.Param;

public interface TextDao {
    public String getText(@Param(value = "sort")int sort,
                          @Param(value = "kind")int kind);
}
