package llc.engine.res;

import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 * The Music file. This is used to play music as a stream.
 * @author MaxiHoeve14
 */
public class Music {
	
	private String path;
	
	private Audio music;
	
	public Music(String path) {
		this.path = path;
	}
	
	/**
	 * This function is used to load the audio file.
	 */
	public Music loadMusic() throws IOException {
		music = AudioLoader.getStreamingAudio("OGG", ResourceLoader.getResource(path));
		return this;
	}
	
	/**
	 * This function is used to play a music track.
	 */
	public void playMusic() {
		music.playAsMusic(1.0f, 1.0f, true);
	}
}
