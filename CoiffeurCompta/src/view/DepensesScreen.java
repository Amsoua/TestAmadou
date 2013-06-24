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

public class DepensesScreen extends Form implements CommandListener {

	private static final int ERROR = Business.getDepensesSize()-1;
	private final static double EPSILON = 0.00001;
	private static final String DEPENSES = "Dépenses";
	private Command cmdValider, cmdAnnuler;
	private ChoiceGroup cgChoice;
	private TextField tfMontant;
	private Alert alert;

	public DepensesScreen(String title) {
		super(title);

		this.cmdValider = new Command("Valider", Command.ITEM, 1);
		this.addCommand(cmdValider);

		this.cmdAnnuler = new Command("Annuler", Command.BACK, 1);
		this.addCommand(cmdAnnuler);

		tfMontant = new TextField("Montant : ", "", 10,
				TextField.DECIMAL);
		this.append(tfMontant);

		cgChoice = new ChoiceGroup("Type : ", ChoiceGroup.EXCLUSIVE);

		Object[] typesDepenses = Business.getInstance().getTypesDepenses();
		for (int i = 0; i < typesDepenses.length; i++) {
			cgChoice.append((String) typesDepenses[i], null);
		}

		this.append(cgChoice);

		this.setCommandListener(this);

	}

	public void commandAction(Command arg0, Displayable arg1) {
		if (arg0 == this.cmdAnnuler) {
			CoiffeurMIDlet.getInstance().setCurrentScreen(
					new CoiffeurScreen(Business.getInstance()));
		} else if (arg0 == this.cmdValider) {
			String label = Business.getInstance().getDepenseLabel(
					cgChoice.getSelectedIndex());
			if (tfMontant.getString().equals("")) {
				alert = new Alert("Montant", "Entrez un montant", null,
						AlertType.WARNING);
				alert.setTimeout(Alert.FOREVER);
				CoiffeurMIDlet.getInstance().setCurrentScreen(alert);
			} else {
				Double montant = Double.valueOf(tfMontant.getString());
				if (cgChoice.getSelectedIndex() != ERROR) {
					Business.getInstance().addDepense(label, montant);
					CoiffeurMIDlet.getInstance().setCurrentScreen(
							new CoiffeurScreen(Business.getInstance()));
				} else {
					double d = Business.getInstance().getDepenses()
							- montant.doubleValue();
					if (d < EPSILON) {
						System.out.println("Somme - Somme trop grande");
						
					alert = new Alert("Somme", "Somme trop grande", null,
								AlertType.WARNING);
						alert.setTimeout(Alert.FOREVER);
						CoiffeurMIDlet.getInstance().setCurrentScreen(alert,
								new DepensesScreen(DEPENSES));
					} else {
						Business.getInstance().subtractDepense(label, montant);
						CoiffeurMIDlet.getInstance().setCurrentScreen(
								new CoiffeurScreen(Business.getInstance()));
					}
				}
			}
		}
	}

}
