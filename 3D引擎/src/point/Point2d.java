package point;

public class Point2d {
	public double x,y;
	public Point2d(double a,double b){
		x=a;
		y=b;
	}
	
	public double disTo(Point2d b) {
		final double xx=x-b.x;
		final double yy=y-b.y;
		return Math.sqrt(xx*xx+yy*yy);
	}
	
	public Point2d toWin(int W,int H) {
		return new Point2d((x+1)/2*W,(y+1)/2*H);
	}

	public boolean isdrawed(boolean[][]drawed) {
		if((int)(x)>=drawed.length||(int)(x)<0)return false;
		if((int)(y)>=drawed[(int)(x)].length||(int)(y)<0)return false;
		return drawed[(int)(x)][(int)(y)];
	}
	
	public boolean near(Point2d b) {
		final double theta=1;
		return disTo(b)<=theta;
	}
	
	public Point2d arc(double a) {
		double dis=Math.sqrt(x*x+y*y);
		if(dis==0)return new Point2d(x,y);
		final double sign=(y>=0?1:-1);
		double narc=sign*Math.acos(x/dis)+a;
		return new Point2d(Math.cos(narc)*dis,Math.sin(narc)*dis);
	}
	public String toString() {
		return "Point2d ( "+x+", "+y+")";
	}
}
