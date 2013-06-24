package view;

import java.io.IOException;

import CoiffeurMIDlet;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;

import model.Business;


public class CoiffeurScreen extends Form implements CommandListener {

	private Command cmdRecettes, cmdDepenses, cmdHelp,
			cmdAbout, cmdPrets, cmdPertes, cmdSMS, cmdRAZ,
			cmdDetails;
	private final Command cmdExit;
	private Command cmdOui;
	private Command cmdNon;
	private Alert alertConf;

	public CoiffeurScreen(Business b) {
		super("CoiffeurCompta");

		this.cmdRecettes = new Command("Recettes", Command.SCREEN, 1);
		this.addCommand(cmdRecettes);

		this.cmdDepenses = new Command("Dépenses", Command.SCREEN, 2);
		this.addCommand(cmdDepenses);

		this.cmdPrets = new Command("Prêts", Command.SCREEN, 3);
		this.addCommand(cmdPrets);

		this.cmdPertes = new Command("Pertes", Command.SCREEN, 4);
		this.addCommand(cmdPertes);

		this.cmdDetails = new Command("Détails", Command.SCREEN, 5);
		this.addCommand(cmdDetails);
		
		try {
			// Check JSR 120
			Class.forName("javax.wireless.messaging.TextMessage");
			this.cmdSMS = new Command("SMS", Command.SCREEN, 6);
			this.addCommand(cmdSMS);
		} catch (Throwable e) {
			// No SMS support
		}

		this.cmdRAZ = new Command("RAZ", Command.SCREEN, 7);
		this.addCommand(cmdRAZ);
		
		this.cmdHelp = new Command("Aide", Command.SCREEN, 8);
		this.addCommand(cmdHelp);
		
		this.cmdAbout = new Command("A propos", Command.SCREEN, 9);
		this.addCommand(cmdAbout);

		this.cmdExit = new Command("Sortir", Command.EXIT, 10);
		this.addCommand(cmdExit);

		ImageItem imageItem;
		try {
			imageItem = new ImageItem(null, Image.createImage("/intro_high.png"),
					ImageItem.LAYOUT_CENTER, "");

			this.append(imageItem);
		} catch (IOException e) {
			e.printStackTrace();
		}

		final String nbClientsItem = "\n" + String.valueOf(b.getNbClients())
				+ " clients\n";
		this.append(nbClientsItem);

		final String gainsItem = "Gains : " + String.valueOf(b.getGains())
				+ "\n";
		this.append(gainsItem);

		final String recettesItem = "Recettes : "
				+ String.valueOf(b.getRecettes()) + "\n";
		this.append(recettesItem);
		
		final String depensesItem = "Dépenses : "
				+ String.valueOf(b.getDepenses()) + "\n";
		this.append(depensesItem);
	
		final String pretsItem = "Prêts : " + String.valueOf(b.getPrets())
				+ "\n";
		this.append(pretsItem);
		
		final String pertesItem = "Pertes : " + String.valueOf(b.getPertes())
				+ "\n";
		this.append(pertesItem);
		
		cmdOui = new Command("Oui", Command.SCREEN, 0);
		cmdNon = new Command("Non", Command.CANCEL, 1);
		alertConf = new Alert("", "Voulez-vous vraiment tout remettre à zéro ?",
				null, AlertType.CONFIRMATION);
		// Aconf.setTimeout(3000);
		alertConf.addCommand(cmdNon);
		alertConf.addCommand(cmdOui);
		alertConf.setCommandListener(this);

		this.setCommandListener(this);
	}

	public void commandAction(Command arg0, Displayable arg1) {
		if (arg0 == this.cmdExit) {
			CoiffeurMIDlet.getInstance().exitNow();
			return;
		} else if (arg0 == this.cmdHelp) {
			CoiffeurMIDlet.getInstance().setCurrentScreen(new HelpScreen());

		} else if (arg0 == this.cmdAbout) {
			CoiffeurMIDlet.getInstance().setCurrentScreen(new AboutScreen());

		} else if (arg0 == this.cmdRecettes) {
			CoiffeurMIDlet.getInstance().setCurrentScreen(
					new RecettesScreen("Recettes"));
		} else if (arg0 == this.cmdDepenses) {
			CoiffeurMIDlet.getInstance().setCurrentScreen(
					new DepensesScreen("Dépenses"));
		} else if (arg0 == this.cmdPrets) {
			CoiffeurMIDlet.getInstance().setCurrentScreen(new PretsScreen("Prêts"));
		} else if (arg0 == this.cmdPertes) {
			CoiffeurMIDlet.getInstance()
					.setCurrentScreen(new PertesScreen("Pertes"));
		} else if (arg0 == this.cmdDetails) {
			CoiffeurMIDlet.getInstance().setCurrentScreen(
					new DetailsScreen("Détails"));
		} else if (arg0 == this.cmdSMS) {
			CoiffeurMIDlet.getInstance().setCurrentScreen(new SMSScreen("SMS"));
		} else if (arg0 == this.cmdRAZ) {
			CoiffeurMIDlet.getInstance().setCurrentScreen(alertConf);
		} else if (arg0 == cmdOui) {
			Business.getInstance().clearAll();
			CoiffeurMIDlet.getInstance().setCurrentScreen(
					new CoiffeurScreen(Business.getInstance()));
		} else if (arg0 == cmdNon) {
			CoiffeurMIDlet.getInstance().setCurrentScreen(
					new CoiffeurScreen(Business.getInstance()));
		}
	}
}
