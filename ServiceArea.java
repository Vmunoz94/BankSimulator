// DO NOT ADD NEW METHODS OR NEW DATA FIELDS!
// DO NOT MODIFY METHODS OR NEW DATA FIELDS!

package PJ3;

import java.util.*;

//--------------------------------------------------------------------------
//
// Define simulation queues in a service area. Queues hold references to Customer 
// and Teller objects
//
// Customer (FIFO) queue is used to hold waiting customers. If the queue is too long
// (i.e. >  customerQLimnit), customer goes away without entering customer queue
//
// There are several tellers in a service area. Use PriorityQueue to 
// hold BUSY tellers and FIFO queue to hold FREE tellers, 
// i.e. a teller that is FREE for the longest time should start be used first.
//
// To handle teller in PriorityQueue, we need to define comparator 
// for comparing 2 teller objects. Here is a constructor from Java API:
//
// 	PriorityQueue(int initialCapacity, Comparator<? super E> comparator) 
//
// For priority queue, the default compare function is "natural ordering"
// i.e. for numbers, minimum value is returned first
//
// User can define own comparator class for PriorityQueue.
// For teller objects, we like to have smallest end busy interval time first.
// i.e. use Teller's getEndBusyIntervalTime()
//
// The following class define compare() for two tellers :

class CompareTellers implements Comparator<Teller>{
	// override compare() method
 	public int compare(Teller o1, Teller o2) {
		return o1.getEndBusyTime() - o2.getEndBusyTime(); 
	}
}

class ServiceArea {
  
  // Private data fields:
  
  // define one priority queue 
  private PriorityQueue <Teller> busyTellerQ;

  // define two FIFO queues
  private Queue<Customer> customerQ;
  private Queue<Teller> freeTellerQ;

  // define customer queue limit
  private int customerQLimit;


  // Constructor 
  public ServiceArea() 
  {// add statements
	  this(0,0);
  }

  // Constructor 
  public ServiceArea(int numTellers, int customerQlimit)
  {// add additional statements

	// use ArrayDeque to construct FIFO queue objects
	  customerQ = new ArrayDeque<Customer>();
	  freeTellerQ = new ArrayDeque<Teller>();
	// construct PriorityQueue object
	  busyTellerQ = new PriorityQueue<Teller>();
 	// override compare() in Comparator to compare Teller objects
	  busyTellerQ= new PriorityQueue<Teller>( numTellers, 
						new CompareTellers()); 

	// initialize customerQlimit
	customerQLimit = customerQlimit;
        // Construct Teller objects and insert into FreeTellerQ
		// assign teller ID from 1, 2,..., numTellers	
			for (int i = 1; numTellers >= i; i++){
				 insertFreeTellerQ( new Teller(i) );
			}//end for loop    
  }//end service area

  // -------------------------------------------------
  // freeTellerQ methods: remove, insert, empty, size
  // -------------------------------------------------

  public Teller removeFreeTellerQ()
  {// Add statements
	// remove and return a free teller
	  return freeTellerQ.remove();
  }

  public void insertFreeTellerQ(Teller teller)
  { // Add statements
	  // insert a free teller
	  freeTellerQ.add(teller);
  }

  public boolean emptyFreeTellerQ()
  {// Add statements
	// is freeTellerQ empty?
	  return freeTellerQ.isEmpty();
  }

  public int numFreeTellers()
  {// Add statements
	// get number of free tellers
	  return freeTellerQ.size();
  }

  // -------------------------------------------------------
  // busyTellerQ methods: remove, insert, empty, size, peek
  // -------------------------------------------------------

  public Teller removeBusyTellerQ() 
  {// Add statements
	// remove and return a busy teller
	  return busyTellerQ.remove();
  }

  public void insertBusyTellerQ(Teller teller)
  {// Add statements
	// insert a busy teller
	  busyTellerQ.offer(teller);
  }

  public boolean emptyBusyTellerQ()
  {
	// is busyTellerQ empty?
	return busyTellerQ.isEmpty();
  }

  public int numBusyTellers()
  {// Add statements
	// get number of busy tellers
	  return busyTellerQ.size();
  }


  public Teller getFrontBusyTellerQ() 
  {// Add statements
	// get front of busy tellers
	// "retrieve" but not "remove"
	  return busyTellerQ.peek();
  }

