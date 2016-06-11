package handy.marketsim.investors;

import java.util.List;

import handy.marketsim.MarketPoint;

public abstract class IndexInvestor {
	protected int investmentCashEachPeriod;
	protected double totalCashInvested = 0;
	protected double cash = 0;
	protected int shares = 0;
	protected String name;
	
	public void makeBiweeklyContribution() {
		cash += investmentCashEachPeriod;
		totalCashInvested += investmentCashEachPeriod;
	}
	
	public abstract void processPoint(List<MarketPoint> mps, int pointIdx);
	
	public String getName(){
		return name;
	}
	
	public double getCash(){
		return cash;
	}
	
	public int getShares(){
		return shares;
	}
	
	public double getTotalCashInvested(){
		return totalCashInvested;
	}
	
}
