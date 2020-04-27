package Main_Package;

import java.util.Map;

import Ect_Package.*;
import JDBC_Package.*;
import Menu_Package.*;
import Session_Package.User_Session;

public class Main_Class {
	public static void main(String[] args)
	{
		if(JDBC_Funtions.Get_Connection()==false)
		{
			System.out.println("데이터 연동 실패.");
			return;
		}
		
		Map<String,String> user_impormation;
		
		Menu_Fuctions menu = new Menu_Fuctions();
		Menu_Borad bmenu = new Menu_Borad();
		
		String user_input;
		
		new Messages();
		
		while(true)
		{
			menu.show_main_menu();
			user_input = User_Input.get_input();
			if(User_Session.USER_LOGIN_ID == null)
			{
				if(user_input.equals("1"))
				{
					user_impormation = menu.create_menu();
					if(user_impormation != null && JDBC_Funtions.insert_impormation(user_impormation))
					{
						System.out.println("회원가입 되었습니다.");
					}
					else
					{
						System.out.println("등록실패했습니다.");
					}
				}
				else if(user_input.equals("2"))
				{
					menu.login_menu();
				}
				else if(user_input.equals("9"))
				{
					JDBC_Funtions.Out_Connection();
					System.out.println("종료합니다.");
					break;
				}
				else
				{
					System.out.println("잘못입력하셨습니다.");
				}
			}
			else
			{
				if(user_input.equals("1"))
				{
					User_Session.USER_LOGIN_ID = null;
					User_Session.USER_LOGIN_NAME = null;
				}
				else if(user_input.equals("2"))
				{
					menu.update_menu();
				}
				else if(user_input.equals("3"))
				{
					bmenu.board_menu();
				}
				else if(user_input.equals("4"))
				{
					menu.delete_id();
				}
				else if(user_input.equals("9"))
				{
					JDBC_Funtions.Out_Connection();
					System.out.println("종료합니다.");
					break;
				}
				else
				{
					System.out.println("잘못입력하셨습니다.");
				}
			}
		}
	}
}