  // -------------------------------------------------------
  // customerQ methods: remove, insert, empty, size
  //                    and check isCustomerQTooLong()
  // -------------------------------------------------------

  public Customer removeCustomerQ()
  {// Add statements
	// remove and return a customer 
	  return customerQ.poll();
  }

  public void insertCustomerQ(Customer customer)
  {// Add statements
	// insert a customer 
	  customerQ.offer(customer);
  }


  public boolean emptyCustomerQ()
  {// Add statements
	// is customerQ empty?
	  return customerQ.isEmpty();
  }

  public int numWaitingCustomers()
  {// Add statements
	// get number of customers 
	  return customerQ.size();
  }

  public boolean isCustomerQTooLong()
  {// Add statements
	// is customerQ too long?
	  if (customerQ.size() >= customerQLimit){
		  return true;
	  }
	  else{
		  return false;
	  }
  }

  public void printStatistics()
  {
  	System.out.println("\t# waiting customers : "+numWaitingCustomers());
  	System.out.println("\t# busy tellers      : "+numBusyTellers());
  	System.out.println("\t# free tellers      : "+numFreeTellers());
  }

  public static void main(String[] args) {

        // quick check

        // create a ServiceArea and 4 customers
        ServiceArea sc = new ServiceArea(4, 5);
        Customer c1 = new Customer(1,18,10);
        Customer c2 = new Customer(2,33,11);
        Customer c3 = new Customer(3,21,12);
        Customer c4 = new Customer(4,37,13);

        // insert customers into customerQ
  	sc.insertCustomerQ(c1);
  	sc.insertCustomerQ(c2);
  	sc.insertCustomerQ(c3);
  	sc.insertCustomerQ(c4);
	System.out.println(""+sc.customerQ);
        System.out.println("===============================================");
	System.out.println("Remove customer:"+sc.removeCustomerQ());
	System.out.println("Remove customer:"+sc.removeCustomerQ());
	System.out.println("Remove customer:"+sc.removeCustomerQ());
	System.out.println("Remove customer:"+sc.removeCustomerQ());
        System.out.println("===============================================");

        // remove tellers from freeTellerQ
	System.out.println("freeTellerQ:"+sc.freeTellerQ);
        System.out.println("===============================================");
	Teller p1=sc.removeFreeTellerQ();
	Teller p2=sc.removeFreeTellerQ();
	Teller p3=sc.removeFreeTellerQ();
	Teller p4=sc.removeFreeTellerQ();
	System.out.println("Remove free teller:"+p1);
	System.out.println("Remove free teller:"+p2);
	System.out.println("Remove free teller:"+p3);
	System.out.println("Remove free teller:"+p4);
        System.out.println("===============================================");
	System.out.println("freeTellerQ:"+sc.freeTellerQ);
	System.out.println("busyTellerQ:"+sc.busyTellerQ);
        System.out.println("===============================================");


        // insert customers to tellers
        p1.freeToBusy (c1, 13);
        p2.freeToBusy (c2, 13);
        p3.freeToBusy (c3, 13);
        p4.freeToBusy (c4, 13);
	System.out.println("Assign customers to free tellers");

        // insert tellers to busyTellerQ
        System.out.println("===============================================");
	System.out.println("Insert tellers to busyTellerQ");
	sc.insertBusyTellerQ(p1);
	sc.insertBusyTellerQ(p2);
	sc.insertBusyTellerQ(p3);
	sc.insertBusyTellerQ(p4);
	System.out.println("busyTellerQ:"+sc.busyTellerQ);
        System.out.println("===============================================");

        // remove tellers from busyTellerQ
	p1=sc.removeBusyTellerQ();
	p2=sc.removeBusyTellerQ();
	p3=sc.removeBusyTellerQ();
	p4=sc.removeBusyTellerQ();

        p1.busyToFree();
        p2.busyToFree();
        p3.busyToFree();
        p4.busyToFree();

	System.out.println("Remove busy teller:"+p1);
	System.out.println("Remove busy teller:"+p2);
	System.out.println("Remove busy teller:"+p3);
	System.out.println("Remove busy teller:"+p4);

   }


};

