package me.natsumeraku.moviewebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import me.natsumeraku.moviewebsite.entity.Actor;
import me.natsumeraku.moviewebsite.mapper.ActorMapper;
import me.natsumeraku.moviewebsite.mapper.MovieActorMapper;
import me.natsumeraku.moviewebsite.service.ActorService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 演员服务实现类
 */
@Service
public class ActorServiceImpl implements ActorService {
    
    @Resource
    private ActorMapper actorMapper;
    
    @Resource
    private MovieActorMapper movieActorMapper;
    
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
        return actorMapper.selectPopularActors(limit);
    }
    
    @Override
    public int getMovieCountByActor(Long actorId) {
        QueryWrapper<me.natsumeraku.moviewebsite.entity.MovieActor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("actor_id", actorId);
        return Math.toIntExact(movieActorMapper.selectCount(queryWrapper));
    }
}