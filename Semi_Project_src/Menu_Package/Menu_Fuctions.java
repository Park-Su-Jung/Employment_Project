package Menu_Package;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Ect_Package.*;
import JDBC_Package.JDBC_Funtions;
import Session_Package.User_Session;

public class Menu_Fuctions {
	
	public void show_main_menu()
	{
		if(User_Session.USER_LOGIN_ID == null)
		{
			System.out.println("<메인메뉴>");
			System.out.println("1.회원가입");
			System.out.println("2.로그인");
			System.out.println("9.종료");
			System.out.print("원하시는 메뉴를 입력해 주세요 : ");
		}
		else
		{
			System.out.println("<메인메뉴>");
			System.out.println("1.로그아웃");
			System.out.println("2.회원정보수정");
			System.out.println("3.게시판");
			System.out.println("4.회원탈퇴");
			System.out.println("9.종료");
			System.out.print("원하시는 메뉴를 입력해 주세요 : ");
		}
	}
	
	public Map<String,String> create_menu()
	{
		ArrayList<String> message = Messages.create_message;
		ArrayList<String> key = Messages.create_key;
		Map<String,String> data = new HashMap<String,String>();
		for(int i=0;i<message.size();i++)
		{
			System.out.print(message.get(i));
			data.put(key.get(i), User_Input.get_input());
			if(data.get(key.get(i)).equals("0"))
			{
				System.out.println("회원가입을 종료합니다.");
				return null;
			}
			if(i==0 && JDBC_Funtions.id_check(data.get(key.get(i))))
			{
				System.out.println("이미 생성되어 있는 ID입니다.");
				System.out.println("다른 ID를 입력해 주세요.");
				System.out.println("회원가입을 종료하고 싶으면 0을 입력해 주세요.");
				i--;
			}
		}
		return data;
	}
	
	public Map<String,String> login_menu()
	{
		ArrayList<String> message = Messages.login_message;
		ArrayList<String> key = Messages.create_key;
		Map<String,String> data = new HashMap<String,String>();
		for(int i=0;i<message.size();i++)
		{
			System.out.print(message.get(i));
			data.put(key.get(i), User_Input.get_input());
		}
		if(JDBC_Funtions.login_check(data))
		{
			System.out.println("로그인 되었습니다.");
			return data;
		}
		else
		{
			System.out.println("로그인 실패하셨습니다.");
			return null;
		}
	}
	
	public void update_menu()
	{
		ArrayList<String> message = Messages.update_message;
		while(true)
		{
			String temp;
				
			System.out.println("<메뉴>");
			System.out.println("1. 비밀번호");
			System.out.println("2. 이름");
			System.out.println("3. 전화번호");
			System.out.println("9. 종료");
			System.out.print("바꾸고 싶은 정보를 메뉴에서 선택해 주세요 : ");
				
			temp = User_Input.get_input();
			
			if(Integer.parseInt(temp)!=1 && Integer.parseInt(temp)!=2 && Integer.parseInt(temp)!=3 && Integer.parseInt(temp)!=9)
			{
				System.out.println("잘못입력했습니다.");
				continue;
			}
			else if(temp.equals("9"))
			{
				break;
			}
			else
			{
				System.out.print(message.get(Integer.parseInt(temp)));
				String temp_data = User_Input.get_input();
				if(JDBC_Funtions.update_impormation(User_Session.USER_LOGIN_ID, temp_data, temp))
				{
					if(temp.equals("2"))
					{
						User_Session.USER_LOGIN_NAME = temp_data;
					}
					System.out.println("변경되었습니다.");
				}
				else
				{
					System.out.println("변경되지 않았습니다.");
				}
			}
		}
	}
	
	public void delete_id()
	{
		System.out.print("회원탈퇴 하시겠습니까?(Y/N) : ");
		if(User_Input.get_input().equals("Y"))
		{
			String sql = "SELECT user_pass FROM user WHERE user_id = \'"+User_Session.USER_LOGIN_ID+"\'";
			
			try 
			{
				Statement stmt = JDBC_Funtions.getConn().createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				String temp_pass = null;
				System.out.print("비밀번호를 입력해 주세요 : ");
				temp_pass = User_Input.get_input();
				
				if(rs.next())
				{
					if(temp_pass.equals(rs.getString("user_pass")))
					{
						sql = "SET foreign_key_checks = 0";
						PreparedStatement pstmt = JDBC_Funtions.getConn().prepareStatement(sql);
						pstmt.execute();
						sql = "DELETE FROM user WHERE user_id = \'"+User_Session.USER_LOGIN_ID + "\'";
						pstmt = JDBC_Funtions.getConn().prepareStatement(sql);
						pstmt.execute();
						sql = "SET foreign_key_checks = 1";
						pstmt = JDBC_Funtions.getConn().prepareStatement(sql);
						pstmt.execute();
						User_Session.USER_LOGIN_ID = null;
						User_Session.USER_LOGIN_NAME = null;
						System.out.println("탈퇴되었습니다.");
					}
					else
					{
						System.out.println("비밀번호가 틀렸습니다.");
					}
				}
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
