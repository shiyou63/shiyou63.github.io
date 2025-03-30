package com.ajuro.common.service.Impl;

import com.ajuro.common.response.ResponseResult;
import com.ajuro.common.service.CategoryService;
import com.ajuro.common.utils.BeanCopyUtils;
import com.ajuro.common.repository.entity.Category;
import com.ajuro.common.vo.ArticleDetailVo;
import com.ajuro.common.vo.ArticleListVo;
import com.ajuro.common.vo.HotArticleVO;
import com.ajuro.common.vo.PageVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ajuro.common.repository.entity.Article;
import com.ajuro.common.repository.mapper.ArticleMapper;
import com.ajuro.common.service.ArticleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ajuro.common.constants.SystemConstants;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    /**
    * 热门文章
    * @return
    */
    @Override
    public List<HotArticleVO> hotArticleList() {
        // 查询热门文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article:: getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
                    .orderByDesc(Article::getViewCount);
        //分页
        Page<Article> page = new Page<>(SystemConstants.ARTICLE_STATUS_CURRENT, SystemConstants.ARTICLE_STATUS_SIZE);
        page(page, queryWrapper);
        List<Article> articles = page.getRecords();
        //vo转换
//        List<HotArticleVO> articleVos = articles.stream()
//                .map(article -> {
//                    HotArticleVO vo = new HotArticleVO();
//                    BeanUtils.copyProperties(article, vo);
//                    return vo;
//                })
//                .collect(Collectors.toList());
        //工具类vo转换
        List<HotArticleVO> articleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVO.class);
        return articleVos;
    }
    /**
     * 文章列表
     *
     * @param pageNum
     * @param pageSize
     * @param categoryId 分页查询文章列表
     */
    @Autowired
    private CategoryService categoryService;
    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        // 1. 构建查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        // 分类过滤（动态条件）
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0,
                Article::getCategoryId, categoryId);

        // 状态过滤（只查正式发布文章）
        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);

        // 排序规则：置顶优先 -> 浏览量降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop)
                .orderByDesc(Article::getViewCount);

        // 2. 执行分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);

        // 3. 批量查询分类名称（性能优化关键）
        List<Article> articles = page.getRecords();
        Set<Long> categoryIds = articles.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());

        // 批量获取分类数据（单次查询解决N+1问题）
        Map<Long, String> categoryMap = categoryService.listByIds(categoryIds)
                .stream()
                .collect(Collectors.toMap(
                        Category::getId,
                        c -> c.getName() != null ? c.getName() : "未分类"
                ));

        // 4. 设置分类名称（流式处理）
        articles.forEach(article ->
                article.setCategoryName(categoryMap.getOrDefault(article.getCategoryId(), "未分类"))
        );

        // 5. 转换VO对象
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);

        // 6. 封装分页结果
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
    /**
     * 文章详情
     *
     * @param id
     * @return
     */
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public ArticleDetailVo getArticleDetail(Long id) {
        // 1. 查询文章主体（添加状态过滤）
        Article article = articleMapper.selectOne(new LambdaQueryWrapper<Article>()
                .eq(Article::getId, id)// 主键查询
                .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL) // 只查正式文章
        );
        if (article == null) {
            throw new IllegalArgumentException("文章不存在");
        }
        // 3. 安全转换VO对象
        ArticleDetailVo vo = new ArticleDetailVo();
        BeanUtils.copyProperties(article, vo); // Spring的BeanUtils需要目标对象先实例化

        // 4. 填充分类信息（防御性编程）
        Optional.ofNullable(article.getCategoryId())
                .map(categoryId -> categoryService.getById(categoryId))
                .ifPresent(category -> vo.setCategoryName(category.getName()));
        return vo;
    }
}