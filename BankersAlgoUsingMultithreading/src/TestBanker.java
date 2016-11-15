
public class TestBanker {

	public static void main(String[] args) {
		for(int j=0;j<5;j++) {
	    		Bank.getInstance().getCustomer(j).start();
        }
 	}
}