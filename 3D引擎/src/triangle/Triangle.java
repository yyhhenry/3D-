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
		if(a.x>c.x) {
			Point d=a;
			a=c;
			c=d;
		}
		if(b.x>c.x) {
			Point d=b;
			b=c;
			c=d;
		}
		if(a.x>b.x) {
			Point d=a;
			a=b;
			b=d;
		}
	}
	public String toString() {
		return "Triangle( Color = "+color.toString()+") { "+a.toString()+", "+b.toString()+", "+c.toString()+"}";
	}
	private void draw(Camera camera, Graphics g,Point a,Point b,Point c) {
		final Point2d pa=a.draw(camera);
		final Point2d pb=b.draw(camera);
		final Point2d pc=c.draw(camera);
		final int[] xPoints= {(int)pa.x, (int)pb.x, (int)pc.x, (int)pa.x};
		final int[] yPoints= {(int)pa.y, (int)pb.y, (int)pc.y, (int)pa.y};
		final int nPoints=4;
		g.fillPolygon(xPoints,yPoints,nPoints);
	}
	public void draw(Camera camera,Graphics g,double dis,double lastDis) {
		new Triangle(a.get(camera),b.get(camera),c.get(camera),color).vDraw(camera, g, dis, lastDis);
	}
	private void vDraw(Camera camera,Graphics g,double dis,double lastDis) {
		sortPoints();
		g.setColor(color);
		if(c.x<dis)return;
		if(a.x>lastDis)return;
		if(c.x>lastDis) {
			if(b.x>lastDis) {
				if(a.x>dis) {
					draw(camera,g,a,a.takeX(b, lastDis),a.takeX(c, lastDis));
				}else {
					draw(camera,g,a.takeX(b, dis),a.takeX(b, lastDis),a.takeX(c, dis));
					draw(camera,g,a.takeX(c, lastDis),a.takeX(b, lastDis),a.takeX(c, dis));
				}
			}else if(b.x>dis) {
				if(a.x>dis) {
					draw(camera,g,a,b,a.takeX(c, lastDis));
					draw(camera,g,b.takeX(c, lastDis),b,a.takeX(c, lastDis));
				}else {
					new Triangle(a,b,a.takeX(c, (dis+lastDis)/2),color).vDraw(camera, g, dis, lastDis);
					new Triangle(c,b,a.takeX(c, (dis+lastDis)/2),color).vDraw(camera, g, dis, lastDis);
				}
			}else {
				draw(camera,g,a.takeX(c, dis),a.takeX(c, lastDis),b.takeX(c, dis));
				draw(camera,g,b.takeX(c, lastDis),a.takeX(c, lastDis),b.takeX(c, dis));
			}
		}else {
			if(b.x>dis) {
				if(a.x>dis) {
					draw(camera,g,a,b,c);
				}else {
					draw(camera,g,a.takeX(b, dis),b,a.takeX(c, dis));
					draw(camera,g,c,b,a.takeX(c, dis));
				}
			}else {
				draw(camera,g,a.takeX(c, dis),b.takeX(c, dis),c);
			}
		}
	}
}
