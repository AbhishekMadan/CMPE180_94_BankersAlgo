import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

public class TestBanker {

	public static void clientState(Customer client[]){
		for(int i=0 ; i<client.length ; i++) {
			client[i].printClientStatus();
		}
		System.out.print("\n");
	}
	
	public static boolean isOneLessThanOther(int a[],int b[]) {
		for(int i=0; i< a.length; i++) {
			if(a[i]>b[i])
				return false;
		}
		return true;
	}
	
	public static void copyArray(int a[],int b[]) {
		for(int i=0; i< a.length; i++) {
		    a[i] = b[i];
		}
	}
	
	public static void copyCustomer(Customer a[], Customer b[]) {
		for(int i=0 ; i< a.length; i++) {
			copyArray(a[i].getClientAllocation(), b[i].getClientAllocation());
			copyArray(a[i].getClientMax(), b[i].getClientMax());
			copyArray(a[i].getClientNeed(), b[i].getClientNeed());
		}
	}
	
	public static boolean safeSequenceExist(Customer c[],int requestingClient,int request[],int available[]){
		
		boolean flag[] = new boolean[c.length];
		Customer client[] = new Customer[c.length];
		for(int i=0 ; i< c.length; i++) {
			client[i] = new Customer();
		}
		copyCustomer(client, c);
		int work[]= new int[available.length];
		Arrays.fill(flag, false);
		
		client[requestingClient].addToClientAllocation(request);
		client[requestingClient].adjustNeed();
		
		for(int i=0 ; i<available.length; i++) {
			work[i]=available[i]-request[i];
		}
		
		int streak=0, index=0;
		while(streak<client.length){
			if(!flag[index]&&isOneLessThanOther(client[index].getClientNeed(),work)){
					for(int i=0 ; i< work.length; i++){
						work[i]+=client[index].getClientAllocation()[i];
					}
					flag[index]=true;
					index = (index+1)%client.length;
					streak=0;
			}else {
				index = (index+1)%client.length;
				streak++;
			}
		}
		
		for(int i=0 ; i<flag.length; i++){
			if(!flag[i])
				return false;
		}
		return true;
	}
	
	
	public static void main(String[] args) {
		/**
		 * Constants
		 */
		final int CLIENTS = 5;
		final int RESOURCE = 3;
		
		/**
		 * housekeeping
		 */
		int available[]={10,5,7};
		Customer client[] = new Customer[CLIENTS];
		int request[] = new int[RESOURCE] , tempNeed[] = new int[RESOURCE];
        Random select = new Random();
        client[0]=new Customer(0,7,5,3);
        client[1]=new Customer(1,3,2,2);
        client[2]=new Customer(2,9,0,2);
        client[3]=new Customer(3,2,2,2);
        client[4]=new Customer(4,4,3,3);
        
        clientState(client);
        
        /**
         * generating requests for resource
         */
        for(int j=0;j<CLIENTS;j++) {
        	tempNeed = client[j].getClientNeed();
            
        	do{
                for(int i=0;i<RESOURCE;i++)                       
                 request[i]=select.nextInt(1+tempNeed[i]);
            }while(request[0] == 0 && request[1] == 0 && request[2] == 0);
        	
            System.out.println("Request from client "+j+" for resource ["
            					+request[0]+"  "+request[1]+"  "+request[2]+" ]");
            System.out.println("Available : [ "+available[0]+" "+ available[1]+" "+available[2]+" ]");
			if (!isOneLessThanOther(request, available)) {
				System.out.println("Request cannot be granted!");
			} else {
				if (safeSequenceExist(client, j, request, available)) {
					client[j].addToClientAllocation(request);
					client[j].adjustNeed();
					for (int i = 0; i < available.length; i++) {
						available[i] = available[i] - request[i];
					}
					System.out.println("Safe sequence exist!");
				} else {
					System.out.println("Safe sequence does not exist!");
				}
			}
            System.out.println("Resource remaining : [ "+available[0]+" "+ available[1]+" "+available[2]+" ]\n");
            clientState(client);
        }
 	}
}
