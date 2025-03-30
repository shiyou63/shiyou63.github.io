package com.ajuro.common.service;

import com.ajuro.common.response.ResponseResult;
import com.ajuro.common.model.vo.ArticleDetailVo;
import com.ajuro.common.model.vo.ArticleListVo;
import com.ajuro.common.model.vo.HotArticleVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ajuro.common.repository.entity.Article;

import java.util.List;

public interface ArticleService extends IService<Article> {
    // 热门文章列表
    List<HotArticleVO> hotArticleList();
    // 文章列表，用articelListVo封装列表数据
    ResponseResult<ArticleListVo> articleList(Integer pageNum, Integer pageSize, Long categoryId);
    // 文章详情
    ArticleDetailVo getArticleDetail(Long id);
}