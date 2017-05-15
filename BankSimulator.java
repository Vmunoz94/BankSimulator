package PJ3;

import java.util.*;
import java.io.*;

// You may add new functions or data in this class 
// You may modify any functions or data members here
// You must use Customer, Teller and ServiceArea classes
// to implement Bank simulator

class BankSimulator {

  // input parameters
  private int numTellers, customerQLimit;
  private int simulationTime, dataSource;
  private int chancesOfArrival, maxTransactionTime;

  // statistical data
  private int numGoaway, numServed, totalWaitingTime;

  // internal data
  private int customerIDCounter;   // customer ID counter
  private ServiceArea servicearea; // service area object
  private Scanner dataFile;	   // get customer data from file
  private Random dataRandom;	   // get customer data using random function

  // most recent customer arrival info, see getCustomerData()
  private boolean anyNewArrival;  
  private int transactionTime;

  // initialize data fields
  private BankSimulator()
  {// add statements
	  dataRandom = new Random();
  }

  private void setupParameters()
  {// add statements
	  //parameters
	  final int MAX_NUMBER_OF_TELLERS = 10;
	  final int MAX_SIMULATION_LENGTH = 10000;
	  final int MAX_TRANSACTION_TIME = 500;
	  final int MAX_CUSTOMER_QUE_LIMIT = 50;
	  boolean ExceptionLoopControl = true;
	// read input parameters
	Scanner input = new Scanner(System.in);
	Scanner stringInput = new Scanner(System.in);
	 	  
	  System.out.println("\t ***  Get Simulation Parameters  ***\n\n");	//title text
	  
	  System.out.print("Enter simulation time (positive integer)\t: ");	//simulation time text
	  	simulationTime = input.nextInt();
	  		while (simulationTime > MAX_SIMULATION_LENGTH || simulationTime <= 0){
	  			if (simulationTime > 0 ){
	  				System.out.println("ERROR!!! \n\tMaximum simulation length is \t" + MAX_SIMULATION_LENGTH);
	  			}//end if
	  			else{
	  				System.out.println("ERROR!!! \n\tSimulation length must be a positive integer");
	  			}//end else
	  			System.out.print("Enter simulation time (positive integer)\t: ");
	  			simulationTime = input.nextInt();
	  		}//end while
	  		
	  System.out.print("Enter the number of tellers\t\t\t: ");	//number of tellers text
	  	numTellers = input.nextInt();
	  		while (numTellers > MAX_NUMBER_OF_TELLERS || numTellers <= 0){
	  			if (numTellers > 0 ){
	  				System.out.println("ERROR!!! \n\tMaximum number of tellers is \t" + MAX_NUMBER_OF_TELLERS);
	  			}//end if
	  			else{
	  				System.out.println("ERROR!!! \n\tNumber of tellers must be a positive integer");
	  			}//end else
	  			System.out.print("Enter the number of tellers\t\t\t: ");
	  			numTellers = input.nextInt();
	  		}
	  		
	  System.out.print("Enter chances (0% < & <= 100%) of new customer\t: ");	//chances text
	  	chancesOfArrival = input.nextInt();
	  		while (chancesOfArrival <= 0 || chancesOfArrival > 100){
	  			if (chancesOfArrival > 100){
	  				System.out.println("ERROR!!! \n\t Chances cannot be over 100%");
	  			}//end if
	  			else{
	  				System.out.println("ERROR!!! \n\t Chances must be a positive integer");
	  			}//end else
	  			System.out.print("Enter chances (0% < & <= 100%) of new customer\t: ");
	  			chancesOfArrival = input.nextInt();
	  		}//end while
	  		
	  System.out.print("Enter maximum transaction time of customers\t: ");	//transaction time text
	  	maxTransactionTime = input.nextInt();
	  		while (maxTransactionTime > MAX_TRANSACTION_TIME || maxTransactionTime <= 0){
	  			if (maxTransactionTime > MAX_TRANSACTION_TIME){
	  				System.out.println("ERROR!!! \n\t Maximum transaction time is \t" + MAX_TRANSACTION_TIME);
	  			}//end if
	  			else{
	  				System.out.println("ERROR!!! \n\t Transaction time must be a positive integer");
	  			}//end else
	  			System.out.print("Enter maximum transaction time of customers\t: ");
	  			maxTransactionTime = input.nextInt();
	  		}//end while
	  		
	  System.out.print("Enter customer queue limit\t\t\t: ");	//customer que limit text
	  	customerQLimit = input.nextInt();
	  		while (customerQLimit > MAX_CUSTOMER_QUE_LIMIT || customerQLimit <= 0){
	  			if (customerQLimit > MAX_CUSTOMER_QUE_LIMIT){
	  				System.out.println("ERROR!!! \n\t Maximum customer que limit is \t" + MAX_CUSTOMER_QUE_LIMIT);
	  			}//end if
	  			else {
	  				System.out.println("ERROR!!! \n\t Customer que limit must be a positive integer");
	  			}//end else
	  		System.out.print("Enter customer queue limit\t\t\t: ");
	  		customerQLimit = input.nextInt();
	  		}//end while
	  		
	  // setup dataFile or dataRandom
	  System.out.print("Enter 0/1 to get data from Random/file\t\t: ");	//get data text
	  	dataSource = input.nextInt();
	  		while (dataSource != 0 && dataSource != 1){
	  			System.out.println("ERROR!!! \n\t Data source must be a 0 or 1");
	  			System.out.print("Enter 0/1 to get data from Random/file\t\t: ");
	  			dataSource = input.nextInt();
	  		}//end while
	  		 if (dataSource == 1){
	 			System.out.print("Enter filename\t\t\t\t\t: ");
	 			String fileName = stringInput.nextLine();
	 			while (ExceptionLoopControl){
	 			try{
	 			      dataFile = new Scanner(new File(fileName));
	 			     ExceptionLoopControl = false;
	 			  }//end try
	 			  catch(Exception noFileFound){
	 				  System.out.println("ERROR!!!\n\tCould not find file");
	 				  System.out.print("Enter filename\t\t\t\t\t: ");
	 	 			  fileName = stringInput.nextLine();
	 			  }//end catch
	 			}//end while
	 			stringInput.close();
	  		 }//end if
	  		input.close();
  }//end setupParameters()

