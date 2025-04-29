package com.example.dhf_springboot.model.dhf.JDBC;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC 的工具类
 *
 * 其中包含: 获取数据库连接, 关闭数据库资源等方法.
 */

public class JDBCTools {

	private static DataSource dataSource = null;

	//数据库连接池应只被初始化一次.
	static{

		dataSource = new ComboPooledDataSource("helloc3p0");
		//创建一个 c3p0 连接池的配置对象,c3p0-config.xml文件中有user,password,需要使用的数据库...

	}

	//处理数据库事务的
	//提交事务
	public static void commit(Connection connection){
		if(connection != null){
			try {
				connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//回滚事务
	public static void rollback(Connection connection){
		if(connection != null){
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//开始事务
	public static void beginTx(Connection connection){
		if(connection != null){
			try {
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}


	//释放资源

	public static void releaseDB(ResultSet resultSet, Statement statement,
								 Connection connection) {

		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (connection != null) {
			try {
				//数据库连接池的 Connection 对象进行 close 时
				//并不是真的进行关闭, 而是把该数据库连接会归还到数据库连接池中.
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
