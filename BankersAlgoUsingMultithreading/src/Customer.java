
public class Customer extends Thread{
	
	public int clientNo;
	public boolean isRunning;
    private int allocation[]=new int[Bank.RESOURCE];
    private int max[]=new int[Bank.RESOURCE];
    private int need[]=new int[Bank.RESOURCE];
   
    private static SharedResource sharedResource= new SharedResource();
    
    Customer() {}
      
    Customer(int num, int resourceA, int resourceB, int resourceC) {
    	clientNo = num;
    	max[0] = resourceA;
    	max[1] = resourceB;
    	max[2] = resourceC;
    	for (int index=0 ; index< Bank.RESOURCE; index++  ){
    		allocation[index] =0;
    		need[index] = max[index]-allocation[index];
    	}
        isRunning = false;
    }
        
    
    @Override
    public void run() {
    	//if (!isRunning)
    		sharedResource.allocateResources(clientNo);
    	/*if (isRunning)
    		sharedResource.deAllocateResources(clientNo);*/
    	}
  
   
    public int[] getClientNeed(){
        return need;
    }
    
    public int[] getClientAllocation() {
        return allocation;	
    }
    
    public int[] getClientMax() {
    	return max;
    }
    
    public void addToClientAllocation(int n[]) {
    	for(int i=0 ; i<n.length; i++) {
    		allocation[i] += n[i];
    	}
    }
    
    public void adjustNeed() {
    	for (int index=0 ; index< Bank.RESOURCE; index++  ){
    		need[index] = max[index]-allocation[index];
    	}
    }
    
    public void deallocate() {
    	for (int index=0 ; index< Bank.RESOURCE; index++  ){
    		need[index] = max[index];
    		allocation[index] = 0;
    	}
    }
    
	public void printClientStatus() {
		System.out.print("Client "+clientNo+"  Allocation :  ");
		for (int i = 0; i < Bank.RESOURCE; i++)
			System.out.print(allocation[i] + "  ");
		System.out.print("Max :  ");
		for (int i = 0; i < Bank.RESOURCE; i++)
			System.out.print(max[i] + "  ");
		System.out.print("Need :  ");
		for (int i = 0; i < Bank.RESOURCE; i++)
			System.out.print(need[i] + "  ");
		System.out.print("\n");
	}
	
	public void setRunning(boolean status){
		isRunning = status;
	}
}
