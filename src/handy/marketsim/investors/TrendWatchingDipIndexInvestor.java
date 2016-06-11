package handy.marketsim.investors;

import java.util.List;

import handy.marketsim.Constants;
import handy.marketsim.MarketPoint;

public class TrendWatchingDipIndexInvestor extends IndexInvestor {

	private double dipPercentageThreshold;
	private int dipDayWindow;
	private int bottomingPeriod;

	public TrendWatchingDipIndexInvestor(int investmentCashEachPeriod, double dipPercentageThreshold, int dipDayWindow,
			int bottomingPeriod) {
		this.investmentCashEachPeriod = investmentCashEachPeriod;
		this.name = "Trend Watching Dip Investor";
		this.dipPercentageThreshold = dipPercentageThreshold;
		this.dipDayWindow = dipDayWindow;
		this.bottomingPeriod = bottomingPeriod;
	}

	public void processPoint(List<MarketPoint> mps, int pointIdx) {
		double currentPrice = mps.get(pointIdx).getPrice();

		if (pointIdx > dipDayWindow) {
			double previousPriceReference = mps.get(pointIdx - dipDayWindow).getPrice();
			double bottomWindowReference = mps.get(pointIdx - bottomingPeriod).getPrice();
			double delta = (currentPrice - previousPriceReference) / previousPriceReference;
			double bottomingDelta = (currentPrice - bottomWindowReference) / bottomWindowReference;
			
			if (delta < 0 && //Market move is negative
					dipPercentageThreshold < Math.abs(delta) && //Market has fallen enough
					Math.abs(bottomingDelta) < (1 - .05)) { //Yet stabilized in the near term
				while (cash - currentPrice > 0) {
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
