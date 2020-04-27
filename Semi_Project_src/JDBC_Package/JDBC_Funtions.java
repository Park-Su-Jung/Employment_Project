package JDBC_Package;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

import Ect_Package.Messages;
import Session_Package.User_Session;

public class JDBC_Funtions {
	private final static String DB_URL = "jdbc:mysql://localhost:3306/login_project?serverTimezone=UTC";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "1234";
    private static Connection conn = null;
    private static Statement stmt = null;
    private static PreparedStatement pstmt = null;
    
    public static Connection getConn() {
		return conn;
	}

	public static PreparedStatement getPstmt() {
		return pstmt;
	}
    
    private JDBC_Funtions() {
    	
	}
    
    public static boolean Get_Connection()
    {
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			System.out.println("Connect");
			return true;
		} 
    	catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
    
    public static void Out_Connection()
    {
    	try{
            if(pstmt!=null)
            {
            	pstmt.close();
            }
        }
    	catch(SQLException se2)
    	{
    		
        }
    	try{
            if(stmt!=null)
            {
            	stmt.close();
            }
        }
    	catch(SQLException se2)
    	{
    		
        }
    	try{
            if(conn!=null)
            {
            	conn.close();
            	System.out.println("Disconnect");
            }
        }
    	catch(SQLException se)
    	{
            se.printStackTrace();
        }
    }
    
    public static boolean insert_impormation(Map<String,String> data)
    {
    	String sql = "INSERT INTO USER";
    	sql += "(user_id,user_pass,user_name) ";
    	sql += "values(?,?,?)";
    	try {
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1,data.get("user_id"));
    		pstmt.setString(2,data.get("user_pass"));
    		pstmt.setString(3,data.get("user_name"));
			pstmt.executeUpdate();
			return true;
		} 
    	catch (SQLException e) {
    		e.printStackTrace();
			return false;
		}
	}
    
    public static boolean id_check(String id)
    {
    	String sql = "SELECT user_id FROM USER WHERE user_id = \'"+id+"\'";
    	try 
    	{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				if(rs.getString("user_id")!=null)
				{
					return true;
				}
			}
			return false;
		} 
    	catch (SQLException e) 
    	{
			return false;
		}
    }
    
    public static boolean login_check(Map<String,String> data)
    {
    	String sql = "SELECT user_id,user_pass,user_name,user_num FROM USER WHERE user_id = \'"+data.get("user_id")+"\' AND user_pass = \'"+data.get("user_pass")+"\'";
    	try 
    	{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				String temp_id = rs.getString("user_id");
				String temp_pass = rs.getString("user_pass");
				String temp_name = rs.getString("user_name");
				String temp_num = rs.getString("user_num");
				if(temp_id !=null && temp_pass!=null)
				{
					User_Session.USER_LOGIN_ID = temp_id;
					User_Session.USER_LOGIN_NAME = temp_name;
					String sql_temp = "INSERT INTO LOGIN_HISTORY";
					sql_temp += "(user_id,user_num)";
					sql_temp += "VALUES(?,?)";
					pstmt = conn.prepareStatement(sql_temp);
					pstmt.setString(1, temp_id);
					pstmt.setString(2, temp_num);
					pstmt.executeUpdate();
					return true;
				}
			}
			return false;
		} 
    	catch (SQLException e) 
    	{
    		e.printStackTrace();
			return false;
		}
    }
    
    public static boolean update_impormation(String target,String data,String update_num)
    {
    	ArrayList<String> message = Messages.create_key;
    	String sql = "UPDATE user SET "+message.get(Integer.parseInt(update_num))+" = ? , update_date = NOW() where user_id = ?";
    	try 
    	{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, data);
			pstmt.setString(2, target);
			pstmt.executeUpdate();
			return true;
		} 
    	catch (SQLException e) 
    	{
			e.printStackTrace();
			return false;
		}
    }
}
