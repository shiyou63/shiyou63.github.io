package com.ajuro.common.repository.mapper;

import com.ajuro.common.vo.ArticleListVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ajuro.common.repository.entity.Category;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CategoryMapper extends BaseMapper<Category> {
    List<ArticleListVo> serchList(String search);
}