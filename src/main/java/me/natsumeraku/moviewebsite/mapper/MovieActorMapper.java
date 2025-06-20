package me.natsumeraku.moviewebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.natsumeraku.moviewebsite.entity.MovieActor;
import org.apache.ibatis.annotations.Mapper;

/**
 * 电影-演员关联表 Mapper 接口
 */
@Mapper
public interface MovieActorMapper extends BaseMapper<MovieActor> {
}