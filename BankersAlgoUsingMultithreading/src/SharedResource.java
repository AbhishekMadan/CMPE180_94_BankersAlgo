import java.util.Arrays;
import java.util.Random;

public class SharedResource {
    public static int available[]={10,5,7};
    int request[] ={0,0,0};
	static Random select = new Random();
	
	public synchronized void allocateResources(int client) {
		generateClientRequest(client);
		showAvailableResource();
		if (!isOneLessThanOther(request, available)) {
			System.out.println("Request cannot be granted!");
		}else{
			Customer c[] = Bank.getInstance().getAllCustomer();
			String seq = "";
			if ((seq=safeSequenceExist(c, client, request))!="") {
					
				Bank.getInstance().getCustomer(client).addToClientAllocation(request);
				Bank.getInstance().getCustomer(client).adjustNeed();
				for (int i = 0; i < available.length; i++) {
					available[i] = available[i] - request[i];
				}
				System.out.println("Safe sequence exist! "+seq);
			} else {
				System.out.println("Safe sequence does not exist!");
			}
		}
		showRemainingResource();
		Bank.getInstance().getCustomer(client).setRunning(true);
		clientState(Bank.getInstance().getAllCustomer());
	}
	
	
	public synchronized void deAllocateResources(int client) {
		int[] clientAllocation = Bank.getInstance().getCustomer(client).getClientAllocation();
		for(int i=0 ; i<available.length; i++) {
			available[i]+= clientAllocation[i];
		}
		Bank.getInstance().getCustomer(client).deallocate();
		Bank.getInstance().getCustomer(client).setRunning(false);
		showCompleteStatus(client);
		
	}
	
	public String safeSequenceExist(Customer c[],int requestingClient,int request[]){
		String sequence="";
		boolean flag[] = new boolean[Bank.CUSTOMER];
		Customer client[] = new Customer[Bank.CUSTOMER];
		for(int i=0 ; i< Bank.CUSTOMER; i++) {
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
					sequence +=(index+1)+ " -> ";
					index = (index+1)%client.length;
					streak=0;
			}else {
				index = (index+1)%client.length;
				streak++;
			}
		}
		
		for(int i=0 ; i<flag.length; i++){
			if(!flag[i])
				return "";
		}
		return sequence;
	}
	
	
	public boolean isOneLessThanOther(int a[],int b[]) {
		for(int i=0; i< a.length; i++) {
			if(a[i]>b[i])
				return false;
		}
		return true;
	}
	
	public void copyArray(int a[],int b[]) {
		for(int i=0; i< a.length; i++) {
		    a[i] = b[i];
		}
	}
	
	public void copyCustomer(Customer a[], Customer b[]) {
		for(int i=0 ; i< a.length; i++) {
			copyArray(a[i].getClientAllocation(), b[i].getClientAllocation());
			copyArray(a[i].getClientMax(), b[i].getClientMax());
			copyArray(a[i].getClientNeed(), b[i].getClientNeed());
		}
	}
	
	public static void clientState(Customer client[]){
		for(int i=0 ; i<client.length ; i++) {
			client[i].printClientStatus();
		}
		System.out.print("\n");
	}
	
	public synchronized void generateClientRequest (int client){
		do{
            for(int i=0;i<Bank.RESOURCE;i++)                       
            request[i]=select.nextInt(1+Math.min(available[i],Bank.getInstance().getCustomer(client).getClientNeed()[i]));
        }while(request[0] == 0 && request[1] == 0 && request[2] == 0);
		System.out.println("Request from client "+(client+1)+" for resource ["
				+request[0]+"  "+request[1]+"  "+request[2]+" ]");
	}
	

	public void showResourceStatus(String type) {
		System.out.println("Resource "+type+" : [ "+available[0]+" "+ available[1]+" "+available[2]+" ]");
	}
	
	public void showAvailableResource() {
		showResourceStatus("Available");
	}
	
	public void showRemainingResource() {
		showResourceStatus("Remaining");
	}
	
	public void showCompleteStatus(int i) {
		System.out.println("Client "+(i+1)+" completes execution.");
		showAvailableResource();
	}
	
}