  // Refer to step 1 in doSimulation()
  private void getCustomerData()
  {// add statements
	// get next customer data : from file or random number generator
	  // set anyNewArrival and transactionTime
        // see Readme file for more info
	  if (dataSource == 1){
					int data1 = dataFile.nextInt();	
					int data2 = dataFile.nextInt();
					anyNewArrival = (((data1%100)+1)<= chancesOfArrival);
					transactionTime = (data2%maxTransactionTime)+1; 
		}//end if
	  else {
		  anyNewArrival = ((dataRandom.nextInt(100)+1) <= chancesOfArrival);
	      transactionTime = dataRandom.nextInt(maxTransactionTime)+1;
	  }//end else if 
  }//end getCustomerData()

  private void doSimulation()
  {// add statements
	// Initialize ServiceArea
	  servicearea = new ServiceArea(numTellers, customerQLimit);
	  
	  System.out.println("\n");
	  System.out.println("\t***  Start Simulation  ***\n");
	  System.out.println("Teller #1 to #" + numTellers+ " are ready...\n");
	  
	// Time driver simulation loop
  	for (int currentTime = 0; currentTime < simulationTime; currentTime++) {
  			System.out.println("---------------------------------------------");
  			System.out.println("Time : " + currentTime);
    		// Step 1: any new customer enters the bank?
    		getCustomerData();
    		Customer nextCustomer;
    		Teller teller;
    		
    		if (anyNewArrival) {
    			customerIDCounter++;
      		    // Step 1.1: setup customer data
    				nextCustomer = new Customer(customerIDCounter, transactionTime, currentTime );
      		    // Step 1.2: check customer waiting queue too long?
    				if (servicearea.isCustomerQTooLong()){
    					System.out.println("\t customer #" + nextCustomer.getCustomerID() + " goes away due to long line");
    					//           if it is too long, update numGoaway
    									numGoaway++;
    				}//end if
    				else{
    					//           else enter customer queue
    					System.out.println("\tcustomer #" + customerIDCounter + " arrives with transaction time " + nextCustomer.getTransactionTime() + " units");
    					System.out.println("\tcustomer #" + customerIDCounter + " wait in the customer queue");
    					servicearea.insertCustomerQ(nextCustomer);
    				}//end else
    		} else {
      		    System.out.println("\tNo new customer!");
    		}
                // Step 2: free busy tellers that are done at currentTime, add to free cashierQ
    				while(!servicearea.emptyBusyTellerQ() && servicearea.getFrontBusyTellerQ().getEndBusyTime() == currentTime){
    					teller = servicearea.removeBusyTellerQ();
    					teller.busyToFree();
    					servicearea.insertFreeTellerQ(teller);
    					
    					System.out.println("\tcustomer #" + teller.getCustomer().getCustomerID() +" is done");
    					System.out.println("\tteller  #" + teller.getTellerID() + " is free");	
    				}//end while
                // Step 3: get free tellers to serve waiting customers at currentTime
    				while(!servicearea.emptyFreeTellerQ() && !servicearea.emptyCustomerQ()){
    					nextCustomer = servicearea.removeCustomerQ();
    					teller = servicearea.removeFreeTellerQ();		
    					teller.freeToBusy(nextCustomer, currentTime);
    					servicearea.insertBusyTellerQ(teller);
    					
    					
    					System.out.println("\tcustomer #" + nextCustomer.getCustomerID() + " gets a teller");
    					System.out.println("\tteller #" + teller.getTellerID() + " starts serving customer #" + nextCustomer.getCustomerID() + 
    										" for " + nextCustomer.getTransactionTime() + " units");
    					numServed++;
    					totalWaitingTime += (currentTime - nextCustomer.getArrivalTime());
    				}//end while
  	} // end simulation loop
  	// clean-up - close scanner
  	if (dataSource == 1){
  		dataFile.close();
  	}
  	else{
  		dataRandom = null;
  	}
  }

