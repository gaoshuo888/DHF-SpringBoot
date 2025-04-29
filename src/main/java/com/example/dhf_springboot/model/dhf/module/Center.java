package com.example.dhf_springboot.model.dhf.module;

import com.example.dhf_springboot.model.dhf.composable.AlgorithmDefineDomain;
import com.example.dhf_springboot.model.dhf.composable.Test3;
import com.example.dhf_springboot.model.dhf.data.Parameter;
import com.example.dhf_springboot.model.dhf.printResult.Out;
import com.example.dhf_springboot.model.dhf.tools.ExcelSummary;
import com.example.dhf_springboot.model.dhf.tools.VarToParameter0;
import com.example.dhf_springboot.model.dhf.tools.WriteResultToExcel;

public class Center {           //主程序main入口

    public static void main(String[] arg) {

        /** 设置参数 **/
        Test3.setTrails_sum(10);       //运行次数
        Parameter.setArea(692);               //设置流域面积,单位：km2
        Parameter.setL(94.9);                    //设置流域最大河长，单位：km
        Parameter.setTimeSpan(1);    //设置资料时间步长  --??降雨的时间步长，h

        AlgorithmDefineDomain.setPopsize(500);                      //种群数量 100， 400，1000
        AlgorithmDefineDomain.setTerminateNum(50000);//??设置繁衍代数，迭代次数,25000. 50000，100000

        AlgorithmDefineDomain.setCrossoverProbability(0.9);//交叉操作发生的概率0.9
        AlgorithmDefineDomain.setCrossoverDistributionIndex(20.0);//分布指数通常用于调整交叉操作的探索性质。较大的分布指数值可以增加探索性，而较小的值则更加聚焦在已知解附近的搜索。
        AlgorithmDefineDomain.setPm(1.0);//变异算子1.0
        AlgorithmDefineDomain.setMutationDistributionIndex(20.0);//分布指数用于控制突变操作的探索性质。较大的分布指数值通常会增加突变操作的探索性，使算法更倾向于在搜索空间中广泛探索

        String csvFile = "E:\\work\\1小论文\\小论文成果\\大伙房模型\\dhfV5.4.2\\VAR.csv"; // 替换为实际的VAR.csv文件路径
        String excelFile = "E:\\work\\1小论文\\小论文成果\\大伙房模型\\dhfV5.4.2\\write result";//存放每次运行结果的文件路径
        WriteResultToExcel.setFilePath(excelFile);
        ExcelSummary.setInputFolderPath(excelFile);

        int n = 0;//1.程序运行多次 2.将结果存储到Excel中 3.汇总结果
//        n = 1;/**手动复制最优参数到Parameter0，输出对应结果*/

        if (n == 0){
            for (int i = 0; i < Test3.getTrails_sum(); i++) {//可以多次运行，并将结果存储到Excel中
                //记录是第几次循环
                Test3.setTrails(i+1);
                /** GA率定 */
                AlgorithmDefineDomain algorithmDefineDomain = new AlgorithmDefineDomain();
                algorithmDefineDomain.best();

                /** 读取VAR.csv，将最优参数赋值给Parameter0 **/
                VarToParameter0 varToParameter0 = new VarToParameter0(csvFile);
                Parameter.setParameter0(varToParameter0.varToParameter0());//修改原程序的if else语句，分两次运行的话需要人工改变Parameter0的值

                /** 使用最优参数模拟产汇流，将结果写入Excel中 + 汇总所有结果 */
                WriteResultToExcel.writeResultToExcel();
                ExcelSummary.excelSummary();
            }
        }else{
            /** 原始输出 **/
            System.out.print("~~~~  原始输出  ~~~~~");//不断调整，将最优参数写入Parameter中，最后得到最优解
            Out out = new Out();
            out.testSimulation();        //原始输出--Parameter使用人为确定的参数

            /** 复制到Excel **/
            System.out.println("~~~~  复制到Excel  ~~~~~");
            Parameter parameter = new Parameter();
            System.out.println(parameter.toString());//输出最优的参数，方便复制->Excel
            out.excelSimulation();     //输出结果，方便复制->Excel
        }
    }
}
