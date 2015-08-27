package handy.marketsim;

import java.util.Date;

public class MarketPoint implements Comparable<Object>{

	private Date date;
	private double price;
	
	public MarketPoint(Date date, double price){
		this.date = date;
		this.price = price;
	}
	
	public Date getDate(){
		return date;
	}
	
	public double getPrice(){
		return price;
	}

	public int compareTo(Object arg0) {
		if(arg0 instanceof MarketPoint){
			MarketPoint other = (MarketPoint) arg0;
			return this.date.compareTo(other.date);
		}else{
			return -1000;
		}
	}
	
	public String toString(){
		return date.toString() + " " + price; 
	}
}
