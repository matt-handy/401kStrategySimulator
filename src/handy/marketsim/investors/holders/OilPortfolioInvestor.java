package handy.marketsim.investors.holders;

import handy.marketsim.Position;

public class OilPortfolioInvestor extends HoldPortfolioInvestor {
	public OilPortfolioInvestor(double cash){
		super(cash);
		positions.add(new Position("XOM", 25.0f));
		positions.add(new Position("CVX", 25.0f));
		positions.add(new Position("BP", 25.0f));
		positions.add(new Position("MPC", 25.0f));
	}

}
