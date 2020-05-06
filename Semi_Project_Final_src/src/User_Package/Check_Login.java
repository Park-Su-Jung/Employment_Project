package User_Package;

public class Check_Login {
	public static boolean Check_Function()
	{
		if (User_Session.USER_LOGIN_ID == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
}
