package handy.marketsim.investors.holders;

import handy.marketsim.Position;

public class AutoPortfolioInvestor extends HoldPortfolioInvestor {
	public AutoPortfolioInvestor(double cash){
		super(cash);
		positions.add(new Position("F", 100.0f));
	}

}
