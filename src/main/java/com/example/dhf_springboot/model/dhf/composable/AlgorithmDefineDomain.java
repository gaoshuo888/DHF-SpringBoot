package com.example.dhf_springboot.model.dhf.composable;

import org.uma.jmetal.algorithm.examples.AlgorithmRunner;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.crossover.impl.SBXCrossover;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.mutation.impl.PolynomialMutation;
import org.uma.jmetal.operator.selection.SelectionOperator;
import org.uma.jmetal.operator.selection.impl.BinaryTournamentSelection;
import org.uma.jmetal.problem.doubleproblem.impl.ComposableDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.AbstractAlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;

import java.util.List;

/**
 * FileName: AlgorithmDefineDomain.java
 * 多目标/单目标优化算法
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2023/11/27
 */
public class AlgorithmDefineDomain extends AbstractAlgorithmRunner {
    public static int trails = 1;      //运行次数,使用的新算法，运行多次是没意义的(已经验证过！！），最后保留的只是最后运行时的最优解
    private static int popsize = 100;                            //种群数量
    private static int terminateNum = 0;                        //繁衍代数，迭代次数

    private static double crossoverProbability = 0.9;//交叉操作发生的概率
    private static double crossoverDistributionIndex = 20.0;//分布指数通常用于调整交叉操作的探索性质。较大的分布指数值可以增加探索性，而较小的值则更加聚焦在已知解附近的搜索。

    private static double pm = 1.0;        //变异算子
    private static double mutationDistributionIndex = 20.0;//分布指数用于控制突变操作的探索性质。较大的分布指数值通常会增加突变操作的探索性，使算法更倾向于在搜索空间中广泛探索

