package handy.marketsim;

public class AnnotatedMarketPoint extends MarketPoint {

	public enum ANNOTATED_POINT_TYPE {MINIMUM, MAXIMUM};
	
	private ANNOTATED_POINT_TYPE type;
	
	public AnnotatedMarketPoint(ANNOTATED_POINT_TYPE type, MarketPoint copyPoint){
		super(copyPoint.getDate(), copyPoint.getPrice());
		this.type = type;
	}
	
	public ANNOTATED_POINT_TYPE getType(){
		return type;
	}
}
