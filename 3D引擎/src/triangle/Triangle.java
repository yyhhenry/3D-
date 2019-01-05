package triangle;

import java.awt.Color;
import java.awt.Graphics;

import camera.Camera;
import point.Point;
import point.Point2d;

public class Triangle {
	public Point a,b,c;
	public Color color;
	public Triangle(Point _a,Point _b,Point _c,Color _color) {
		a=_a;
		b=_b;
		c=_c;
		color=_color;
	}
	private void sortPoints() {
		if(a.getX()>c.getX()) {
			Point d=a;
			a=c;
			c=d;
		}
		if(b.getX()>c.getX()) {
			Point d=b;
			b=c;
			c=d;
		}
		if(a.getX()>b.getX()) {
			Point d=a;
			a=b;
			b=d;
		}
	}
	public String toString() {
		return "Triangle(Color = "+color.toString()+") {"+a.toString()+", "+b.toString()+", "+c.toString()+"}";
	}
	private void wholeDraw(Camera camera, Graphics g) {
		g.setColor(color);
		final Point2d pa=a.toSrc(camera);
		final Point2d pb=b.toSrc(camera);
		final Point2d pc=c.toSrc(camera);
		final int[] xPoints= {pa.getIntX(), pb.getIntX(), pc.getIntX(), pa.getIntX()};
		final int[] yPoints= {pa.getIntY(), pb.getIntY(), pc.getIntY(), pa.getIntY()};
		final int nPoints=4;
		g.fillPolygon(xPoints,yPoints,nPoints);
	}
	public Triangle actualTriangle(Camera camera) {
		return new Triangle(a.actualPoint(camera),b.actualPoint(camera),c.actualPoint(camera),color);
	}
	public void draw(Camera camera,Graphics g,double dis,double lastDis) {
		sortPoints();
		g.setColor(color);
		if(c.getX()<dis)return;
		if(a.getX()>lastDis)return;
		if(c.getX()>lastDis) {
			if(b.getX()>lastDis) {
				if(a.getX()>dis) {
					new Triangle(
							a,a.takeX(b, lastDis),a.takeX(c, lastDis),color
					).wholeDraw(camera,g);
				}else {
					new Triangle(
							a.takeX(b, dis),a.takeX(b, lastDis),a.takeX(c, dis),color
					).wholeDraw(camera, g);
					new Triangle(
							a.takeX(c, lastDis),a.takeX(b, lastDis),a.takeX(c, dis),color
					).wholeDraw(camera, g);
				}
			}else if(b.getX()>dis) {
				if(a.getX()>dis) {
					new Triangle(
							a,b,a.takeX(c, lastDis),color
					).wholeDraw(camera, g);
					new Triangle(
							b.takeX(c, lastDis),b,a.takeX(c, lastDis),color
					).wholeDraw(camera, g);
				}else {
					new Triangle(
							a,b,a.takeX(c, (dis+lastDis)/2),color
					).draw(camera, g, dis, lastDis);
					new Triangle(
							c,b,a.takeX(c, (dis+lastDis)/2),color
					).draw(camera, g, dis, lastDis);
				}
			}else {
				new Triangle(
						a.takeX(c, dis),a.takeX(c, lastDis),b.takeX(c, dis),color
				).wholeDraw(camera, g);
				new Triangle(
						b.takeX(c, lastDis),a.takeX(c, lastDis),b.takeX(c, dis),color
				).wholeDraw(camera, g);
			}
		}else {
			if(b.getX()>dis) {
				if(a.getX()>dis) {
					new Triangle(
							a,b,c,color
					).wholeDraw(camera, g);
				}else {
					new Triangle(
							a.takeX(b, dis),b,a.takeX(c, dis),color
					).wholeDraw(camera, g);
					new Triangle(
							c,b,a.takeX(c, dis),color
					).wholeDraw(camera, g);
				}
			}else {
				new Triangle(
						a.takeX(c, dis),b.takeX(c, dis),c,color
				).wholeDraw(camera, g);
			}
		}
	}
}
