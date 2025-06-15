package me.natsumeraku.moviewebsite.entity;

import com.baomidou.mybatisplus.annotation.*;
import java.time.LocalDate;

/**
 * 演员表
 */
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
    
    // 构造函数
    public Actor() {}
    
    // Getter和Setter方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEnglishName() {
        return englishName;
    }
    
    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }
    
    public Integer getGender() {
        return gender;
    }
    
    public void setGender(Integer gender) {
        this.gender = gender;
    }
    
    public LocalDate getBirthdate() {
        return birthdate;
    }
    
    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
    
    public String getBirthplace() {
        return birthplace;
    }
    
    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }
    
    public String getIntroduction() {
        return introduction;
    }
    
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    
    public String getAvatarUrl() {
        return avatarUrl;
    }
    
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    
    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", englishName='" + englishName + '\'' +
                ", gender=" + gender +
                ", birthdate=" + birthdate +
                ", birthplace='" + birthplace + '\'' +
                ", introduction='" + introduction + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}