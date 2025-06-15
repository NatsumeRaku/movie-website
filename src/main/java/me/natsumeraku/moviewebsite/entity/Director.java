package me.natsumeraku.moviewebsite.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("director")
public class Director {
    
    /**
     * 导演ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 导演姓名
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
     * 导演风格
     */
    @TableField("style")
    private String style;
    
    /**
     * 导演简介
     */
    @TableField("introduction")
    private String introduction;
    
    /**
     * 头像URL
     */
    @TableField("avatar_url")
    private String avatarUrl;
    
}