package com.ajuro.common.repository.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户表
 * @author Ajuro
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("sys_user") // 指定对应数据库表名
public class User {
    @TableId(type = IdType.AUTO) // 主键自增
    private Long id;

    @TableField("user_name") // 映射数据库字段 user_name
    private String userName;

    @TableField("nick_name") // 映射数据库字段 nick_name
    private String nickName;

    private String password; // BCrypt加密后的密码

    private String type; // 用户类型：0-普通用户 1-管理员
    private String status; // 账号状态：0-正常 1-停用
    private String email; // 邮箱
    private String phonenumber; // 手机号
    private String sex; // 性别：0-男 1-女 2-未知
    private String avatar; // 头像URL

    @TableField("signature") // 新增：个性签名
    private String signature;

    @TableField("post_count") // 新增：发帖数量
    private Integer postCount;

    @TableField(fill = FieldFill.INSERT) // 插入时自动填充创建人ID
    private Long createBy;

    @TableField(fill = FieldFill.INSERT) // 插入时自动填充创建时间
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE) // 更新时自动填充更新人ID
    private Long updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE) // 更新时自动填充更新时间
    private Date updateTime;

    @TableLogic // 逻辑删除注解（删除标志）
    private Integer delFlag; // 0-未删除 1-已删除
}
