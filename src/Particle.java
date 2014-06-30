import java.awt.Color;
import java.awt.geom.Line2D;
class Particle
{
	public double x, y, mass, velX = 0, velY = 0, consumed = 1;
	public Color color;
	public LimitedLinkedList<Line2D.Double> path;
	public int pathLimit = 100, pathTimer = 5;
	public double pathStartX = 0, pathStartY = 0;
	public Particle(double px, double py, double m)
	{
		x = px;
		y = py;
		pathStartX = px;
		pathStartY = py;
		mass = m;
		path = new LimitedLinkedList<Line2D.Double>(pathLimit); 
		color = new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
	}
	public Particle(double px, double py, double m, double vx, double vy)
	{
		x = px;
		y = py;
		pathStartX = px;
		pathStartY = py;
		mass = m;
		velX = vx;
		velY = vy;
		path = new LimitedLinkedList<Line2D.Double>(pathLimit); 
		color = new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
	}
	Color weightColors(Particle p1, Particle p2)
	{
		return new Color((int)((p1.getColor().getRed()*p1.mass + p2.getColor().getRed()*p2.mass)/(p1.mass+p2.mass)),
				(int)((p1.getColor().getGreen()*p1.mass + p2.getColor().getGreen()*p2.mass)/(p1.mass+p2.mass)),
				(int)((p1.getColor().getBlue()*p1.mass + p2.getColor().getBlue()*p2.mass)/(p1.mass+p2.mass)));
	}
	public void consume(Particle p)
	{
		color = weightColors(this, p);
		velX = (mass*velX + p.mass*p.velX)/(mass + p.mass);
		velY = (mass*velY + p.mass*p.velY)/(mass + p.mass);
		mass = mass + p.mass;
		consumed += p.consumed;
	}
	public void updatePosition()
	{
		x += velX*.1;
		y += velY*.1;
	}
	public void updatePath()
	{
		pathTimer++;
		if(pathTimer >= 5)
		{
			path.add(new Line2D.Double(pathStartX, pathStartY, x, y));
			pathStartX = x;
			pathStartY = y;
			pathTimer = 0;
		}
	}
	void updateVelocity(Particle p)
	{
		double deltaX = (x - p.x);
		double deltaY = (y - p.y);
		double theta = Math.abs(Math.atan(deltaY/deltaX));
		double accelMagnitude = p.mass/Math.sqrt(deltaX*deltaX + deltaY*deltaY);
		velX += Math.cos(theta)*accelMagnitude*Math.signum(deltaX)*-1;
		velY += Math.sin(theta)*accelMagnitude*Math.signum(deltaY)*-1;
	}
	public Color getColor()
	{
		return color;
	}
}