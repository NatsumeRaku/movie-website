package me.natsumeraku.moviewebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import me.natsumeraku.moviewebsite.entity.Director;
import me.natsumeraku.moviewebsite.mapper.DirectorMapper;
import me.natsumeraku.moviewebsite.mapper.MovieDirectorMapper;
import me.natsumeraku.moviewebsite.service.DirectorService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 导演服务实现类
 */
@Service
public class DirectorServiceImpl implements DirectorService {

    @Resource
    private DirectorMapper directorMapper;
    
    @Resource
    private MovieDirectorMapper movieDirectorMapper;
    
    @Override
    public boolean addDirector(Director director) {
        return directorMapper.insert(director) > 0;
    }
    
    @Override
    public Director findById(Long id) {
        return directorMapper.selectById(id);
    }
    
    @Override
    public boolean updateDirector(Director director) {
        return directorMapper.updateById(director) > 0;
    }
    
    @Override
    public boolean deleteDirector(Long id) {
        return directorMapper.deleteById(id) > 0;
    }
    
    @Override
    public IPage<Director> getDirectorList(Page<Director> page) {
        return directorMapper.selectPage(page, null);
    }
    
    @Override
    public List<Director> searchByName(String name) {
        QueryWrapper<Director> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name).or().like("english_name", name);
        return directorMapper.selectList(queryWrapper);
    }
    
    @Override
    public List<Director> getPopularDirectors(int limit) {
        // 根据执导电影数量排序获取热门导演
        QueryWrapper<Director> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("ORDER BY (SELECT COUNT(*) FROM movie_director md WHERE md.director_id = director.id) DESC LIMIT " + limit);
        return directorMapper.selectList(queryWrapper);
    }
    
    @Override
    public int getMovieCountByDirector(Long directorId) {
        QueryWrapper<me.natsumeraku.moviewebsite.entity.MovieDirector> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("director_id", directorId);
        return Math.toIntExact(movieDirectorMapper.selectCount(queryWrapper));
    }
}