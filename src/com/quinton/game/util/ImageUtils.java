package com.quinton.game.util;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferByte;

import static com.quinton.game.util.MathUtils.*;

public class ImageUtils {

	private ImageUtils() {
		
	}
	
	public static BufferedImage changeBrightness(BufferedImage original, int amount) {
	
		
		BufferedImage result = new BufferedImage(original.getWidth(),original.getHeight(),BufferedImage.TYPE_INT_ARGB);
		byte[] pixels = ((DataBufferByte)original.getRaster().getDataBuffer()).getData();
		int[] resultPixels = ((DataBufferInt)result.getRaster().getDataBuffer()).getData();

		int offset = 0;
		
		for(int yy=0;yy<original.getHeight();yy++) {
			for(int xx=0;xx<original.getWidth();xx++) {
				int a = Byte.toUnsignedInt(pixels[offset++]);
				int r = Byte.toUnsignedInt(pixels[offset++]);
				int g = Byte.toUnsignedInt(pixels[offset++]);
				int b = Byte.toUnsignedInt(pixels[offset++]);
				
				r+= clamp(r+amount,0,255);
				g+= clamp(g+amount,0,255);
				b+= clamp(b+amount,0,255);
				
				
	
				resultPixels[xx+yy*original.getWidth()] = a << 24 | r << 16 | g << 8 | b;
			}
		}
		
		return result;
		
		/*
		 
		 for(int yy=0;yy<original.getHeight();yy++) {
			for(int xx=0;xx<original.getWidth();xx++) {
				int color = newPixels[xx + yy *original.getWidth()];
				int r = (color & 0xff0000) >> 16;
				int g = (color & 0xff00) >> 8;
				int b = color & 0xff;
				
				r+= 50;
				g+= 50;
				b+= 50;
				
				color &= 0xff000000;
				newPixels[xx+yy*original.getWidth()] = original.getRGB(xx,yy);
			}
			
		// make image brighter
		for(int yy=0;yy<original.getHeight();yy++) {
			for(int xx=0;xx<original.getWidth();xx++) {
				int color = newPixels[xx + yy *original.getWidth()];
				int r = (color & 0xff0000) >> 16;
				int g = (color & 0xff00) >> 8;
				int b = color & 0xff;
				
				r+= 50;
				g+= 50;
				b+= 50;
				
				color &= 0xff000000;
				newPixels[xx+yy*original.getWidth()] = color| r << 16 | g << 8 | b;
			}
			
			
		}
		*/
		
	}
}
