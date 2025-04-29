//package test;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class WriteDoubleArrayToExcel1 {
//    public static void main(String[] args) {
//        // 定义生成的工作簿数量
//        int workbookCount = 3;
//
//        for (int i = 0; i < workbookCount; i++) {
//            // 模拟获取不同参数
//            double[] par = Parameter.getParameter0(); // 可根据不同索引获取不同的参数
//            String fileName = generateUniqueFileName(i);
//
//            // 调用方法生成 Excel 文件
//            createExcelFile(par, fileName);
//        }
//    }
//
//    /**
//     * 生成唯一文件名
//     */
//    private static String generateUniqueFileName(int index) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
//        String timestamp = sdf.format(new Date());
//        return "E:\\work\\1小论文\\小论文成果\\大伙房模型\\dhfV5.3.0\\DoubleData_" + timestamp + "_Run" + index + ".xlsx";
//    }
//
//    /**
//     * 创建 Excel 文件
//     */
//    private static void createExcelFile(double[] par, String filePath) {
//        String[] parName = {"KC=", "g=", "S0=", "U0=", "D0= ", "a=", "B= ", "K2=", "B0= ", "K0=", "K=", "CC=", "DD= ", "COE= ", "N=", "KW=", "E1=", "E2=", "E3=", "E4=", "E5=", "E6=", "E7= ", "E8=", "E9=", "E10=", "E11="};
//        String[] resultName = {"洪水合格：", "产流合格：", "汇流合格：", "洪水总场次："};
//        double[] result = new double[4];
//
//        // 模拟运行获取结果
//        Out out = new Out();
//        out.testSimulation();
//        result[0] = out.getPassFlood_sum();
//        result[1] = out.getPassY_sum();
//        result[2] = out.getPassQ_sum();
//        result[3] = ProgramRuntimeData.getFloodNum();
//
//        // 创建工作簿
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Double Data");
//
//        // 写入运行结果
//        Row row1 = sheet.createRow(0);
//        Cell cell1 = row1.createCell(0);
//        cell1.setCellValue("运行结果：");
//        for (int i = 0; i < resultName.length; i++) {
//            Row row = sheet.createRow(i + 1);
//            row.createCell(0).setCellValue(resultName[i]);
//            row.createCell(1).setCellValue(result[i]);
//        }
//
//        // 写入参数
//        Row row = sheet.createRow(6);
//        row.createCell(0).setCellValue("Parameter 输出参数:");
//        for (int i = 0; i < parName.length; i++) {
//            Row row2 = sheet.createRow(i + 7);
//            row2.createCell(0).setCellValue(parName[i]);
//            row2.createCell(1).setCellValue(par[i]);
//        }
//
//        // 保存文件
//        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
//            workbook.write(fileOut);
//            System.out.println("文件已生成: " + filePath);
//        } catch (IOException e) {
//            System.err.println("写入文件时发生错误：" + e.getMessage());
//        } finally {
//            try {
//                workbook.close();
//            } catch (IOException e) {
//                System.err.println("关闭工作簿时发生错误：" + e.getMessage());
//            }
//        }
//    }
//}
