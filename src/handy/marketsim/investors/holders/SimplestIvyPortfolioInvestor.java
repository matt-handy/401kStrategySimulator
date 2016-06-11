package handy.marketsim.investors.holders;

import handy.marketsim.Position;

public class SimplestIvyPortfolioInvestor extends HoldPortfolioInvestor {
	public SimplestIvyPortfolioInvestor(double cash){
		super(cash);
		positions.add(new Position("VTI", 20.0f));
		positions.add(new Position("VEU", 20.0f));
		positions.add(new Position("BND", 20.0f));
		positions.add(new Position("VNQ", 20.0f));
		positions.add(new Position("DBC", 20.0f));
	}

}
