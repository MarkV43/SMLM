package com.estudante.sc.senai.br.lhama.smlm;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ZClip {

	private List<Clip> clips;

	private String path;
	public static float defaultVolume = 0.5f;
	public static float musicVolume = 0.1f;
	private float volume = defaultVolume;

	public ZClip(String path) {
		this.path = "sounds/" + path;
		if (!path.contains(".")) {
			this.path += ".wav";
		}
		clips = new ArrayList<>();
	}

	public synchronized int play() {
		try {
			Clip clip = AudioSystem.getClip();
			URL u = this.getClass().getClassLoader().getResource(path);
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(u);

			clip.open(inputStream);
			setVolume(clip, volume);
			clip.start();
			clips.add(clip);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return clips.size() - 1;
	}

	public synchronized int loop(int i) {
			try {
				Clip clip = AudioSystem.getClip();
				URL u = this.getClass().getClassLoader().getResource(path);
				AudioInputStream inputStream = AudioSystem.getAudioInputStream(u);

				clip.open(inputStream);
				setVolume(clip, volume);
				clip.loop(i - 1);
				clips.add(clip);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return clips.size() - 1;
	}

	public void stop(int i) {
		clips.get(i).stop();
	}

	public void wait(int i, Runnable r) {
		new Thread(() -> {
			Clip c = clips.get(i);
			while (c.getFramePosition() < c.getFrameLength()) {
				Thread.yield();
			}
			r.run();
		}).start();
	}

	public ZClip setVolume(float volume) {
		if(volume < 0 || volume > 1) {
			throw new IllegalArgumentException("Volume not valid: " + volume);
		}
		this.volume = volume;
		return this;
	}

	public float getVolume() {
		return volume;
	}

	public float getVolume(int i) {
		FloatControl control = (FloatControl) clips.get(i).getControl(FloatControl.Type.MASTER_GAIN);
		return (float) Math.pow(10f, control.getValue() / 20f);
	}

	public void setVolume(Clip c, float volume) {
		if(volume < 0 || volume > 1) {
			throw new IllegalArgumentException("Volume not valid: " + volume);
		}
		FloatControl control = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
		control.setValue(20f * (float) Math.log10(volume));
	}
}
