package br.senai.sc.engine;

public class Fps {
	private int targetFps = 30;
	private long optimalTime;
	private long lastFrameTime;
	private int fps;
	private long lastFPSMs;
	private boolean pause;
	private double frameRate;
	private long deltaTime;
	private double loss;

	public Fps() {
		optimalTime = (long) (1000 / this.targetFps);
		updateDelta();
		lastFPSMs = getTime();
	}

	public Fps(int fps) {
		targetFps = fps;
		optimalTime = (long) (1000 / this.targetFps);
		updateDelta();
		lastFPSMs = getTime();
	}

	public int getTargetFps() {
		return targetFps;
	}

	public void setTargetFps(int targetFps) {
		this.targetFps = targetFps;
	}

	public long getOptimalTime() {
		return optimalTime;
	}

	public void setOptimalTime(long optimalTime) {
		this.optimalTime = optimalTime;
	}

	public long getLastFrameTime() {
		return lastFrameTime;
	}

	public void setLastFrameTime(long lastFrameTime) {
		this.lastFrameTime = lastFrameTime;
	}

	public int getFps() {
		return this.fps;
	}

	public double getFrameRate() {
		return frameRate;
	}

	public void setFps(int fps) {
		this.fps = fps;
	}

	public long getLastFPSMs() {
		return lastFPSMs;
	}

	public void setLastFPSMs(long lastFPSMs) {
		this.lastFPSMs = lastFPSMs;
	}

	public long getTime() {
		return System.nanoTime() / 1000000;
	}

	public void updateDelta() {
		long time = getTime();
		deltaTime = (int) (time - lastFrameTime);
		lastFrameTime = time;
	}

	public long getDelta() {
		return deltaTime;
	}

	public void updateFPS() {
		updateDelta();
		frameRate = 1000D / (double) deltaTime;

		if (lastFPSMs > 1000) {
			fps = 0;
			lastFPSMs = 0;
		}

		lastFPSMs += deltaTime;
		++this.fps;
	}

	public void synchronize(boolean p) {
		if (p) {
			while (pause) {
				System.out.print("");
			}
		}

		deltaTime = lastFrameTime - getTime();
		long ms = deltaTime + optimalTime;

		try {
			if (ms > 0L) {
				Thread.sleep(ms);
			}
		} catch (InterruptedException var4) {
			System.err.println(var4.getMessage());
			System.exit(-1);
		}

	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}
}
