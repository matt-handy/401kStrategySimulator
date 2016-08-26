package handy.marketsim.investors;

import java.util.Date;

import handy.marketsim.Constants;
import handy.marketsim.Position;

public class TimingIvyPortfolioInvestor extends PortfolioInvestor {
	public TimingIvyPortfolioInvestor(double cash){
		super(cash);
		positions.add(new Position("VTI", 5.0f));
		positions.add(new Position("VO", 5.0f));
		positions.add(new Position("VB", 5.0f));
		positions.add(new Position("IWC", 5.0f));
		positions.add(new Position("VEU", 5.0f));
		positions.add(new Position("VWO", 5.0f));
		positions.add(new Position("GWX", 5.0f));
		positions.add(new Position("EWX", 5.0f));
		positions.add(new Position("BND", 5.0f));
		positions.add(new Position("TIP", 5.0f));
		positions.add(new Position("BWX", 5.0f));
		positions.add(new Position("ESD", 5.0f));
		positions.add(new Position("VNQ", 5.0f));
		positions.add(new Position("RWX", 5.0f));
		positions.add(new Position("IGF", 5.0f));
		positions.add(new Position("WOOD", 5.0f));
		positions.add(new Position("DBA", 5.0f));
		positions.add(new Position("DBE", 5.0f));
		positions.add(new Position("DBB", 5.0f));
		positions.add(new Position("DBP", 5.0f));
	}

	public void checkIfSidelinePosition(Date date) {
		for(Position p : positions){
			try{
				double currentPrice = p.getPriceOnOrAfterDate(date);
				if(p.isSidelining && p.oneMonthAboveTenMonthSMA(date)){
					p.isSidelining = false;
					p.numberOwned += p.sidelinedCash / currentPrice;
					p.sidelinedCash -= p.numberOwned * currentPrice;
					if(Constants.DEBUG){
						System.out.println("Back in the game: " + p.securityTicker + " " + date);
					}
				}else if(!p.isSidelining && !p.oneMonthAboveTenMonthSMA(date)){
					p.isSidelining = true;
					p.sidelinedCash += p.numberOwned * currentPrice;
					p.numberOwned = 0;
					if(Constants.DEBUG){
						System.out.println("Sidlining!: " + p.securityTicker + " " + date);
					}
				}
			}catch(Exception e){
				//Continue and process another security
				e.printStackTrace();
			}
		}
	}

	
}
