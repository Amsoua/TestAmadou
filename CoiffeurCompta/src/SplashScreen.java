/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * URL: http://www.peterscorner.co.uk
 * Date: 02-Oct-2009
 * Time: 17:46:18
 */
import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import model.Business;

import view.CoiffeurScreen;

/**
 * A simple splash screen.
 */
public class SplashScreen extends Canvas implements Runnable {

	private Image img; // img for the splash
	private String imageFilename = "/coiffeurcompta_splash_high.png";
	private int backgroundColour = 0x00fecf1f; 
	private String splashText = "CoiffeurCompta";
	private boolean blank = false;

	/**
	 * The constructor attempts to load the named image and begins a timeout
	 * thread. The splash screen can be dismissed with a key press, a pointer
	 * press, or a timeout
	 * 
	 * @param projectMIDlet
	 *            instance of MIDlet
	 */
	public SplashScreen() {
		setFullScreenMode(true);
		try {
			img = Image.createImage(imageFilename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread t = new Thread(this);
		t.start();
	}

	/**
	 * Paints the image centered on the screen.
	 */
	public void paint(Graphics g) {
		// do your drawing here
		int width = getWidth();
		int height = getHeight();

		// fill a background (in case the image is smaller than the screen)
		g.setColor(backgroundColour);
		g.fillRect(0, 0, width, height);

		if (!blank) {
			if (img != null) {
				// centre the image on the screen
				g.drawImage(img, width / 2, height / 2, Graphics.HCENTER
						| Graphics.VCENTER);
			} else {
				g.setColor(~backgroundColour);
				g.drawString(splashText, width / 2, height / 2,
						Graphics.HCENTER | Graphics.BASELINE);
			}
		}

		// int width = getWidth();
		// int height = getHeight();
		//
		// // set background color to overdraw what ever was previously
		// displayed
		// g.setColor(0x000000);
		// g.fillRect(0, 0, width, height);
		// g.drawImage(mImage, width / 2, height / 2, Graphics.HCENTER
		// | Graphics.VCENTER);
	}

	/**
	 * Dismisses the splash screen with a key press or a pointer press
	 */
	public void dismiss() {
		if (isShown()) {
			blank = true;
			repaint();
			serviceRepaints();

			CoiffeurMIDlet.getInstance().setCurrentScreen(
					new CoiffeurScreen(Business.getInstance()));
		}
	}

	/**
	 * Default timeout with thread
	 */
	public void run() {
		try {
			Thread.sleep(3000);// set for 3 seconds
		} catch (InterruptedException e) {
			System.out.println("Splash interruptedException");
			e.printStackTrace();
		}
		dismiss();
	}

	/**
	 * A key release event triggers the dismiss() method to be called.
	 */
	public void keyReleased(int keyCode) {
		dismiss();
	}

	/**
	 * A pointer release event triggers the dismiss() method to be called.
	 */
	public void pointerReleased(int x, int y) {
		dismiss();
	}
}