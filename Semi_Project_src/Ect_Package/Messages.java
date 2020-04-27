package Ect_Package;

import java.util.ArrayList;

public class Messages {
	public static ArrayList<String> create_message = new ArrayList<String>();
	public static ArrayList<String> create_key = new ArrayList<String>();
	public static ArrayList<String> login_message = new ArrayList<String>();
	public static ArrayList<String> update_message = new ArrayList<String>();
	public Messages() {
		create_message.add("1.ID를 입력해 주세요 : ");
		create_message.add("2.비밀번호를 입력해 주세요 : ");
		create_message.add("3.이름 입력해 주세요 : ");
		
		create_key.add("user_id");
		create_key.add("user_pass");
		create_key.add("user_name");
		create_key.add("user_phone");
		
		login_message.add("1.ID를 입력해 주세요 : ");
		login_message.add("2.비밀번호를 입력해 주세요 : ");
		
		update_message.add("ID");
		update_message.add("변경하실 비밀번호를 입력해 주세요 : ");
		update_message.add("변경하실 이름을 입력해 주세요 : ");
		update_message.add("변경하실 전화번호를 입력해 주세요 : ");
	}
}
