class Customer {
	
	public int clientNo;
	final int RESOURCE=3;                             
    private int allocation[]=new int[RESOURCE];
    private int max[]=new int[RESOURCE];
    private int need[]=new int[RESOURCE];
    
    Customer() {}
      
    Customer(int num, int resourceA, int resourceB, int resourceC) {
    	clientNo = num;
    	max[0] = resourceA;
    	max[1] = resourceB;
    	max[2] = resourceC;
    	for (int index=0 ; index< RESOURCE; index++  ){
    		allocation[index] =0;
    		need[index] = max[index]-allocation[index];
    	}
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
    	for (int index=0 ; index< RESOURCE; index++  ){
    		need[index] = max[index]-allocation[index];
    	}
    }
    
	public void printClientStatus() {
		System.out.print("Client "+clientNo+"  Allocation :  ");
		for (int i = 0; i < RESOURCE; i++)
			System.out.print(allocation[i] + "  ");
		System.out.print("Max :  ");
		for (int i = 0; i < RESOURCE; i++)
			System.out.print(max[i] + "  ");
		System.out.print("Need :  ");
		for (int i = 0; i < RESOURCE; i++)
			System.out.print(need[i] + "  ");
		System.out.print("\n");
	}
}
