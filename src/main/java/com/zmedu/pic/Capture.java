package com.zmedu.pic;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

public class Capture
{

    public static void main(String[] args) throws IOException
    {
    	Webcam webcam = Webcam.getDefault();
		webcam.setCustomViewSizes(WebcamResolution.VGA.getSize());
		webcam.setViewSize(WebcamResolution.VGA.getSize());
    	webcam.open();
    	ImageIO.write(webcam.getImage(), "JPG", new File("pic.jpg"));
    }
}
