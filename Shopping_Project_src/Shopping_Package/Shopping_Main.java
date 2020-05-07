package Shopping_Package;

import Ect_Package.*;
import JDBC_Package.JDBC_Functions;
import User_Package.*;

public class Shopping_Main {
	
	private JDBC_Functions jdbc = null;
	
	private Shopping_Function sf = null;
	
	public Shopping_Main(JDBC_Functions jdbc)
	{
		this.jdbc = jdbc;
		this.sf = new Shopping_Function(this.jdbc);
	}
	
	public void Shopping_Main_Menu()
	{	
		while(true)
		{
			for(String temp : Messages.shopping_main_message)
			{
				System.out.println(temp);
			}
			System.out.print("메뉴를 입력해 주세요 : ");
			String choose = User_Input.get_input();
			if(choose.equals("1"))
			{
				sf.Select_Function();
			}
			else if(choose.equals("2"))
			{
				sf.Basket_Function();
			}
			else if(choose.equals("3"))
			{
				sf.History_Function();
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
