package llc.engine.res;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;

public class Shader {

	public static final int vertexShader = ARBVertexShader.GL_VERTEX_SHADER_ARB;
	public static final int fragmentShader = ARBFragmentShader.GL_FRAGMENT_SHADER_ARB;
	
	private String path;
	private int shaderType;
	private int programID;
	
	public Shader(String path, int shaderType) {
		this.path = path;
		this.shaderType = shaderType;
		
		this.load();
	}
	
	private void load() {
		int shaderID = 0;
		BufferedReader reader = null;
		
		try {
			shaderID = ARBShaderObjects.glCreateShaderObjectARB(this.shaderType);
			if(shaderID == 0) return;
			
			// Load file
			StringBuilder builder = new StringBuilder();
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.path)));
			
			String line;
			while((line = reader.readLine()) != null) builder.append(line).append("\n");
			reader.close();
			
			// Compile shader
			ARBShaderObjects.glShaderSourceARB(shaderID, builder.toString());
			ARBShaderObjects.glCompileShaderARB(shaderID);
			
			if(ARBShaderObjects.glGetObjectParameteriARB(shaderID, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE) {
				throw new RuntimeException("Error by creating shader: " + this.getLog(shaderID));
			}
			
			this.programID = ARBShaderObjects.glCreateProgramObjectARB();
			if(this.programID == 0) return;
			
			ARBShaderObjects.glAttachObjectARB(this.programID, shaderID);
			ARBShaderObjects.glLinkProgramARB(this.programID);
		} catch(Exception e) {
			ARBShaderObjects.glDeleteObjectARB(shaderID);
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String getLog(int shaderID) {
		if(shaderID == 0) return "";
		return ARBShaderObjects.glGetInfoLogARB(shaderID, ARBShaderObjects.glGetObjectParameteriARB(shaderID, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
	}
	
	public void bind() {
		ARBShaderObjects.glUseProgramObjectARB(this.programID);
	}
	
}
