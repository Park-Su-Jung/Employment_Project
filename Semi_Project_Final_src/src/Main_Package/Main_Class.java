package Main_Package;

import Board_Package.Board_Main;
import Ect_Package.Messages;
import JDBC_Package.JDBC_Functions;
import User_Package.*;
import Work_Package.Work_Functions;

public class Main_Class {
	public static void main(String[] arge)
	{
		JDBC_Functions jdbc = new JDBC_Functions();
		Work_Functions work = new Work_Functions(jdbc);
		Board_Main board = new Board_Main(jdbc);
		
		@SuppressWarnings("unused")
		Messages ms = new Messages();
		
		while(true)
		{
			if(Check_Login.Check_Function()==false)
			{
				System.out.println("<메인메뉴>");
				for(String temp : Messages.main_menu_before_login)
				{
					System.out.println(temp);
				}
				System.out.print("메뉴를 선택해 주세요 : ");
				String num = User_Input.get_input();
				if(num.equals("1"))
				{
					work.Create_Function();
				}
				else if(num.equals("2"))
				{
					work.Login_Function();
				}
				else if(num.equals("9"))
				{
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
				System.out.println("<메인메뉴>");
				for(String temp : Messages.main_menu_after_login)
				{
					System.out.println(temp);
				}
				System.out.print("메뉴를 선택해 주세요 : ");
				String num = User_Input.get_input();
				if(num.equals("1"))
				{
					User_Session.Logout();
				}
				else if(num.equals("2"))
				{
					work.Change_Function();
				}
				else if(num.equals("3"))
				{
					board.Board_Main_Menu();
				}
				else if(num.equals("4"))
				{
					work.Delete_Function();
				}
				else if(num.equals("9"))
				{
					System.out.println("종료합니다.");
					break;
				}
				else
				{
					System.out.println("잘못입력하셨습니다.");
				}
			}
		}
		
		jdbc.Lose_Connection();
		
	}
}
