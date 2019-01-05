package camera;

import point.Point;

public class Camera {
	private double src;
	private int width;
	private int height;
	private double xMove;
	private double yMove;
	private double zMove;
	private double dis;
	private double flrArc;
	private double heiArc;
	private double srcArc;
	public Camera(double _src,int _width,int _height,
			double _xMove,double _yMove,double _zMove,double _dis,
			double _flrArc,double _heiArc,double _srcArc) {
		src=_src;
		width=_width;
		height=_height;
		xMove=_xMove;
		yMove=_yMove;
		zMove=_zMove;
		dis=_dis;
		flrArc=_flrArc;
		heiArc=_heiArc;
		srcArc=_srcArc;
	}
	public Camera resize(double _src,int _width,int _height) {
		return new Camera(_src,_width,_height,
				xMove,yMove,zMove,dis,
				flrArc,heiArc,srcArc);
	}
	public double getSrc() {
		return src;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public Camera move(double deltaXMove,double deltaYMove,double deltaZMove) {
		final Point lastPoint=new Point(0,deltaYMove,deltaZMove)
				.arc(0,0,-srcArc).arc(0,-heiArc,0).arc(-flrArc,0,0);
		return new Camera(src,width,height,
				xMove+lastPoint.getX(),yMove+lastPoint.getY(),zMove+lastPoint.getZ(),dis+deltaXMove,
				flrArc,heiArc,srcArc);
	}
	public Camera arc(double deltaFlrArc,double deltaHeiArc,double deltaSrcArc) {
		return new Camera(src,width,height,
				xMove,yMove,zMove,dis,
				flrArc+deltaFlrArc,heiArc+deltaHeiArc,srcArc+deltaSrcArc);
	}
	public double getXMove() {
		return xMove;
	}
	public double getYMove() {
		return yMove;
	}
	public double getZMove() {
		return zMove;
	}
	public double getDis() {
		return dis;
	}
	public double getFlrArc() {
		return flrArc;
	}
	public double getHeiArc() {
		return heiArc;
	}
	public double getsrcArc() {
		return srcArc;
	}
}
