import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class Launcher extends JFrame
{
	private static final long serialVersionUID = 5566322968333965313L;
	final public static JFrame frame = new JFrame("Particles");
	public static void createWindow()
	{
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){System.exit(0);}});
		frame.add(new Game());
		frame.pack();
		frame.setSize(500, 500);
		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH );
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	public static void main(String[] args) { createWindow(); }
}