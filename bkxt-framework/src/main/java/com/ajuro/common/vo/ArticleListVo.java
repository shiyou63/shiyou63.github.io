package com.ajuro.common.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListVo {
    private Long id;
    private String title;
    private String summary;
    private String thumbnail;
    private Integer isTop;
    private Date createTime;
    private Long viewCount;
    private Long categoryId;
    private String categoryName; // 分类名称需要联表查询
    @TableField(exist = false)
    private List<String> tagNameList;
    private Long total;
}