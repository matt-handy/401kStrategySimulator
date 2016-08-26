package handy.marketsim;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import handy.marketsim.AnnotatedMarketPoint.ANNOTATED_POINT_TYPE;

public class DataSeries {
	List<MarketPoint> priceHistory;
	
	List<AnnotatedMarketPoint> minAndMaxSet = new ArrayList<AnnotatedMarketPoint>();
	int minMaxWindow;
	
	public DataSeries(String stockTicker, int minMaxWindow){
		this.priceHistory = MarketPointIngester.getPoints("securities\\" + stockTicker + "_PRICE.csv", 1, 0, 4, "dd-MMM-yy");
		this.minMaxWindow = minMaxWindow;
		
		detectLocalMinimaAndMaxima();
	}
	
	private void detectLocalMinimaAndMaxima(){
		for(int currentIdx = 0; currentIdx < priceHistory.size(); currentIdx++){
			Set<MarketPoint> adjacentPoints = new HashSet<MarketPoint>();
			for(int adjIdx = currentIdx - minMaxWindow; adjIdx <= currentIdx + minMaxWindow; adjIdx++){
				//Grab all data points within the window provided, except the current
				//point to be looked at
				if(adjIdx >= 0 && adjIdx < priceHistory.size() &&
						adjIdx != currentIdx){
					adjacentPoints.add(priceHistory.get(adjIdx));
				}
			}
			
			MarketPoint currentPoint = priceHistory.get(currentIdx);
			//iterate over all adjacent points. if they are all lower, this is 
			//a max. if all higher, this is a min
			boolean seenHigher = false;
			boolean seenLower = false;		
			for(MarketPoint mp : adjacentPoints){
				if(mp.getPrice() < currentPoint.getPrice()){
					seenLower = true;
				}
				if(mp.getPrice() > currentPoint.getPrice()){
					seenHigher = true;
				}
			}
			
			if(!seenHigher){
				minAndMaxSet.add(new AnnotatedMarketPoint(ANNOTATED_POINT_TYPE.MAXIMUM, currentPoint));
			}
			
			if(!seenLower){
				minAndMaxSet.add(new AnnotatedMarketPoint(ANNOTATED_POINT_TYPE.MINIMUM, currentPoint));
			}
		}
	}
	
	public List<AnnotatedMarketPoint> getMinAndMaxSet(){
		return minAndMaxSet;
	}
}
