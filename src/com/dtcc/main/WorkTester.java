package com.dtcc.main;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.dtcc.pojos.Order;

/**
 * WorkTester Class which places the created orders in the queue and starts processing orders.
 * @author Krishna
 *
 */
public class WorkTester {

	public static void main(String[] argv) throws Exception {

		int size = 5;
		int NUM_OF_ORDERS = 10;
		final BlockingQueue<Order> bQueue = new ArrayBlockingQueue<Order>(size); 	// ArrayBlockingQueue of size 5

		ExecutorService threadPool = Executors.newFixedThreadPool(NUM_OF_ORDERS);

		for (int i = 0; i < NUM_OF_ORDERS; i++) {
			final int threadNum = i;
			threadPool.submit(new Runnable() {	
				public void run() {
					Order o = new Order(threadNum);					// Order Creation 
					try {
						bQueue.put(o);	
						//	Thread.sleep(10);						// Assuming creation of orders takes some time
						System.out.println("Order " + threadNum + " created. Order Status: "+ o.getState());
						Worker aWorker = new Worker();	
						aWorker.setVal(bQueue);						// Pass BlockingQueue reference to worker class
						aWorker.start();							// Starting worker thread

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}

		threadPool.shutdown();
		threadPool.awaitTermination(1, TimeUnit.MILLISECONDS);	// Choose the awaitTermination time appropriately based on Order creation time
																// 	Increase the awaitTermination time if order creation time is taking more time
		//		System.out.println("count 1:"+Thread.activeCount());
		Thread.sleep(2);										// Choose the sleep time for main thread appropriately based on order processing time
																// Increase the sleep time if order processing time is taking more time
		//		System.out.println("count 2:"+Thread.activeCount());
		if(bQueue.isEmpty()){
			System.out.println("****All orders are processed Succesfully!!!****");
		}

	}
}

/**
 * Worker class which processes orders.
 * @author Krishna
 *
 */

class Worker extends Thread {		
	BlockingQueue<Order> val;

	public BlockingQueue<Order> getVal() {
		return val;
	}

	public void setVal(BlockingQueue<Order> val) {
		this.val = val;
	}

	@Override
	public void run() {
		try {
			Order order = getVal().take();	
			//			Thread.sleep(10);			// Assuming processing of orders takes some time
			order.setState("FULFILLED");
			System.out.println("Updated Order Status:" + order.getState() + " of OrderNo:" + order.getOrderNo());
		} catch (InterruptedException ex) {
		}
	}
}