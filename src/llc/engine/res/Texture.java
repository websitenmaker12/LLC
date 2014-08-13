package llc.engine.res;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.glu.GLU;

/**
 * @author BennyQBD on GitHub
 */
public class Texture {

	public final String path;
	private int textureID;
	
	public Texture(String path) {
		this.path = path;
		this.load();
	}
	
	/**
	 * Binds this Texture to OpenGL
	 */
	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureID);
	}
	
	/**
	 * Loads the Texture. Don't call manually!
	 */
	public void load() {
		try {
			BufferedImage image = ImageIO.read(new File(this.path));
			int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
			
			ByteBuffer buffer = BufferUtils.createByteBuffer(image.getHeight() * image.getWidth() * 4);
			boolean alpha = image.getColorModel().hasAlpha();
			
			for(int y = 0; y < image.getHeight(); y++) {
				for(int x = 0; x < image.getWidth(); x++) {
					int pixel = pixels[y * image.getWidth() + x];
					
					buffer.put((byte)((pixel >> 16) & 0xFF));
					buffer.put((byte)((pixel >> 8)  & 0xFF));
					buffer.put((byte)( pixel 		& 0xFF));
					if(alpha) buffer.put((byte)((pixel >> 24) & 0xFF));
					else buffer.put((byte)0xFF);
				}
			}
			
			buffer.flip();
			
			this.textureID = GL11.glGenTextures();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureID);
			
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			
			GLU.gluBuild2DMipmaps(GL11.GL_TEXTURE_2D, GL11.GL_RGBA8, image.getWidth(), image.getHeight(), GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
			//GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Deletes the Texture
	 */
	public void dispose() {
		GL11.glDeleteTextures(this.textureID);
	}
	
}
