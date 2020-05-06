package User_Package;

public class User_Session {
	public static String USER_LOGIN_ID = null;
	public static String USER_LOGIN_NAME = null;
	public static String USER_LOGIN_NUM = null;
	
	public static void Login(String user_id,String user_name,String user_num)
	{
		USER_LOGIN_ID = user_id;
		USER_LOGIN_NAME = user_name;
		USER_LOGIN_NUM = user_num;
	}
	
	public static void Logout()
	{
		USER_LOGIN_ID = null;
		USER_LOGIN_NAME = null;
		USER_LOGIN_NUM = null;
	}
}