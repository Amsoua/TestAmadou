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

public class PertesScreen extends Form implements CommandListener {

	private static final int ERROR = Business.getPertesSize()-1;
	private final static double EPSILON = 0.00001;
	private static final String PERTES = "Pertes";
	private Command cmdValider, cmdAnnuler;
	private ChoiceGroup cgPertes;
	private TextField tfMontant;
	private Alert alert;

	public PertesScreen(String title) {
		super(title);

		this.cmdValider = new Command("Valider", Command.ITEM, 1);
		this.addCommand(cmdValider);

		this.cmdAnnuler = new Command("Annuler", Command.BACK, 1);
		this.addCommand(cmdAnnuler);

		tfMontant = new TextField("Montant : ", "", 10,
				TextField.DECIMAL);

		this.append(tfMontant);

		cgPertes = new ChoiceGroup("Type : ", ChoiceGroup.EXCLUSIVE);
		Object[] typesPertes = Business.getInstance().getTypesPertes();
		for (int i = 0; i < typesPertes.length; i++) {
			cgPertes.append((String) typesPertes[i], null);
		}

		this.append(cgPertes);

		this.setCommandListener(this);
	}

	public void commandAction(Command arg0, Displayable arg1) {
		if (arg0 == this.cmdAnnuler) {
			CoiffeurMIDlet.getInstance().setCurrentScreen(
					new CoiffeurScreen(Business.getInstance()));
		} else if (arg0 == this.cmdValider) {
			String label = Business.getInstance().getPerteLabel(
					cgPertes.getSelectedIndex());
			if (tfMontant.getString().equals("")) {
				alert = new Alert("Montant", "Entrez un montant", null,
						AlertType.WARNING);
				alert.setTimeout(Alert.FOREVER);
				CoiffeurMIDlet.getInstance().setCurrentScreen(alert);
			} else {
				Double montant = Double.valueOf(tfMontant.getString());
				if (cgPertes.getSelectedIndex() != ERROR) {

					Business.getInstance().addPerte(label, montant);
					CoiffeurMIDlet.getInstance().setCurrentScreen(
							new CoiffeurScreen(Business.getInstance()));
				} else {
					double d = Business.getInstance().getPertes()
							- montant.doubleValue();
					if (d < EPSILON) {
						System.out.println("Somme - Somme trop grande");
						
						alert = new Alert("Somme", "Somme trop grande", null,
								AlertType.WARNING);
						alert.setTimeout(Alert.FOREVER);
						CoiffeurMIDlet.getInstance().setCurrentScreen(alert,
								new PertesScreen(PERTES));
					} else {
						Business.getInstance().subtractPerte(label, montant);
						CoiffeurMIDlet.getInstance().setCurrentScreen(
								new CoiffeurScreen(Business.getInstance()));
					}
				}
			}
		}

	}

}
