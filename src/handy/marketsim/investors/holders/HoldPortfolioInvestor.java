package handy.marketsim.investors.holders;

import java.util.Date;

import handy.marketsim.investors.PortfolioInvestor;

public class HoldPortfolioInvestor extends PortfolioInvestor {

	public HoldPortfolioInvestor(double cash) {
		super(cash);
	}

	public void checkIfSidelinePosition(Date date) {
		//Do nothing, we just hold stuff
	}

}
