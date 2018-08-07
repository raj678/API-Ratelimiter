package com.rt.db;

public class OrderDAO /* implements interface and contains required methods for persistence*/{
	//For simplicity making this as static
	public static String readOrder(String orderId) {
		return "Order details: "+orderId;
	}

}
