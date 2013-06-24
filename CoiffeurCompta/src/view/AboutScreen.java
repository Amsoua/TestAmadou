package view;

import java.io.IOException;

import CoiffeurMIDlet;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;

import model.Business;

public class AboutScreen extends Form implements CommandListener {

	private Command cmdRetour;
	private Image imgSenmobile;

	public AboutScreen() {
		super("A propos");
		this.append("CoiffeurCompta v1.1\n");
		this.append("SenMobile \nsenmobileapp@senmobile.com \nhttp://senmobile.com");
		try {
			imgSenmobile = Image.createImage("/senmobile.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.append(imgSenmobile);
		this.append("\nSenMobile respecte la vie privée de ses clients. Lorsque l'utilisateur utilise la fonction SMS pour envoyer un SMS, l'application n'utilise cette information qu'une seule fois pour envoyer le SMS. Elle ne recueille pas et ne stocke pas le numéro de téléphone utilisé. L'application ne collecte aucune information.");
		cmdRetour = new Command("Retour", Command.BACK, 1);
		this.setCommandListener(this);
		this.addCommand(cmdRetour);
	}

	public void commandAction(Command cmd, Displayable arg1) {
		if (cmd == cmdRetour) {
			System.out.print("Retour");
			CoiffeurMIDlet.getInstance().setCurrentScreen(
					new CoiffeurScreen(Business.getInstance()));
		}
	}

}