  private void printStatistics()
  {// add statements into this method!
	// print out simulation results
	// see the given example in project statement
	  System.out.println("\n============================================\n\n");
	  System.out.println("End of simulation report\n");
	  System.out.println("\t# total arrival customers  : " + customerIDCounter);
	  System.out.println("\t# customers gone-away      : " + numGoaway);
	  System.out.println("\t# customers served         : " + numServed);
        // you need to display all free and busy gas pumps
	  System.out.println("\n\t*** Current Tellers Info. ***\n\n");
	  servicearea.printStatistics();

	  	// need to free up all customers in queue to get extra waiting time.
	  Customer customerInLine;
	  while (!servicearea.emptyCustomerQ()){
		  customerInLine = servicearea.removeCustomerQ();
		  totalWaitingTime += (simulationTime - customerInLine.getArrivalTime());
	  }
	  System.out.println("\n\tTotal waiting time         : " + totalWaitingTime );
	  System.out.format("\tAverage waiting time       : %.2f%n\n\n", (totalWaitingTime*1.0)/(numServed + servicearea.numWaitingCustomers()));

        // need to free up all tellers in free/busy queues to get extra free & busy time.
	  	Teller teller;
	  		if (!servicearea.emptyBusyTellerQ()){
	  			System.out.println("\tBusy Tellers Info. :\n\n");
	  			while (!servicearea.emptyBusyTellerQ()){
	  				teller = servicearea.removeBusyTellerQ();
	  				teller.setEndBusyTime(simulationTime);
	  				teller.updateTotalBusyTime();
	  				teller.printStatistics();	
	  			}//end while
	  		}else{
	  			System.out.println("\tNo Busy Tellers");
	  		}//end else
	  		if (!servicearea.emptyFreeTellerQ()){
	  		System.out.println("\tFree Tellers Info. :\n\n");
	  			while (!servicearea.emptyFreeTellerQ()){
	  				teller = servicearea.removeFreeTellerQ();
	  				teller.setEndFreeTime(simulationTime);
	  				teller.updateTotalFreeTime();
	  				teller.printStatistics();
	  			}//end while
	  		}else{
	  			System.out.println("\tNo Free Tellers");
	  		}//end else
	  		
  }

  // *** main method to run simulation ****

  public static void main(String[] args) {
   	BankSimulator runBankSimulator=new BankSimulator();
   	runBankSimulator.setupParameters();
   	runBankSimulator.doSimulation();
    runBankSimulator.printStatistics();
  }

}
