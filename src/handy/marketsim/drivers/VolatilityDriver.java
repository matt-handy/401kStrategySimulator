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
import handy.marketsim.investors.SimpleVolatilityInvestor;
import handy.marketsim.investors.TrendWatchingDipIndexInvestor;

public class VolatilityDriver {

	public static void main(String[] args) {
		List<MarketPoint> points = MarketPointIngester.getPoints("btcusd.csv", 1, 0, 1, "MM/dd/yyyy");
		MarketPoint lastPoint = null;
		IndexInvestor simpleVol = new SimpleVolatilityInvestor(30, .1, .15);
		 
		List<IndexInvestor> investors = new ArrayList<IndexInvestor>();
		investors.add(simpleVol);
		
		
		if(points != null){
			Calendar start = Calendar.getInstance();
			start.setTime(points.get(0).getDate());
			Calendar end = Calendar.getInstance();
			end.setTime(points.get(points.size() - 1).getDate());
			
			for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
			    if(Constants.DEBUG){
			    	System.out.println(date);
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
