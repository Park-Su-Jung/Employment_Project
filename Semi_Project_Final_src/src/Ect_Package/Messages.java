package Ect_Package;

import java.util.ArrayList;

public class Messages {
	public static ArrayList<String> main_menu_before_login = new ArrayList<String>();
	public static ArrayList<String> main_menu_after_login = new ArrayList<String>();
	public static ArrayList<String> create_message = new ArrayList<String>();
	public static ArrayList<String> create_key = new ArrayList<String>();
	public static ArrayList<String> login_message = new ArrayList<String>();
	public static ArrayList<String> update_message_menu = new ArrayList<String>();
	public static ArrayList<String> update_message = new ArrayList<String>();
	public static ArrayList<String> board_main_message = new ArrayList<String>();
	
	public Messages() {
		main_menu_before_login.add("1.회원가입");
		main_menu_before_login.add("2.로그인");
		main_menu_before_login.add("9.종료");
		
		main_menu_after_login.add("1.로그아웃");
		main_menu_after_login.add("2.회원정보수정");
		main_menu_after_login.add("3.게시판");
		main_menu_after_login.add("4.회원탈퇴");
		main_menu_after_login.add("9.종료");
		
		create_message.add("1.ID를 입력해 주세요 : ");
		create_message.add("2.비밀번호를 입력해 주세요 : ");
		create_message.add("3.이름 입력해 주세요 : ");
		
		create_key.add("user_id");
		create_key.add("user_pass");
		create_key.add("user_name");
		create_key.add("user_phone");
		
		login_message.add("1.ID를 입력해 주세요 : ");
		login_message.add("2.비밀번호를 입력해 주세요 : ");
		
		update_message_menu.add("<회원정보변경 메뉴>");
		update_message_menu.add("1.비밀번호");
		update_message_menu.add("2.이름");
		update_message_menu.add("3.전화번호");
		update_message_menu.add("9.종료");
		
		update_message.add("ID");
		update_message.add("변경하실 비밀번호를 입력해 주세요 : ");
		update_message.add("변경하실 이름을 입력해 주세요 : ");
		update_message.add("변경하실 전화번호를 입력해 주세요 : ");
		
		board_main_message.add("<게시판 메뉴>");
		board_main_message.add("1. 게시판 읽기");
		board_main_message.add("2. 게시판 글 작성");
		board_main_message.add("3. 게시판 글 수정");
		board_main_message.add("4. 게시판 글 삭제");
		board_main_message.add("9. 게시판 나가기");
	}
}
