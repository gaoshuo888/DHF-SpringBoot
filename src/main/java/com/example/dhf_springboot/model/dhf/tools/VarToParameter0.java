package com.example.dhf_springboot.model.dhf.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * FileName: ReadCSV.java
 * 读取VAR.csv文件的变量值，并更新Parameter0的参数值
 * 此方法还可以进一步改进，省略使用csv这一步骤
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2024/12/11
 */
public class VarToParameter0 {

    String csvFile;// 替换为实际的文件路径
    public double[] varToParameter0() {
        double[] data = new double[27];
        // CSV文件的路径

        // 创建CSVReader对象并调用方法
        CSVReader reader = new CSVReader();
        reader.readCSV(csvFile, data);

        return data;
    }

    public VarToParameter0(String csvFile) {
        this.csvFile = csvFile;
    }

    public VarToParameter0() {
    }
}

class CSVReader {
    public void readCSV(String csvFile, double[] data) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // 读取第一行
            String line = br.readLine();

            if (line != null) {
                // 按逗号分隔数据
                String[] values = line.split(",");

                // 检查数据量是否足够
                if (values.length >= 27) {
                    // 将前27个数据转换为double并存入数组
                    for (int i = 0; i < 27; i++) {
                        data[i] = Double.parseDouble(values[i]);
                    }
                } else {
                    System.err.println("CSV文件中的数据不足27列！");
                }
            } else {
                System.err.println("CSV文件是空的或无法读取！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("CSV文件中的数据格式错误，无法转换为double类型！");
            e.printStackTrace();
        }
    }
}
