package com.ajuro.common.service;

import com.ajuro.common.repository.entity.Category;
import com.ajuro.common.model.vo.CategoryVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CategoryService extends IService<Category> {
    //查询文章分类的接口
    List<CategoryVo> getCategoryList();
}