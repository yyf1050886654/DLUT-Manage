package com.yyf.service.impl;

import com.yyf.dao.TextDao;
import com.yyf.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TextServiceImpl implements TextService {
    @Autowired
    private TextDao textDao;

    @Override
    public String getText(int kind, int sort) {
        return textDao.getText(sort,kind);
    }
}
