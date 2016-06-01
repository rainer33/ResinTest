package com.resin.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class DBConn {
	
	//@Named("jdbc/test_mariadb") DataSource fbpDataSource;
	Connection conn = null;	 	
	public Connection getFbpDBConnection(){		
		
      try {
      	if(conn != null){
      		System.out.println("fbp DataSource alredy connected !");
      	}
      	else {
      		Context ic = new InitialContext(); 
      		Context envContext  = (Context)ic.lookup("java:comp/env");
    		DataSource ds = (DataSource) envContext.lookup("test_mariadb");      		
      		conn = ds.getConnection();
      		System.out.println("Database connection successful !");
      	}
      	
      }catch (SQLException se){
      	se.printStackTrace();
      	System.out.println("Database connection failed !");
      }catch (NamingException ne){
    	ne.printStackTrace();
        System.out.println("Database connection failed !");  
      }
      
      return conn;
	}
	
	/*
	 *	
	 */
	public void closeConnection(ResultSet rs, PreparedStatement pstmt, Connection conn){
		
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null){
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try{
				if (conn != null) {
					conn.close();
				}
			} catch(SQLException se){}
			
			System.out.println("Database Connection closed.");
		}

	}
	
	/*
	 *	
	 */
	public void closeConnection(CallableStatement stmt, Connection conn){
		
		try {
			
			if (stmt != null){
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try{
				if (conn != null) {
					conn.close();
				}
			} catch(SQLException se){}
			
			System.out.println("Database Connection closed.");
		}
	}
}