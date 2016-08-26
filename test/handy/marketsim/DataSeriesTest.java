package handy.marketsim;

import java.util.Calendar;
import java.util.Date;

import handy.marketsim.drivers.PortfolioSimulator;
import junit.framework.TestCase;

public class DataSeriesTest extends TestCase {

	public void testDataSeriesMinAndMax(){
		assertTrue(PortfolioSimulator.buildSecuritiesDataset());
		
		DataSeries slvSeries = new DataSeries("SLV", 20);
		
		for(AnnotatedMarketPoint amp : slvSeries.getMinAndMaxSet()){
			System.out.println(amp);
		}

		Calendar c = Calendar.getInstance();
		c.set(2006, 4, 11, 0, 0, 0);
		MarketPoint sampleTestPoint1 = new MarketPoint(c.getTime(), 14.86);
		c.set(2006, 5, 13, 0, 0, 0);
		MarketPoint sampleTestPoint2 = new MarketPoint(c.getTime(), 9.62);
		c.set(2006, 8, 6, 0, 0, 0);
		MarketPoint sampleTestPoint3 = new MarketPoint(c.getTime(), 12.99);
		c.set(2006, 8, 14, 0, 0, 0);
		MarketPoint sampleTestPoint4 = new MarketPoint(c.getTime(), 10.7);
		c.set(2006, 11, 4, 0, 0, 0);
		MarketPoint sampleTestPoint5 = new MarketPoint(c.getTime(), 14.08);
		
		assertTrue(slvSeries.getMinAndMaxSet().size() >= 50);
		assertTrue((slvSeries.getMinAndMaxSet().get(0).getDate().getTime() / 1000)
					== (sampleTestPoint1.getDate().getTime() / 1000));
		assertTrue((slvSeries.getMinAndMaxSet().get(1).getDate().getTime() / 1000)
				== (sampleTestPoint2.getDate().getTime() / 1000));
		assertTrue((slvSeries.getMinAndMaxSet().get(2).getDate().getTime() / 1000)
				== (sampleTestPoint3.getDate().getTime() / 1000));
		assertTrue((slvSeries.getMinAndMaxSet().get(3).getDate().getTime() / 1000)
				== (sampleTestPoint4.getDate().getTime() / 1000));
		assertTrue((slvSeries.getMinAndMaxSet().get(4).getDate().getTime() / 1000)
				== (sampleTestPoint5.getDate().getTime() / 1000));
		assertTrue(slvSeries.getMinAndMaxSet().get(0).getPrice() == sampleTestPoint1.getPrice());
		assertTrue(slvSeries.getMinAndMaxSet().get(1).getPrice() == sampleTestPoint2.getPrice());
		assertTrue(slvSeries.getMinAndMaxSet().get(2).getPrice() == sampleTestPoint3.getPrice());
		assertTrue(slvSeries.getMinAndMaxSet().get(3).getPrice() == sampleTestPoint4.getPrice());
		assertTrue(slvSeries.getMinAndMaxSet().get(4).getPrice() == sampleTestPoint5.getPrice());
	}
}
