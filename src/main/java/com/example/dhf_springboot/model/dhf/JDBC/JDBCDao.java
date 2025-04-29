package com.example.dhf_springboot.model.dhf.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JDBCDao {
    //JDBC--java数据库连接到
    //JDBC连接池--数据库

    public static String[] getString(String sql, Object... args) //形参列表，Object... 可变形参0~无穷个参数
    //String sql 执行数据库操作的语句
    //返回类型为String[]--字符串数组
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String[] str = null;

        try {
            connection = JDBCTools.getConnection(); //调用连接函数，返回建立的连接
            preparedStatement = connection.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); //生成PrepardStatement对象，支持滚动

            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            resultSet = preparedStatement.executeQuery(); //按照sql的内容执行查找语句，生成ResultSet对象

            // 如果查询结果为空，直接返回空数组
            if (!resultSet.isBeforeFirst()) {
                return new String[0]; // 如果没有结果，返回空数组
            }

            resultSet.last(); //将指针移到当前记录的最后一行

            int num = resultSet.getRow(); //得到此时指针所指向的行号
            str = new String[num]; //根据从数据库查找结果的数量确定相应的数组

            resultSet.beforeFirst(); //将指针移到集合的开头（第一行位置）
            int i = 0; //为将数据库资料逐个赋值，创立数组底标的初始值

            //遍历查找结果
            while (resultSet.next()) {
                str[i] = resultSet.getString(1); //得到逐行的q列的值，并赋值给数组
                i++; //数组底标自增
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.releaseDB(resultSet, preparedStatement, connection); //释放资源
        }

        return str;
    }

    public static double[] getDouble(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        double[] p = null;

        try {
            connection = JDBCTools.getConnection(); //调用连接函数，返回建立的连接
            preparedStatement = connection.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); //生成PrepardStatement对象，支持滚动

            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            resultSet = preparedStatement.executeQuery(); //按照sql的内容执行查找语句，生成ResultSet对象

            // 如果查询结果为空，直接返回空数组
            if (!resultSet.isBeforeFirst()) {
                return new double[0]; // 如果没有结果，返回空数组
            }

            resultSet.last(); //将指针移到当前记录的最后一行

            int num = resultSet.getRow(); //得到此时指针所指向的行号
            p = new double[num]; //根据从数据库查找结果的数量确定相应的数组

            resultSet.beforeFirst(); //将指针移到集合的开头（第一行位置）
            int i = 0; //为将数据库资料逐个负值，创立数组底标的初始值

            //遍历查找结果
            while (resultSet.next()) {
                p[i] = resultSet.getDouble(1); //得到逐行的q列的值，并赋值给数组
                i++; //数组底标自增
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.releaseDB(resultSet, preparedStatement, connection); //释放资源
        }

        return p;
    }


    public static Date[] getDate(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Date[] date = null;

        try {
            connection = JDBCTools.getConnection(); //调用连接函数，返回建立的连接
            preparedStatement = connection.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); //生成PrepardStatement对象，支持滚动

            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            resultSet = preparedStatement.executeQuery(); //按照sql的内容执行查找语句，生成ResultSet对象

            // 如果查询结果为空，直接返回空数组
            if (!resultSet.isBeforeFirst()) {
                return new Date[0]; // 如果没有结果，返回空数组
            }

            resultSet.last(); //将指针移到当前记录的最后一行

            int num = resultSet.getRow(); //得到此时指针所指向的行号
            date = new Date[num]; //根据从数据库查找结果的数量确定相应的数组

            resultSet.beforeFirst(); //将指针移到集合的开头（第一行位置）
            int i = 0; //为将数据库资料逐个负值，创立数组底标的初始值

            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //遍历查找结果
            while (resultSet.next()) {
                String tempt = resultSet.getString(1); //得到逐行的date列的值
                try {
                    date[i] = simpledateformat.parse(tempt);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                i++; //数组底标自增
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.releaseDB(resultSet, preparedStatement, connection); //释放资源
        }

        return date;
    }
}
