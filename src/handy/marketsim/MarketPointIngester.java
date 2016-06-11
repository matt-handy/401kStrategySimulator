package handy.marketsim;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MarketPointIngester {

	public static List<MarketPoint> getPoints(String filename, int rowStart, int dateColumn, int priceColumn, String format) {
		List<MarketPoint> points = new ArrayList<MarketPoint>();
		SimpleDateFormat formatter = new SimpleDateFormat(format);

		if(Constants.DEBUG){
			System.out.println("Ingesting: " + filename);
		}
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			int idx = 0;
			while ((line = br.readLine()) != null) {
				if (idx > rowStart) {
					String elements[] = line.split(",");
					MarketPoint mp = new MarketPoint(formatter.parse(elements[dateColumn]),
							Double.parseDouble(elements[priceColumn]));
					points.add(mp);
				}
				idx++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		Collections.sort(points);
		
		return points;
	}
}
