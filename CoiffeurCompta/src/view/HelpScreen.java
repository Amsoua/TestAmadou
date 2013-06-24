package view;

import CoiffeurMIDlet;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;

import model.Business;

public class HelpScreen extends Form implements CommandListener {

	private Command cmdBack;

	public HelpScreen() {
		super("Aide");
		this.append("CoiffeurCompta v1.1 \n"
				+ "* Recettes : Enregistrer des recettes de diff�rents types (Coupe homme, Coupe enfant, Barbe etc)\n"
				+ "* D�penses : Enregistrer des d�penses de diff�rents types (Factures eaux, Courant, Loyer etc)\n"
				+ "* Pr�ts : Enregistrer des pr�ts et des remboursements\n"
				+ "* Pertes : Enregistrer des pertes (Vols et Sociales ou Autre)\n"
				+ "* Details : Obtenir le r�sum� des transactions financi�res r�alis�es\n"
				+ "* RAZ : Remise � z�ro de l'application. Toutes les donn�es sont remises �z�ro\n"
				+ "* SMS : Envoi de SMS �un partenaire ou au patron avec un r�sum� simple des transactions financi�res r�alis�es \n"
				+ "* Notes : Les donn�es sont sauv�es lors des diverses utilisations de l'application\n"
				+ "* Notes : Des corrections peuvent �tre effectu�es en enregistrant des Erreurs. Les sommes d'argent de type Erreur sont soustraites aux recettes, d�penses et pertes. Les pr�ts peuvent etre corrig�s en ajustant les emprunts et les remboursements\n");

		cmdBack = new Command("Retour", Command.BACK, 1);
		this.setCommandListener(this);
		this.addCommand(cmdBack);
	}

	public void commandAction(Command cmd, Displayable arg1) {

		if (cmd == cmdBack) {
			System.out.print("Retour");
			CoiffeurMIDlet.getInstance().setCurrentScreen(
					new CoiffeurScreen(Business.getInstance()));
		}
	}

}
