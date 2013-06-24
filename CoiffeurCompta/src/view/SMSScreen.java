package view;

import javax.microedition.io.Connector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

import model.Business;

import CoiffeurMIDlet;

public class SMSScreen extends Form implements CommandListener {

	private Command cmdEnvoi, cmdRetour;
	private TextField tfField;
	private String message;
	private StringItem siMessage;

	public SMSScreen(String title) {
		super(title);
		Business b = Business.getInstance();
		int nbClients = b.getNbClients();
		double recettes = b.getRecettes();
		double depenses = b.getDepenses();
		double gains = b.getGains();
		double prets = b.getPrets();
		double pertes = b.getPertes();
		message = nbClients + " clients" + "; Recettes : " + recettes
				+ "; Depenses : " + depenses + "; Gains : " + gains
				+ "; Prets : " + prets + "; Pertes : " + pertes;

		tfField = new TextField("Numéro de téléphone : ", "", 20,
				TextField.PHONENUMBER);
		this.append(tfField);

		siMessage = new StringItem("", message);
		this.append(siMessage);

		cmdEnvoi = new Command("Envoi", Command.ITEM, 1);
		cmdRetour = new Command("Retour", Command.BACK, 1);
		this.addCommand(cmdEnvoi);
		this.addCommand(cmdRetour);

		this.setCommandListener(this);
	}

	public void sendText(final String address, final String text) {
		new Thread(new Runnable() {
			public void run() {
				MessageConnection mc;
				try {
					mc = (MessageConnection) Connector.open("sms://" + address);
					TextMessage tm = (TextMessage) mc
							.newMessage(MessageConnection.TEXT_MESSAGE);
					tm.setPayloadText(text);
					mc.send(tm);
					mc.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();

	}

	public void commandAction(Command arg0, Displayable arg1) {
		if (arg0 == cmdEnvoi) {
			String phoneNumber = tfField.getString();
			System.out.println("phonenumber : *" + phoneNumber + "*");
			if (phoneNumber == null) {
				Alert alert = new Alert("Téléphone",
						"Entrez un numéro de téléphone", null,
						AlertType.WARNING);
				alert.setTimeout(Alert.FOREVER);
				CoiffeurMIDlet.getInstance().setCurrentScreen(alert);
			} else if (phoneNumber.equals("")) {
				System.out.println("phone empty");
				Alert alert = new Alert("Téléphone",
						"Entrez un numéro de téléphone", null,
						AlertType.WARNING);
				alert.setTimeout(Alert.FOREVER);
				CoiffeurMIDlet.getInstance().setCurrentScreen(alert);
			} else {
				sendText(tfField.getString(), message);
				CoiffeurMIDlet.getInstance().setCurrentScreen(
						new CoiffeurScreen(Business.getInstance()));
			}
		} else if (arg0 == cmdRetour) {
			CoiffeurMIDlet.getInstance().setCurrentScreen(
					new CoiffeurScreen(Business.getInstance()));
		}

	}
}
