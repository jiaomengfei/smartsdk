package image;

public class ImageExInfo {
	public String dateTime;
	public String flash;
	public String latitude;
	public String latitude_ref;
	public String longgitude;
	public String longgitude_ref;
	public String length;
	public String width;
	public String make;
	public String model;
	public String orientation;
	public String whiteBalance;
	
	public String TAB = "    ";
	
	@Override
	public String toString() {
		StringBuilder aBuilder = new StringBuilder();
		
		aBuilder.append("dateTime=" + dateTime + TAB 
				+ "model=" + model + TAB
				+ "length=" + length + TAB
				+ "width=" + width + TAB
				+ "orientation=" + orientation + TAB);
		
		return aBuilder.toString();
	}
	
	
}
