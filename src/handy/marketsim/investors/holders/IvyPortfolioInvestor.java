package handy.marketsim.investors.holders;

import handy.marketsim.Position;

public class IvyPortfolioInvestor extends HoldPortfolioInvestor {
	public IvyPortfolioInvestor(double cash){
		super(cash);
		positions.add(new Position("VTI", 5.0f));
		positions.add(new Position("VO", 5.0f));
		positions.add(new Position("VB", 5.0f));
		positions.add(new Position("IWC", 5.0f));
		positions.add(new Position("VEU", 5.0f));
		positions.add(new Position("VWO", 5.0f));
		positions.add(new Position("GWX", 5.0f));
		positions.add(new Position("EWX", 5.0f));
		positions.add(new Position("BND", 5.0f));
		positions.add(new Position("TIP", 5.0f));
		positions.add(new Position("BWX", 5.0f));
		positions.add(new Position("ESD", 5.0f));
		positions.add(new Position("VNQ", 5.0f));
		positions.add(new Position("RWX", 5.0f));
		positions.add(new Position("IGF", 5.0f));
		positions.add(new Position("WOOD", 5.0f));
		positions.add(new Position("DBA", 5.0f));
		positions.add(new Position("DBE", 5.0f));
		positions.add(new Position("DBB", 5.0f));
		positions.add(new Position("DBP", 5.0f));
	}

}
