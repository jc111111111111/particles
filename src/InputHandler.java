import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
class InputHandler extends KeyAdapter implements MouseListener, MouseMotionListener
{
	Game game;
	
	int startX = 0, startY = 0;
	
	/**
	 * Mass of any new particles being launched
	 */
	int newMass = 100;
	InputHandler(Game g)
	{
		game = g;
	}
	public void mousePressed(MouseEvent e)
	{
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			startX = e.getX();
			startY = e.getY();
		}
		if(e.getButton() == MouseEvent.BUTTON3)
		{
			game.GUI.drawing = true;
			game.GUI.drawLine = new Line2D.Double(e.getX(), e.getY(), e.getX(), e.getY());
		}
	}
	public void mouseDragged(MouseEvent e)
	{
		if(game.GUI.drawing)
		{
			game.GUI.drawLine.setLine(
					game.GUI.drawLine.getX1(), 
					game.GUI.drawLine.getY1(), 
					e.getX(), 
					e.getY());
		}
		else
		{
			game.GUI.offsetX += startX - e.getX();
			game.GUI.offsetY += startY - e.getY();
			startX = e.getX();
			startY = e.getY();
		}
	}
	public void mouseReleased(MouseEvent e)
	{
		if(e.getButton() == MouseEvent.BUTTON3)
		{
			game.GUI.drawing = false;
			game.addParticles.offer(
					new Particle((game.GUI.drawLine.getX1()/game.GUI.scale+game.GUI.offsetX), 
					             (game.GUI.drawLine.getY1()/game.GUI.scale+game.GUI.offsetY),
					             newMass, 
					             -(game.GUI.drawLine.getX2()-game.GUI.drawLine.getX1()), 
					             -(game.GUI.drawLine.getY2()-game.GUI.drawLine.getY1())
					)
			);
			game.GUI.drawLine.setLine(-1, -1, -1, -1);
		}
	}
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		if(key >= 48 && key <= 57) newMass = (key - 48)*100;
		else if(key == KeyEvent.VK_Z)
		{
			game.GUI.offsetX += game.getWidth()*.05 + game.GUI.offsetX*.1;
			game.GUI.offsetY += game.getHeight()*.05 + game.GUI.offsetY*.1;
			game.GUI.scale *= 1.1;
		}
		else if(key == KeyEvent.VK_X)
		{
			game.GUI.offsetX -= game.getWidth()*.05 + game.GUI.offsetX*.1;
			game.GUI.offsetY -= game.getHeight()*.05 + game.GUI.offsetY*.1;
			game.GUI.scale *= .9;
		}
		else if(key == KeyEvent.VK_W) game.GUI.offsetY -= 25/game.GUI.scale;
		else if(key == KeyEvent.VK_A) game.GUI.offsetX -= 25/game.GUI.scale;
		else if(key == KeyEvent.VK_S) game.GUI.offsetY += 25/game.GUI.scale;
		else if(key == KeyEvent.VK_D) game.GUI.offsetX += 25/game.GUI.scale;
		else if(key == 192) System.exit(0); //Tilde
	}
	public void keyReleased(KeyEvent e) {}
	public void mouseMoved(MouseEvent arg0) {}
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
}