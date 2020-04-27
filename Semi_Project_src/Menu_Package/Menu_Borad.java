package Menu_Package;

import java.sql.*;
import java.util.ArrayList;

import Ect_Package.*;
import JDBC_Package.*;
import Session_Package.User_Session;

public class Menu_Borad {
	
	Connection conn = JDBC_Funtions.getConn();
	
	public void board_menu()
	{
		String temp;
		
		while(true)
		{
			System.out.println("<게시판 메뉴>");
			System.out.println("1.게시판 글 읽기");
			System.out.println("2.게시판 글 쓰기");
			System.out.println("3.게시판 글 삭제");
			System.out.println("9.종료");
			System.out.print("원하시는 메뉴를 입력해 주세요 : ");
			temp = User_Input.get_input();
			
			if(temp.equals("1"))
			{
				this.board_list();
			}
			else if(temp.equals("2"))
			{
				this.board_write();
			}
			else if(temp.equals("3"))
			{
				this.board_delete();
			}
			else if(temp.equals("9"))
			{
				break;
			}
			else
			{
				System.out.println("잘못입력하셨습니다.");
			}
		}
	}
	
	public boolean show_list()
	{
		String sql = "SELECT board_num,board_title,board_pass,create_date,user_id FROM notice_board";
		try 
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				String temp_pass = rs.getString("board_pass");
				
				if(temp_pass == null)
				{
					System.out.println("글 번호 : " + rs.getString("board_num") + " |글 제목 : " + rs.getString("board_title") + "|비밀번호 : 무 |쓴 날짜 : " + rs.getString("create_date") + " |글쓴이 : " + rs.getString("user_id"));
				}
				else
				{
					System.out.println("글 번호 : " + rs.getString("board_num") + " |글 제목 : " + rs.getString("board_title") + "|비밀번호 : 유 |쓴 날짜 : " + rs.getString("create_date") + " |글쓴이 : " + rs.getString("user_id"));
				}
			}	
			return true;
		}
		catch (SQLException e) 
		{
			System.out.println("게시글이 없습니다.");
			return false;
		}
	}
	
	public void board_list()
	{
		String ui;
		String sql;
		try 
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = null;
			if(this.show_list() == false)
			{
				return;
			}
			System.out.print("읽을 글을 선택해 주세요 : ");
			ui=User_Input.get_input();
			sql = "SELECT board_message,board_pass FROM notice_board WHERE board_num = "+ui;
			rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				String temp_pass = rs.getString("board_pass");
				if(temp_pass == null)
				{
					System.out.println(rs.getString("board_message"));
				}
				else
				{
					System.out.print("비밀번호를 입력해 주세요 : ");
					String check_temp = User_Input.get_input();
					if(check_temp.equals(temp_pass))
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
				System.out.println("읽으실 글이 없습니다.");
			}
		} 
		catch (SQLException e) 
		{
			
		}
	}
	
	public void board_write()
	{
		String isql = "INSERT INTO notice_board(board_title,board_message,user_id,user_num,board_pass)";
		isql += "VALUES(?,?,?,?,?)";
		
		String osql = "SELECT user_num FROM USER WHERE user_id = \'"+User_Session.USER_LOGIN_ID+"\'";
		ResultSet rs = null;
		
		Statement stmt = null;
		PreparedStatement pstmt = null;
		
		String title;
		ArrayList<String> temp_message = new ArrayList<String>();
		String final_message = "";
		String pass = null;
		
		System.out.print("글 제목을 입력해 주세요 : ");
		title = User_Input.get_input();
		
		System.out.println("글 내용을 입력해 주세요(END_M 입력시 종료) : ");
		while(true)
		{
			String temp = User_Input.get_input();
			if(temp.equals("END_M"))
			{
				break;
			}
			temp_message.add(temp);
		}
		for(int i=0;i<temp_message.size();i++)
		{
			final_message += temp_message.get(i)+"\n";
		}
		
		System.out.print("비밀번호를 설정하시겠습니까?(Y/N) : ");
		if(User_Input.get_input().equals("Y"))
		{
			System.out.print("비밀번호를 입력해 주세요 : ");
			pass = User_Input.get_input();
		}
		
		try 
		{
			String temp = null;
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(osql);
			if(rs.next())
			{
				temp = rs.getString("user_num");
			}
			
			pstmt = conn.prepareStatement(isql);
			
			pstmt.setString(1, title);
			pstmt.setString(2, final_message);
			pstmt.setString(3, User_Session.USER_LOGIN_ID);
			pstmt.setInt(4,Integer.parseInt(temp));
			pstmt.setString(5, pass);
			pstmt.execute();
			System.out.println("글이 등록되었습니다.");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void board_delete()
	{
		String ui;
		String sql;
		try 
		{
			String tsql = "SELECT board_num,board_title,board_pass,create_date,user_id FROM notice_board WHERE user_id = \'"+User_Session.USER_LOGIN_ID+"\'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(tsql);
			while(rs.next())
			{
				String temp_pass = rs.getString("board_pass");
					
				if(temp_pass == null)
				{
					System.out.println("글 번호 : " + rs.getString("board_num") + " |글 제목 : " + rs.getString("board_title") + "|비밀번호 : 무 |쓴 날짜 : " + rs.getString("create_date") + " |글쓴이 : " + rs.getString("user_id"));
				}
				else
				{
					System.out.println("글 번호 : " + rs.getString("board_num") + " |글 제목 : " + rs.getString("board_title") + "|비밀번호 : 유 |쓴 날짜 : " + rs.getString("create_date") + " |글쓴이 : " + rs.getString("user_id"));
				}
			}
			System.out.print("삭제할 글을 선택해 주세요(종료를 원하면 0을 입력) : ");
			ui=User_Input.get_input();
			tsql = "SELECT board_title, board_pass FROM notice_board WHERE user_id = \'"+User_Session.USER_LOGIN_ID+"\' AND board_num = "+ui;
			rs = stmt.executeQuery(tsql);
			if(rs.next())
			{
				String temp_pass = rs.getString("board_pass");
				if(temp_pass==null)
				{
					sql = "DELETE FROM notice_board WHERE board_num = "+ui;
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.execute();
					System.out.println("글이 삭제되었습니다.");
				}
				else
				{
					System.out.print("비밀번호를 입력해 주세요 : ");
					String check_temp = User_Input.get_input();
					if(check_temp.equals(temp_pass))
					{
						sql = "DELETE FROM notice_board WHERE board_num = "+ui;
						PreparedStatement pstmt = conn.prepareStatement(sql);
						pstmt.execute();
						System.out.println("글이 삭제되었습니다.");
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
		} 
		catch (SQLException e) 
		{
			System.out.println("삭제할 글이 없습니다.");
		}
		
	}
}
