package handy.marketsim.investors.holders;

import handy.marketsim.Position;

public class SimpleIvyPortfolioInvestor extends HoldPortfolioInvestor {
	public SimpleIvyPortfolioInvestor(double cash){
		super(cash);
		positions.add(new Position("VTI", 10.0f));
		positions.add(new Position("VB", 10.0f));
		positions.add(new Position("VEU", 10.0f));
		positions.add(new Position("VWO", 10.0f));
		positions.add(new Position("BND", 10.0f));
		positions.add(new Position("TIP", 10.0f));
		positions.add(new Position("VNQ", 10.0f));
		positions.add(new Position("RWX", 10.0f));
		positions.add(new Position("DBC", 10.0f));
		positions.add(new Position("GSG", 10.0f));
	}

}
