package dkbank;

import java.util.Scanner;

public class Admin extends Employee{

	static String password = "345"; 
	static String id = "admin";
	
	void getAdminOptions() {
		boolean logout = false; 
		Scanner sc = new Scanner(System.in);
		while (logout == false) {
			System.out.println("\n**************************** COMMANDS ********************************");
			System.out.println("  View All Accounts[1], View Account Info[2], View Customer Info[3]");
			System.out.println("     Approve Accounts[4], Deny Accounts[5], Cancel Account[6]");
			System.out.println("         Deposit[7], Withdraw[8], Transfer[9], Exit[10]");
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
				adminCancel();
			}else if(userInput.equals("7")) {
				adminDeposit();		
			}else if(userInput.equals("8")) {
				adminWithdraw();
			}else if(userInput.equals("9")) {
				adminTransfer();				
			}else if(userInput.equals("10")) {
				System.out.println("Bye now.");
				logout = true;
				break; 
			}else {
				System.out.println("*ERROR*: Invalid input");
			}
		}
	}
	
	private void adminDeposit() {
		Scanner sc = new Scanner(System.in);
		System.out.println("[INPUT]: What is the account number?");
		String accNum = sc.nextLine();
		boolean accFound = MainDriver.hashMapAcc.containsKey(accNum);
		if (accFound==false) {
			System.out.println("*ERROR*: Account number not found.");
		}else {
			System.out.println("#SUCCESS#: Account number found.");
			Customers.depositCustomer(accNum);
		}	
	}
	
	private void adminWithdraw() {
		Scanner sc = new Scanner(System.in);
		System.out.println("[INPUT]: What is the account number?");
		String accNum = sc.nextLine();
		boolean accFound = MainDriver.hashMapAcc.containsKey(accNum);
		if (accFound==false) {
			System.out.println("*ERROR*: Account number not found.");
		}else {
			Customers.withdrawCustomer(accNum);
			
		}
	}
	private void adminTransfer() {
		Scanner sc = new Scanner(System.in);
		System.out.println("[INPUT]: What is the account number?");
		String accNum = sc.nextLine();
		boolean accFound = MainDriver.hashMapAcc.containsKey(accNum);
		if (accFound==false) {
			System.out.println("*ERROR*: Account number not found.");
		}else {
			System.out.println("#SUCCESS#: Account number found.");
			Customers.transferCustomer(accNum);
		}
	}
	
	private void adminCancel() {
		Scanner sc = new Scanner(System.in);
		System.out.println("[INPUT]: What is the account number?");
		String accNum = sc.nextLine();
		boolean accFound = MainDriver.hashMapAcc.containsKey(accNum);
		if (accFound==false) {
			System.out.println("*ERROR*: Account number not found.");
		}else {
			System.out.println("#SUCCESS#: Account number found.");
			int arrayCustSize = MainDriver.hashMapAcc.get(accNum).arrayCust.size();
			for (int i=0;i<arrayCustSize; i++) {
				System.out.println("Fetching the account's customer #"+ i + " information...");
				String id = MainDriver.hashMapAcc.get(accNum).arrayCust.get(i);
				int arrayAccSize = MainDriver.hashMapCust.get(id).arrayAcc.size();
				for (int x=0; i<arrayAccSize; i++) {
					System.out.println("Customer #" + i + ": finding the right account...");
					if (MainDriver.hashMapCust.get(id).arrayAcc.get(x).equals(accNum)) {
						System.out.println("Customer #" + i + ": correct account found.");
						System.out.println("Executing cancellation...");
						MainDriver.hashMapCust.get(id).arrayAcc.remove(x);
						System.out.println("Customer #" + i + ": cancellation complete.");
						break;
					}
				}				
			}
			System.out.println("#SUCCESS#: Account cancelled.");
		}
	}
	
	static void verifyAdmin() {
		boolean correct = false;
		Scanner sc = new Scanner(System.in);
		String id;
		while (correct ==false) {
			System.out.println("[INPUT]: Account ID?");
			id = sc.nextLine();
			System.out.println("[INPUT]: Account Password?");
			String employeePswd = sc.nextLine();
			if (id.equals(Admin.id) && employeePswd.equals(Admin.password)) {
				correct=true;
				break;
			}else {
				System.out.println("*ERROR*: Access denied. Please try again.");
			}
		}
		System.out.println("#SUCCESS#: Sign in success.");
	}
}


