package me.natsumeraku.moviewebsite.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.natsumeraku.moviewebsite.entity.Movie;
import me.natsumeraku.moviewebsite.service.MovieService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MockMovieServiceImpl implements MovieService {
    
    private List<Movie> createMockMovies() {
        List<Movie> movies = new ArrayList<>();
        
        Movie movie1 = new Movie();
        movie1.setId(1L);
        movie1.setTitle("阿凡达：水之道");
        movie1.setType("科幻");
        movie1.setReleaseDate(LocalDate.of(2022, 12, 16));
        movie1.setScore(new java.math.BigDecimal("8.5"));
        movie1.setPlayCount(1500000L);
        movie1.setCoverUrl("https://via.placeholder.com/300x400?text=阿凡达");
        movie1.setVipFlag(1);
        movies.add(movie1);
        
        Movie movie2 = new Movie();
        movie2.setId(2L);
        movie2.setTitle("流浪地球2");
        movie2.setType("科幻");
        movie2.setReleaseDate(LocalDate.of(2023, 1, 22));
        movie2.setScore(new java.math.BigDecimal("8.3"));
        movie2.setPlayCount(1200000L);
        movie2.setCoverUrl("https://via.placeholder.com/300x400?text=流浪地球2");
        movie2.setVipFlag(0);
        movies.add(movie2);
        
        Movie movie3 = new Movie();
        movie3.setId(3L);
        movie3.setTitle("满江红");
        movie3.setType("剧情");
        movie3.setReleaseDate(LocalDate.of(2023, 1, 22));
        movie3.setScore(new java.math.BigDecimal("8.0"));
        movie3.setPlayCount(1000000L);
        movie3.setCoverUrl("https://via.placeholder.com/300x400?text=满江红");
        movie3.setVipFlag(0);
        movies.add(movie3);
        
        Movie movie4 = new Movie();
        movie4.setId(4L);
        movie4.setTitle("深海");
        movie4.setType("动画");
        movie4.setReleaseDate(LocalDate.of(2023, 1, 22));
        movie4.setScore(new java.math.BigDecimal("7.8"));
        movie4.setPlayCount(800000L);
        movie4.setCoverUrl("https://via.placeholder.com/300x400?text=深海");
        movie4.setVipFlag(1);
        movies.add(movie4);
        
        Movie movie5 = new Movie();
        movie5.setId(5L);
        movie5.setTitle("熊出没·伴我熊芯");
        movie5.setType("动画");
        movie5.setReleaseDate(LocalDate.of(2023, 1, 22));
        movie5.setScore(new java.math.BigDecimal("7.5"));
        movie5.setPlayCount(600000L);
        movie5.setCoverUrl("https://via.placeholder.com/300x400?text=熊出没");
        movie5.setVipFlag(0);
        movies.add(movie5);
        
        Movie movie6 = new Movie();
        movie6.setId(6L);
        movie6.setTitle("无名");
        movie6.setType("动作");
        movie6.setReleaseDate(LocalDate.of(2023, 1, 22));
        movie6.setScore(new java.math.BigDecimal("7.9"));
        movie6.setPlayCount(700000L);
        movie6.setCoverUrl("https://via.placeholder.com/300x400?text=无名");
        movie6.setVipFlag(1);
        movies.add(movie6);
        
        Movie movie7 = new Movie();
        movie7.setId(7L);
        movie7.setTitle("中国乒乓之绝地反击");
        movie7.setType("剧情");
        movie7.setReleaseDate(LocalDate.of(2023, 2, 17));
        movie7.setScore(new java.math.BigDecimal("7.6"));
        movie7.setPlayCount(500000L);
        movie7.setCoverUrl("https://via.placeholder.com/300x400?text=中国乒乓");
        movie7.setVipFlag(0);
        movies.add(movie7);
        
        Movie movie8 = new Movie();
        movie8.setId(8L);
        movie8.setTitle("交换人生");
        movie8.setType("喜剧");
        movie8.setReleaseDate(LocalDate.of(2023, 1, 25));
        movie8.setScore(new java.math.BigDecimal("7.4"));
        movie8.setPlayCount(400000L);
        movie8.setCoverUrl("https://via.placeholder.com/300x400?text=交换人生");
        movie8.setVipFlag(0);
        movies.add(movie8);
        
        return movies;
    }
    
    @Override
    public IPage<Movie> getMovieList(Page<Movie> page) {
        List<Movie> movies = createMockMovies();
        page.setRecords(movies);
        page.setTotal(movies.size());
        return page;
    }
    
    @Override
    public IPage<Movie> getMoviesByType(Page<Movie> page, String type) {
        List<Movie> allMovies = createMockMovies();
        List<Movie> filteredMovies = allMovies.stream()
                .filter(movie -> movie.getType().contains(type))
                .toList();
        page.setRecords(filteredMovies);
        page.setTotal(filteredMovies.size());
        return page;
    }
    
    @Override
    public IPage<Movie> searchMovies(Page<Movie> page, String keyword) {
        List<Movie> allMovies = createMockMovies();
        List<Movie> filteredMovies = allMovies.stream()
                .filter(movie -> movie.getTitle().contains(keyword) || 
                               movie.getDescription().contains(keyword))
                .toList();
        page.setRecords(filteredMovies);
        page.setTotal(filteredMovies.size());
        return page;
    }
    
    @Override
    public List<Movie> getHotMovies(int limit) {
        List<Movie> movies = createMockMovies();
        return movies.stream()
                .sorted((m1, m2) -> Long.compare(m2.getPlayCount(), m1.getPlayCount()))
                .limit(limit)
                .toList();
    }
    
    @Override
    public List<Movie> getLatestMovies(int limit) {
        List<Movie> movies = createMockMovies();
        return movies.stream()
                .sorted((m1, m2) -> m2.getReleaseDate().compareTo(m1.getReleaseDate()))
                .limit(limit)
                .toList();
    }
    
    @Override
    public List<Movie> getRecommendedMovies(int limit) {
        List<Movie> movies = createMockMovies();
        return movies.stream()
                .sorted((m1, m2) -> m2.getScore().compareTo(m1.getScore()))
                .limit(limit)
                .toList();
    }
    
    @Override
    public Movie findById(Long id) {
        return createMockMovies().stream()
                .filter(movie -> movie.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public boolean addMovie(Movie movie) {
        return true;
    }
    
    @Override
    public boolean updateMovie(Movie movie) {
        return true;
    }
    
    @Override
    public boolean deleteMovie(Long id) {
        return true;
    }
    
    @Override
    public boolean incrementPlayCount(Long id) {
        return true;
    }
    
    @Override
    public long getTotalCount() {
        return createMockMovies().size();
    }

    @Override
    public List<Movie> getMoviesByDirector(Long directorId) {
        // Mock implementation - return empty list
        return new ArrayList<>();
    }

    @Override
    public List<Movie> getMoviesByActor(Long actorId) {
        // Mock implementation - return empty list
        return new ArrayList<>();
    }
    
    @Override
    public List<Movie> getWeeklyHotMovies(int limit) {
        // Mock implementation - return hot movies for this week
        List<Movie> movies = createMockMovies();
        return movies.stream()
                .sorted((m1, m2) -> Long.compare(m2.getPlayCount(), m1.getPlayCount()))
                .limit(limit)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Movie> getMonthlyHotMovies(int limit) {
        // Mock implementation - return hot movies for this month
        List<Movie> movies = createMockMovies();
        return movies.stream()
                .sorted((m1, m2) -> Long.compare(m2.getPlayCount(), m1.getPlayCount()))
                .limit(limit)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Movie> getAllTimeHotMovies(int limit) {
        // Mock implementation - return all time hot movies
        List<Movie> movies = createMockMovies();
        return movies.stream()
                .sorted((m1, m2) -> Long.compare(m2.getPlayCount(), m1.getPlayCount()))
                .limit(limit)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Movie> getTopRatedMovies(int limit) {
        // Mock implementation - return top-rated movies
        List<Movie> movies = createMockMovies();
        return movies.stream()
                .sorted((m1, m2) -> m2.getScore().compareTo(m1.getScore()))
                .limit(limit)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Movie> getMovieRanking(String rankType, int limit) {
        // Mock implementation - return movies based on rank type
        return switch (rankType.toLowerCase()) {
            case "week" -> getWeeklyHotMovies(limit);
            case "month" -> getMonthlyHotMovies(limit);
            case "all" -> getAllTimeHotMovies(limit);
            case "rating" -> getTopRatedMovies(limit);
            default -> getHotMovies(limit);
        };
    }
}