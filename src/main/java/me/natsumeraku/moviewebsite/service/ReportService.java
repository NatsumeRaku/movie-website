package me.natsumeraku.moviewebsite.service;

import java.io.OutputStream;
import java.io.IOException;

/**
 * 报表服务接口
 */
public interface ReportService {
    
    /**
     * 导出电影数据Excel报表
     * @param outputStream 输出流
     * @throws IOException IO异常
     */
    void exportMoviesExcel(OutputStream outputStream) throws IOException;
    
    /**
     * 导出评分数据Excel报表
     * @param outputStream 输出流
     * @throws IOException IO异常
     */
    void exportScoresExcel(OutputStream outputStream) throws IOException;
    
    /**
     * 导出统计数据Excel报表
     * @param outputStream 输出流
     * @throws IOException IO异常
     */
    void exportStatisticsExcel(OutputStream outputStream) throws IOException;
}