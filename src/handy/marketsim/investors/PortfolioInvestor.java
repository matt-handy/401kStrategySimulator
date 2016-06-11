package handy.marketsim.investors;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import handy.marketsim.Constants;
import handy.marketsim.MarketPoint;
import handy.marketsim.Position;

public abstract class PortfolioInvestor {
	protected List<Position> positions = new ArrayList<Position>();
	protected double cash;
	
	public PortfolioInvestor(double cash){
		this.cash = cash;
	}
	
	
	public Date getOldestCommonPriceDate(){
		Date ocpd = null;
		for (Position position : positions){
			if(ocpd == null ||
					position.priceHistory.get(0).getDate().after(ocpd)){
				ocpd = position.priceHistory.get(0).getDate();
			}
		}
		return ocpd;
	}
	
	public Date getFinalPriceDate() throws Exception{
		Date fpd = null;
		for(Position position : positions){
			if(fpd == null){
				fpd = position.priceHistory.get(position.priceHistory.size() - 1).getDate();
			}
			if(!fpd.equals(position.priceHistory.get(position.priceHistory.size() - 1).getDate())){
				throw new Exception("Securities do not end on the same date");
			}
		}
		return fpd;
	}
	
	public double runSum() throws Exception {
		Date firstDate = getOldestCommonPriceDate();
		Date endDate = getFinalPriceDate();
		Calendar start = Calendar.getInstance();
		start.setTime(firstDate);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		
		System.out.println("Starting worth: " + cash);
		double startValue = cash;
		System.out.println("Starting date: " + getOldestCommonPriceDate());
		
		distributeCashFromZero(getOldestCommonPriceDate());
		
		for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
			reapDividendsOnDate(date);
			
			try{
				investAvailableCash(date);
			}catch(Exception e){
				e.printStackTrace();
			}

			checkIfSidelinePosition(date);
		}
		
		double endValue = getPortfolioValueAtEnd();
		System.out.println("Ending worth: " + endValue);
		System.out.println("Ending date: " + getFinalPriceDate());
		double yearFraction = ((endDate.getTime() - firstDate.getTime()) / 1000) / (365 * 24 * 60 * 60);
		double totalPercentChange = ((endValue - startValue) / startValue) * 100;
	    System.out.println("Total Percent Change: " + totalPercentChange);
	    System.out.println("Annual Percent Change: " + totalPercentChange / yearFraction);
		return endValue;
	}
	
	public abstract void checkIfSidelinePosition(Date date);
	
	public void distributeCashFromZero(Date onDate){
		double initialCash = cash;
		for(Position p : positions){
			try{
				double availableCash = initialCash * (p.targetPercentage / 100f);
				double price = p.getPriceOnOrAfterDate(onDate);
				int numberToBuy = (int) (availableCash / price); 
				p.numberOwned = numberToBuy;
				cash -= (numberToBuy * price);
			}catch(Exception e){
				System.out.println("Unable to distribute to position: " + p.securityTicker);
				e.printStackTrace();
			}
		}
	}
	
	public double getTotalWorthOnDay(Date onDate) throws Exception{
		double totalValue = cash;
		for(Position p : positions){
			totalValue += p.getPositionValueOnDate(onDate);
		}
		return totalValue;
	}
	
	public void investAvailableCash(Date onDate) throws Exception{
		double totalWorth = getTotalWorthOnDay(onDate);
		for(Position p : positions){
			try{
				double currentPrice = p.getPriceOnOrAfterDate(onDate);
				double targetPositionValue = totalWorth * (p.targetPercentage / 100f);
				double actualPositionValue = p.getPositionValueOnDate(onDate);
				int securitiesToBuy = (int) ((targetPositionValue - actualPositionValue) / currentPrice);
				
				if(securitiesToBuy > 0){
					
					if((securitiesToBuy * currentPrice) < cash){
						double investment = securitiesToBuy * currentPrice;
						p.invest(investment, currentPrice);
						cash -= investment;
						if(Constants.DEBUG){
							System.out.println("Buying " + securitiesToBuy + " of " + p.securityTicker + " at " + currentPrice + 
									" on " + onDate);
						}
					}else{
						int possibleBuys = (int) (cash / currentPrice);
						if(possibleBuys > 0 && (possibleBuys * currentPrice) < cash){
							double investment = possibleBuys * currentPrice;
							p.invest(investment, currentPrice);
							cash -= investment;
							if(Constants.DEBUG){
								System.out.println("I want to buy: " + securitiesToBuy + " of " + p.securityTicker);
								System.out.println("Reduced buying " + possibleBuys + " of " + p.securityTicker + " at " + currentPrice + 
										" on " + onDate);
							}
						}else{
							if(Constants.DEBUG){
								System.out.println("I want to buy: " + securitiesToBuy + " of " + p.securityTicker);
							}
						}
					}
				}
			}catch (Exception e){
				e.printStackTrace();
				throw new Exception("Unable to invest on security: " + p.securityTicker);
			}
		}
	}
	
	public void reapDividendsOnDate(Date onDate){
		for(Position p : positions){
			cash += p.getDividendOnDate(onDate) * p.numberOwned;
		}
	}
	
	public double getPortfolioValueAtEnd(){
		try {
			return getTotalWorthOnDay(getFinalPriceDate());
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
