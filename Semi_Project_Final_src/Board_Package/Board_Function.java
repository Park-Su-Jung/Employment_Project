package Board_Package;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import JDBC_Package.JDBC_Functions;
import User_Package.User_Input;
import User_Package.User_Session;

public class Board_Function {
	private JDBC_Functions jdbc = null;
	
	public Board_Function(JDBC_Functions jdbc)
	{
		this.jdbc=jdbc;
	}
	
	public void Create_Function()
	{
		String title = null;
		String total_message = "";
		String pass = null;
		
		ArrayList<String> temp_message = new ArrayList<String>();
		
		ArrayList<String> target = new ArrayList<String>(Arrays.asList("user_id","user_num","board_title","board_message","board_pass"));
		ArrayList<String> condition = new ArrayList<String>();
		
		System.out.print("글 제목을 입력해 주세요 : ");
		title = User_Input.get_input();
		
		System.out.println("글 내용을 적어주세요(END_M을 입력하면 종료합니다.)");
		while(true)
		{
			String ms = User_Input.get_input();
			if(ms.equals("END_M"))
			{
				break;
			}
			temp_message.add(ms);
		}
		for(String s : temp_message)
		{
			total_message += s+"\n";
		}
		
		System.out.print("비밀번호를 만드시겠습니까?(Y/N) : ");
		if(User_Input.get_input().equals("Y"))
		{
			System.out.print("비밀번호를 입력해 주세요 : ");
			pass = User_Input.get_input();
		}
		
		condition.add("'"+User_Session.USER_LOGIN_ID+"'");
		condition.add(User_Session.USER_LOGIN_NUM);
		condition.add("'"+title+"'");
		condition.add("'"+total_message+"'");
		condition.add("'"+pass+"'");
		
		if(jdbc.Insert_Function(jdbc.getConn(),target, "notice_board", condition))
		{
			System.out.println("글이 등록되었습니다.");
		}
		else
		{
			System.out.println("글이 등록되지 않았습니다.");
		}
	}
	
