import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class CloudScrollingRight{
	private Image forward; //backward, left, right; 	
	private AffineTransform tx;
	
	//attributes of this class
	int dir = 0; 					//0-forward, 1-backward, 2-left, 3-right
	int width, height;				//collision detection (hit box)
	int x, y;						//position of the object
	int vx, vy;						//movement variables
	double scaleWidth = 0.12;		//change to scale image
	double scaleHeight = 0.12; 		//change to scale image

	public CloudScrollingRight() {
		
		//load the main image (front or forward view)
		forward 	= getImage("/imgs/"+"cloud.png"); //load the image for Pony

		//width and height for hit box
		width = 110;
		height = 50;
		
		//used for placement on the JFrame top left
		x = -width;
		y = 300;
		
		//if your movement will not be "hopping" base
		vx = 4;
		vy = 0;
		
		//better movement
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(x, y); 				//initialize the location of the image
									//use your variables
		
	}
	
	public boolean collided(Pony character) {
		Rectangle main = new Rectangle(character.getX(), 
				character.getY(), 
				character.getWidth(), 
				character.getHeight());
		Rectangle thisObject = new Rectangle(x,y,width,height); //in scope of hot air balloon
		
		//user built-in method to check intersection (COLLISION)
		return main.intersects(thisObject);
	}

	//2nd constructor - allow setting x and y during construction
	public CloudScrollingRight(int x, int y) {
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
		if (x > 600) {
			x = -200;
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
	public int getVx() {
		return vx;
	}
	
	public int getVy() {
		return vy;
	}
	
	public int getY() {
		return y;
	}
	
	public int getX() {
		return x;
	}
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = CloudScrollingRight.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}
