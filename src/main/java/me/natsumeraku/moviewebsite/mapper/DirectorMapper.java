package me.natsumeraku.moviewebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.natsumeraku.moviewebsite.entity.Director;
import org.apache.ibatis.annotations.Mapper;

/**
 * 导演表 Mapper 接口
 */
@Mapper
public interface DirectorMapper extends BaseMapper<Director> {
}