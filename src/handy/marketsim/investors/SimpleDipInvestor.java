package handy.marketsim.investors;

import java.util.List;

import handy.marketsim.Constants;
import handy.marketsim.MarketPoint;

public class SimpleDipInvestor extends Investor{

	private double dipPercentageThreshold;
	private int dipDayWindow;
	
	public SimpleDipInvestor(int investmentCashEachPeriod, double dipPercentageThreshold, int dipDayWindow){
		this.investmentCashEachPeriod = investmentCashEachPeriod;
		this.name = "Simple Dip Investor";
		this.dipPercentageThreshold = dipPercentageThreshold;
		this.dipDayWindow = dipDayWindow;
	}
	
	public void processPoint(List<MarketPoint> mps, int pointIdx) {
		double currentPrice = mps.get(pointIdx).getPrice();
		
		if(pointIdx > dipDayWindow){
			double previousPriceReference = mps.get(pointIdx - dipDayWindow).getPrice();
			double delta = (currentPrice - previousPriceReference) / previousPriceReference;
			
			if(delta < 0 && 
					dipPercentageThreshold < Math.abs(delta)){
				while(cash - currentPrice > 0){
					if(Constants.DEBUG){
						System.out.println("Ref price: " + previousPriceReference);
						System.out.println(name + " says delta is " + delta);
						System.out.println(name + " says, I'm buying! I have " + shares + " shares and $" + cash);
					}
					
					shares++;
					cash -= currentPrice;
				}
			}
		}
		
	}

}
