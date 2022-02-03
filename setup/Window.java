package setup;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Window extends Canvas
		implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;

	public int Width, Height;

	private double TPS, FPS;
	

	JFrame f;
	
	private Thread thread;
	private boolean running = false;

	private float fps = 0;

	Manager manager;

	public Window(int width, int height, int TPS, int FPS, String title,
			Manager manager) {
		this.manager = manager;
		Width = width;
		Height = height;

		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		setMinimumSize(new Dimension(WIDTH, HEIGHT));

		this.TPS = TPS;
		this.FPS = FPS;
		fps = FPS;

		setFocusable(true);
		requestFocus();

		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);

		f = new JFrame();
		f.add(this);
		f.pack();
		f.setSize(Width, Height);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle(title);
		f.setVisible(true);

		this.start();
	}

	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if (!running)
			return;

		running = false;
		try {
			thread.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	@Override
	public void run() {
		long count = System.currentTimeMillis();
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / TPS;
		double nsPerFPS = 1000000000D / FPS;
		double delta = 0;
		double delta2 = 0;
		long now;
		while (running) {
			now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			delta2 += (now - lastTime) / nsPerFPS;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			while (delta2 >= 1) {
				render();
				delta2--;
			}
			if (System.currentTimeMillis() - count >= 1000) {
				//System.out.println(fps);
				count = System.currentTimeMillis();
				fps = 0;
			}
		}
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		rh.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		g2d.setRenderingHints(rh);

		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, getWidth(), getHeight());

		manager.draw(g2d);

		fps++;
		g2d.dispose();
		bs.show();
	}

	public JFrame getJFrame() {
		return f;
	}

	public void tick() {
		manager.tick();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		manager.mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		manager.mouseMoved(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		manager.mouseClicked(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		manager.mouseEntered(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		manager.mouseExited(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		manager.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		manager.mouseReleased(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		manager.keyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		manager.keyReleased(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {
		manager.keyTyped(e.getKeyCode());
	}
}
