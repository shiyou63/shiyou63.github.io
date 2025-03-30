package com.ajuro.common.service.Impl;


import com.ajuro.common.constants.SystemConstants;
import com.ajuro.common.repository.entity.Article;
import com.ajuro.common.repository.entity.Category;
import com.ajuro.common.repository.mapper.CategoryMapper;
import com.ajuro.common.service.ArticleService;
import com.ajuro.common.service.CategoryService;
import com.ajuro.common.utils.BeanCopyUtils;
import com.ajuro.common.model.vo.CategoryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;

    @Override
    //查询分类列表的核心代码
    public List<CategoryVo> getCategoryList() {
        // 1. 查询状态为正常的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);

        // 2. 提取分类ID并去重
        Set<Long> categoryIds = articleList.stream()
                .map(Article::getCategoryId)//提取分类ID
                .collect(Collectors.toSet());//去重

        // 3. 查询分类信息（过滤有效状态）
        List<Category> categories = Collections.emptyList();//避免空指针
        if (!categoryIds.isEmpty()) {
            LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Category::getId, categoryIds)
                    .eq(Category::getStatus, SystemConstants.STATUS_NORMAL);
            categories = categoryService.list(wrapper);
        }

        // 4. 转换为VO并返回
        return BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
    }

}