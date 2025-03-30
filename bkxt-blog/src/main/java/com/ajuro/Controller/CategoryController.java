package com.ajuro.Controller;

import com.ajuro.common.response.ResponseResult;
import com.ajuro.common.service.CategoryService;
import com.ajuro.common.model.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    public ResponseResult getCategoryList(){
        List<CategoryVo> getCategory = categoryService.getCategoryList();
        // 2. 封装为统一响应格式
        return ResponseResult.okResult(getCategory);
    }
}