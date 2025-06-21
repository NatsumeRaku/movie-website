package me.natsumeraku.moviewebsite.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import me.natsumeraku.moviewebsite.common.Result;
import me.natsumeraku.moviewebsite.dto.MovieRankingDTO;
import me.natsumeraku.moviewebsite.entity.Actor;
import me.natsumeraku.moviewebsite.entity.Director;
import me.natsumeraku.moviewebsite.entity.Movie;
import me.natsumeraku.moviewebsite.service.MovieService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 报表控制器 - 处理POI Excel导出和ECharts图表数据
 */
@Controller
@RequestMapping("/report")
public class ReportController {
    
    @Resource
    private MovieService movieService;
    
    /**
     * 报表页面
     */
    @GetMapping("/")
    public String reportPage() {
        return "report/index";
    }
    
    /**
     * 导出电影播放榜单Excel报表
     * @param type 排行榜类型：week-本周，month-本月，all-全部时间，rating-好评排行
     * @param response HTTP响应
     */
    @GetMapping("/export/ranking")
    public void exportMovieRanking(
            @RequestParam(defaultValue = "week") String type,
            HttpServletResponse response) throws IOException {
        
        // 固定获取前100名排行榜数据
        final int RANKING_LIMIT = 100;
        List<Movie> movies = movieService.getMovieRanking(type, RANKING_LIMIT);
        
        // 创建工作簿
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("电影播放榜单");
        
        // 创建标题样式
        CellStyle titleStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 16);
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        
        // 创建表头样式
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        
        // 创建数据样式
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
        
        // 创建标题行
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        String titleText = getTypeDisplayName(type) + "电影播放榜单";
        titleCell.setCellValue(titleText);
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 6));
        
        // 创建生成时间行
        Row timeRow = sheet.createRow(1);
        Cell timeCell = timeRow.createCell(0);
        timeCell.setCellValue("生成时间：" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(1, 1, 0, 6));
        
        // 创建表头
        Row headerRow = sheet.createRow(3);
        String[] headers = {"排名", "电影名称", "类型", "地区", "评分", "播放次数", "上映日期"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        // 填充数据
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            Row row = sheet.createRow(i + 4);
            
            Cell rankCell = row.createCell(0);
            rankCell.setCellValue(i + 1);
            rankCell.setCellStyle(dataStyle);
            
            Cell titleCellData = row.createCell(1);
            titleCellData.setCellValue(movie.getTitle());
            titleCellData.setCellStyle(dataStyle);
            
            Cell typeCell = row.createCell(2);
            typeCell.setCellValue(movie.getType());
            typeCell.setCellStyle(dataStyle);
            
            Cell regionCell = row.createCell(3);
            regionCell.setCellValue(movie.getRegion());
            regionCell.setCellStyle(dataStyle);
            
            Cell scoreCell = row.createCell(4);
            scoreCell.setCellValue(movie.getScore() != null ? movie.getScore().doubleValue() : 0.0);
            scoreCell.setCellStyle(dataStyle);
            
            Cell playCountCell = row.createCell(5);
            playCountCell.setCellValue(movie.getPlayCount());
            playCountCell.setCellStyle(dataStyle);
            
            Cell releaseDateCell = row.createCell(6);
            releaseDateCell.setCellValue(movie.getReleaseDate() != null ? movie.getReleaseDate().toString() : "-");
            releaseDateCell.setCellStyle(dataStyle);
        }
        
        // 自动调整列宽
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        // 设置响应头
        String typeDisplayName = getTypeDisplayName(type);
        String fileName = "电影排行榜_" + typeDisplayName + "_前100名_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        
        // 写入响应
        workbook.write(response.getOutputStream());
        workbook.close();
    }
    
    /**
     * 获取ECharts图表数据 - 电影类型分布
     */
    @GetMapping("/chart/type-distribution")
    @ResponseBody
    public Result<Map<String, Object>> getTypeDistributionData() {
        List<MovieRankingDTO> typeData = movieService.getMovieTypeDistribution();
        
        Map<String, Object> chartData = new HashMap<>();
        chartData.put("title", "电影类型分布");
        chartData.put("data", typeData);
        
        return Result.success(chartData);
    }
    
    /**
     * 获取ECharts图表数据 - 月度播放趋势
     */
    @GetMapping("/chart/monthly-trend")
    @ResponseBody
    public Result<Map<String, Object>> getMonthlyTrendData() {
        List<MovieRankingDTO> trendData = movieService.getMonthlyPlayTrend();
        
        Map<String, Object> chartData = new HashMap<>();
        chartData.put("title", "月度播放趋势");
        chartData.put("data", trendData);
        
        return Result.success(chartData);
    }
    
    /**
     * 获取ECharts图表数据 - 地区分布
     */
    @GetMapping("/chart/region-distribution")
    @ResponseBody
    public Result<Map<String, Object>> getRegionDistributionData() {
        List<MovieRankingDTO> regionData = movieService.getMovieRegionDistribution();
        
        Map<String, Object> chartData = new HashMap<>();
        chartData.put("title", "电影地区分布");
        chartData.put("data", regionData);
        
        return Result.success(chartData);
    }
    
    /**
     * 导出电影数据Excel报表
     * @param response HTTP响应
     */
    @GetMapping("/export/movies")
    public void exportMoviesExcel(HttpServletResponse response) throws IOException {
        // 获取所有电影数据
        List<Movie> movies = movieService.getAllMovies();
        
        // 设置响应头
        String fileName = "电影数据报表_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("电影数据报表");
        
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "电影名称", "类型", "地区", "导演", "主演", "评分", "播放次数"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            Row row = sheet.createRow(i + 1);
            
            // 获取导演信息
            List<Director> directors = movieService.getDirectorsByMovieId(movie.getId());
            String directorNames = directors.stream()
                    .map(Director::getName)
                    .collect(Collectors.joining(", "));
            
            // 获取演员信息
            List<Actor> actors = movieService.getActorsByMovieId(movie.getId());
            String actorNames = actors.stream()
                    .map(Actor::getName)
                    .collect(Collectors.joining(", "));
            
            row.createCell(0).setCellValue(movie.getId());
            row.createCell(1).setCellValue(movie.getTitle());
            row.createCell(2).setCellValue(movie.getType());
            row.createCell(3).setCellValue(movie.getRegion());
            row.createCell(4).setCellValue(directorNames);
            row.createCell(5).setCellValue(actorNames);
            row.createCell(6).setCellValue(movie.getScore() != null ? movie.getScore().doubleValue() : 0.0);
            row.createCell(7).setCellValue(movie.getPlayCount());
        }
        
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        workbook.write(response.getOutputStream());
        workbook.close();
    }
    
    /**
     * 获取类型显示名称
     */
    private String getTypeDisplayName(String type) {
        switch (type) {
            case "week":
                return "本周";
            case "month":
                return "本月";
            case "rating":
                return "好评";
            default:
                return "全部";
        }
    }
}