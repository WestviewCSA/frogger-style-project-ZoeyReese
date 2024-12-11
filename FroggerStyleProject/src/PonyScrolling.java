import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class PonyScrolling{
	private Image forward; //backward, left, right; 	
	private AffineTransform tx;
	
	//attributes of this class
	int dir = 0; 					//0-forward, 1-backward, 2-left, 3-right
	int width, height;				//collision detection (hit box)
	int x, y;						//position of the object
	int vx, vy;						//movement variables
	double scaleWidth = 0.20;		//change to scale image
	double scaleHeight = 0.20; 		//change to scale image

	public PonyScrolling() {
		
		//load the main image (front or forward view)
		forward 	= getImage("/imgs/"+"rainbowdash.png"); //load the image for Pony

		//width and height for hit box
		width = 155;
		height = 155;
		
		//used for placement on the JFrame top left
		x = -width;
		y = 300;
		
		//if your movement will not be "hopping" base
		vx = 3;
		vy = 0;
		
		//better movement
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(x, y); 				//initialize the location of the image
									//use your variables
		
	}
	
	//2nd constructor - allow setting x and y during construction
	public PonyScrolling(int x, int y) {
		//call default constructor for all the normal stuff
		this(); //invokes default constructor
		
		//do the specific task for this constructor
		this.x = x;
		this.y = y;
	}

	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		
		//update x and y if using vx, vy variables
		x	+=	vx;
		y	+=	vy;	
		
		//for infinite scrolling - teleport to the other side
		//once it leaves the other side!
		if (x > 650) {
			x = -225;
		}
		
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
			URL imageURL = PonyScrolling.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}
