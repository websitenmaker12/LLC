package llc.engine.audio;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;

import llc.engine.res.Music;
import llc.engine.res.Sound;

/**
 * The sound engine. This is used to play sounds... obvious
 * @author MaxiHoeve14
 */
public class AudioEngine {
	
	public static Sound buttonClick;
	public static Music music_1;
	
	public AudioEngine() {
		
	}
	
	/**
	 * Init function: Creates AL and loads sounds.
	 * @throws LWJGLException 
	 */
	public void initAudioEngine() throws LWJGLException {
		AL.create();
		loadSounds();
	}
	
	/**
	 * Function that is used to load sounds.
	 */
	private void loadSounds() {
		buttonClick = new Sound("/res/sound/gui_click.wav");
		music_1 = new Music("/res/sound/music_1.ogg");
	}
	
	/**
	 * This function is used to play a sound at a given spot.
	 * Note that you also need to give the camera / player position due to some cool AL calculations.
	 * @param sound The {@link EnumSounds} to play.
	 * @param x	The source x position.
	 * @param y The source y position.
	 * @param z The source z position.
	 * @param playerX The player / camera x position.
	 * @param playerY The player / camera y position.
	 * @param playerZ The player / camera z position.
	 */
	public void playSoundAt(EnumSounds sound, float x, float y, float z, float playerX, float playerY, float playerZ) {
		if(sound == EnumSounds.BUTTONCLICK) buttonClick.playSound(x, y, z, playerX, playerY, playerZ);
	}
	
	/**
	 * This function is used to play a sound without giving some location. The result is that
	 * the volume is the same at every location. This is used to play GUI button sounds for example.
	 * @param sound The {@link Sounds} to play.
	 */
	public void playSound(EnumSounds sound) {
		if(sound == EnumSounds.BUTTONCLICK) buttonClick.playSound(0, 0, 0, 0, 0, 0);
	}
	
	/**
	 * This function is used to play music.
	 * @param music The {@link EnumMusic} to be played.
	 */
	public void playMusic(EnumMusic music) {
		if (music == EnumMusic.MUSIC1) music_1.playMusic();
	}
	
	/**
	 * This function should be called when the sound engine is not used anymore.
	 * It frees all the sound engines memory.
	 */
	public void dispose() {
		buttonClick.dispose();
	}
}
