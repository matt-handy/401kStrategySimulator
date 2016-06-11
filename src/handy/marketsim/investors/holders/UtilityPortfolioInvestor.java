package handy.marketsim.investors.holders;

import handy.marketsim.Position;

public class UtilityPortfolioInvestor extends HoldPortfolioInvestor {
	public UtilityPortfolioInvestor(double cash){
		super(cash);
		positions.add(new Position("VZ", 25.0f));
		positions.add(new Position("T", 25.0f));
		positions.add(new Position("EXC", 25.0f));
		positions.add(new Position("DUK", 25.0f));
	}

}
