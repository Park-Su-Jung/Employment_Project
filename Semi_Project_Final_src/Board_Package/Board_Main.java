package Board_Package;

import Ect_Package.*;
import JDBC_Package.JDBC_Functions;
import User_Package.*;

public class Board_Main {
	
	private JDBC_Functions jdbc = null;
	
	private Board_Function bf = null;
	
	public Board_Main(JDBC_Functions jdbc)
	{
		this.jdbc = jdbc;
		this.bf = new Board_Function(this.jdbc);
	}
	
	public void Board_Main_Menu()
	{	
		while(true)
		{
			for(String temp : Messages.board_main_message)
			{
				System.out.println(temp);
			}
			System.out.print("메뉴를 입력해 주세요 : ");
			String choose = User_Input.get_input();
			if(choose.equals("1"))
			{
				bf.Read_Function();
			}
			else if(choose.equals("2"))
			{
				bf.Create_Function();
			}
			else if(choose.equals("3"))
			{
				bf.Update_Function();
			}
			else if(choose.equals("4"))
			{
				bf.Delete_Function();
			}
			else if(choose.equals("9"))
			{
				break;
			}
			else
			{
				System.out.println("잘못입력했습니다.");
			}
		}
	}
}
