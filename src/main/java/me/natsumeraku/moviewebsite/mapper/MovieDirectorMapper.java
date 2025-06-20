package me.natsumeraku.moviewebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.natsumeraku.moviewebsite.entity.MovieDirector;
import org.apache.ibatis.annotations.Mapper;

/**
 * 电影-导演关联表 Mapper 接口
 */
@Mapper
public interface MovieDirectorMapper extends BaseMapper<MovieDirector> {
}