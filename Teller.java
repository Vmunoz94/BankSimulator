// DO NOT ADD NEW METHODS OR DATA FIELDS!
// DO NOT MODIFY ANY METHODS OR DATA FIELDS!

package PJ3;

class Teller {

   // teller id and current customer which is served by this cashier
   private int tellerID;
   private Customer customer;

   // start time and end time of current free/busy interval
   private int startFreeTime;
   private int endFreeTime;
   private int startBusyTime;
   private int endBusyTime;

   // for keeping statistical data
   private int totalFreeTime;
   private int totalBusyTime;
   private int totalCustomers;

   // Constructor
   Teller()
   {
	this(-1);
   }


   // Constructor with teller id
   Teller(int tellerId)
   {// add statements
	   tellerID = tellerId;
   }

   //--------------------------------
   // accessor methods
   //--------------------------------

   int getTellerID () 
   {
	return tellerID;
   }

   Customer getCustomer() 
   {
	return customer;
   }

   int getEndBusyTime() 
   {
	return endBusyTime; 
   }


   //--------------------------------
   // mutator methods
   //--------------------------------

   void setCustomer(Customer newCustomer)
   {
  	customer = newCustomer;
   }

   void setStartFreeTime (int time)
   {
  	startFreeTime = time;
   }

   void setStartBusyTime (int time)
   {
  	startBusyTime = time;
   }
 
   void setEndFreeTime (int time)
   {
  	endFreeTime = time;
   }

   void setEndBusyTime (int time)
   {
  	endBusyTime = time;
   }
 
   void updateTotalFreeTime()
   {//add statements
	   totalFreeTime += (endFreeTime - startFreeTime);
   }

   void updateTotalBusyTime()
   {//add statements
	   totalBusyTime += (endBusyTime - startBusyTime);
   }

   void updateTotalCustomers()
   {//add statements
	   totalCustomers++;
   }


   //--------------------------------
   // Teller State Transition methods
   //--------------------------------

   // From free interval to busy interval
   void freeToBusy (Customer newCustomer, int currentTime)
   {//add statements 			
  	// goal  : start serving newCustomer at currentTime
	   //			
	// steps : set endFreeTime, update TotalFreeTime
	   			setEndFreeTime(currentTime);
	   			updateTotalFreeTime();
	//         set startBusyTime, endBusyTime, customer
	   			setStartBusyTime(currentTime);
	   			setEndBusyTime(startBusyTime + newCustomer.getTransactionTime());
	   			this.customer = newCustomer;
	   			customer.setFinishTime(startBusyTime + newCustomer.getTransactionTime());
	//         update totalCustomers
	   			updateTotalCustomers(); 			
   }

   // Transition from busy interval to free interval
   Customer busyToFree ()
   {//add statements   	   
  	// goal  : end serving customer at endBusyTime
        //
	// steps : update TotalBusyTime, set startFreeTime
	   			updateTotalBusyTime();
	   			setStartFreeTime(endBusyTime);
	   			
  	//         return customer 
	   			return customer;   			
   }


   //--------------------------------
   // Print statistical data   
   //--------------------------------
   void printStatistics () 
   {
  	// print teller statistics, see project statement
  	System.out.println("\t\tTeller ID                : "+tellerID);
  	System.out.println("\t\tTotal free time          : "+totalFreeTime);
  	System.out.println("\t\tTotal busy time          : "+totalBusyTime);
  	System.out.println("\t\tTotal # of customers     : "+totalCustomers);
  	if (totalCustomers > 0)
  	   System.out.format("\t\tAverage transaction time : %.2f%n\n",(totalBusyTime*1.0)/totalCustomers);
   }

   public String toString()
   {
        return "tellerID="+tellerID+
               ":startFreeTime="+startFreeTime+":endFreeTime="+endFreeTime+
               ":startBusyTime="+startBusyTime+":endBusyTime="+endBusyTime+
               ":totalFreeTime="+totalFreeTime+":totalBusyTime="+totalBusyTime+
               ":totalCustomer="+totalCustomers+">>customer:"+customer;
   }

   public static void main(String[] args) {
        // quick check
        Customer mycustomer = new Customer(1,5,15);
        Teller myteller = new Teller(5);
        myteller.setStartFreeTime(0);
        System.out.println(myteller);
        myteller.freeToBusy(mycustomer, 20);
        System.out.println("\n"+myteller);
        myteller.busyToFree();
        System.out.println("\n"+myteller);
        System.out.println("\n\n");
        myteller.printStatistics();


   }

};

