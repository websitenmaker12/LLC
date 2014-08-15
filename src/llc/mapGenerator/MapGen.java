package llc.mapGenerator;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import llc.mapGenerator.PerlinNoise;



public class MapGen {
	public static PerlinNoise perlin = new PerlinNoise(75756434);
	public static void main(String[] args) throws IOException 
	{
		start();
			
		BufferedImage buffy = new BufferedImage(30, 30, BufferedImage.TYPE_INT_RGB);
		buffy.setRGB(0, 10, 0);
		System.out.println(buffy.getRGB(0, 10));
		ImageIO.write(buffy, "BMP", new File("map.bmp"));
	}
		
	public static void start()
	{
		for (int x = 0; x < 100; x++)
		{
//			for (int y = 0; y < 100; y++)
//			System.out.println(perlin.Noise((double) x/100.0f, (double) y/100.f, (double) 1)*10.0f);
		}
	}
			
		
	}


