package view;

import TaxiMIDlet;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;

import model.Business;

public class HelpScreen extends Form implements CommandListener {

	private Command cmdBack;

	public HelpScreen() {
		super("Aide");
		this.append("TaxiCompta v1.0 \n"
				+ "* Recettes : Enregistrer des recettes de différents types (courses, location etc)\n"
				+ "* Dépenses : Enregistrer des dépenses de différents types (essence, réparations etc)\n"
				+ "* Prêts : Enregistrer des prêts et des remboursements\n"
				+ "* Pertes : Enregistrer des pertes (vols et raisons sociales)\n"
				+ "* Details : Obtenir le résumé des transactions financières réalisées\n"
				+ "* RAZ : Remise à zéro de l'application. Toutes les données sont remises à zéro\n"
				+ "* SMS : Envoi de SMS à un partenaire ou au patron du taxi avec un résumé simple des transactions financières réalisées \n"
				+ "* Notes : Les données sont sauvées lors des diverses utilisations de l'application\n"
				+ "* Notes : Des corrections peuvent être effectuées en enregistrant des Erreurs. Les sommes d'argent de type Erreur sont soustraites aux recettes, dépenses et pertes. Les prêts peuvent etre corrigés en ajustant les emprunts et les remboursements\n");

		cmdBack = new Command("Retour", Command.BACK, 1);
		this.setCommandListener(this);
		this.addCommand(cmdBack);
	}

	public void commandAction(Command cmd, Displayable arg1) {

		if (cmd == cmdBack) {
			System.out.print("Retour");
			TaxiMIDlet.getInstance().setCurrentScreen(
					new TaxiScreen(Business.getInstance()));
		}
	}

}
