package com.example.dhf_springboot.model.dhf.tools;

import com.example.dhf_springboot.model.dhf.data.Parameter;
import com.example.dhf_springboot.model.dhf.data.ProgramRuntimeData;
import com.example.dhf_springboot.model.dhf.printResult.Out;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class WriteResultToExcel {
    //文件路径
    private static String filePath;
    public static void writeResultToExcel() {
            String fileName = generateUniqueFileName();
            //调用方法生成 Excel 文件
            createExcelFile(fileName);
    }

    /**
     * 生成唯一文件名
     */
    private static String generateUniqueFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        return filePath +"\\" + timestamp + ".xlsx";
    }

    /**
     * 结果写入Excel
     */
    private static  void createExcelFile(String filePath){
        // 要写入的 double[] 数据
        double[] par = Parameter.getParameter0();
        String parAsString = Arrays.toString(par).replace("[", "").replace("]", "");
        String[] parName = {"KC=", "g=", "S0=", "U0=", "D0= ", "a=", "B= ", "K2=", "B0= ", "K0=", "K=", "CC=", "DD= ", "COE= ", "N=", "KW=", "E1=", "E2=", "E3=", "E4=", "E5=", "E6=", "E7= ", "E8=", "E9=", "E10=", "E11=",
        };
//        String[] resultName = {"洪水合格：", "产流合格：", "汇流合格：","平均确定性系数", "洪水总场次："};
        String[] resultName = {"洪水总场次：", "产流合格：", "汇流合格：","洪水合格：","平均确定性系数:","目标函数Max:" };
        double[] result = new double[6];
        int FloodNum;
        int PassY_sum;
        int PassQ_sum;
        int PassFlood_sum;
        double DC_average;
        double value;//目标函数

        Out out = new Out();
        out.resultSimulation();//需要运行一次，计算采用最优参数的结果
        FloodNum = ProgramRuntimeData.getFloodNum();
        PassY_sum = out.getPassY_sum();
        PassQ_sum = out.getPassQ_sum();
        PassFlood_sum = out.getPassFlood_sum();
        DC_average = out.getDC_sum()/FloodNum;
        value = PassFlood_sum+PassY_sum+PassQ_sum+DC_average;

        result[0] = FloodNum;
        result[1] = PassY_sum;
        result[2] = PassQ_sum;
        result[3] = PassFlood_sum;
        result[4] = DC_average;
        result[5] = value;

        // 创建工作簿和工作表
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("ParAndResult");//工作表名
//
//        Row row1 = sheet.createRow(0);
//        Cell cell1 = row1.createCell(0);
//        cell1.setCellValue("目标函数Max");//写入A1，value = PassFlood_sum + PassY_sum + PassQ_sum + DC_average;
//        Cell cell4 = row1.createCell(2);
//        cell4.setCellValue(value);
        for (int i = 0; i < 6; i++) {//写入A2~C6
            Row row = sheet.createRow(i);
            Cell cell = row.createCell(0);
            cell.setCellValue(resultName[i]);
            Cell cell2 = row.createCell(2);
            cell2.setCellValue(result[i]);
        }

        Row row = sheet.createRow(7);
        Cell cell = row.createCell(0);
        cell.setCellValue("Parameter 输出参数:");//写入A8
        for (int i = 0; i < 27; i++) {
            Row row2 = sheet.createRow(i + 8);
            Cell cell2 = row2.createCell(0);
            cell2.setCellValue(parName[i]);//写入A9~A35

            Cell cell3 = row2.createCell(1);
            cell3.setCellValue(par[i]);
        }

        Row row2 = sheet.createRow(36);
        Cell cell2 = row2.createCell(0);
        cell2.setCellValue("可复制Parameter0:");
        Cell cell3 = row2.createCell(2);
        cell3.setCellValue(parAsString);

        // 保存文件
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            System.out.println("文件已生成: " + filePath);
        } catch (IOException e) {
            System.err.println("写入文件时发生错误：" + e.getMessage());
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                System.err.println("关闭工作簿时发生错误：" + e.getMessage());
            }
        }

    }

    public static String getFilePath() {
        return filePath;
    }

    public static void setFilePath(String filePath) {
        WriteResultToExcel.filePath = filePath;
    }
}

