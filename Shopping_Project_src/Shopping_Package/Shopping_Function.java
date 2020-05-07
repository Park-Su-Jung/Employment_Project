package Shopping_Package;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import JDBC_Package.JDBC_Functions;
import User_Package.User_Input;
import User_Package.User_Session;

public class Shopping_Function {
	private JDBC_Functions jdbc = null;
	
	public Shopping_Function(JDBC_Functions jdbc)
	{
		this.jdbc = jdbc;
	}
	
	public void Select_Function()
	{
		ArrayList<String> target = new ArrayList<String>(Arrays.asList("idshoes","shoes_name","shoes_price"));
		ArrayList<String> condition = new ArrayList<String>();
		String table = "shopping_shop.shoes_kinds";
		
		ResultSet rs = jdbc.Select_Function(jdbc.getConn(), target, table, condition);
		
		try 
		{
			while(rs.next())
			{
				String temp_num = rs.getString("idshoes");
				String temp_name = rs.getString("shoes_name");
				String temp_price = rs.getString("shoes_price");
				System.out.printf("상품번호 : %s | 상품명 : %s | 상품가격 : %s\n",temp_num,temp_name,temp_price);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		System.out.print("주문하고 싶은 상품의 번호를 입력해 주세요 : ");
		String temp_num = User_Input.get_input();
		
		target = new ArrayList<String>(Arrays.asList("shoes_name","shoes_price"));
		condition = new ArrayList<String>(Arrays.asList("idshoes = "+temp_num));
		table = "shopping_shop.shoes_kinds";
		rs = jdbc.Select_Function(jdbc.getConn(), target, table, condition);
		
		String temp_name = null;
		String temp_price = null;
		try 
		{
			if(rs.next())
			{
				temp_name = rs.getString("shoes_name");
				temp_price = rs.getString("shoes_price");
			}
		} 
		catch (SQLException e1) 
		{
			e1.printStackTrace();
		}
		
		System.out.print("신발의 사이즈를 입력해 주세요 : ");
		String temp_size = User_Input.get_input();
		
		target = new ArrayList<String>(Arrays.asList("shoes_amount"));
		condition = new ArrayList<String>(Arrays.asList("shoes_id = "+temp_num,"shoes_size = "+temp_size));
		table = "shopping_shop.shoes_stock";
		
		rs = jdbc.Select_Function(jdbc.getConn(), target, table, condition);
		
		try 
		{
			if(rs.next())
			{
				String temp_amount = rs.getString("shoes_amount");
				if(temp_amount == null || temp_amount.equals("null"))
				{
					System.out.println("매진된 상품입니다.");
				}
				else
				{
					System.out.println("제품이 "+temp_amount+"개 있습니다.");
					System.out.print("구입할 갯수를 입력해 주세요 : ");
					String user_amount = User_Input.get_input();
					if(Integer.parseInt(temp_amount)<Integer.parseInt(user_amount))
					{
						System.out.println("제품 수량이 모자랍니다.");
					}
					else
					{
						User_Session.baskets.add(new ArrayList<String>(Arrays.asList(temp_num,temp_name,temp_size,user_amount,temp_price)));
						System.out.println("장바구니에 등록되었습니다.");
					}
				}
			}
			else
			{
				System.out.println("상품이 없습니다.");
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void Basket_Function()
	{
		ResultSet rs = null;
		
		while(true)
		{
			System.out.println("<장바구니>");
			if(User_Session.baskets.size()==0)
			{
				System.out.println("주문하신 상품이 없습니다.");
				return;
			}
			for(int i=0;i<User_Session.baskets.size();i++)
			{
				System.out.printf("주문번호 : %d | 상품이름 : %s | 상품크기 : %s | 구매수량 : %s\n",i+1,User_Session.baskets.get(i).get(1),User_Session.baskets.get(i).get(2),User_Session.baskets.get(i).get(3));
			}
			System.out.print("구매를 원하시면 BUY를 입력하시고 취소를 원하시면 취소할 주문번호를 입력해 주세요(BACK을 입력하면 종료) : ");
			String temp = User_Input.get_input();
			if(temp.equals("BUY"))
			{
				for(ArrayList<String> temp_basket : User_Session.baskets)
				{
					ArrayList<String> target = new ArrayList<String>(Arrays.asList("user_id","shoes_num","shoes_amount","shoes_size"));
					ArrayList<String> condition = new ArrayList<String>(Arrays.asList(User_Session.USER_LOGIN_NUM,temp_basket.get(0),temp_basket.get(3),temp_basket.get(2)));
					String table = "shopping_shop.user_history";
					
					if(jdbc.Insert_Function(jdbc.getConn(), target, table, condition))
					{
						String temp_update = null;
						
						target = new ArrayList<String>(Arrays.asList("users_money"));
						condition = new ArrayList<String>(Arrays.asList("users_id = '"+User_Session.USER_LOGIN_ID+"'"));
						table = "shopping_shop.users";
						
						rs=jdbc.Select_Function(jdbc.getConn(), target, table, condition);
						try {
							if(rs.next())
							{
								temp_update = rs.getString("users_money");
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
						String cal = Integer.toString(Integer.parseInt(temp_update)+(Integer.parseInt(temp_basket.get(4))*Integer.parseInt(temp_basket.get(3))));
						target = new ArrayList<String>(Arrays.asList("users_money = "+cal));
						if(jdbc.Update_Function(jdbc.getConn(), target, table, condition))
						{
							target = new ArrayList<String>(Arrays.asList("shoes_amount"));
							condition = new ArrayList<String>(Arrays.asList("shoes_id = "+temp_basket.get(0)));
							table = "shopping_shop.shoes_stock";
							
							rs=jdbc.Select_Function(jdbc.getConn(), target, table, condition);
							try {
								if(rs.next())
								{
									temp_update = rs.getString("shoes_amount");
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
							cal = Integer.toString(Integer.parseInt(temp_update)-Integer.parseInt(temp_basket.get(3)));
							target = new ArrayList<String>(Arrays.asList("shoes_amount = "+cal));
							if(jdbc.Update_Function(jdbc.getConn(), target, table, condition))
							{
								System.out.println("구매 완료하였습니다.");
							}
							else
							{
								System.out.println("구매에 실패하였습니다.");
							}
						}
						else
						{
							System.out.println("구매에 실패하였습니다.");
						}
					}
					else
					{
						System.out.println("구매에 실패하였습니다.");
					}
				}
				User_Session.baskets = new ArrayList<ArrayList<String>>();
			}
			else if(temp.equals("BACK"))
			{
				return;
			}
			else if(Integer.parseInt(temp)>User_Session.baskets.size() || Integer.parseInt(temp)<=0)
			{
				System.out.println("잘못입력하셨습니다.");
			}
			else
			{
				User_Session.baskets.remove(Integer.parseInt(temp)-1);
			}
		}
	}
	
	public void History_Function()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT b.shoes_name , a.shoes_amount, a.shoes_size, a.buy_date FROM shopping_shop.user_history a,shopping_shop.shoes_kinds b WHERE a.user_id = "+User_Session.USER_LOGIN_NUM+" AND a.shoes_num = b.idshoes";
		
		try 
		{
			pstmt = jdbc.getConn().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				String temp_name = rs.getString("shoes_name");
				String temp_amount = rs.getString("shoes_amount");
				String temp_size = rs.getString("shoes_size");
				String temp_date = rs.getString("buy_date");
				System.out.printf("상품이름 : %s | 상품크기 : %s | 상품수량 : %s | 구매날짜 %s\n", temp_name,temp_size,temp_amount,temp_date);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}
