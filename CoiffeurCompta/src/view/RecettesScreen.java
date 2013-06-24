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

public class RecettesScreen extends Form implements CommandListener {

	private static final String RECETTES = "Recettes";
	private final static double EPSILON = 0.00001;
	private static final int ERROR = Business.getRecettesSize()-1;
	private Command cmdValider, cmdAnnuler;
	private ChoiceGroup cgRecettes;
	private TextField tfMontant;
	private Alert alert;

	public RecettesScreen(String title) {
		super(title);

		this.cmdValider = new Command("Valider", Command.ITEM, 1);
		this.addCommand(cmdValider);

		this.cmdAnnuler = new Command("Annuler", Command.BACK, 1);
		this.addCommand(cmdAnnuler);

		tfMontant = new TextField("Montant : ", "", 10,
				TextField.DECIMAL);
		this.append(tfMontant);

		cgRecettes = new ChoiceGroup("Type : ", ChoiceGroup.EXCLUSIVE);
		Object[] typesRecettes = Business.getInstance().getTypesRecettes();
		for (int i = 0; i < typesRecettes.length; i++) {
			cgRecettes.append((String) typesRecettes[i], null);
		}

		this.append(cgRecettes);

		this.setCommandListener(this);
	}

	public void commandAction(Command arg0, Displayable arg1) {
		if (arg0 == this.cmdAnnuler) {
			CoiffeurMIDlet.getInstance().setCurrentScreen(
					new CoiffeurScreen(Business.getInstance()));
		} else if (arg0 == this.cmdValider) {
			String label = Business.getInstance().getRecetteLabel(
					cgRecettes.getSelectedIndex());
			System.out.println("label " + label);
			if (tfMontant.getString().equals("")) {
				alert = new Alert("Montant", "Entrez un montant", null,
						AlertType.WARNING);
				alert.setTimeout(Alert.FOREVER);
				CoiffeurMIDlet.getInstance().setCurrentScreen(alert);
			} else {
				Double montant = Double.valueOf(tfMontant.getString());
				if (cgRecettes.getSelectedIndex() != ERROR) {
					Business.getInstance().addRecette(label, montant);
					CoiffeurMIDlet.getInstance().setCurrentScreen(
							new CoiffeurScreen(Business.getInstance()));
				} else {
					double d = Business.getInstance().getRecettes()
							- montant.doubleValue();
					if (d < EPSILON) {
						System.out.println("Somme - Somme trop grande");
						
						alert = new Alert("Somme", "Somme trop grande", null,
								AlertType.WARNING);
						alert.setTimeout(Alert.FOREVER);
						CoiffeurMIDlet.getInstance().setCurrentScreen(alert,
								new RecettesScreen(RECETTES));
					} else {
						Business.getInstance().subtractRecette(label, montant);
						CoiffeurMIDlet.getInstance().setCurrentScreen(
								new CoiffeurScreen(Business.getInstance()));
					}
				}
			}
		}
	}

}
