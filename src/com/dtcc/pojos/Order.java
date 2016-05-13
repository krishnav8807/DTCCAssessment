package com.dtcc.pojos;

/**
 * Order class
 * @author Krishna
 *
 */
public class Order {

	private int orderNo;
	private String state;

	public Order( int orderNo){
		state = "NEW";
		this.orderNo = orderNo;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
