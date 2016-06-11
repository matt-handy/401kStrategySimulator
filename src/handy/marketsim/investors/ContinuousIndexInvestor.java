package handy.marketsim.investors;

import java.util.List;

import handy.marketsim.Constants;
import handy.marketsim.MarketPoint;

public class ContinuousIndexInvestor extends IndexInvestor {

	public ContinuousIndexInvestor (int investmentCashEachPeriod){
		this.investmentCashEachPeriod = investmentCashEachPeriod;
		this.name = "Continuous Contributor";
	}
	
	public void processPoint(List<MarketPoint> mps, int pointIdx) {
		double currentPrice = mps.get(pointIdx).getPrice();
		while(cash - currentPrice > 0){
			if(Constants.DEBUG){
				System.out.println(name + " says, I'm buying! I have " + shares + " shares and $" + cash);
			}
			shares++;
			cash -= currentPrice;
		}
	}

}
