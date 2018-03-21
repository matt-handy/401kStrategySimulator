package handy.marketsim.investors;

import java.util.ArrayList;
import java.util.List;

import handy.marketsim.Constants;
import handy.marketsim.MarketPoint;

public class SimpleVolatilityInvestor extends IndexInvestor{

	//Strategy is to buy asset at X% dip and sell at Y% up, with
	//Z day intervals
	private double currentMax;
	private double currentMin;
	
	private double dipThreshold;
	private double sellThreshold;
	
	private int daysInSeries; //How many day window should we look at?
	
	private List<MarketPoint> samples = new ArrayList<MarketPoint>();
	
	public SimpleVolatilityInvestor(int daysInSeries, double dipThreshold,
			double sellThreshold){
		this.daysInSeries = daysInSeries;
		this.dipThreshold = dipThreshold;
		this.sellThreshold = sellThreshold;
		
		this.name = "Simple Volatility Investor";
		
		this.cash = 100000;
	}
	
	public void processPoint(List<MarketPoint> mps, int pointIdx) {
		MarketPoint currentSample = mps.get(pointIdx);
		double currentPrice = currentSample.getPrice();
		
		if(samples.size() == 0){
			if(Constants.DEBUG){
				System.out.println("Current Min: " + currentMin);
				System.out.println("Current Max: " + currentMax);
				System.out.println("Current Price: " + currentPrice);
			}
			samples.add(mps.get(pointIdx));
			currentMax = currentPrice;
			currentMin = currentPrice;
		}else{
			if(Constants.DEBUG){
				System.out.println("Current Min: " + currentMin);
				System.out.println("Current Max: " + currentMax);
				System.out.println("Current Price: " + currentPrice);
			}
			
			if(currentPrice < currentMin){
				if(Math.abs((currentMax - currentPrice / currentMax)) >= dipThreshold){
					if(Constants.DEBUG){
						System.out.println("Buying: current price: " + currentPrice + " vs recent Max: " + currentMax);
					}
					while (cash - currentPrice > 0) {
						shares++;
						cash -= currentPrice;
					}
				}
			}
			
			if(currentPrice > currentMax){
				if(((currentPrice - currentMin) / currentMin) >= sellThreshold){
					if(Constants.DEBUG){
						System.out.println("Selling: current price: " + currentPrice + " vs recent Max: " + currentMax);
					}
					cash += (currentPrice * shares);
					shares = 0;
				}
			}
				
			if(samples.size() == daysInSeries){
				samples.remove(0);
				samples.add(currentSample);
			}else{
				samples.add(currentSample);
			}
			
			
			currentMax = -1;
			currentMin = Double.MAX_VALUE;
			for(MarketPoint mp : samples){
				double price = mp.getPrice();
				if(price < currentMin){
					currentMin = price;
				}
				
				if(price > currentMax){
					currentMax = price;
				}
			}
		}
	}

}
