import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import model.Business;

public class CoiffeurMIDlet extends MIDlet {

	private static CoiffeurMIDlet instance;

	public static CoiffeurMIDlet getInstance() {
		return instance;
	}

	private Display myDisplay;

	public CoiffeurMIDlet() {
		Business.createBusiness();
		instance = this;
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		Business.getInstance().saveState();
	}

	protected void pauseApp() {
		Business.getInstance().saveState();

	}
	protected void startApp() throws MIDletStateChangeException {
		if (myDisplay == null) {
			System.out.println("load1");
			Business.loadStateNbClients();
			System.out.println("load2");
			Business.loadStateRecettes();
			System.out.println("load3");
			Business.loadStateDepenses();
			System.out.println("load4");
			Business.loadStatePertes();
			System.out.println("load5");
			Business.loadStatePrets();
			System.out.println("load6");
			// setCurrentScreen(new TaxiMEScreen(Business.getInstance()));
			this.initMIDlet();
			System.out.println("load7");
		}
	}

	private void initMIDlet() {
		myDisplay = Display.getDisplay(this);
		System.out.println("init1");
		//Displayable d = new TaxiMEScreen(Business.getInstance());
		System.out.println("init2");
		myDisplay.setCurrent(new SplashScreen());
	}

	public void setCurrentScreen(Displayable d) {
		Display.getDisplay(this).setCurrent(d);
	}

	public void setCurrentScreen(Alert a, Displayable d) {
		Display.getDisplay(this).setCurrent(a, d);
	}

	public void exitNow() {
		Business.getInstance().saveState();
		this.notifyDestroyed();
	}
}
