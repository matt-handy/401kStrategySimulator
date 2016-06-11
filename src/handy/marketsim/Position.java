package handy.marketsim;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Position {
	public String securityTicker;
	public float targetPercentage;
	public List<MarketPoint> priceHistory;
	public List<MarketPoint> dividendHistory;
	public int numberOwned = 0;
	public double sidelinedCash = 0;
	public boolean isSidelining = false;
	
	private List<MarketPoint> tenMonthSMA = new ArrayList<MarketPoint>();
	private List<MarketPoint> oneMonthSMA = new ArrayList<MarketPoint>();
	
	
	public Position(String securityTicker, float targetPercentage){
		this.securityTicker = securityTicker;
		this.targetPercentage = targetPercentage;
		this.priceHistory = MarketPointIngester.getPoints("securities\\" + securityTicker + "_PRICE.csv", 1, 0, 4, "dd-MMM-yy");
		this.dividendHistory = MarketPointIngester.getPoints("securities\\" + securityTicker + "_DIV.csv", 1, 0, 1, "YYYY-MM-dd");
		
		List<Double> tenMonthSamples = new ArrayList<Double>();
		List<Double> oneMonthSamples = new ArrayList<Double>();
		for(MarketPoint mp : this.priceHistory){
			tenMonthSamples.add(mp.getPrice());
			oneMonthSamples.add(mp.getPrice());
			
			while(tenMonthSamples.size() > 200)
				tenMonthSamples.remove(0);
			while(oneMonthSamples.size() > 20)
				oneMonthSamples.remove(0);
			
			tenMonthSMA.add(new MarketPoint(mp.getDate(), MathHelper.getAverage(tenMonthSamples)));
			oneMonthSMA.add(new MarketPoint(mp.getDate(), MathHelper.getAverage(oneMonthSamples)));
		}
	}
	
	public double getDividendOnDate(Date onDate){
		for(MarketPoint mp : dividendHistory){
			if(mp.getDate().equals(onDate)){
				return mp.getPrice();
			}else if(mp.getDate().after(onDate)){
				return 0;
			}
		}
		return 0;
	}
	
	public double getPriceOnOrAfterDate(Date targetDate) throws Exception{
		for(MarketPoint mp : priceHistory){
			if(mp.getDate().equals(targetDate) || mp.getDate().after(targetDate)){
				return mp.getPrice();
			}
		}
		throw new Exception("Position does not have a price on or after the oldest common price date");
	}
	
	public double getPositionValueOnDate(Date onDate) throws Exception{
		return getPriceOnOrAfterDate(onDate) * numberOwned + sidelinedCash;
	}
	
	public void invest(double cash, double price){
		if(isSidelining){
			sidelinedCash += cash;
		}else{
			//Assume cash is a multiple of price
			numberOwned += cash / price;
		}
	}
	
	public boolean oneMonthAboveTenMonthSMA(Date onDate) throws Exception{
		double oneMonthSMA = -1;
		for(MarketPoint mp : this.oneMonthSMA){
			if(onDate.equals(mp.getDate()) || 
					onDate.after(mp.getDate())){
				oneMonthSMA = mp.getPrice();
			}
		}
		
		double tenMonthSMA = -1;
		for(MarketPoint mp : this.tenMonthSMA){
			if(onDate.equals(mp.getDate()) || 
					onDate.after(mp.getDate())){
				tenMonthSMA = mp.getPrice();
			}
		}
		
		if(oneMonthSMA == -1 || tenMonthSMA == -1)
			throw new Exception("Not an acceptable date");
		
		//Add in small buffer so 20 day SMA has an edge at low sample rates
		return (oneMonthSMA + 0.00001) > tenMonthSMA;
	}
}
