package com.ajuro.common.repository.mapper;

import com.ajuro.common.repository.entity.Article;
import com.ajuro.common.model.vo.ArticleListVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.data.repository.query.Param;

public interface ArticleMapper extends BaseMapper<Article> {
    IPage<ArticleListVo> selectArticlePage(Page<ArticleListVo> page,
                                           @Param(Constants.WRAPPER) Wrapper<Article> wrapper);
}
