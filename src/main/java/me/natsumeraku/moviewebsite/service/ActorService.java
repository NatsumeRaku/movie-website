package me.natsumeraku.moviewebsite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.natsumeraku.moviewebsite.entity.Actor;

import java.util.List;

/**
 * 演员服务接口
 */
public interface ActorService {
    
    /**
     * 添加演员
     */
    boolean addActor(Actor actor);
    
    /**
     * 根据ID查询演员
     */
    Actor findById(Long id);
    
    /**
     * 更新演员信息
     */
    boolean updateActor(Actor actor);
    
    /**
     * 删除演员
     */
    boolean deleteActor(Long id);
    
    /**
     * 分页查询演员列表
     */
    IPage<Actor> getActorList(Page<Actor> page);
    
    /**
     * 根据姓名搜索演员
     */
    List<Actor> searchByName(String name);
    
    /**
     * 获取热门演员
     */
    List<Actor> getPopularActors(int limit);
    
    /**
     * 根据演员ID获取其参演的电影数量
     */
    int getMovieCountByActor(Long actorId);
}