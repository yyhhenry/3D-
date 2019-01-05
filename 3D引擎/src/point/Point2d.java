package point;

public class Point2d {
	private double x,y;
	public Point2d(double _x,double _y){
		x=_x;
		y=_y;
	}
	public double disTo(Point2d b) {
		final double xx=x-b.x;
		final double yy=y-b.y;
		return Math.sqrt(xx*xx+yy*yy);
	}
	
	public Point2d toSrc(int W,int H) {
		return new Point2d((x+1)/2*W,(y+1)/2*H);
	}
	
	public Point2d arc(double a) {
		double dis=Math.sqrt(x*x+y*y);
		if(dis==0)return new Point2d(x,y);
		final double sign=(y>=0?1:-1);
		double narc=sign*Math.acos(x/dis)+a;
		return new Point2d(Math.cos(narc)*dis,Math.sin(narc)*dis);
	}
	public double getX() {
		return x;
	}
	public int getIntX() {
		return (int)(x);
	}
	public double getY() {
		return y;
	}
	public int getIntY() {
		return (int)(y);
	}
	public String toString() {
		return "Point2d ("+x+", "+y+")";
	}
}
