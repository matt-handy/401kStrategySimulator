package handy.marketsim.drivers;

import java.io.IOException;

import handy.marketsim.MarketPointDownloader;
import handy.marketsim.investors.TimingIvyPortfolioInvestor;
import handy.marketsim.investors.holders.AutoPortfolioInvestor;
import handy.marketsim.investors.holders.IvyPortfolioInvestor;
import handy.marketsim.investors.holders.OilPortfolioInvestor;
import handy.marketsim.investors.holders.PreciousMetalPortfolioInvestor;
import handy.marketsim.investors.holders.SimpleIvyPortfolioInvestor;
import handy.marketsim.investors.holders.SimplestIvyPortfolioInvestor;
import handy.marketsim.investors.holders.UtilityPortfolioInvestor;

public class PortfolioSimulator {

	public static void main(String args[]){
		buildSecuritiesDataset();
		IvyPortfolioInvestor ipi = new IvyPortfolioInvestor(100000);
		try {
			System.out.println("Ivy Portfolio Investor:");
			ipi.runSum();
		} catch (Exception e) {
			System.out.println("Sim failed");
			e.printStackTrace();
		}
		
		System.out.println();
		
		SimpleIvyPortfolioInvestor sipi = new SimpleIvyPortfolioInvestor(100000);
		try {
			System.out.println("Simple Ivy Portfolio Investor:");
			sipi.runSum();
		} catch (Exception e) {
			System.out.println("Sim failed");
			e.printStackTrace();
		}
		
		System.out.println();
		
		SimplestIvyPortfolioInvestor ssipi = new SimplestIvyPortfolioInvestor(100000);
		try {
			System.out.println("Simplest Ivy Portfolio Investor:");
			ssipi.runSum();
		} catch (Exception e) {
			System.out.println("Sim failed");
			e.printStackTrace();
		}
		
		System.out.println();
		
		AutoPortfolioInvestor aipi = new AutoPortfolioInvestor(100000);
		try {
			System.out.println("Auto Portfolio Investor:");
			aipi.runSum();
		} catch (Exception e) {
			System.out.println("Sim failed");
			e.printStackTrace();
		}
		
		System.out.println();
		
		OilPortfolioInvestor oipi = new OilPortfolioInvestor(100000);
		try {
			System.out.println("Oil Portfolio Investor:");
			oipi.runSum();
		} catch (Exception e) {
			System.out.println("Sim failed");
			e.printStackTrace();
		}
		
		System.out.println();
		
		PreciousMetalPortfolioInvestor pmipi = new PreciousMetalPortfolioInvestor(100000);
		try {
			System.out.println("Precious Metal Portfolio Investor:");
			pmipi.runSum();
		} catch (Exception e) {
			System.out.println("Sim failed");
			e.printStackTrace();
		}
		
		System.out.println();
		
		UtilityPortfolioInvestor uipi = new UtilityPortfolioInvestor(100000);
		try {
			System.out.println("Utility Portfolio Investor:");
			uipi.runSum();
		} catch (Exception e) {
			System.out.println("Sim failed");
			e.printStackTrace();
		}
		
		System.out.println();
		
		TimingIvyPortfolioInvestor tipi = new TimingIvyPortfolioInvestor(100000);
		try {
			System.out.println("Timing Ivy Portfolio Investor:");
			tipi.runSum();
		} catch (Exception e) {
			System.out.println("Sim failed");
			e.printStackTrace();
		}
	}
	
	public static boolean buildSecuritiesDataset(){
		try{
			//Basic 20 from IVY portfolio, substituting TREE.L
			MarketPointDownloader.downloadStock("VTI");
			MarketPointDownloader.downloadStock("VO");
			MarketPointDownloader.downloadStock("VB");
			MarketPointDownloader.downloadStock("IWC");
			MarketPointDownloader.downloadStock("VEU");
			MarketPointDownloader.downloadStock("VWO");
			MarketPointDownloader.downloadStock("GWX");
			MarketPointDownloader.downloadStock("EWX");
			MarketPointDownloader.downloadStock("BND");
			MarketPointDownloader.downloadStock("TIP");
			MarketPointDownloader.downloadStock("BWX");
			MarketPointDownloader.downloadStock("ESD");
			MarketPointDownloader.downloadStock("VNQ");
			MarketPointDownloader.downloadStock("RWX");
			MarketPointDownloader.downloadStock("IGF");
			MarketPointDownloader.downloadStock("WOOD");
			MarketPointDownloader.downloadStock("DBA");
			MarketPointDownloader.downloadStock("DBE");
			MarketPointDownloader.downloadStock("DBB");
			MarketPointDownloader.downloadStock("DBP");
			
			//Simplified Ivy 5
			//VTI, VEU, BND, VNQ
			MarketPointDownloader.downloadStock("DBC");
			
			//Simplified Ivy 10
			//VTI, VB, VEU, VWO, BND, TIP, VNQ, RWX, DBC
			MarketPointDownloader.downloadStock("GSG");
			
			//Ivy Matching
			//VTI (10 H, 10 Y), VEU (10 H 5 Y), VWO (10 H, 10 Y), BND (10 H, 5 Y)
			//TIP (5 H, 0 Y), VNQ (10 H, 15 Y), DBC (15 H, 15 Y),
			//Hedges (20 H, 20 Y)
			//PSP (10 H, 20 Y)
			MarketPointDownloader.downloadStock("PSP");
			
			MarketPointDownloader.downloadStock("F");
			
			MarketPointDownloader.downloadStock("SLV");
			MarketPointDownloader.downloadStock("GLD");
			
			MarketPointDownloader.downloadStock("VZ");
			MarketPointDownloader.downloadStock("T");
			MarketPointDownloader.downloadStock("EXC");
			MarketPointDownloader.downloadStock("DUK");
			
			MarketPointDownloader.downloadStock("XOM");
			MarketPointDownloader.downloadStock("CVX");
			MarketPointDownloader.downloadStock("BP");
			MarketPointDownloader.downloadStock("MPC");
			
		}catch(IOException ex){
			ex.printStackTrace();
			return false;
		}
		return true;
	}
}
