package setup;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

public interface Manager {
	public void draw(Graphics g);
	public void tick();
	
	public void mouseDragged(MouseEvent e);

	public void mouseMoved(MouseEvent e);

	public void mouseClicked(MouseEvent e);

	public void mouseEntered(MouseEvent e);

	public void mouseExited(MouseEvent e);

	public void mousePressed(MouseEvent e);

	public void mouseReleased(MouseEvent e);

	public void keyPressed(int e);

	public void keyReleased(int e);

	public void keyTyped(int e);
}
