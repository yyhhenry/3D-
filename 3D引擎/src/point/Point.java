package point;

import camera.Camera;

public class Point {
	public double x,y,z;
	public Point mid(Point b) {
		return new Point((x+b.x)/2,(y+b.y)/2,(z+b.z)/2);
	}
	public Point takeX(Point b,double _x) {
		final double src=(_x-x)/(b.x-x);
		return new Point(_x,y+src*(b.y-y),z+src*(b.z-z));
	}
	public double disToZero() {
		return Math.sqrt(x*x+y*y+z*z);
	}
	public double disTo(Point b) {
		final double xx=x-b.x;
		final double yy=y-b.y;
		final double zz=z-b.z;
		return Math.sqrt(xx*xx+yy*yy+zz*zz);
	}
	public Point(double _x,double _y,double _z){
		x=_x;
		y=_y;
		z=_z;
	}
	private Point move(double a,double b,double c) {
		return new Point(x-a,y-b,z-c);
	}
	private void arcxy(double a) {
		final Point2d xy=new Point2d(x,y).arc(a);
		x=xy.x;
		y=xy.y;
	}
	private void arcxz(double a) {
		final Point2d xz=new Point2d(x,z).arc(a);
		x=xz.x;
		z=xz.y;
	}
	private void arcyz(double a) {
		final Point2d yz=new Point2d(y,z).arc(a);
		y=yz.x;
		z=yz.y;
	}
	private void eqArc(double a,double b,double c) {
		arcxy(a);
		arcxz(b);
		arcyz(c);
	}
	public Point arc(double a,double b,double c) {
		final Point ans=this;
		ans.eqArc(a, b, c);
		return ans;
	}
	public Point2d draw(Camera camera) {
		if(Math.abs(x)==0)return null;
		final double bs = 1 / Math.abs(x);
		final double yy =  y * bs / camera.getSrc() / camera.getWidth() * 2;
		final double zz = -z * bs / camera.getSrc() / camera.getHeight() * 2;
    	return new Point2d(yy,zz).toWin(camera.getWidth(), camera.getHeight());
	}
	public Point get(Camera camera) {
		return move(camera.getXMove(), camera.getYMove(), camera.getZMove())
				.arc(camera.getFlrArc(), camera.getHeiArc(), camera.getsrcArc())
				.move(-camera.getDis(), 0, 0);
	}
	public String toString() {
		return "Point ( "+x+", "+y+", "+z+")";
	}
}
