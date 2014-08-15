package llc.engine.res;

import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 * @author MaxiHoeve14
 */
public class Sound {

	String path;
	
	Audio audio;
	
	public Sound(String path) {
		this.path = path;
	}
	
	/**
	 * This function is used to load the sound.
	 */
	public Sound loadSound() {
		try {
			audio = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	/**
	 * This function is used to play a sound at a given location.
	 * @param posX The source x pos.
	 * @param posY The source y pos.
	 * @param posZ The source z pos.
	 * @param flag PlayAtCamera
	 */
	public void playSound(float posX, float posY, float posZ, boolean flag) {
		if(flag) audio.playAsSoundEffect(1.0f, 1.0f, false, posX, posY, posZ);
		else audio.playAsSoundEffect(1.0f, 1.0f, false);
	}
	
	/**
	 * This function is used to free the sounds memory.
	 */
	public void dispose() {
	}
}
