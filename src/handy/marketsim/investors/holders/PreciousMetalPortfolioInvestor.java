package handy.marketsim.investors.holders;

import handy.marketsim.Position;

public class PreciousMetalPortfolioInvestor extends HoldPortfolioInvestor {
	public PreciousMetalPortfolioInvestor(double cash){
		super(cash);
		positions.add(new Position("GLD", 50.0f));
		positions.add(new Position("SLV", 50.0f));
	}

}
