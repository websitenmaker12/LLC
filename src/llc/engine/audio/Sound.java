package llc.engine.audio;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class Sound {

	private int buffer;
	private int source;
	private boolean repeat;
	
	public final String path;
	
	public Sound(String path, boolean repeat) {
		this.path = path;
		this.repeat = repeat;
	}
	
	/**
	 * Loads the sound. This is called automatically. You don't need to do it manually.
	 */
	public void load() throws Exception {
		WaveData data = WaveData.create(new BufferedInputStream(new FileInputStream(this.path)));
		if(data == null) throw new FileNotFoundException("Sound file wasn't found!");
		
		this.buffer = AL10.alGenBuffers();
		AL10.alBufferData(this.buffer, data.format, data.data, data.samplerate);
		data.dispose();
		
		this.source = AL10.alGenSources();
		AL10.alSourcei(this.source, AL10.AL_BUFFER, this.buffer);
		AL10.alSourcef(this.source, AL10.AL_PITCH, 1);
		AL10.alSourcef(this.source, AL10.AL_GAIN, 25);
		AL10.alSource3f(this.source, AL10.AL_POSITION, 0, 0, 0);
		AL10.alSource3f(this.source, AL10.AL_VELOCITY, 0, 0, 0);
		AL10.alSourcei(this.source, AL10.AL_LOOPING, this.repeat ? AL10.AL_TRUE : AL10.AL_FALSE);
	}
	
	/**
	 * Plays the sound
	 */
	public void play() {
		AL10.alSourcePlay(this.source);
	}
	
	/**
	 * Pauses the sound
	 */
	public void pause() {
		AL10.alSourcePause(this.source);
	}
	
	/**
	 * Stops the sound
	 */
	public void stop() {
		AL10.alSourceStop(this.source);
	}
	
	/**
	 * Remove the sound from the game
	 */
	public void dispose() {
		this.stop();
		AL10.alDeleteBuffers(this.buffer);
		AL10.alDeleteSources(this.source);
	}
	
	/**
	 * Sets whether the sound repeats itself after finish playing
	 */
	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}
	
	/**
	 * Returns the unique source id
	 */
	public int getSourceID() {
		return this.source;
	}
	
	/**
	 * Copies the sound. After copying the sound have to be loaded!
	 */
	public Sound copy() {
		return new Sound(this.path, this.repeat);
	}
	
}
