package com.example.dhf_springboot.model.dhf_springboot;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * FileName: Param.java
 * 用于封装参数，对象创建后，自动实现数据库的参数加载
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/29
 */
@Data
@Component
public class Parameter {
//    流域参数
    private Double area;       // 流域面积（km²）
    private Double timeSpan;   // 降雨时段数
    private Double L;          // 流域最大河长（km）
//    产流参数
    private Double Kc;         // 产流系数
    private Double g;          // 产流参数
    private Double S0;         // 初始表层蓄水量
    private Double U0;         // 初始下层蓄水量
    private Double D0;
    private Double a;
    private Double B;
    private Double K2;
    private Double KW;
//    汇流参数
    private Double B0;
    private Double K0;
    private Double K;
    private Double CC;
    private Double DD;
    private Double COE;
    private Double N;
//    蒸发参数
    private Double E1;
    private Double E2;
    private Double E3;
    private Double E4;
    private Double E5;
    private Double E6;
    private Double E7;
    private Double E8;
    private Double E9;
    private Double E10;
    private Double E11;

    private final ParameterCache parameterCache;

    public Parameter(ParameterCache parameterCache) {
        this.parameterCache = parameterCache;
    }

    @PostConstruct
    //@PostConstruct用来标记一个方法，这个方法会在依赖注入完成后被自动调用，==”初始化操作“
    public void init() {
//        parameterCache.refresh(); // 刷新参数缓存
        // 从缓存中获取流域参数
        this.area = parameterCache.getParameter("area");
        this.timeSpan = parameterCache.getParameter("timeSpan");
        this.L = parameterCache.getParameter("L");

        // 从缓存中获取产流参数
        this.Kc = parameterCache.getParameter("Kc");
        this.g = parameterCache.getParameter("g");
        this.S0 = parameterCache.getParameter("S0");
        this.U0 = parameterCache.getParameter("U0");
        this.D0 = parameterCache.getParameter("D0");
        this.a = parameterCache.getParameter("a");
        this.B = parameterCache.getParameter("B");
        this.K2 = parameterCache.getParameter("K2");
        this.KW = parameterCache.getParameter("KW");
        // 从缓存中获取汇流参数
        this.B0 = parameterCache.getParameter("B0");
        this.K0 = parameterCache.getParameter("K0");
        this.K = parameterCache.getParameter("K");
        this.CC = parameterCache.getParameter("CC");
        this.DD = parameterCache.getParameter("DD");
        this.COE = parameterCache.getParameter("COE");
        this.N = parameterCache.getParameter("N");

        // 从缓存中获取蒸发参数
        this.E1 = parameterCache.getParameter("E1");
        this.E2 = parameterCache.getParameter("E2");
        this.E3 = parameterCache.getParameter("E3");
        this.E4 = parameterCache.getParameter("E4");
        this.E5 = parameterCache.getParameter("E5");
        this.E6 = parameterCache.getParameter("E6");
        this.E7 = parameterCache.getParameter("E7");
        this.E8 = parameterCache.getParameter("E8");
        this.E9 = parameterCache.getParameter("E9");
        this.E10 = parameterCache.getParameter("E10");
        this.E11 = parameterCache.getParameter("E11");

        // 其他参数初始化...
    }
}
