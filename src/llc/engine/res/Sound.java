package llc.engine.res;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class Sound {

	IntBuffer buffer = BufferUtils.createIntBuffer(1);
	IntBuffer source = BufferUtils.createIntBuffer(1);
	public static WaveData data;
	public Sound(String path) {
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public void loadSound() {
		AL10.alGenBuffers(buffer);
		
		data = WaveData.create("res/sound/buttonClick.wav");
		AL10.alBufferData(buffer.get(0), data.format, data.data, data.samplerate);
		data.dispose();
	}
	
	public void playSound(float posX, float posY, float posZ, float lPosX, float lPosY, float lPosZ) {
		FloatBuffer sourcePos = BufferUtils.createFloatBuffer(3).put(new float[] { posX, posY, posZ });
		FloatBuffer listenerPos = BufferUtils.createFloatBuffer(3).put(new float[] { lPosX, lPosY, lPosZ });
		
		AL10.alSourcei(source.get(0), AL10.AL_BUFFER,   buffer.get(0) );
		AL10.alSourcef(source.get(0), AL10.AL_PITCH,    1.0f          );
		AL10.alSourcef(source.get(0), AL10.AL_GAIN,     1.0f          );
		AL10.alSource (source.get(0), AL10.AL_POSITION, sourcePos     );
		
		AL10.alListener(AL10.AL_POSITION,    listenerPos);
		
		AL10.alSourcePlay(source.get(0));
	}
	
	public void dispose() {
		AL10.alDeleteSources(source);
		AL10.alDeleteBuffers(buffer);
	}
}
