package dkbank;
import java.util.Scanner;

public class Employee {
	
	static String password = "123"; 
	static String id = "employee";
	
	void getEmpOptions() {
		boolean logout = false; 
		Scanner sc = new Scanner(System.in);
		while (logout == false) {
			System.out.println("\n**************************** COMMANDS ********************************");
			System.out.println("   View All Accounts[1], View Account Info[2], View Customer Info[3]");
			System.out.println("         Approve Accounts[4], Deny Accounts[5],  Exit[6]");
			System.out.println("**********************************************************************");
			System.out.println("[INPUT] Type available command:");
			String userInput = sc.nextLine();
			if (userInput.equals("1")) {
				emp_viewAllAcc();
			}else if(userInput.equals("2")) {
				emp_viewAcc();
			}else if(userInput.equals("3")) {
				emp_viewCust();	
			}else if(userInput.equals("4")) {
				emp_approveAcc();	
			}else if(userInput.equals("5")) {
				emp_denyAcc();	
			}else if(userInput.equals("6")) {
				System.out.println("Bye now.");
				logout = true;
				break; 
			}else {
				System.out.println("*ERROR*: Invalid input");
			}
		}
	}
	
	void emp_viewAllAcc(){
		System.out.println("\n------------------------All Accounts-------------------------");
		System.out.println(MainDriver.hashMapAcc); 
		System.out.println("--------------------------------------------------------------");
	}
	
	void emp_approveAcc(){
		Scanner sc = new Scanner(System.in);
		System.out.println("[INPUT]: Account number to be approved?");
		String custAccNum = sc.nextLine(); 
		boolean accFound = MainDriver.hashMapAcc.containsKey(custAccNum);
		if (accFound==false) {
			System.out.println("*ERROR*: Account number not found.");
		}else {
			//updating account object
			System.out.println("#SUCCESS#: The account status was: " + MainDriver.hashMapAcc.get(custAccNum).status);
			MainDriver.hashMapAcc.get(custAccNum).status = "approved";
			System.out.println("#SUCCESS#: The account status is now: 'approved'");
		}
	}
	
	void emp_denyAcc(){
		Scanner sc = new Scanner(System.in);
		System.out.println("[INPUT]: Account number to be denied?");
		String custAccNum = sc.nextLine(); 
		boolean accFound = MainDriver.hashMapAcc.containsKey(custAccNum);
		if (accFound==false) {
			System.out.println("*ERROR*: Account number not found.");
		}else {
			//updating account object
			System.out.println("#SUCCESS#: The account status was: " + MainDriver.hashMapAcc.get(custAccNum).status);
			MainDriver.hashMapAcc.get(custAccNum).status = "denied";
			System.out.println("#SUCCESS#: The account status is now: 'denied'");
		}
	}
	
	void emp_viewAcc() {
		Scanner sc = new Scanner(System.in);
		System.out.println("[INPUT]: Account number?");
		String custAcc = sc.nextLine(); 
		boolean accFound = MainDriver.hashMapAcc.containsKey(custAcc);
		if (accFound==false) {
			System.out.println("*ERROR*: Account number not found.");
		}else {
			Accounts acc = MainDriver.hashMapAcc.get(custAcc);
			Accounts.printAccInfo(acc);
		}
	}
	
	void emp_viewCust() {
		Scanner sc = new Scanner(System.in);
		System.out.println("[INPUT]: Customer ID?");
		String custId = sc.nextLine(); 
		boolean accFound = MainDriver.hashMapCust.containsKey(custId);
		if (accFound==false) {
			System.out.println("*ERROR*: Customer ID number not found.");
		}else {
			Customers cust = MainDriver.hashMapCust.get(custId);
			Customers.printCustInfo(cust);
		}
	}	
	
	static void verifyEmployee() {
		boolean correct = false;
		Scanner sc = new Scanner(System.in);
		String id;
		while (correct == false) {
			System.out.println("[INPUT]: Account ID?");
			id = sc.nextLine();
			System.out.println("[INPUT]: Account Password?");
			String employeePswd = sc.nextLine();
			if (id.equals(Employee.id )&& employeePswd.equals(Employee.password)) {
				correct=true;
				break;
			}else {
				System.out.println("*ERROR*: Access denied. Please try again.");
			}
		}
		System.out.println("#SUCCESS#: Sign in success.");
	}

}
