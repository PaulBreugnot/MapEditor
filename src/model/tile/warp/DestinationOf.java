package model.tile.warp;

public class DestinationOf {

	private String originMapPath;
	private int originXCoordinate;
	private int originYCoordinate;
	
	public DestinationOf(String originMapPath, int originXCoordinate, int originYCoordinate) {
		this.originMapPath = originMapPath;
		this.originXCoordinate = originXCoordinate;
		this.originYCoordinate = originYCoordinate;
	}
	
	public String getOriginMapPath() {
		return originMapPath;
	}
	
	public int getOriginXCoordinate() {
		return originXCoordinate;
	}
	
	public int getOriginYCoordinate() {
		return originYCoordinate;
	}
}
