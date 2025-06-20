package me.natsumeraku.moviewebsite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.natsumeraku.moviewebsite.entity.Director;

import java.util.List;

/**
 * 导演服务接口
 */
public interface DirectorService {
    
    /**
     * 添加导演
     */
    boolean addDirector(Director director);
    
    /**
     * 根据ID查询导演
     */
    Director findById(Long id);
    
    /**
     * 更新导演信息
     */
    boolean updateDirector(Director director);
    
    /**
     * 删除导演
     */
    boolean deleteDirector(Long id);
    
    /**
     * 分页查询导演列表
     */
    IPage<Director> getDirectorList(Page<Director> page);
    
    /**
     * 根据姓名搜索导演
     */
    List<Director> searchByName(String name);
    
    /**
     * 获取热门导演
     */
    List<Director> getPopularDirectors(int limit);
    
    /**
     * 根据导演ID获取其执导的电影数量
     */
    int getMovieCountByDirector(Long directorId);
}