package me.natsumeraku.moviewebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.natsumeraku.moviewebsite.entity.Actor;
import me.natsumeraku.moviewebsite.mapper.ActorMapper;
import me.natsumeraku.moviewebsite.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 演员服务实现类
 */
@Service
public class ActorServiceImpl implements ActorService {
    
    @Autowired
    private ActorMapper actorMapper;
    
    @Override
    public boolean addActor(Actor actor) {
        return actorMapper.insert(actor) > 0;
    }
    
    @Override
    public Actor findById(Long id) {
        return actorMapper.selectById(id);
    }
    
    @Override
    public boolean updateActor(Actor actor) {
        return actorMapper.updateById(actor) > 0;
    }
    
    @Override
    public boolean deleteActor(Long id) {
        return actorMapper.deleteById(id) > 0;
    }
    
    @Override
    public IPage<Actor> getActorList(Page<Actor> page) {
        QueryWrapper<Actor> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("name");
        return actorMapper.selectPage(page, queryWrapper);
    }
    
    @Override
    public List<Actor> searchByName(String name) {
        QueryWrapper<Actor> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name)
                   .or()
                   .like("english_name", name);
        return actorMapper.selectList(queryWrapper);
    }
    
    @Override
    public List<Actor> getPopularActors(int limit) {
        // 根据参演电影数量排序，需要自定义SQL
        return actorMapper.selectPopularActors(limit);
    }
}