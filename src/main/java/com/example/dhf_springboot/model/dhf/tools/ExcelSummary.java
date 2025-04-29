package com.example.dhf_springboot.model.dhf.tools;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelSummary {
    private static String inputFolderPath; // 替换为你的文件夹路径
    public static void excelSummary() {
        // 设置待输出文件路径
        String outputFilePath = inputFolderPath + "\\Summary\\Summary.xlsx"; // 替换为汇总Excel的路径

        // 检查并创建输出目录
        File outputFolder = new File(inputFolderPath + "\\Summary");
        if (!outputFolder.exists()) {
            boolean created = outputFolder.mkdirs();
            if (!created) {
                System.out.println("无法创建输出目录: " + outputFolder.getPath());
                return;
            }
        }

        // 创建汇总Excel
        Workbook summaryWorkbook = new XSSFWorkbook();
        Sheet summarySheet = summaryWorkbook.createSheet("汇总");

        // 创建标题行
        Row titleRow = summarySheet.createRow(0);
        String[] titles = {"工作簿名", "洪水总场次", "产流合格场次", "汇流合格场次", "洪水合格场次", "平均确定性系数", "目标函数Max"};
        for (int i = 0; i < titles.length; i++) {
            Cell cell = titleRow.createCell(i);
            cell.setCellValue(titles[i]);
        }

        // 设置列宽
        summarySheet.setColumnWidth(0, 15 * 256); // A列宽度15像素
        for (int i = 1; i <= 6; i++) {
            summarySheet.setColumnWidth(i, 10 * 256); // B~G列宽度10像素
        }

        // 遍历文件夹中的每个Excel文件
        File inputFolder = new File(inputFolderPath);
        File[] files = inputFolder.listFiles((dir, name) -> name.endsWith(".xlsx") || name.endsWith(".xls"));

        if (files != null) {
            int rowIndex = 1; // 数据行从第1行开始
            for (File file : files) {
                try (FileInputStream fis = new FileInputStream(file);
                     Workbook workbook = WorkbookFactory.create(fis)) {
                    Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表

                    Row dataRow = summarySheet.createRow(rowIndex++);
                    // 第一列存放文件名
                    dataRow.createCell(0).setCellValue(file.getName());

                    // 读取C1到C6的数据，存放在B~G列
                    for (int i = 0; i < 6; i++) {
                        Cell sourceCell = sheet.getRow(i).getCell(2); // C列是第3列，索引为2
                        Cell targetCell = dataRow.createCell(i + 1); // B列是第2列，索引为1
                        if (sourceCell != null) {
                            switch (sourceCell.getCellType()) {
                                case NUMERIC:
                                    targetCell.setCellValue(sourceCell.getNumericCellValue());
                                    break;
                                case STRING:
                                    targetCell.setCellValue(sourceCell.getStringCellValue());
                                    break;
                                default:
                                    targetCell.setCellValue(""); // 空单元格
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("处理文件时出错: " + file.getName() + ", 错误: " + e.getMessage());
                }
            }
        }

        // 保存汇总Excel
        try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
            summaryWorkbook.write(fos);
            System.out.println("汇总完成，文件保存至: " + outputFilePath);
        } catch (IOException e) {
            System.out.println("保存汇总文件时出错: " + e.getMessage());
        }
    }

    public static String getInputFolderPath() {
        return inputFolderPath;
    }

    public static void setInputFolderPath(String inputFolderPath) {
        ExcelSummary.inputFolderPath = inputFolderPath;
    }
}
