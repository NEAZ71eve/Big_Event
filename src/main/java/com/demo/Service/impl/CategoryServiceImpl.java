package com.demo.Service.impl;

import com.demo.Mapper.CategoryMapper;
import com.demo.Service.CategoryService;
import com.demo.Utils.ThreadLocalUtil;
import com.demo.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(Category category) {
        String name = category.getCategoryName();
        String alias = category.getCategoryAlias();
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        categoryMapper.add(name,alias,userId);
    }
}
