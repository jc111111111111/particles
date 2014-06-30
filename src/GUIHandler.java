import java.awt.Graphics2D;
import java.awt.geom.Line2D;
public class GUIHandler
{
	/**
	 * Set to true if you want to show the velocity of each particle with a line
	 */
	private final boolean DRAW_VELOCITY_VECTORS = false;
	
	/**
	 * Decides whether or not to show the paths of the particles
	 */
	private final boolean DRAW_PARTICLE_PATH = true;
	
	/**
	 * The line that represents the launch velocity of the particle to be made
	 */
	Line2D.Double drawLine;
	
	/**
	 * Decides whether or not we are launching a particle
	 */
	boolean drawing = false;
	
	/**
	 * X and Y offset of the screen
	 */
	int offsetX = 0, offsetY = 0;
	
	/**
	 * Scale of the screen
	 */
	double scale = 1;
	
	/**
	 * Game object
	 */
	Game game;
	
	public GUIHandler(Game g) { game = g; }
	
	/**
	 * Draws all the components of the game
	 */
	public void paint(Graphics2D g2d)
	{
		if(drawing)
			drawLaunchingVelocity(g2d);
		
		for(Particle p : game.particles)
			draw(p, g2d);
	}
	
	/**
	 * Draws the particle and it's path
	 */
	private void draw(Particle p, Graphics2D g2d)
	{
		g2d.setColor(p.getColor());

		drawParticle(p, g2d);
		
		if(DRAW_PARTICLE_PATH)
			drawPath(p, g2d);
		
		if(DRAW_VELOCITY_VECTORS)
			drawVelocityVectors(p, g2d);
	}

	/**
	 * Draw the particle
	 */
	private void drawParticle(Particle p, Graphics2D g2d)
	{
		g2d.fillOval((int)(((p.x-game.getRadius(p))-offsetX)*scale), 
				     (int)(((p.y-game.getRadius(p))-offsetY)*scale), 
				     (int)(game.getRadius(p)*2*scale), 
				     (int)(game.getRadius(p)*2*scale));
	}
	
	/**
	 * Draw the particle's path
	 */
	private void drawPath(Particle p, Graphics2D g2d)
	{
		for(Line2D.Double line : p.path)
			g2d.drawLine((int)((line.getX1()-offsetX)*scale), 
					     (int)((line.getY1()-offsetY)*scale), 
					     (int)((line.getX2()-offsetX)*scale), 
					     (int)((line.getY2()-offsetY)*scale));
		
		//Connect the path to the particle to make it look clean
		if(p.path.tail != null) 
			g2d.drawLine((int)((p.path.tail.getData().x1-offsetX)*scale), 
					     (int)((p.path.tail.getData().y1-offsetY)*scale), 
					     (int)((p.x-offsetX)*scale), 
					     (int)((p.y-offsetY)*scale));
	}

	/**
	 * Draw the velocity vectors
	 */
	private void drawVelocityVectors(Particle p, Graphics2D g2d)
	{
		g2d.drawLine((int)(p.x)-offsetX,
		             (int)(p.y)-offsetY, 
		             (int)(p.x + (p.velX))-offsetX, 
		             (int)(p.y + (p.velY))-offsetY);
	}
	
	/**
	 * Draw the line representing the launching velocity of the new particle
	 */
	private void drawLaunchingVelocity(Graphics2D g2d)
	{
		g2d.draw(drawLine);
	}
}
