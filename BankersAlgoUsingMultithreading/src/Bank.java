public class Bank {
	public static final int RESOURCE = 3;
	public static final int CUSTOMER = 5;
	
	private static Bank instance = null;
	private static Customer c[];
	
	private Bank() {}
	
	public static Bank getInstance() {
		if (instance == null) {
			instance = new Bank();
			initStudents();
		}
		return instance;
	}
	
	private static void initStudents() {
		c = new Customer[Bank.CUSTOMER];
		initializeCustomer();
	}
	
	public static void initializeCustomer() {
		c[0]=new Customer(0,7,5,3);
        c[1]=new Customer(1,3,2,2);
        c[2]=new Customer(2,9,0,2);
        c[3]=new Customer(3,2,2,2);
        c[4]=new Customer(4,4,3,3);
	}
	
	public Customer getCustomer(int i) {
		return c[i];
	}
	
	public Customer[] getAllCustomer() {
		return c;
	}
}
