package llc.mapGenerator;

import java.awt.image.BufferedImage;
import java.util.Random;

public class RandomMapGenerator {
	
	public static BufferedImage generateMap(int mapsize){
		Random r = new Random();
		PerlinNoise perlinNoise = new PerlinNoise(r.nextInt(9999));
	    BufferedImage bitmap = new BufferedImage(mapsize, mapsize, BufferedImage.TYPE_INT_ARGB);
		double widthDivisor = 1 / (double)mapsize;
	    double heightDivisor = 1 / (double)mapsize;
	    
		for(int y = 0; y<mapsize; y++){
			for(int x = 0; x<mapsize; x++){
				// Result from the noise function is in the range -1 to 1
				double v =
	                // First octave
	                (perlinNoise.Noise(2 * x * widthDivisor, 2 * y * heightDivisor, -0.5) + 1) / 2 * 0.7 +
	                // Second octave
	                (perlinNoise.Noise(4 * x * widthDivisor, 4 * y * heightDivisor, 0) + 1) / 2 * 0.2 +
	                // Third octave
	                (perlinNoise.Noise(24 * x * widthDivisor, 24 * y * heightDivisor, 0.5) + 1) / 2 * 0.1;
				 	// Multiplier of 32 gives more detailed maps, values below this lower the detail
	
				// Clamp v to range from 0 to 1
	            v = Math.min(1, Math.max(0, v));
	            
	            
	            // Height calculation of doom
	            // WARNING! highly experimental. pure trial & error...
	            double heightlevel = (Math.pow(v * 50.0, 2) * 1.0) - 500.0;
	            		       
	            // Clamp the value to 0 - 255
	            int pixelval = (int)(Math.max(Math.min(heightlevel, 255), 1));
	            
				//System.out.println(pixelval + " " + heightlevel + " " + v);
				
				// color calculation (bit shifts), R G B A
				int col = (pixelval << 16) | (pixelval << 8) | pixelval | 0xFF000000;
				
				// set pixel
				bitmap.setRGB(x, y, col);
				
			}
		}
			
		return bitmap;
			
	}
}
