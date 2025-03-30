package com.ajuro.common.repository.mapper;

import com.ajuro.common.model.vo.ArticleListVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ajuro.common.repository.entity.Category;

import java.util.List;


public interface CategoryMapper extends BaseMapper<Category> {
    List<ArticleListVo> serchList(String search);
}