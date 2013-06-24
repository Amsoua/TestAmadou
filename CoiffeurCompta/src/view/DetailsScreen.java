package view;

import CoiffeurMIDlet;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;

import model.Business;

public class DetailsScreen extends Form implements CommandListener {

	private Command cmdRetour;

	public DetailsScreen(String title) {
		super(title);

		this.cmdRetour = new Command("Retour", Command.BACK, 1);
		this.addCommand(cmdRetour);

		final StringItem nbClientsItem = new StringItem("",
				String.valueOf(Business.getInstance().getNbClients())
						+ " clients" + "\n");
		this.append(nbClientsItem);

		final StringItem gainsItem = new StringItem("", "Gain : "
				+ String.valueOf(Business.getInstance().getGains()) + "\n");
		this.append(gainsItem);

		final StringItem gainsPerClientItem = new StringItem("",
				"Gain/client : "
						+ String.valueOf(Business.getInstance()
								.getGainsPerClient()) + "\n");
		this.append(gainsPerClientItem);

		final StringItem separator1Item = new StringItem("", "----------"
				+ "\n");
		this.append(separator1Item);

		final StringItem recettesItem = new StringItem("", "Recettes : "
				+ String.valueOf(Business.getInstance().getRecettes()) + "\n");
		this.append(recettesItem);

		Object[] typesRecettes = Business.getInstance().getTypesRecettes();
		for (int j = 0; j < typesRecettes.length; j++) {
			String label = (String) typesRecettes[j];
			Double recs = Business.getInstance().getRecette(label);
			if (recs.intValue() != 0) {
				final StringItem autresRecettesItem = new StringItem("", label
						+ " : "
						+ Business.getInstance().getRecette(label).toString()
						+ "\n");
				this.append(autresRecettesItem);
			}

		}

		final StringItem separator2Item = new StringItem("", "----------"
				+ "\n");
		this.append(separator2Item);

		final StringItem depensesItem = new StringItem("", "Dépenses : "
				+ String.valueOf(Business.getInstance().getDepenses()) + "\n");
		this.append(depensesItem);

		Object[] typesDepenses = Business.getInstance().getTypesDepenses();
		for (int j = 0; j < typesDepenses.length; j++) {
			String label = (String) typesDepenses[j];
			Double deps = Business.getInstance().getDepense(label);
			if (deps.intValue() != 0) {
				final StringItem autresDepensesItem = new StringItem("", label
						+ " : " + deps.toString() + "\n");
				this.append(autresDepensesItem);
			}
		}

		final StringItem separator3Item = new StringItem("", "----------"
				+ "\n");
		this.append(separator3Item);

		final StringItem pretsItem = new StringItem("", "Prêts : "
				+ String.valueOf(Business.getInstance().getPrets()) + "\n");
		this.append(pretsItem);

		Object[] typesPrets = Business.getInstance().getTypesPrets();
		for (int j = 0; j < typesPrets.length; j++) {
			String label = (String) typesPrets[j];
			Double pr = Business.getInstance().getPret(label);
			if (pr.intValue() != 0) {
				final StringItem autresPretsItem = new StringItem("", label
						+ " : "
						+ Business.getInstance().getPret(label).toString()
						+ "\n");
				this.append(autresPretsItem);
			}
		}

		final StringItem separator4Item = new StringItem("", "----------"
				+ "\n");
		this.append(separator4Item);

		final StringItem pertesItem = new StringItem("", "Pertes : "
				+ String.valueOf(Business.getInstance().getPertes()) + "\n");
		this.append(pertesItem);

		Object[] typesPertes = Business.getInstance().getTypesPertes();
		for (int i = 0; i < typesPertes.length; i++) {
			String label = (String) typesPertes[i];
			Double per = Business.getInstance().getPerte(label);
			if (per.intValue() != 0) {
				final StringItem autresPertesItem = new StringItem("", label
						+ " : "
						+ Business.getInstance().getPerte(label).toString()
						+ "\n");
				this.append(autresPertesItem);
			}
		}

		this.setCommandListener(this);
	}

	public void commandAction(Command arg0, Displayable arg1) {
		if (arg0 == this.cmdRetour) {
			CoiffeurMIDlet.getInstance().setCurrentScreen(
					new CoiffeurScreen(Business.getInstance()));
		}
	}

}
