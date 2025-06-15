package me.natsumeraku.moviewebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.natsumeraku.moviewebsite.entity.Director;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 导演表 Mapper 接口
 */
@Mapper
public interface DirectorMapper extends BaseMapper<Director> {
    
    /**
     * 根据导演姓名查询导演
     * @param name 导演姓名
     * @return 导演信息
     */
    @Select("SELECT * FROM director WHERE name = #{name}")
    Director selectByName(@Param("name") String name);
    
    /**
     * 根据性别查询导演
     * @param page 分页参数
     * @param gender 性别
     * @return 导演列表
     */
    @Select("SELECT * FROM director WHERE gender = #{gender}")
    IPage<Director> selectByGender(Page<Director> page, @Param("gender") Integer gender);
    
    /**
     * 根据导演风格查询导演
     * @param page 分页参数
     * @param style 导演风格
     * @return 导演列表
     */
    @Select("SELECT * FROM director WHERE style LIKE CONCAT('%', #{style}, '%')")
    IPage<Director> selectByStyle(Page<Director> page, @Param("style") String style);
    
    /**
     * 模糊搜索导演
     * @param page 分页参数
     * @param keyword 关键词
     * @return 导演列表
     */
    @Select("SELECT * FROM director WHERE name LIKE CONCAT('%', #{keyword}, '%') OR english_name LIKE CONCAT('%', #{keyword}, '%')")
    IPage<Director> searchDirectors(Page<Director> page, @Param("keyword") String keyword);
    
    /**
     * 根据电影ID查询导演列表
     * @param movieId 电影ID
     * @return 导演列表
     */
    @Select("SELECT d.* FROM director d INNER JOIN movie_director md ON d.id = md.director_id WHERE md.movie_id = #{movieId}")
    List<Director> selectByMovieId(@Param("movieId") Long movieId);
    
    /**
     * 统计指定性别的导演数量
     * @param gender 性别
     * @return 导演数量
     */
    @Select("SELECT COUNT(*) FROM director WHERE gender = #{gender}")
    Long countByGender(@Param("gender") Integer gender);
    
    /**
     * 查询执导电影最多的导演
     * @param limit 限制数量
     * @return 导演列表
     */
    @Select("SELECT d.*, COUNT(md.movie_id) as movie_count FROM director d LEFT JOIN movie_director md ON d.id = md.director_id GROUP BY d.id ORDER BY movie_count DESC LIMIT #{limit}")
    List<Director> selectMostActiveDirectors(@Param("limit") Integer limit);
    
    /**
     * 根据导演风格统计数量
     * @param style 导演风格
     * @return 导演数量
     */
    @Select("SELECT COUNT(*) FROM director WHERE style LIKE CONCAT('%', #{style}, '%')")
    Long countByStyle(@Param("style") String style);
}