package model.tile.warp;

public class GoTo {
	
	private String destinationMapPath;
	private int destinationXCoordinate;
	private int destinationYCoordinate;
	
	public GoTo(String destinationMapPath, int destinationXCoordinate, int destinationYCoordinate) {
		this.destinationMapPath = destinationMapPath;
		this.destinationXCoordinate = destinationXCoordinate;
		this.destinationYCoordinate = destinationYCoordinate;
	}
	
	public String getDestinationMapPath() {
		return destinationMapPath;
	}
	
	public int getDestinationXCoordinate() {
		return destinationXCoordinate;
	}
	
	public int getDestinationYCoordinate() {
		return destinationYCoordinate;
	}
}
