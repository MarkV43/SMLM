package com.estudante.sc.senai.br.lhama.smlm;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ZClip {

	private List<Clip> clips;

	private String path;

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
			InputStream is = this.getClass().getClassLoader().getResourceAsStream(path);
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(is);

			clip.open(inputStream);
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
				InputStream ist = this.getClass().getClassLoader().getResourceAsStream(path);
				AudioInputStream inputStream = AudioSystem.getAudioInputStream(ist);

				clip.open(inputStream);
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
}
