import java.awt.Color;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane; 

import camera.Camera;
import point.Point;
import triangle.Triangle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class DrawSee extends JFrame implements KeyListener{
	private static final long serialVersionUID = 6911313143921093744L;
	private MyPanel mp;
	private int W=800,H=600;
    private Color rectColor = new Color(255,255,255);
    public DrawSee() throws FileNotFoundException{
        Container p = getContentPane();
        mp=new MyPanel(W,H);
        this.add(mp);
        this.addKeyListener(this);
        this.addMouseListener(mp);
        this.addMouseMotionListener(mp);
        this.addMouseWheelListener(mp);
        setBounds(100, 100, W, H);
        setVisible(true);
        p.setBackground(rectColor);
        setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("yyhhenry");
        addComponentListener(new ComponentAdapter(){
        	@Override 
        	public void componentResized(ComponentEvent e){
        	    W=e.getComponent().getWidth();
        	    H=e.getComponent().getHeight();
        	    mp.cameraResize(W, H);
                p.setBackground(rectColor);
        	}
        });
    }
    public void se() {
    	mp.se();
    }
    public void sq() {
    	mp.sq();
    }
    public void repaint() {
    	mp.repaint();
    }
	public void keyPressed(KeyEvent arg0) {
		
	}
	public void keyReleased(KeyEvent arg0) {
		
	}
	public void keyTyped(KeyEvent e) {
		char t=e.getKeyChar();
		if(t=='q') {
			sq();
		}
		if(t=='e') {
			se();
		}
		mp.repaint();
	}
}
class MyPanel extends JPanel implements MouseListener,MouseMotionListener,MouseWheelListener{
	private static final long serialVersionUID = -8829427583115300581L;
	private int n;
	private Triangle[]v;
	private final double src=0.001;
	private final double deltaDis=0.003;
	private final double minDis=0.5;
	private final double maxDis=1000;
	private Camera camera;
    public void paint(Graphics g){
        super.paint(g);
        Triangle[]actualV=new Triangle[n];
        for(int i=0;i<n;i++) {
        	actualV[i]=v[i].actualTriangle(camera);
        }
        for(double dis=maxDis;dis>=minDis;dis*=1-deltaDis) {
	        for(int i=0;i<n;i++) {
	        	actualV[i].draw(camera, g, dis*(1-deltaDis), dis);
	        }
        }
        g.setColor(Color.gray);
        g.drawLine(camera.getWidth()/2-10, camera.getHeight()/2, camera.getWidth()/2+10, camera.getHeight()/2);
        g.drawLine(camera.getWidth()/2, camera.getHeight()/2-10, camera.getWidth()/2, camera.getHeight()/2+10);
    }
    @SuppressWarnings("resource")
	MyPanel(int _W,int _H) throws FileNotFoundException{
    	n=0;
    	Scanner fin;
    	try {
			fin=new Scanner(new File("./val.in"));
		} catch (FileNotFoundException e) {
			fin=new Scanner(System.in);
			JOptionPane.showMessageDialog(null, "应用找不到关键文件val.in。","文件缺失",JOptionPane.ERROR_MESSAGE);
			throw e;
		}
    	n=fin.nextInt();
    	double dis=fin.nextDouble();
    	v=new Triangle[n];
    	double x,y,z;
    	int cr,cg,cb;
    	Point AA,BB,CC;
    	for(int i=0;i<n;i++) {
    		x=fin.nextDouble();
    		y=fin.nextDouble();
    		z=fin.nextDouble();
    		AA=new Point(x,y,z);
    		x=fin.nextDouble();
    		y=fin.nextDouble();
    		z=fin.nextDouble();
    		BB=new Point(x,y,z);
    		x=fin.nextDouble();
    		y=fin.nextDouble();
    		z=fin.nextDouble();
    		CC=new Point(x,y,z);
    		cr=fin.nextInt();
    		cg=fin.nextInt();
    		cb=fin.nextInt();
    		v[i]=new Triangle(AA,BB,CC,new Color(cr,cg,cb));
    	}
    	fin.close();
    	camera=new Camera(src,_W,_H,0,0,0,dis,0,0,0);
    }
    public void cameraResize(int _W,int _H) {
    	camera=camera.resize(src,_W,_H);
    }
    private void arc(double a,double b,double c) {
    	camera=camera.arc(a, b, c);
    }
    private void move(double a,double b,double c) {
    	camera=camera.move(a, b, c);
    }
    public void se() {
    	arc(0,0,-Math.PI/36);
    }
    public void sq() {
    	arc(0,0,Math.PI/36);
    }
    private int mouseLastX,mouseLastY;
    private boolean mouseLeftDown=false;
    private boolean mouseRightDown=false;
	@Override
	public void mouseClicked(MouseEvent arg0) {
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
			mouseLastX=arg0.getX();
			mouseLastY=arg0.getY();
		if(arg0.getButton()==MouseEvent.BUTTON1) {
			mouseLeftDown=true;
		}else if(arg0.getButton()==MouseEvent.BUTTON3) {
			mouseRightDown=true;
		}
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(arg0.getButton()==MouseEvent.BUTTON1) {
			mouseLeftDown=false;
		}else if(arg0.getButton()==MouseEvent.BUTTON3) {
			mouseRightDown=false;
		}
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		if(mouseLeftDown) {
			double delX=e.getX()-mouseLastX,delY=e.getY()-mouseLastY;
			mouseLastX=e.getX();
			mouseLastY=e.getY();
			arc(-delX/camera.getWidth()*Math.PI,delY/camera.getHeight()*Math.PI,0);
			repaint();
		}else if(mouseRightDown) {
			double delX=e.getX()-mouseLastX,delY=e.getY()-mouseLastY;
			mouseLastX=e.getX();
			mouseLastY=e.getY();
			move(0,-delX/camera.getWidth()*5,delY/camera.getHeight()*5);
			repaint();
		}
	}
	@Override
	public void mouseMoved(MouseEvent e) {
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		camera=camera.move(-0.5*arg0.getWheelRotation(),0,0);
		repaint();
	}
}