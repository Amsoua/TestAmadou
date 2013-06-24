package view;

import CoiffeurMIDlet;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;

import model.Business;

public class PretsScreen extends Form implements CommandListener {

	private Command cmdValider, cmdAnnuler;
	private ChoiceGroup cgPrets;
	private TextField tfMontant;

	public PretsScreen(String title) {
		super(title);

		this.cmdValider = new Command("Valider", Command.ITEM, 1);
		this.addCommand(cmdValider);

		this.cmdAnnuler = new Command("Annuler", Command.BACK, 1);
		this.addCommand(cmdAnnuler);

		tfMontant = new TextField("Montant : ", "", 10,
				TextField.DECIMAL);
		this.append(tfMontant);

		cgPrets = new ChoiceGroup("Type : ", ChoiceGroup.EXCLUSIVE);
		Object[] typesPrets = Business.getInstance().getTypesPrets();
		for (int i = 0; i < typesPrets.length; i++) {
			cgPrets.append((String) typesPrets[i], null);
		}

		this.append(cgPrets);

		this.setCommandListener(this);
	}

	public void commandAction(Command arg0, Displayable arg1) {
		if (arg0 == this.cmdAnnuler) {
			CoiffeurMIDlet.getInstance().setCurrentScreen(
					new CoiffeurScreen(Business.getInstance()));
		} else if (arg0 == this.cmdValider) {
			String label = Business.getInstance().getPretLabel(
					cgPrets.getSelectedIndex());
			if (tfMontant.getString().equals("")) {
				Alert alert = new Alert("Montant", "Entrez un montant", null,
						AlertType.WARNING);
				alert.setTimeout(Alert.FOREVER);
				CoiffeurMIDlet.getInstance().setCurrentScreen(alert);
			} else {
				Double montant = Double.valueOf(tfMontant.getString());
				// if (pretsChoice.getSelectedIndex() != ERROR) {
				Business.getInstance().addPret(label, montant);
				// } else {
				// Business.getInstance().subtractPret(label, montant);
				// }
				CoiffeurMIDlet.getInstance().setCurrentScreen(
						new CoiffeurScreen(Business.getInstance()));
			}
		}

	}

}
