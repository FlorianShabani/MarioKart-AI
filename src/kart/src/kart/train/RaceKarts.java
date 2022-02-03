package kart.train;

import java.awt.Graphics;

import kart.main.Map;
import kart.main.NKart;
import setup.ManagerA;

public class RaceKarts extends ManagerA {
	
	//The environment which simulates the karts race
	
	NKart[] karts;
	//How many checkpoints the kart has passed(start 0)
	public int[] kartCheckPoint;
	//Final score after passing last checkpoint or running out of time
	int[] finalScore;

	String mapType;
	Map map;
	//camI - index of the kart being focused by the camera
	int camI;
	//camIMax - int used to calculate which kart is leading
	int camIMax;
	//ticks++ in tick() - keeping track of time passed
	int ticks;
	//booleans used by the manual kart controller
	boolean tright = false, tleft = false, forward = false, backward = false;

	public RaceKarts(NKart[] karts, String mapType) {
		this.mapType = mapType;
		this.karts = karts;
		kartCheckPoint = new int[karts.length];
		finalScore = new int[karts.length];
		map = new Map(0, 0, mapType, false);
		reset();
	}

	public RaceKarts(NKart[] karts, String mapType, int width, int height) {
		this.mapType = mapType;
		this.karts = karts;
		kartCheckPoint = new int[karts.length];
		finalScore = new int[karts.length];
		map = new Map(width, height, mapType);
		reset();
	}

	//Reset the karts to the first checkPoint( use map.getStartingPosition())
	public void reset() {
		for(NKart k : karts) {
			int[] start = map.getStartingPosition();
			k.setX(start[0]);
			k.setY(start[1]);
			k.a = 3.14;
			k.velm = 0;
		}
	}
	//Run the simulation
	public int[] runSim() {
		//Run the stimulation for n ticks. (60ticks ~ 1second real time)
		for (int i = 0; i < 2500; i++) {
			tick();
		}
		//If score is 0, kart hasn't finished till the end, calculate score bases on how many checkpoints it has passed
		for(int i = 0; i < kartCheckPoint.length; i++) {
			if(finalScore[i] == 0) {
				finalScore[i] = (kartCheckPoint[i] * 50) - ticks + 2510  - karts[i].wallsHit;
			}
		}
		return finalScore;
	}

	//tick method - method which updates the state of the karts and the map
	@Override
	public void tick() {
		ticks++;
		map.tick(karts, kartCheckPoint);//Update map tick
		//Tick the karts based on manual/neural network
		for (NKart k : karts) {
			if (k.manual) {
				if (tright)
					k.steerR();
				else if (tleft)
					k.steerL();
				if (forward)
					k.driveF();
				else if (backward)
					k.driveB();
			}
			k.tick(map);
		}
		//Tick the karts
		//Check if kart is finished and calc score
		for(int i = 0; i < kartCheckPoint.length; i++) {
			if(kartCheckPoint[i] == map.nCheckPoints() && finalScore[i] == 0) {
				finalScore[i] = (kartCheckPoint[i] * 50) - ticks + 2510 - karts[i].wallsHit;
			}
		}
		//check which kart is leading and set the index of the camera to it
		for(int i = 0; i < karts.length; i++) {
			if(kartCheckPoint[i] > camIMax) {
				camI = i;
				camIMax = kartCheckPoint[i];
			}
				
		}
	}

	@Override
	public void draw(Graphics g) {
		map.draw(g, karts, camI);// !Check camI
	}
	
	//Controll of the user controlled kart
	@Override
	public void keyPressed(int e) {
		// WASD
		if (e == 68) {
			tright = true;
		} else if (e == 65) {
			tleft = true;
		}

		if (e == 87) {
			forward = true;
		} else if (e == 83) {
			backward = true;
		}

		if (e == 32) {// SPACE
			camI++;
			if (camI >= karts.length) {
				camI = 0;
			}
		}
	}

	@Override
	public void keyReleased(int e) {
		if (e == 68) {
			tright = false;
		} else if (e == 65) {
			tleft = false;
		}

		if (e == 87) {
			forward = false;
		} else if (e == 83) {
			backward = false;
		}
	}
}
