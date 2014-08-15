package llc.engine.res;

import org.lwjgl.opengl.ARBShaderObjects;

public class Program {

	private int programID;
	private boolean isValidated = false;
	
	public Program() {
		this.programID = ARBShaderObjects.glCreateProgramObjectARB();
	}
	
	public void addShader(Shader shader) {
		if(this.isValidated) return;
		ARBShaderObjects.glAttachObjectARB(this.programID, shader.getShaderID());
	}
	
	public void validate() {
		this.isValidated = true;
		ARBShaderObjects.glLinkProgramARB(this.programID);
		ARBShaderObjects.glValidateProgramARB(this.programID);
	}

	public void bind() {
		ARBShaderObjects.glUseProgramObjectARB(this.programID);
	}
	
}
