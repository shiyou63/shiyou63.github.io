package com.ajuro.Controller;

import com.ajuro.common.model.vo.ArticleDetailVo;
import com.ajuro.common.model.vo.ArticleListVo;
import com.ajuro.common.model.vo.HotArticleVO;
import com.ajuro.common.model.vo.PageVo;
import com.ajuro.common.repository.entity.Category;
import com.ajuro.common.response.ResponseResult;
import com.ajuro.common.repository.mapper.CategoryMapper;
import com.ajuro.common.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    //注入公共模块的ArticleService接口
    private ArticleService articleService;
    @Autowired
    private CategoryMapper categoryMapper;


    //----------------------------------测试mybatisPlus---------------------------------
  //  @GetMapping("/list")
   // Article是公共模块的实体类
   // public List<Article> test(){
        //查询数据库的所有数据
   //     return articleService.list();
   // }

    //热门文章列表
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        //查询热门文章，封装成ResponseResult返回
        List<HotArticleVO> result = articleService.hotArticleList();
        return ResponseResult.okResult(result);
    }

    //----------------------------------分页查询文章的列表---------------------------------
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId,@RequestParam(required = false) String search){
        if (search!=""){
            List<ArticleListVo> articleListVo = categoryMapper.serchList(search);
            if (articleListVo.size()!=0) {
                PageVo pageVo = new PageVo(articleListVo,articleListVo.get(0).getTotal());
                List<ArticleListVo> list = pageVo.getRows();
                for (ArticleListVo one : list) {
                    Category category = categoryMapper.selectById(one.getId());
                    one.setCategoryName(category.getName());
                    one.setCategoryId(category.getId());
                }

                return ResponseResult.okResult(pageVo);
            }

        }
        ResponseResult responseResult = articleService.articleList(pageNum, pageSize, categoryId);
        return responseResult;
    }
    //----------------------------------查询文章详情---------------------------------
    @GetMapping("{id}")
    public ResponseResult articleDetail(@PathVariable Long id){
        ArticleDetailVo vo = articleService.getArticleDetail(id);
        return ResponseResult.okResult(vo);
    }
}