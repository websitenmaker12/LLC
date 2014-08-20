package llc.engine.audio;

import llc.LLC;

import org.lwjgl.openal.AL10;

public class SoundSource {

	private float x;
	private float y;
	public String name;
	public Sound sound;
	public int channel;
	
	private final int source;
	public boolean isPlaying = false;
	
	public SoundSource(float x, float y, String name, int channel) {
		this.name = name;
		this.channel = channel;
		
		this.sound = LLC.getLLC().soundEngine.getSound(this.name).copy();
		try {
			this.sound.load();
		} catch (Exception e) {
		}
		this.source = this.sound.getSourceID();
		this.translate(x, y);
	}
	
	/**
	 * Sets the pitch
	 */
	public void pitch(float pitch) {
		AL10.alSourcef(this.source, AL10.AL_PITCH, pitch);
	}
	
	/**
	 * Sets the gain
	 */
	public void gain(float gain) {
		AL10.alSourcef(this.source, AL10.AL_GAIN, gain);
	}

	/**
	 * Sets the velocity
	 */
	public void setVelocity(float x, float y) {
		AL10.alSource3f(this.source, AL10.AL_VELOCITY, x, y, 0);
	}
	
	/**
	 * Translates the source
	 */
	public void translate(float x, float y) {
		this.x = x;
		this.y = y;
		AL10.alSource3f(this.source, AL10.AL_POSITION, this.x, this.y, 0);
	}

	/**
	 * Moves the source
	 */
	public void move(float dx, float dy) {
		this.translate(this.x + dx, this.y + dy);
	}
	
	/**
	 * Plays the sound
	 */
	public void play() {
		this.sound.play();
	}
	
	/**
	 * Pauses the sound
	 */
	public void pause() {
		this.sound.pause();
	}
	
	/**
	 * Stops the sound
	 */
	public void stop() {
		this.sound.stop();
	}
	
	/**
	 * Deletes the sound
	 */
	public void dispose() {
		this.sound.dispose();
	}

	/**
	 * Returns the x-coordinate for the source
	 */
	public float getX() {
		return this.x;
	}
	
	/**
	 * Returns the y-coordinate for the source
	 */
	public float getY() {
		return this.y;
	}
	
}
