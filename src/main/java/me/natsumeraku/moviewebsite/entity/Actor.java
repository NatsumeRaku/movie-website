package me.natsumeraku.moviewebsite.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 * 演员表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("actor")
public class Actor {
    
    /**
     * 演员ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 演员姓名
     */
    @TableField("name")
    private String name;
    
    /**
     * 英文名
     */
    @TableField("english_name")
    private String englishName;
    
    /**
     * 性别(0-女,1-男)
     */
    @TableField("gender")
    private Integer gender;
    
    /**
     * 出生日期
     */
    @TableField("birthdate")
    private LocalDate birthdate;
    
    /**
     * 出生地
     */
    @TableField("birthplace")
    private String birthplace;
    
    /**
     * 演员简介
     */
    @TableField("introduction")
    private String introduction;
    
    /**
     * 头像URL
     */
    @TableField("avatar_url")
    private String avatarUrl;
    

}