	public void Read_Function()
	{
		ArrayList<String> target = new ArrayList<String>(Arrays.asList("board_pass","board_message"));
		ArrayList<String> condition = new ArrayList<String>();
		
		ResultSet rs = null;
		
		this.Show_List(true);
		System.out.print("읽으실 게시판에 번호를 입력해 주세요 : ");
		String choose = User_Input.get_input();
		
		condition.add("board_num = "+choose);
		
		rs = jdbc.Select_Function(jdbc.getConn(),target, "notice_board", condition);
		try 
		{
			if(rs.next())
			{
				String temp_pass = rs.getString("board_pass");
				if(temp_pass==null || temp_pass.equals("null"))
				{
					System.out.println(rs.getString("board_message"));
				}
				else
				{
					System.out.print("비밀번호를 입력해 주세요 : ");
					if(User_Input.get_input().equals(temp_pass))
					{
						System.out.println(rs.getString("board_message"));
					}
					else
					{
						System.out.println("비밀번호가 틀렸습니다.");
					}
				}
			}
			else
			{
				System.out.println("게시글이 없습니다.");
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void Show_List(boolean state)
	{
		ArrayList<String> target = new ArrayList<String>(Arrays.asList("board_num","board_title","board_pass","create_date","user_id"));
		ArrayList<String> condition = new ArrayList<String>();
		
		ResultSet rs = null;
		
		if(state)
		{
			rs = jdbc.Select_Function(jdbc.getConn(),target, "notice_board", condition);
			try 
			{
				while(rs.next())
				{
					String temp_pass = rs.getString("board_pass");
					
					if(temp_pass == null || temp_pass.equals("null"))
					{
						System.out.println("글 번호 : " + rs.getString("board_num") + " |글 제목 : " + rs.getString("board_title") + "|비밀번호 : 무 |쓴 날짜 : " + rs.getString("create_date") + " |글쓴이 : " + rs.getString("user_id"));
					}
					else
					{
						System.out.println("글 번호 : " + rs.getString("board_num") + " |글 제목 : " + rs.getString("board_title") + "|비밀번호 : 유 |쓴 날짜 : " + rs.getString("create_date") + " |글쓴이 : " + rs.getString("user_id"));
					}
				}
			} 
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			condition.add("user_id = '"+User_Session.USER_LOGIN_ID+"'");
			rs = jdbc.Select_Function(jdbc.getConn(),target, "notice_board", condition);
			try 
			{
				while(rs.next())
				{
					String temp_pass = rs.getString("board_pass");
					
					if(temp_pass == null || temp_pass.equals("null"))
					{
						System.out.println("글 번호 : " + rs.getString("board_num") + " |글 제목 : " + rs.getString("board_title") + "|비밀번호 : 무 |쓴 날짜 : " + rs.getString("create_date") + " |글쓴이 : " + rs.getString("user_id"));
					}
					else
					{
						System.out.println("글 번호 : " + rs.getString("board_num") + " |글 제목 : " + rs.getString("board_title") + "|비밀번호 : 유 |쓴 날짜 : " + rs.getString("create_date") + " |글쓴이 : " + rs.getString("user_id"));
					}
				}
			} 
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void Update_Function()
	{
		ArrayList<String> target = new ArrayList<String>(Arrays.asList("board_pass"));
		ArrayList<String> condition = new ArrayList<String>();
		
		ArrayList<String> u_target = new ArrayList<String>();
		
		ResultSet rs = null;
		
		String b_num = null;
		
		ArrayList<String> change = new ArrayList<String>();
		String final_change = "";
		
		this.Show_List(false);
		
		System.out.print("수정할 게시물을 선택해 주세요 : ");
		b_num = User_Input.get_input();
		
		condition.add("board_num = "+b_num);
		condition.add("user_id = '"+User_Session.USER_LOGIN_ID+"'");
		
		rs = jdbc.Select_Function(jdbc.getConn(),target, "notice_board", condition);
		try 
		{
			if(rs.next())
			{
				String temp_pass = rs.getString("board_pass");
				if(!(temp_pass == null || temp_pass.equals("null")))
				{
					System.out.print("비밀번호를 입력해 주세요 : ");
					if(!User_Input.get_input().equals(temp_pass))
					{
						System.out.println("비밀번호를 잘못 입력하였습니다.");
						return;
					}
				}
				while(true)
				{
					System.out.println("<게시판 수정 메뉴>");
					System.out.println("1.게시판 제목");
					System.out.println("2.게시판 내용");
					System.out.println("3.게시판 비밀번호");
					System.out.println("9.뒤로가기");
					System.out.print("수정할 내용을 선택해 주세요 : ");
					String choose = User_Input.get_input();
					if(choose.equals("1"))
					{
						System.out.print("바꿀 내용을 입력해 주세요 : ");
						final_change = User_Input.get_input();
						u_target.add("board_title = '"+final_change+"'");
						if(jdbc.Update_Function(jdbc.getConn(),u_target, "notice_board", condition))
						{
							System.out.println("변경되었습니다.");
						}
						else
						{
							System.out.println("변경에 실패하였습니다.");
						}
					}
					else if(choose.equals("2"))
					{
						System.out.println("바꿀 내용을 입력해 주세요(END_M을입력하면 종료)");
						while(true)
						{
							String temp = User_Input.get_input();
							if(temp.equals("END_M"))
							{
								break;
							}
							change.add(temp);
						}
						for(String s : change)
						{
							final_change += s;
						}
						u_target.add("board_message = '"+final_change+"'");
						if(jdbc.Update_Function(jdbc.getConn(),u_target, "notice_board", condition))
						{
							System.out.println("변경되었습니다.");
						}
						else
						{
							System.out.println("변경에 실패하였습니다.");
						}
					}
					else if(choose.equals("3"))
					{
						System.out.print("바꿀 내용을 입력해 주세요 : ");
						final_change = User_Input.get_input();
						u_target.add("board_pass = '"+final_change+"'");
						if(jdbc.Update_Function(jdbc.getConn(),u_target, "notice_board", condition))
						{
							System.out.println("변경되었습니다.");
						}
						else
						{
							System.out.println("변경에 실패하였습니다.");
						}
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
			else
			{
				System.out.println("잘못된 게시물입니다.");
			}
		} 
		catch (SQLException e) 
		{
			System.out.println("잘못된 게시물입니다.");
			e.printStackTrace();
		}
	}
	
	public void Delete_Function()
	{
		ArrayList<String> target = new ArrayList<String>(Arrays.asList("board_pass"));
		ArrayList<String> condition = new ArrayList<String>();
		ResultSet rs = null;
		
		this.Show_List(false);
		
		System.out.print("삭제할 글에 번호를 입력해 주세요 : ");
		String temp_num = User_Input.get_input();
		
		condition.add("board_num = "+temp_num);
		condition.add("user_id = '"+User_Session.USER_LOGIN_ID+"'");
		
		rs = jdbc.Select_Function(jdbc.getConn(), target, "notice_board", condition);
		try {
			if(rs.next())
			{
				String temp_pass = rs.getString("board_pass");
				if(temp_pass == null || temp_pass.equals("null"))
				{
					if(jdbc.Delete_Function(jdbc.getConn(), "notice_board", condition))
					{
						System.out.println("삭제가 되었습니다.");
					}
					else
					{
						System.out.println("삭제가되지 않았습니다.");
					}
				}
				else
				{
					System.out.print("비밀번호를 입력해 주세요 : ");
					String user_pass = User_Input.get_input();
					if(user_pass.equals(temp_pass))
					{
						if(jdbc.Delete_Function(jdbc.getConn(), "notice_board", condition))
						{
							System.out.println("삭제가 되었습니다.");
						}
						else
						{
							System.out.println("삭제가되지 않았습니다.");
						}
					}
					else
					{
						System.out.println("비밀번호가 틀렸습니다.");
					}
				}
			}
			else
			{
				System.out.println("삭제할 글이 없습니다.");
			}
		} catch (SQLException e) {
			System.out.println("삭제할 글이 없습니다.");
			e.printStackTrace();
		}
	}
}
