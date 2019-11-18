package dkbank;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import org.apache.log4j.Logger;
public class Customers implements java.io.Serializable{
	
	/*
	 * SerialVersionUID
	 * represents your class's version
	 *
	 * the serialization runtime associates a version number with each serializable class
	 * it is used during deserialization to verify that the sender and the receiver of 
	 * the serialized object have loaded the correct class.
	 */
	
	//private static final long serialVersionUID = 1L;
	String id, password, name;
	int ssn;
	ArrayList<String> arrayAcc; 
	final static Logger logger = Logger.getLogger(Customers.class);
	
	Customers(String id, String password, String name, int ssn){
		this.id = id;
		this.password = password;
		this.name = name;
		this.ssn = ssn;
		this.arrayAcc = new ArrayList<>();
	}
	
	static HashMap<String, Customers> deSerializeCustomer() {
		String filename = "customers.ser";
		HashMap<String, Customers> hashMapCust = null; 
		try { 
			FileInputStream file = new FileInputStream(filename); 
			ObjectInputStream in = new ObjectInputStream(file); 
			hashMapCust = (HashMap<String, Customers>)in.readObject(); 	
			in.close(); 
			file.close(); 
			System.out.println("Retrieving customer information..."); 
			System.out.println("[Number of Customers]:" + hashMapCust.size());
		} 
		catch(Exception e) { 
			System.out.println("IOException is caught. Fatal failure. Database must be re-initialized."); 
			System.out.println("Problem fixed. Run the program once again."); 
			//re-initialization in case of data corruption.
			Customers cust = new Customers("a", "1", "dan", 2);
			Accounts account = new Accounts(cust); 
			cust.arrayAcc.add(account.accNum);
			HashMap<String, Accounts> hashMapAcc1 = new HashMap<>();
			HashMap<String, Customers> hashMapCust1 = new HashMap<>();
			hashMapAcc1.put(account.accNum, account);
			hashMapCust1.put(cust.id, cust);
			Accounts.serializeAccount(hashMapAcc1);
			Customers.serializeCustomer(hashMapCust1);
			System.exit(0);
		} 
		return hashMapCust; 
	}
	
	static void serializeCustomer(HashMap<String, Customers> hashMapCust) {
		String filename = "customers.ser";
		boolean append = false;
		//HashMap<String, Customers> hashMapCust = deSerializeCustomer();
		//HashMap<String, Accounts> hashMapAcc = new HashMap<String, Accounts>(); 
		//hashMapCust.put(newAccount.id, newAccount);
		try{ 
			FileOutputStream file = new FileOutputStream(filename, append); 
			ObjectOutputStream out = new ObjectOutputStream(file); 
			out.writeObject(hashMapCust); 
			out.close(); 
			file.close(); 
			System.out.println("Customer registrations have been serialized"); 
			System.out.println("[Number of Customers]:" + hashMapCust.size());
		} 
		catch(Exception e) { 
			System.out.println("IOException is caught"); 
		} 
	}
	
	static void withdrawCustomer(String cust_acc) {
		Scanner sc = new Scanner(System.in);
		double amount;
		if (MainDriver.hashMapAcc.get(cust_acc).status.equals("approved")) {
			amount = MainDriver.checkInputDouble();
			if (MainDriver.hashMapAcc.get(cust_acc).balance >= amount) {
				MainDriver.hashMapAcc.get(cust_acc).balance -= amount;
				System.out.println("#SUCCESS#: Withdrwal complete. The account balance is now: $" + MainDriver.hashMapAcc.get(cust_acc).balance);
				logger.info("Account "+cust_acc + ": withdrawed $" + amount);
			} else {
				System.out.println("*ERROR*: Insufficient account balance. The account balance is: $" + MainDriver.hashMapAcc.get(cust_acc).balance);
			}
		} else {
			System.out.println("*ERROR*: Sorry. The status of the account is: " + MainDriver.hashMapAcc.get(cust_acc).status );
		}
	}
	
	static void depositCustomer(String cust_acc) {
		Scanner sc = new Scanner(System.in);
		double amount;
		if (MainDriver.hashMapAcc.get(cust_acc).status.equals("approved")) {
			amount = MainDriver.checkInputDouble();
			MainDriver.hashMapAcc.get(cust_acc).balance += amount;
			System.out.println("#SUCCESS#: Deposit complete. The account balance is now: $" + MainDriver.hashMapAcc.get(cust_acc).balance);
			logger.info("Account " + cust_acc + ": deposited $" + amount);
		}else {
			System.out.println("*ERROR*: Sorry. The status of the account is: " +MainDriver.hashMapAcc.get(cust_acc).status );
		}
	}
	
	static void transferCustomer(String cust_acc) {
		Scanner sc = new Scanner(System.in);
		double amount;
		String transferAcc; 
		if (MainDriver.hashMapAcc.get(cust_acc).status.equals("approved")) {
			amount = MainDriver.checkInputDouble();
			System.out.println("[INPUT]: What is the account number of the reciepient?");
			transferAcc = sc.nextLine();
			if (MainDriver.hashMapAcc.containsKey(transferAcc)==true && MainDriver.hashMapAcc.get(transferAcc).status.equals("approved")) {
				if (MainDriver.hashMapAcc.get(cust_acc).balance >= amount) {
					MainDriver.hashMapAcc.get(cust_acc).balance -=  amount; 
					MainDriver.hashMapAcc.get(transferAcc).balance += amount; //transfer account balance
					System.out.println("#SUCCESS#: Withdrwal complete. The account balance is now: $" + MainDriver.hashMapAcc.get(cust_acc).balance);
					logger.info("Account " + cust_acc + ": transferred $" + amount + " to " + "account " + transferAcc);
				} else {
					System.out.println("*ERROR*: Insufficient account balance. The account balance is: $" + MainDriver.hashMapAcc.get(cust_acc).balance);
				}
			} else {
				System.out.println("*ERROR*: Either the account number provided does not exist or the status is still pending.");
			}
		} else {
			System.out.println("*ERROR*: Sorry. The status of the account is: " + MainDriver.hashMapAcc.get(cust_acc).status );
		}
	}
	
	public String toString() {
		return "[ID: "+id + "] [Password: "+password + "] [Name: "+name + "] [SSN: "+ssn +  "]\n[Accounts: " + arrayAcc+"]"; 
	}
	
	public static void printCustInfo(Customers cust) {
		System.out.println("\n--------------------" + "Customer Info" + "--------------------");
		System.out.println(cust);
		System.out.println("------------------------Thanks------------------------");
	}
}
