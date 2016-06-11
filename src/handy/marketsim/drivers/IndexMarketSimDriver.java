package handy.marketsim.drivers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import handy.marketsim.Constants;
import handy.marketsim.MarketPointIngester;
import handy.marketsim.MarketPoint;
import handy.marketsim.investors.ContinuousIndexInvestor;
import handy.marketsim.investors.IndexInvestor;
import handy.marketsim.investors.SimpleDipIndexInvestor;
import handy.marketsim.investors.TrendWatchingDipIndexInvestor;

public class IndexMarketSimDriver {

	public static void main(String[] args) {
		List<MarketPoint> points = MarketPointIngester.getPoints("table.csv", 1, 0, 6, "yyyy-MM-dd");
		MarketPoint lastPoint = null;
		IndexInvestor continuous = new ContinuousIndexInvestor(300);
		//Set 10% as dip over 90 days threshold
		IndexInvestor simpleDip = new SimpleDipIndexInvestor(300, .1, 90); 
		//Buy more at 30% dip over 90 days, but stabilize to less than 5% variation over the last 10 days
		IndexInvestor trendDip = new TrendWatchingDipIndexInvestor(300, .3, 90, 10); 
		List<IndexInvestor> investors = new ArrayList<IndexInvestor>();
		investors.add(continuous);
		investors.add(simpleDip);
		investors.add(trendDip);
		
		
		if(points != null){
			Calendar start = Calendar.getInstance();
			start.setTime(points.get(0).getDate());
			Calendar end = Calendar.getInstance();
			end.setTime(points.get(points.size() - 1).getDate());
			Calendar nextPayDay = Calendar.getInstance();
			nextPayDay.setTime(points.get(0).getDate());

			for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
			    if(Constants.DEBUG){
			    	System.out.println(date);
			    }
				
				if(date.equals(nextPayDay.getTime())){
					if(Constants.DEBUG){
						System.out.println("Payday, making contributions");
					}
					 
			    	for(IndexInvestor investor : investors){
			    		investor.makeBiweeklyContribution();
			    	}
			    	//Get paid 14 days from now
			    	nextPayDay.add(Calendar.DATE, 14);
			    }
				
				int matchPointIdx = -1;
				for(int idx = 0; idx < points.size(); idx++){
			    	if(date.equals(points.get(idx).getDate())){
			    		matchPointIdx = idx;
			    	}
			    }
				
				if(matchPointIdx != -1){
					lastPoint = points.get(matchPointIdx);
					if(Constants.DEBUG){
						System.out.println("Market day, we closed at: " + lastPoint.getPrice());
					}
					
					for(IndexInvestor investor: investors){
						investor.processPoint(points, matchPointIdx);
					}
				}
			}
					
			
			System.out.println("Simulation complete");
			
			for(IndexInvestor investor : investors){
				System.out.printf("%s total cash invested: $%.2f\n", investor.getName(), investor.getTotalCashInvested());
				System.out.printf("Final investment worth: $%.2f\n", (investor.getShares() * lastPoint.getPrice()));
				System.out.printf("Final cash holding: $%.2f\n", investor.getCash());
			}
			
		}else{
			System.out.println("Unable to parse file");
		}
	}

}
