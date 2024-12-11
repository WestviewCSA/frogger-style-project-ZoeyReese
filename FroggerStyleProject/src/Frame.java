import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {
	
	//for any debugging code we add
	public static boolean debugging = false;
	
	//Timer related variables
	int waveTimer = 5; //each wave of enemies is 20s
	long ellapseTime = 0;
	Font timeFont = new Font("Marker Felt", Font.BOLD, 70);
	int level = 0;
	int initX = 260;
	int initY = 520;
	
	int sky1Width = 600;
	int sky1Height = 55;
	int sky1X = 0;
	int sky1Y = 455;
	
	int score = 0;
	
	Font myFont = new Font("Courier", Font.BOLD, 40);
	SimpleAudioPlayer backgroundMusic = new SimpleAudioPlayer("scifi.wav", false);
//	Music soundHaha = new Music("haha.wav", false);
	
//	Pony rainbowDash = new Pony();
	Pony rainbowDash = new Pony(260, 520);
	Background background = new Background(0,-10);
	
	//frame width/height
	int width = 600;
	int height = 600;	

	HelicopterScrolling[] row1=new HelicopterScrolling[3]; //container, still need to create objects
	HotAirBalloonScrolling[] row2 = new HotAirBalloonScrolling[3];
	HotAirBalloonScrolling[] row3 = new HotAirBalloonScrolling[3];
	CloudScrollingLeft[] row4 = new CloudScrollingLeft[3];
	CloudScrollingRight[] row5 = new CloudScrollingRight[3];
	ArrayList<HotAirBalloonScrolling> row3List = new ArrayList<HotAirBalloonScrolling>(); //converting row3 to an arraylist
	ArrayList<LifeImg> lives = new ArrayList<LifeImg>();

	
	

	public void paint(Graphics g) {
		super.paintComponent(g);
		//paint the other objects on the screen
		
		background.paint(g);
		
		
		//a row of HelicopterScrolling objects
		//have the row
		for(HelicopterScrolling obj : row1) {
			obj.paint(g);
		}
		
		
		
		//a row of HotAirBalloonScrolling objects
		for(HotAirBalloonScrolling obj : row2) {
			obj.paint(g);
		}
		
		for(HotAirBalloonScrolling obj : row3) {
			obj.paint(g);
		}
		
		for(HotAirBalloonScrolling obj : row3List) { //for every hot air balloon obj in row3List
			obj.paint(g);
		}
		
		for(CloudScrollingLeft obj : row4) {
			obj.paint(g);
		}
		
		for(CloudScrollingRight obj : row5) {
			obj.paint(g);
		}
		
		rainbowDash.paint(g);
		
	//	if (debugging == true)
//		g.drawRect(sky1X,sky1Y,sky1Width, sky1Height);
		// collision detection
		//for each hot air balloon object in row1 array
		for (HotAirBalloonScrolling obj : row2) {
			//invoke collided method for your class -
			//pass the main character as your argument
			if (obj.collided(rainbowDash)) {
				resetPony();
			}
		}
		
		for (HotAirBalloonScrolling obj : row3) {
			if (obj.collided(rainbowDash)) {
				resetPony();
			}
		}
		
		for (HelicopterScrolling obj : row1) {
			if (obj.collided(rainbowDash)) {
				resetPony();
			}
		}
		
		//drawing the life counter images
		for (LifeImg obj : lives) {
			//draw the life image objects
			obj.paint(g);
		}
		
		if(debugging) {
			g.drawRect(225, 10, 130, 40);
		}
		
		g.setFont(new Font("Marker Felt", Font.BOLD, 20));
		g.setColor(Color.black);

		g.drawString("Score: " + score, 18, 545);
		
		g.setFont(new Font("Marker Felt", Font.BOLD, 40));
		g.setColor(Color.white);

		
		if (rainbowDash.collided()) {
			score++;
			rainbowDash.setX(initX);
			rainbowDash.setY(initY);
		}
		if (score == 5) {
			for (int i = 0; i < lives.size(); i++) {
				lives.remove(i);
			}
			
			g.drawString("You Win!", 15, 65);
			g.setFont(new Font("Marker Felt", Font.BOLD, 25));
			g.drawString("Press ENTER", 390, 40);
			g.drawString("to restart", 420, 70);
		}
		
		if (lives.size() == 0 && score != 5) {
			g.drawString("You Lose!", 15, 65);
			g.setFont(new Font("Marker Felt", Font.BOLD, 25));
			g.drawString("Press ENTER", 390, 40);
			g.drawString("to restart", 420, 70);
			}
		

		//collision detection
		//for each cloud object in row5 array
		boolean riding = false;
		for(CloudScrollingRight obj : row5) {
			//for every object, invoke the collided method
			if (obj.collided(rainbowDash)) {
				rainbowDash.setVx(obj.getVx());
				riding = true;
				break; // can break out of for loop as soon as you know you're colliding with them
			}
		}
		
		for(CloudScrollingLeft obj : row4) {
			//for every object, invoke the collided method
			if (obj.collided(rainbowDash)) {
				rainbowDash.setVx(obj.getVx());
				riding = true;
				break; // can break out of for loop as soon as you know you're colliding with them
			}
		}
		//main character stops moving if not on a rideable object
		//but also let's limit it in the y
		if (!riding && (rainbowDash.getY() == 462 || rainbowDash.getY()==346)) {
			resetPony();
			
		}
		if (!riding) {
			rainbowDash.setVx(0);
		}
		
		
		
		}
	
	public void resetPony() {
		rainbowDash.setVx(0);
		rainbowDash.setX(initX);
		rainbowDash.setY(initY);
		if (lives.size() > 0)
			lives.remove(lives.size()-1);
	}
	
	
	public static void main(String[] arg) {
		Frame f = new Frame();
		
	}
	
	public Frame() {
		JFrame f = new JFrame("My Little Pony");
		f.setSize(new Dimension(width, height));
		f.setBackground(Color.white);
		f.add(this);
		f.setResizable(false);
 		f.addMouseListener(this);
		f.addKeyListener(this);
	
	//	backgroundMusic.play();

		//set up 1d array here! - create objects that go in them
		//traverse the array
		for(int i = 0; i < row1.length; i++) {
			row1[i] = new HelicopterScrolling(i*300, 168);
		}
		
		for (int i = 0; i < row2.length; i++) {
			row2[i] = new HotAirBalloonScrolling(i*300,223);
		}
		
		for (int i = 0; i < row3.length; i++) {
			row3[i] = new HotAirBalloonScrolling(i*300+150, 109);
		}
		
		//practice row for ArrayList

		//start with 6 attempts
		for (int i = 0; i < 6; i++) {
			this.lives.add(new LifeImg((i*30+10),10));
		}
		
		for (int i = 0; i < row4.length; i++) {
			row4[i] = new CloudScrollingLeft(i*300+400, 346);
		}
		
		for (int i = 0; i < row5.length; i++) {
			row5[i] = new CloudScrollingRight(i*300+100, 457);
		}
	
		
		
		//the cursor image must be outside of the src folder
		//you will need to import a couple of classes to make it fully 
		//functional! use eclipse quick-fixes
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("torch.png").getImage(),
				new Point(0,0),"custom cursor"));	
		
		
		Timer t = new Timer(16, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0);
		System.out.println(rainbowDash.getY());
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent m) {
		
	
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0.getKeyCode());
		
		SimpleAudioPlayer thunk = new SimpleAudioPlayer("thunk.wav", false);
		SimpleAudioPlayer jump = new SimpleAudioPlayer("jump.wav", false);


		//move up
		if (arg0.getKeyCode() == 38) {
			rainbowDash.setY(rainbowDash.getY()-58);
			jump.play();
		}
		//move down
		if (arg0.getKeyCode() == 40) {
			rainbowDash.setY(rainbowDash.getY()+58);
			jump.play();
		}
		//move left
		if (arg0.getKeyCode() == 37) {
			rainbowDash.setX(rainbowDash.getX()-58);
			jump.play();
		}
		//move right
		if (arg0.getKeyCode() == 39) {
			rainbowDash.setX(rainbowDash.getX()+58);
			jump.play();
		}
		//replay
		if (arg0.getKeyCode() == 10) {
			rainbowDash.setX(initX);
			rainbowDash.setY(initY);
			for (int i = 0; i < 6; i++) {
				lives.add(new LifeImg(i*30+10,10));
			}
			thunk.play();
			score = 0;
			
		}
		
	}
	

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
