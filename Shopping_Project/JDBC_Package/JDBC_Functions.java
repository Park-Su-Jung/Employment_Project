package JDBC_Package;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JDBC_Functions {
	private final String DB_URL = "jdbc:mysql://localhost:3306/login_project?serverTimezone=UTC";
    private final String USERNAME = "root";
    private final String PASSWORD = "1234";
	
	private Connection conn = null;
	
	public JDBC_Functions() {
		this.Get_Connection();
	}
	
	public Connection getConn() {
		return conn;
	}
	
	public boolean Get_Connection()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			System.out.println("Connect Succese");
			return true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean Lose_Connection()
	{
        try 
        {
        	if(conn!=null)
            {
        		conn.close();
        		System.out.println("Disconnect Succese");
        		return true;
            }
		} 
        catch (SQLException e) 
        {
			e.printStackTrace();
		}
        return false;
	}
	
	public boolean Delete_Function(Connection conn,String Target_Table,ArrayList<String> Condition_Colume)
	{
		PreparedStatement pstmt = null;
		
		String sql = "DELETE FROM "+Target_Table+" WHERE 1=1 ";
		String temp = null;
		for(String temp_s : Condition_Colume)
		{
			sql += "AND "+temp_s+" ";
		}
		sql = sql.substring(0,sql.length()-1);
		try 
		{
			temp = "SET foreign_key_checks = 0";
			pstmt = conn.prepareStatement(temp);
			pstmt.execute();
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			temp = "SET foreign_key_checks = 1";
			pstmt = conn.prepareStatement(temp);
			pstmt.execute();
			return true;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean Insert_Function(Connection conn,ArrayList<String> Target_Colume,String Target_Table,ArrayList<String> insert_values)
	{
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO "+Target_Table+"(";
		for(int i=0;i<Target_Colume.size();i++)
		{
			sql+=(Target_Colume.get(i)+",");
		}
		sql = sql.substring(0,sql.length()-1);
		sql += (") VALUES(");
		for(int i=0;i<insert_values.size();i++)
		{
			sql +=(insert_values.get(i)+",");
		}
		sql = sql.substring(0,sql.length()-1);
		sql += ")";
		try 
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			return true;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public ResultSet Select_Function(Connection conn,ArrayList<String> Target_Colume,String Target_Table,ArrayList<String> Condition_Colume)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT ";
		for(int i=0;i<Target_Colume.size();i++)
		{
			sql+=(Target_Colume.get(i)+",");
		}
		sql = sql.substring(0,sql.length()-1);
		sql += (" FROM "+Target_Table + " WHERE 1=1");
		for(int i=0;i<Condition_Colume.size();i++)
		{
			sql +=(" AND "+Condition_Colume.get(i));
		}
		try 
		{
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return rs;
	}
	
	public boolean Update_Function(Connection conn,ArrayList<String> Update_Values,String Target_Table,ArrayList<String> Condition_Colume)
	{
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE "+Target_Table+" SET ";
		for(String temp : Update_Values)
		{
			sql += (temp+",");
		}
		sql = sql.substring(0,sql.length()-1);
		sql += " WHERE 1=1 ";
		for(String temp : Condition_Colume)
		{
			sql += ("AND "+temp+" ");
		}
		sql = sql.substring(0,sql.length()-1);
		try 
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			return true;
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