    public void best() {
        for (int l = 0; l < trails; l++) {

            ComposableDoubleProblem problem = new ComposableDoubleProblem();
            /**定义参数变化区间**/
//            {
//            problem.addVariable(0.8, 1.0);      //Kc,第一个参数x[0]
//            problem.addVariable(0, 1.0);      //g,第二个参数x[1]
//            problem.addVariable(0, 50);     //S0，x[2]
//            problem.addVariable(0, 90);     //U0,x[3]
//            problem.addVariable(70, 160);   //D0,x[4]
//            problem.addVariable(0, 5);      //a,x[5]
//            problem.addVariable(1.0, 3.0);      //B,x[6]
//            problem.addVariable(0.2, 0.8);      //K2,x[7]
//            problem.addVariable(0.5, 2.0);      //B0,x[8]
//            problem.addVariable(0.0, 0.8);      //K0,x[9]
//            problem.addVariable(0.7, 1.0);      //K,x[10]
//            problem.addVariable(0.5, 4.0);      //CC,x[11]
//            problem.addVariable(0.5, 4.0);      //DD,x[12]
//            problem.addVariable(0.1, 0.8);      //COE,x[13]
//            problem.addVariable(2.0, 6.0);      //N,x[14]
//            problem.addVariable(0, 1.0);      //KW,x[15]
//
//            for (int i = 0; i < 9; i++) {
//                problem.addVariable(0, 9);  //E1~E9
//            }
//                problem.addVariable(0, 4);  //E10,x[25]
//                problem.addVariable(0, 3);  //E11,x[26]
//            }

            {  //模型的参数多半在满足其物理意义的前提下确定，只有6个需要用优选法选定或试错法确定。
                problem.addVariable(0.88, 0.88);      //Kc,第一个参数x[0]
                problem.addVariable(0.05, 0.05);      //g,第二个参数x[1]
                problem.addVariable(50, 50);     //S0，x[2]
                problem.addVariable(70, 70);     //U0,x[3]
                problem.addVariable(125, 125);   //D0,x[4]
                problem.addVariable(0, 5);      //a,x[5]
                problem.addVariable(1.0, 3.0);      //B,x[6]
                problem.addVariable(0.2, 0.8);      //K2,x[7]

                problem.addVariable(1.06, 1.06);      //B0,x[8]
                problem.addVariable(0.0, 0.8);      //K0,x[9]
                problem.addVariable(0.7, 1.0);      //K,x[10]
                problem.addVariable(0.5, 4.0);      //CC,x[11]
                problem.addVariable(0.5, 4.0);      //DD,x[12]
                problem.addVariable(0.1, 0.8);      //COE,x[13]
                problem.addVariable(2.0, 6.0);      //N,x[14]
                problem.addVariable(0.85, 1.0);      //KW,x[15]

                problem.addVariable(0, 0);  //E1,X[16]，？？参数不影响结果。
                problem.addVariable(0, 0);  //E2,X[17]，？？
                problem.addVariable(0, 0);  //E3,X[18]。？？

                problem.addVariable(3.5, 3.5);  //E4,X[19]
                problem.addVariable(4, 4);  //E5,X[20]
                problem.addVariable(4.6, 4.6);  //E6,X[21]
                problem.addVariable(5.0, 5.0);  //E7,X[22]
                problem.addVariable(5.4, 5.4);  //E8,X[23]
                problem.addVariable(4.8, 4.8);  //E9,X[24]

                problem.addVariable(2, 2);  //E10,x[25]
                problem.addVariable(0.8, 0.8);  //E11,x[26]
            }


//            {
//                problem.addVariable(2.5, 2.5);  //E1,X[16]
//                problem.addVariable(2.5, 2.5);  //E2,X[17]
//                problem.addVariable(2.5, 2.5);  //E3,X[18]
//
//                problem.addVariable(3.5, 3.5);  //E4,X[19]
//                problem.addVariable(3.6, 3.6);  //E5,X[20]
//                problem.addVariable(4.0, 4.0);  //E6,X[21]
//                problem.addVariable(4.4, 4.4);  //E7,X[22]
//                problem.addVariable(4.6, 4.6);  //E8,X[23]
//                problem.addVariable(3.6, 3.6);  //E9,X[24]
//
//                problem.addVariable(4, 4);  //E10,x[25]
//                problem.addVariable(3, 3);  //E11,x[26]
//            }

            Test3 test3 = new Test3();
            //建立变量和目标函数的关系
            problem.addFunction((x) -> -test3.f1(x));//第一个目标，只使用一个目标的话是单目标优化。
//            problem.addFunction((x) -> -test3.f2(x));//第二个目标,多目标
//            problem.addFunction((x) -> -test3.f3(x));//第三个目标
//            problem.addFunction((x) -> -test3.f4(x));//第三个目标

            CrossoverOperator<DoubleSolution> crossover = new SBXCrossover(crossoverProbability,
                    crossoverDistributionIndex);

            // 配置突变操作
            double mutationProbability = pm / problem.numberOfVariables();//突变概率
            MutationOperator<DoubleSolution> mutation = new PolynomialMutation(mutationProbability,
                    mutationDistributionIndex);

            // 配置选择操作
            SelectionOperator<List<DoubleSolution>, DoubleSolution> selection = new BinaryTournamentSelection<>(
                    new RankingAndCrowdingDistanceComparator<>());
//        int populationSize = 100;//种群大小
            NSGAII<DoubleSolution> algorithm =
                    new NSGAIIBuilder<>(problem, crossover, mutation, popsize)
                            .setSelectionOperator(selection)
                            .setMaxEvaluations(terminateNum)//迭代次数25000，自动取整百，1301-->1400;1523-->1600;1300-->1300
                            .build();

            AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();

            List<DoubleSolution> population = algorithm.result();
            long computingTime = algorithmRunner.getComputingTime();

            JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

            printFinalSolutionSet(population);

//        System.out.println(population);//变量
//
//        for (int i = 0; i < population.size(); i++) {
//            System.out.println(population.get(i).objectives()[0]);//变量对应函数值
//        }
//        for (int i = 0; i < population.size(); i++) {
//            System.out.println(population.get(i).objectives()[1]);
//        }

        }
    }

    public static int getTrails() {
        return trails;
    }

    public static void setTrails(int trails) {
        AlgorithmDefineDomain.trails = trails;
    }

    public static int getPopsize() {
        return popsize;
    }

    public static void setPopsize(int popsize) {
        AlgorithmDefineDomain.popsize = popsize;
    }

    public static int getTerminateNum() {
        return terminateNum;
    }

    public static void setTerminateNum(int terminateNum) {
        AlgorithmDefineDomain.terminateNum = terminateNum;
    }

    public static double getCrossoverProbability() {
        return crossoverProbability;
    }

    public static void setCrossoverProbability(double crossoverProbability) {
        AlgorithmDefineDomain.crossoverProbability = crossoverProbability;
    }

    public static double getCrossoverDistributionIndex() {
        return crossoverDistributionIndex;
    }

    public static void setCrossoverDistributionIndex(double crossoverDistributionIndex) {
        AlgorithmDefineDomain.crossoverDistributionIndex = crossoverDistributionIndex;
    }

    public static double getPm() {
        return pm;
    }

    public static void setPm(double pm) {
        AlgorithmDefineDomain.pm = pm;
    }

    public static double getMutationDistributionIndex() {
        return mutationDistributionIndex;
    }

    public static void setMutationDistributionIndex(double mutationDistributionIndex) {
        AlgorithmDefineDomain.mutationDistributionIndex = mutationDistributionIndex;
    }
}
