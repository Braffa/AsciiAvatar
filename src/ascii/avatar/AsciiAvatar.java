package ascii.avatar;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class AsciiAvatar {
	
	private boolean negative;
	private int targetWidth = 150;
	private int targetHeight = 100;	
	
	private BufferedImage resizeImage(BufferedImage image) {
	    int originalWidth = image.getWidth();
	    int originalHeight = image.getHeight();
	    int newWidth = originalWidth;
	    int newHeight = originalHeight;
	    if (originalWidth > targetWidth) {
	        //scale width to fit
	    	newWidth = targetWidth;
	        //scale height to maintain aspect ratio
	    	newHeight = (newWidth * originalHeight) / originalWidth;
	    }		
	    if (newHeight > targetHeight) {
	        //scale height to fit instead
	    	newHeight = targetHeight;
	        //scale width to maintain aspect ratio
	        newWidth = (newHeight * originalWidth) / originalHeight;
	    }
	    BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, image.getType());
	    Graphics2D g = resizedImage.createGraphics();
	    g.drawImage(image, 0, 0, newWidth, newHeight, null);
	    g.dispose();
        return resizedImage;
    }
	
	private String convert(BufferedImage image) {
		StringBuilder sb = new StringBuilder((image.getWidth() + 1) * image.getHeight());
		for (int y = 0; y < image.getHeight(); y++) {
			if (sb.length() != 0) {
				sb.append("\n");
			}
			for (int x = 0; x < image.getWidth(); x++) {
				Color pixelColor = new Color(image.getRGB(x, y));
				double gValue = (double) pixelColor.getRed() * 0.2989 + (double) pixelColor.getBlue() * 0.5870 + (double) pixelColor.getGreen() * 0.1140;
				char s = negative ? returnStrNeg(gValue) : returnStrPos(gValue);
				sb.append(s);
			}
		}
		return sb.toString();
	}
	
	private char returnStrPos(double g) {
		final char str;
		if (g >= 230.0) {
			str = '.';
		} else if (g >= 200.0) {
			str = ':';
		} else if (g >= 180.0) {
			str = '_';
		} else if (g >= 160.0) {
			str = '=';
		} else if (g >= 130.0) {
			str = '+';
		} else if (g >= 100.0) {
			str = '*';
		} else if (g >= 70.0) {
			str = '#';
		} else if (g >= 50.0) {
			str = '%';
		} else {
			str = '@';
		}
		return str; 
	}
	
	private char returnStrNeg(double g) {
		final char str;
		if (g >= 230.0) {
			str = '@';
		} else if (g >= 200.0) {
			str = '%';
		} else if (g >= 180.0) {
			str = '#';
		} else if (g >= 160.0) {
			str = '*';
		} else if (g >= 130.0) {
			str = '+';
		} else if (g >= 100.0) {
			str = '=';
		} else if (g >= 70.0) {
			str = '_';
		} else if (g >= 50.0) {
			str = ':';
		} else {
			str = '.';
		}
		return str;
	}
	
	private String  makeAvatar (String imagePath) {
		try {
		BufferedImage image = ImageIO.read(new File(imagePath));
		if (image.getWidth() > 150 || image.getHeight() > 100) {
			return convert(resizeImage(image));
		}
		return convert(image);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		
		//String imagePath =  "C:/Users/david.a.brayfield/workspace/asciiavatar/asciiavatar/images/617.jpg";
		//String imagePath =  "C:/Users/david.a.brayfield/workspace/asciiavatar/asciiavatar/images/dave in garden.jpg";
		//String imagePath =  "C:/Users/david.a.brayfield/workspace/asciiavatar/asciiavatar/images/Koala.jpg";
		
		String imagePath = "";
		if (args.length == 0 ) {
			imagePath =  "C:/Users/david.a.brayfield/workspace/asciiavatar/asciiavatar/images/617.jpg";
		} else {
			imagePath = args [0];
		}
		String ascii = new AsciiAvatar().makeAvatar(imagePath);
		System.out.println(ascii);
	}
}
