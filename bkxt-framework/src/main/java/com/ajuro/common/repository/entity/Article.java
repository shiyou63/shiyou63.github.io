package com.ajuro.common.repository.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sg_article")
public class Article {
    @TableId
    private Long id;
    private String title;
    private String content;
    private String summary;

    // 修正为Long类型（与数据库一致）
    private Long categoryId;

    @TableField(exist = false)
    private String categoryName; // 需联表查询

    private String thumbnail;

    // 修正为Integer类型（0/1）
    private Integer isTop;

    private String status;
    private Long viewCount;
    private String isComment;
    private Long createBy;
    private Date createTime;
    private Long updateBy;
    private Date updateTime;
    private Integer delFlag;
}