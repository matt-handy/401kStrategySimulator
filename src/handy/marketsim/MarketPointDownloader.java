package handy.marketsim;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class MarketPointDownloader {
	public static void downloadStock(String ticker) throws IOException{
		downloadPrices(ticker, ticker + "_PRICE.csv");
		downloadDividends(ticker, ticker + "_DIV.csv");
	}
	
	public static void downloadPrices(String ticker, String filename) throws IOException {
		String template = "http://www.google.com/finance/historical?q=" + ticker + "&startdate=Sep+10%2C+1975&num=25000&output=csv";
		download(template, filename);
	}
	
	public static void downloadDividends(String ticker, String filename) throws IOException{
		String template ="http://real-chart.finance.yahoo.com/table.csv?s=" + ticker + "&a=03&b=10&c=1975&d=05&e=8&f=2016&g=v&ignore=.csv";
		download(template, filename);
	}
	
	public static void download(String url, String filename) throws IOException{
		URL website = new URL(url);
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream("securities\\" + filename);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
	}
	
	
}
