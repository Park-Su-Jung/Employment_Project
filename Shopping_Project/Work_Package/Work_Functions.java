package Work_Package;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import Ect_Package.Messages;
import JDBC_Package.JDBC_Functions;
import User_Package.User_Input;
import User_Package.User_Session;

public class Work_Functions {
	private JDBC_Functions jdbc = null;
	
	public Work_Functions(JDBC_Functions jdbc)
	{
		this.jdbc = jdbc;
	}
	
	public void Change_Function()
	{
		ArrayList<String> target = new ArrayList<String>();
		ArrayList<String> condition = new ArrayList<String>(Arrays.asList("users_id = '"+User_Session.USER_LOGIN_ID+"'"));
		
		for(String temp : Messages.update_message_menu)
		{
			System.out.println(temp);
		}
		System.out.print("메뉴를 입력해 주세요 : ");
		String choose = User_Input.get_input();
		System.out.print(Messages.update_message.get(Integer.parseInt(choose)));
		String temp = User_Input.get_input();
		target.add(Messages.create_key.get(Integer.parseInt(choose))+"='"+temp+"'");
		jdbc.Update_Function(jdbc.getConn(),target, "shopping_shop.users", condition);
	}
	
	public void Create_Function()
	{
		ArrayList<String> target = new ArrayList<String>();
		ArrayList<String> impormation = new ArrayList<String>();
		ArrayList<String> check_id = new ArrayList<String>();
		
		for(int i=0;i<Messages.create_message.size();i++)
		{
			System.out.print(Messages.create_message.get(i));
			String user_impor = User_Input.get_input();
			target.add(Messages.create_key.get(i));
			impormation.add("'"+user_impor+"'");
			if(i==0)
			{
				check_id.add(target.get(i)+"="+impormation.get(i));
				ResultSet rs = jdbc.Select_Function(jdbc.getConn(),target, "shopping_shop.users", check_id);
				try 
				{
					if(rs.next())
					{
						target.remove(i);
						impormation.remove(i);
						check_id.remove(i);
						i--;
						System.out.println("중복된 ID입니다. 다른 ID를 입력해 주세요.");
					}
				} 
				catch (SQLException e) 
				{
					continue;
				}
			}
		}
		
		if(jdbc.Insert_Function(jdbc.getConn(),target, "shopping_shop.users", impormation))
		{
			System.out.println("회원가입 완료되었습니다");
		}
		else
		{
			System.out.println("회원가입에 실패하였습니다.");
		}
	}
	
	public void Delete_Function()
	{
		ArrayList<String> target = new ArrayList<String>();
		ArrayList<String> condition = new ArrayList<String>();
		
		ResultSet rs = null;
		
		System.out.print("회원탈퇴 하시겠습니까?(Y/N) : ");
		if(User_Input.get_input().equals("Y"))
		{
			String temp_pass = null;
			System.out.print("비밀번호를 입력해 주세요 : ");
			temp_pass = User_Input.get_input();
			
			target.add("*");
			condition.add("users_id ='"+User_Session.USER_LOGIN_ID+"'");
			condition.add("users_pass = '"+temp_pass+"'");
			
			rs = jdbc.Select_Function(jdbc.getConn(),target, "shopping_shop.users", condition);
			try 
			{
				if(rs.next())
				{
					if(jdbc.Delete_Function(jdbc.getConn(),"shopping_shop.users", condition))
					{
						System.out.println("탈퇴하였습니다. 안녕히가세요.");
						User_Session.Logout();
					}
					else
					{
						System.out.println("탈퇴에 실패하였습니다.");
					}
				}
				else
				{
					System.out.println("비밀번호가 틀렸습니다.");
				}
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
				System.out.println("비밀번호가 틀렸습니다.");
			}
		}
	}
	
	public void Login_Function()
	{
		ArrayList<String> target = new ArrayList<String>();
		ArrayList<String> impormation = new ArrayList<String>();
		ArrayList<String> check = new ArrayList<String>();
		ArrayList<String> his_impormation = new ArrayList<String>();
		
		for(int i=0;i<Messages.login_message.size();i++)
		{
			System.out.print(Messages.login_message.get(i));
			String user_impor = User_Input.get_input();
			target.add(Messages.create_key.get(i));
			impormation.add("'"+user_impor+"'");
			check.add(target.get(i)+"="+impormation.get(i));
		}
		target.add("users_name");
		target.add("idusers");
		ResultSet rs = jdbc.Select_Function(jdbc.getConn(),target, "shopping_shop.users", check);
		try 
		{
			if(rs.next())
			{
				String id_temp = rs.getString("users_id");
				String name_temp = rs.getString("users_name");
				String num_temp = rs.getString("idusers");
				his_impormation.add("'"+id_temp+"'");
				his_impormation.add("'"+num_temp+"'");
				User_Session.Login(id_temp, name_temp,num_temp);
				System.out.println("로그인 되었습니다.");
			}
			else
			{
				System.out.println("로그인에 실패하였습니다.");
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("로그인에 실패하였습니다.");
		}
	}
}
