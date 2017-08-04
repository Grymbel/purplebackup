package login;

public class TestClass {
	public static void main(String[]args){
		BadTyping.clearTypo(); //Use on successful login
		
		BadTyping.logTypo();//Use on failed login
		System.out.println(BadTyping.getPenalty());//Use on Failed login
	}
}
