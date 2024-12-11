import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Pony{
	private Image forward; //backward, left, right; 	
	private AffineTransform tx;
	
	//attributes of this class
	int dir = 0; 					//0-forward, 1-backward, 2-left, 3-right
	int width, height;				//collision detection (hit box)
	int x, y;						//position of the object
	int vx, vy;						//movement variables
	double scaleWidth = 0.06;		//change to scale image
	double scaleHeight = 0.06; 		//change to scale image

	public Pony() {
		
		//load the main image (front or forward view)
		forward 	= getImage("/imgs/"+"rainbowdash.png"); //load the image for Pony

		//width and height for hit box
		width = 47;
		height = 47;
		
		//used for placement on the JFrame
		x = 0;
		y = 0;
		
		//if your movement will not be "hopping" base
		vx = 0;
		vy = 0;
		
		//better movement
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(x, y); 				//initialize the location of the image
									//use your variables
		
	}
	
	//2nd constructor - allow setting x and y during construction
	public Pony(int x, int y) {
		//call default constructor for all the normal stuff
		this(); //invokes default constructor
		
		//do the specific task for this constructor
		this.x = x;
		this.y = y;
	}

	public int getY() {
		return y;
	}
	
	public void setY(int newY) {
		y = newY;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int newX) {
		x = newX;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setVx(int newVx) {
		vx = newVx;
	}
	
	public void setVy(int newVy) {
		vy = newVy;
	}
	
	public boolean collided() {
		Rectangle pony = new Rectangle(x, y, width, height);
		Rectangle winSpot = new Rectangle(225, 10, 130, 40);
		return pony.intersects(winSpot);
	}


	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		
		//update x and y if using vx, vy variables
		x+=vx;
		y+=vy;	
		
		init(x,y);
		
		g2.drawImage(forward, tx, null);

		//draw hit box based on x, y, width, height
		//for collision detection
		if(Frame.debugging) {
			//draw hitbox only if debugging
			g.setColor(Color.green);
			g.drawRect(x, y, width, height);
		}
	}
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Pony.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}
