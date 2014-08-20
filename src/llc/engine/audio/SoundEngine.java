package llc.engine.audio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import llc.engine.Camera;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;

public class SoundEngine {

	public Map<String, Sound> sounds = new HashMap<String, Sound>();
	public Map<Integer, List<SoundSource>> sources = new HashMap<Integer, List<SoundSource>>();
	
	private Camera camera;
	
	/**
	 * Plays a sound without transformation
	 */
	public void playSound(String name) {
		Sound sound = sounds.get(name);
		if(sound != null) sound.play();
	}
	
	/**
	 * Initializes the SoundEngine
	 */
	public void init() {
		try {
			AL.create();
			for(Sound sound : this.sounds.values()) sound.load();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		this.setPosition(0, 0, 0);
		this.setVelocity(1, 1, 1);
		this.setOrientation(0, 0, 0);
	}
	
	/**
	 * Deletes all sound and disposes the SoundEngine
	 */
	public void dispose() {
		for(Sound sound : this.sounds.values()) sound.dispose();
		this.sounds.clear();
		if(AL.isCreated()) AL.destroy();
	}
	
	/**
	 * Adds a sound for loading to the engine
	 */
	public void addSound(String name, Sound sound) {
		if(!this.sounds.containsKey(name)) this.sounds.put(name, sound);
	}
	
	/**
	 * Returns the sound for the given name
	 */
	public Sound getSound(String name) {
		return this.sounds.get(name);
	}
	
	/**
	 * Sets the position of the listener
	 */
	public void setPosition(float x, float y, float z) {
		AL10.alListener3f(AL10.AL_POSITION, x, y, z);
	}

	/**
	 * Sets the velocity of the listener
	 */
	public void setVelocity(float x, float y, float z) {
		AL10.alListener3f(AL10.AL_VELOCITY, x, y, z);
	}

	/**
	 * Sets the orientation of the listener
	 */
	public void setOrientation(float x, float y, float z) {
		AL10.alListener3f(AL10.AL_ORIENTATION, x, y, z);
	}

	/**
	 * Adds a SoundSource to the engine
	 */
	public void addSource(SoundSource source) {
		if(!this.sources.containsKey(source.channel)) this.sources.put(source.channel, new ArrayList<SoundSource>());
		this.sources.get(source.channel).add(source);
	}
	
	/**
	 * Pauses all sounds
	 */
	public void pauseAllSounds() {
		for(Entry<Integer, List<SoundSource>> entry : this.sources.entrySet()) {
			for(SoundSource source : entry.getValue()) {
				source.pause();
			}
		}
	}
	
	/**
	 * Stops all sounds
	 */
	public void stopAllSounds() {
		for(Entry<Integer, List<SoundSource>> entry : this.sources.entrySet()) {
			for(SoundSource source : entry.getValue()) {
				source.stop();
			}
		}
	}
	
	/**
	 * Pauses all sound on a channel
	 */
	public void pauseChannel(int channel) {
		for(SoundSource source : this.sources.get(channel)) source.pause();
	}
	
	/**
	 * Stops all sounds on a channel
	 */
	public void stopChannel(int channel) {
		for(SoundSource source : this.sources.get(channel)) source.stop();
	}

	/**
	 * Binds the given camera to the engine
	 */
	public void bindCamera(Camera camera) {
		this.camera = camera;
	}
	
	/**
	 * Updates the engine
	 */
	public void update(int delta) {
		if(this.camera != null) {
			this.setPosition(this.camera.pos.x, this.camera.pos.y, this.camera.pos.z);
			this.setOrientation(this.camera.viewDir.x, this.camera.viewDir.y, this.camera.viewDir.z);
		}
	}
	
}
