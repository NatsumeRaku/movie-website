package me.natsumeraku.moviewebsite.service.impl;

import jakarta.annotation.Resource;
import me.natsumeraku.moviewebsite.entity.Actor;
import me.natsumeraku.moviewebsite.entity.Director;
import me.natsumeraku.moviewebsite.entity.Movie;
import me.natsumeraku.moviewebsite.entity.Score;
import me.natsumeraku.moviewebsite.service.MovieService;
import me.natsumeraku.moviewebsite.service.ReportService;
import me.natsumeraku.moviewebsite.service.ScoreService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    
    @Resource
    private MovieService movieService;
    
    @Resource
    private ScoreService scoreService;
    
    @Override
    public void exportMoviesExcel(OutputStream outputStream) throws IOException {
        List<Movie> movies = movieService.getAllMovies();
        
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("电影数据报表");
        
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle dataStyle = createDataStyle(workbook);
        
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("电影数据统计报表");
        titleCell.setCellStyle(createTitleStyle(workbook));
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 7));
        
        Row timeRow = sheet.createRow(1);
        Cell timeCell = timeRow.createCell(0);
        timeCell.setCellValue("生成时间：" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(1, 1, 0, 7));
        
        Row headerRow = sheet.createRow(3);
        String[] headers = {"ID", "电影名称", "类型", "地区", "导演", "主演", "评分", "播放次数"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            Row row = sheet.createRow(i + 4);
            
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
            
            for (int j = 0; j < 8; j++) {
                row.getCell(j).setCellStyle(dataStyle);
            }
        }
        
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        workbook.write(outputStream);
        workbook.close();
    }
    
    @Override
    public void exportScoresExcel(OutputStream outputStream) throws IOException {
        List<Movie> movies = movieService.getAllMovies();
        
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("评分数据报表");
        
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle dataStyle = createDataStyle(workbook);
        
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("电影评分统计报表");
        titleCell.setCellStyle(createTitleStyle(workbook));
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 5));
        
        Row timeRow = sheet.createRow(1);
        Cell timeCell = timeRow.createCell(0);
        timeCell.setCellValue("生成时间：" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(1, 1, 0, 5));
        
        Row headerRow = sheet.createRow(3);
        String[] headers = {"电影ID", "电影名称", "评分总数", "平均评分", "最高评分", "最低评分"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            List<Score> scores = scoreService.findByMovieId(movie.getId());
            
            Row row = sheet.createRow(i + 4);
            
            row.createCell(0).setCellValue(movie.getId());
            row.createCell(1).setCellValue(movie.getTitle());
            row.createCell(2).setCellValue(scores.size());
            
            if (!scores.isEmpty()) {
                double avgScore = scores.stream().mapToInt(Score::getScore).average().orElse(0.0);
                int maxScore = scores.stream().mapToInt(Score::getScore).max().orElse(0);
                int minScore = scores.stream().mapToInt(Score::getScore).min().orElse(0);
                
                row.createCell(3).setCellValue(Math.round(avgScore * 100.0) / 100.0);
                row.createCell(4).setCellValue(maxScore);
                row.createCell(5).setCellValue(minScore);
            } else {
                row.createCell(3).setCellValue(0.0);
                row.createCell(4).setCellValue(0);
                row.createCell(5).setCellValue(0);
            }
            
            for (int j = 0; j < 6; j++) {
                row.getCell(j).setCellStyle(dataStyle);
            }
        }
        
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        workbook.write(outputStream);
        workbook.close();
    }
    
    @Override
    public void exportStatisticsExcel(OutputStream outputStream) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        
        createTypeStatisticsSheet(workbook);
        createRegionStatisticsSheet(workbook);
        createPlayCountStatisticsSheet(workbook);
        
        workbook.write(outputStream);
        workbook.close();
    }
    
    private void createTypeStatisticsSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet("类型统计");
        
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle dataStyle = createDataStyle(workbook);
        
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("电影类型分布统计");
        titleCell.setCellStyle(createTitleStyle(workbook));
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 2));
        
        Row headerRow = sheet.createRow(2);
        String[] headers = {"类型", "数量", "占比(%)"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        List<Movie> allMovies = movieService.getAllMovies();
        java.util.Map<String, Long> typeCount = allMovies.stream()
            .collect(java.util.stream.Collectors.groupingBy(
                Movie::getType, java.util.stream.Collectors.counting()));
        
        int rowIndex = 3;
        long totalMovies = allMovies.size();
        
        for (java.util.Map.Entry<String, Long> entry : typeCount.entrySet()) {
            Row row = sheet.createRow(rowIndex++);
            
            row.createCell(0).setCellValue(entry.getKey());
            row.createCell(1).setCellValue(entry.getValue());
            row.createCell(2).setCellValue(Math.round(entry.getValue() * 100.0 / totalMovies * 100.0) / 100.0);
            
            for (int j = 0; j < 3; j++) {
                row.getCell(j).setCellStyle(dataStyle);
            }
        }
        
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }
    
    private void createRegionStatisticsSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet("地区统计");
        
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle dataStyle = createDataStyle(workbook);
        
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("电影地区分布统计");
        titleCell.setCellStyle(createTitleStyle(workbook));
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 2));
        
        Row headerRow = sheet.createRow(2);
        String[] headers = {"地区", "数量", "占比(%)"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        List<Movie> allMovies = movieService.getAllMovies();
        java.util.Map<String, Long> regionCount = allMovies.stream()
            .collect(java.util.stream.Collectors.groupingBy(
                Movie::getRegion, java.util.stream.Collectors.counting()));
        
        int rowIndex = 3;
        long totalMovies = allMovies.size();
        
        for (java.util.Map.Entry<String, Long> entry : regionCount.entrySet()) {
            Row row = sheet.createRow(rowIndex++);
            
            row.createCell(0).setCellValue(entry.getKey());
            row.createCell(1).setCellValue(entry.getValue());
            row.createCell(2).setCellValue(Math.round(entry.getValue() * 100.0 / totalMovies * 100.0) / 100.0);
            
            for (int j = 0; j < 3; j++) {
                row.getCell(j).setCellStyle(dataStyle);
            }
        }
        
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }
    
    private void createPlayCountStatisticsSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet("播放统计");
        
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle dataStyle = createDataStyle(workbook);
        
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("电影播放量统计");
        titleCell.setCellStyle(createTitleStyle(workbook));
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 3));
        
        Row headerRow = sheet.createRow(2);
        String[] headers = {"排名", "电影名称", "播放次数", "类型"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        List<Movie> topMovies = movieService.getAllTimeHotMovies(50);
        
        for (int i = 0; i < topMovies.size(); i++) {
            Movie movie = topMovies.get(i);
            Row row = sheet.createRow(i + 3);
            
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(movie.getTitle());
            row.createCell(2).setCellValue(movie.getPlayCount());
            row.createCell(3).setCellValue(movie.getType());
            
            for (int j = 0; j < 4; j++) {
                row.getCell(j).setCellStyle(dataStyle);
            }
        }
        
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }
    
    private CellStyle createTitleStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }
    
    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }
    
    private CellStyle createDataStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        return style;
    }
}