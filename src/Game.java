import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.*;
class Game extends JPanel
{
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 2815300321471730044L;

	/**
	 * The thread that runs everything
	 */
	Thread thread;
	
	/**
	 * Here's where we store all of our particles
	 */
	CopyOnWriteArrayList<Particle> particles = new CopyOnWriteArrayList<Particle>();
	
	/**
	 * Particles to be added
	 */
	LinkedBlockingQueue<Particle> addParticles = new LinkedBlockingQueue<Particle>();
	
	/**
	 * Particles to be removed
	 */
	LinkedBlockingQueue<Particle> removeParticles = new LinkedBlockingQueue<Particle>();
	
	/**
	 * GUIHandler
	 */
	GUIHandler GUI;
	
	/**
	 * Constructor: Adds input and GUI handlers, spawns particles, and starts the game thread
	 */
	public Game()
	{
		addHandlers();
		spawnParticles(100);
		startThread();
	}

	/**
	 * Starts the thread that runs the game
	 */
	public void startThread()
	{
		thread = new Thread(gravity);
		thread.start();
	}
	
	/**
	 * Spawns the particles
	 */
	public void spawnParticles(int num)
	{
		for(int x = 0; x < num; x++)
			particles.add(new Particle(Math.random()*1366, Math.random()*768, 1));
	}
	
	/**
	 * Adds the input and GUI handlers
	 */
	public void addHandlers()
	{
		InputHandler input = new InputHandler(this);
		this.setFocusable(true);
		this.addKeyListener(input);
		this.addMouseMotionListener(input);
		this.addMouseListener(input);
		GUI = new GUIHandler(this);
	}
	
	/**
	 * The thread that runs just about everything
	 */
	Runnable gravity = new Runnable()
	{
		public void run()
		{
			while(true)
			{
				for(Particle p1 : particles)
				{
					p1.updatePosition();
					p1.updatePath();
					for(Particle p2 : particles)
					{
						if(p1 != p2)
						{
							p1.updateVelocity(p2);
							if(dist(p1, p2) <= getRadius(p1) && !removeParticles.contains(p1) && !removeParticles.contains(p2))
							{
								p1.consume(p2);
								removeParticles.offer(p2);
								break;
							}
						}
					}
				}
				
				while(!removeParticles.isEmpty())
					particles.remove(removeParticles.poll());
				
				while(!addParticles.isEmpty())
					particles.add(addParticles.poll());
				
				repaint();
				try{ Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
			}
		}
	};
	
	/**
	 * Calculates the distance between two given particles
	 */
	double dist(Particle p1, Particle p2) { return Math.sqrt(Math.pow(p1.x-p2.x, 2) + Math.pow(p1.y-p2.y, 2)); }
	
	/**
	 * Calculates the radius of the given particle
	 */
	double getRadius(Particle p) { return Math.cbrt((3*p.mass)/(4*Math.PI))*10; }
	
	/**
	 * Paints the screen
	 */
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		GUI.paint(g2d);
	}
}