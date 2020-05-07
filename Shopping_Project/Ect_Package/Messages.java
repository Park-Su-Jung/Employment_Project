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
	public static ArrayList<String> shopping_main_message = new ArrayList<String>();
	
	public Messages() {
		main_menu_before_login.add("1.회원가입");
		main_menu_before_login.add("2.로그인");
		main_menu_before_login.add("9.종료");
		
		main_menu_after_login.add("1.로그아웃");
		main_menu_after_login.add("2.회원정보수정");
		main_menu_after_login.add("3.쇼핑");
		main_menu_after_login.add("4.회원탈퇴");
		main_menu_after_login.add("9.종료");
		
		create_message.add("1.ID를 입력해 주세요 : ");
		create_message.add("2.비밀번호를 입력해 주세요 : ");
		create_message.add("3.이름 입력해 주세요 : ");
		
		create_key.add("users_id");
		create_key.add("users_pass");
		create_key.add("users_name");
		
		login_message.add("1.ID를 입력해 주세요 : ");
		login_message.add("2.비밀번호를 입력해 주세요 : ");
		
		update_message_menu.add("<회원정보변경 메뉴>");
		update_message_menu.add("1.비밀번호");
		update_message_menu.add("2.이름");
		update_message_menu.add("9.종료");
		
		update_message.add("ID");
		update_message.add("변경하실 비밀번호를 입력해 주세요 : ");
		update_message.add("변경하실 이름을 입력해 주세요 : ");
		
		shopping_main_message.add("<게시판 메뉴>");
		shopping_main_message.add("1. 상품 보기/선택");
		shopping_main_message.add("2. 장바구니");
		shopping_main_message.add("3. 쇼핑 내역");
		shopping_main_message.add("9. 쇼핑몰 나가기");
	}
}
