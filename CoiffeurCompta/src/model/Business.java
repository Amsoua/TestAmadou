package model;

import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;
import javax.microedition.rms.RecordStoreNotOpenException;

public class Business {

	private static Business instance;
	private static OrderedHashTable recettesMap, depensesMap, pertesMap,
			pretsMap, clientMap;

	private static String[] recettes = { "Coupe homme","Barbe","Coupe enfant","Coupe femme","Abonnement","Autre", "Erreur" };
	
	private static int recettesSize = recettes.length;
	
	private static String[] depenses = {"Lames","Alcool","Eponges","Mousse à raser","Peigne","Brosse",
		"Cosmetiques","Mouchoirs","Gel","Eau","Electricité","Loyer","Ménage","Salaires","Tondeuse",
		"Rénovation salon","Ciseaux","Aiguisage","Dents de tondeuse","Dépannage tondeuse","Autre","Erreur" };
	
	private static int depensesSize = depenses.length;
	private static String[] pertes = {"Social", "Vol","Autre","Erreur"};
	private static int pertesSize = pertes.length;
	private static String[] prets = {"Emprunt", "Remboursement"};
	private static int pretsSize = prets.length;
	
	private Business() {
	}

	public static Business getInstance() {
		return instance;
	}

	public static void createBusiness() {

		instance = new Business();
	}

	public static int getRecettesSize() {
		return recettesSize;
	}

	public static void setRecettesSize(int recettesSize) {
		Business.recettesSize = recettesSize;
	}

	public static int getDepensesSize() {
		return depensesSize;
	}

	public static void setDepensesSize(int depensesSize) {
		Business.depensesSize = depensesSize;
	}

	public static int getPertesSize() {
		return pertesSize;
	}

	public static void setPertesSize(int pertesSize) {
		Business.pertesSize = pertesSize;
	}

	public static int getPretsSize() {
		return pretsSize;
	}

	public static void setPretsSize(int pretsSize) {
		Business.pretsSize = pretsSize;
	}

	public void saveState() {
		save(recettesMap, "TRecettes");
		save(depensesMap, "TDepenses");
		save(pertesMap, "TPertes");
		save(pretsMap, "TPrets");
		save(clientMap, "TClients");
	}

	private void save(OrderedHashTable map, String name) {
		try {
			RecordStore.deleteRecordStore(name);
		} catch (RecordStoreNotFoundException e1) {
		} catch (RecordStoreException e1) {
		}

		RecordStore rs = null;
		try {
			rs = RecordStore.openRecordStore(name, true);
			for (int i = 0; i < map.getValueCount(); i++) {
				store(rs, map.getKeyAt(i).toString());
				store(rs, map.getValueAt(i).toString());
			}

		} catch (RecordStoreFullException e) {
			e.printStackTrace();
		} catch (RecordStoreNotFoundException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.closeRecordStore();
				} catch (RecordStoreNotOpenException e) {
					e.printStackTrace();
				} catch (RecordStoreException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void store(RecordStore rs, String value)
			throws RecordStoreNotOpenException, InvalidRecordIDException,
			RecordStoreException, RecordStoreFullException {
		final byte[] bytesToStore = value.getBytes();
		rs.addRecord(bytesToStore, 0, bytesToStore.length);
	}

	static boolean loaded = false;

	public static void loadStateRecettes() {
		System.out.println("loadRecettes - debut");
		if (loaded)
			return;

		RecordStore rsRecettes = null;
		recettesMap = new OrderedHashTable();
		try {
			rsRecettes = RecordStore.openRecordStore("TRecettes", false);
			int stop = rsRecettes.getNumRecords();

			for (int i = 1; i <= stop; i += 2) {
				String label = new String(rsRecettes.getRecord(i));
				String montant = new String(rsRecettes.getRecord(i + 1));
				recettesMap.put(label, Double.valueOf(montant));
			}
			loaded = true;
		} catch (RecordStoreFullException e) {
			e.printStackTrace();
		} catch (RecordStoreNotFoundException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		} finally {
			if (rsRecettes != null) {
				try {
					rsRecettes.closeRecordStore();
				} catch (RecordStoreNotOpenException e) {
					e.printStackTrace();
				} catch (RecordStoreException e) {
					e.printStackTrace();
				}
			}
		}

		// FallBack
		if (!loaded) {
			for (int i = 0; i < recettes.length; i++) {
				recettesMap.put(recettes[i], new Double(0));
			}
			// recettesMap.put("Course", new Double(0));
			// recettesMap.put("Location", new Double(0));
			// recettesMap.put("Abonnement", new Double(0));
			// recettesMap.put("Autre", new Double(0));
			// recettesMap.put("Erreur", new Double(0));
		}
		loaded = true;
		System.out.println("loadRecettes - fin");
	}

	public static void loadStateDepenses() {
		System.out.println("loadDepenses - debut");
		boolean loaded = false;
		RecordStore rsDepenses = null;
		depensesMap = new OrderedHashTable();
		try {
			rsDepenses = RecordStore.openRecordStore("TDepenses", false);
			int stop = rsDepenses.getNumRecords();

			for (int i = 1; i <= stop; i += 2) {
				String label = new String(rsDepenses.getRecord(i));
				String montant = new String(rsDepenses.getRecord(i + 1));
				depensesMap.put(label, Double.valueOf(montant));
			}
			loaded = true;
		} catch (RecordStoreFullException e) {
			e.printStackTrace();
		} catch (RecordStoreNotFoundException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		} finally {
			if (rsDepenses != null) {
				try {
					rsDepenses.closeRecordStore();
				} catch (RecordStoreNotOpenException e) {
					e.printStackTrace();
				} catch (RecordStoreException e) {
					e.printStackTrace();
				}
			}
		}

		// FallBack
		if (!loaded) {
			for (int i = 0; i < depenses.length; i++) {
				depensesMap.put(depenses[i], new Double(0));
			}
			// depensesMap.put("Location", new Double(0));
			// depensesMap.put("Essence", new Double(0));
			// depensesMap.put("Réparations", new Double(0));
			// depensesMap.put("Entretien", new Double(0));
			// depensesMap.put("Stationnement", new Double(0));
			// depensesMap.put("Lavage", new Double(0));
			// depensesMap.put("Assurance", new Double(0));
			// depensesMap.put("Amende", new Double(0));
			// depensesMap.put("Association", new Double(0));
			// depensesMap.put("Enregistrement", new Double(0));
			// depensesMap.put("ContrÃ´le technique", new Double(0));
			// depensesMap.put("Autre", new Double(0));
			// depensesMap.put("Erreur", new Double(0));
		}
		System.out.println("loadDepenses - fin");
	}

	public static void loadStatePertes() {
		System.out.println("loadPertes - debut");
		boolean loaded = false;
		RecordStore rsPertes = null;
		pertesMap = new OrderedHashTable();
		try {
			rsPertes = RecordStore.openRecordStore("TPertes", false);
			int stop = rsPertes.getNumRecords();

			for (int i = 1; i <= stop; i += 2) {
				String label = new String(rsPertes.getRecord(i));
				String montant = new String(rsPertes.getRecord(i + 1));
				pertesMap.put(label, Double.valueOf(montant));
			}
			loaded = true;
		} catch (RecordStoreFullException e) {
			e.printStackTrace();
		} catch (RecordStoreNotFoundException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		} finally {
			if (rsPertes != null) {
				try {
					rsPertes.closeRecordStore();
				} catch (RecordStoreNotOpenException e) {
					e.printStackTrace();
				} catch (RecordStoreException e) {
					e.printStackTrace();
				}
			}
		}

		// FallBack
		if (!loaded) {
			for (int i = 0; i < pertes.length; i++) {
				pertesMap.put(pertes[i], new Double(0));
			}
//			pertesMap.put("Social", new Double(0));
//			pertesMap.put("Vol", new Double(0));
//			pertesMap.put("Erreur", new Double(0));
		}
		System.out.println("loadPertes - fin");
	}

	public static void loadStatePrets() {
		System.out.println("loadPrets - debut");
		boolean loaded = false;
		RecordStore rsPrets = null;
		pretsMap = new OrderedHashTable();
		try {
			rsPrets = RecordStore.openRecordStore("TPrets", false);
			int stop = rsPrets.getNumRecords();

			for (int i = 1; i <= stop; i += 2) {
				String label = new String(rsPrets.getRecord(i));
				String montant = new String(rsPrets.getRecord(i + 1));
				pretsMap.put(label, Double.valueOf(montant));
			}
			loaded = true;
		} catch (RecordStoreFullException e) {
			e.printStackTrace();
		} catch (RecordStoreNotFoundException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		} finally {
			if (rsPrets != null) {
				try {
					rsPrets.closeRecordStore();
				} catch (RecordStoreNotOpenException e) {
					e.printStackTrace();
				} catch (RecordStoreException e) {
					e.printStackTrace();
				}
			}
		}

		// FallBack
		if (!loaded) {
			for (int i = 0; i < prets.length; i++) {
				pretsMap.put(prets[i], new Double(0));
			}
//			pretsMap.put("Emprunt", new Double(0));
//			pretsMap.put("Remboursement", new Double(0));
			// pretsMap.put("Erreur", new Double(0));
		}
		System.out.println("loadPrets - fin");
	}

	public static void loadStateNbClients() {
		System.out.println("loadNBClients - debut");
		boolean loaded = false;
		RecordStore rsClients = null;
		clientMap = new OrderedHashTable();
		try {
			rsClients = RecordStore.openRecordStore("TClients", false);
			int stop = rsClients.getNumRecords();

			for (int i = 1; i <= stop; i += 2) {
				String label = new String(rsClients.getRecord(i));
				System.out.println("LABEL " + label + "\n");
				String nb = new String(rsClients.getRecord(i + 1));
				clientMap.put(label, Integer.valueOf(nb));
			}
			loaded = true;
		} catch (RecordStoreFullException e) {
			e.printStackTrace();
		} catch (RecordStoreNotFoundException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		} finally {
			if (rsClients != null) {
				try {
					rsClients.closeRecordStore();
				} catch (RecordStoreNotOpenException e) {
					e.printStackTrace();
				} catch (RecordStoreException e) {
					e.printStackTrace();
				}
			}
		}

		// FallBack
		if (!loaded) {
			clientMap.put("Clients", new Integer(0));
		}
		System.out.println("loadNBClients - fin");
	}

	public int getNbClients() {
		return ((Integer) clientMap.getValueAt(0)).intValue();
	}

	public double getGains() {
		return this.getRecettes() - this.getDepenses();
	}

	public double getGainsPerClient() {
		if (this.getNbClients() == 0)
			return 0;
		else
			return this.getGains() / this.getNbClients();
	}

	public double getRecettes() {
		double total = 0;
		for (int i = 0; i < recettesMap.getValueCount(); i++) {
			Double t = (Double) recettesMap.getValueAt(i);
			total += t.doubleValue();
		}
		return total;
	}

	public double getDepenses() {
		double total = 0;
		for (int i = 0; i < depensesMap.getValueCount(); i++) {
			Double t = (Double) depensesMap.getValueAt(i);
			total += t.doubleValue();
		}
		return total;

	}

	public double getPrets() {
		return ((Double) pretsMap.getValueAt(1)).doubleValue()
				- ((Double) pretsMap.getValueAt(0)).doubleValue();
	}

	public double getPertes() {

		double total = 0;
		for (int i = 0; i < pertesMap.getValueCount(); i++) {
			Double t = (Double) pertesMap.getValueAt(i);
			total += t.doubleValue();
		}
		return total;

	}

	public void clearAll() {
		recettesMap.setAllValuesTo(new Double(0));
		depensesMap.setAllValuesTo(new Double(0));
		pertesMap.setAllValuesTo(new Double(0));
		pretsMap.setAllValuesTo(new Double(0));
		clientMap.setAllValuesTo(new Integer(0));
	}

	public Object[] getTypesRecettes() {
		return recettesMap.keys();
	}

	public String getRecetteLabel(int selectedIndex) {
		return (String) recettesMap.getKeyAt(selectedIndex);

	}

	public void addRecette(String label, Double montant) {
		Double currentMontant = (Double) recettesMap.getValueForKey(label);
		Double newMontant = new Double(currentMontant.doubleValue()
				+ montant.doubleValue());
		recettesMap.put(label, newMontant);
		int currentNbClient = ((Integer) clientMap.getValueAt(0)).intValue();
		clientMap.put(clientMap.getKeyAt(0), new Integer(currentNbClient + 1));
	}

	public void subtractRecette(String label, Double montant) {
		Double currentMontant = (Double) recettesMap.getValueForKey(label);
		Double newMontant = new Double(currentMontant.doubleValue()
				- montant.doubleValue());
		recettesMap.put(label, newMontant);
	}

	public Double getRecette(String label) {
		return (Double) recettesMap.getValueForKey(label);
	}

	public Object[] getTypesDepenses() {
		return depensesMap.keys();
	}

	public Object[] getTypesPertes() {
		return pertesMap.keys();
	}

	public Object[] getTypesPrets() {
		return pretsMap.keys();
	}

	public Double getDepense(String label) {
		return (Double) depensesMap.getValueForKey(label);
	}

	public Double getPret(String label) {
		return (Double) pretsMap.getValueForKey(label);
	}

	public Double getPerte(String label) {
		return (Double) pertesMap.getValueForKey(label);
	}

	public String getDepenseLabel(int selectedIndex) {
		return (String) depensesMap.getKeyAt(selectedIndex);

	}

	public void addDepense(String label, Double montant) {
		Double currentMontant = (Double) depensesMap.getValueForKey(label);
		Double newMontant = new Double(currentMontant.doubleValue()
				+ montant.doubleValue());
		depensesMap.put(label, newMontant);
	}

	public void subtractDepense(String label, Double montant) {
		Double currentMontant = (Double) depensesMap.getValueForKey(label);
		Double newMontant = new Double(currentMontant.doubleValue()
				- montant.doubleValue());
		depensesMap.put(label, newMontant);
	}

	public String getPerteLabel(int selectedIndex) {
		return (String) pertesMap.getKeyAt(selectedIndex);
	}

	public void addPerte(String label, Double montant) {
		Double currentMontant = (Double) pertesMap.getValueForKey(label);
		Double newMontant = new Double(currentMontant.doubleValue()
				+ montant.doubleValue());
		pertesMap.put(label, newMontant);
	}

	public void subtractPerte(String label, Double montant) {
		Double currentMontant = (Double) pertesMap.getValueForKey(label);
		Double newMontant = new Double(currentMontant.doubleValue()
				- montant.doubleValue());
		pertesMap.put(label, newMontant);
	}

	public String getPretLabel(int selectedIndex) {
		return (String) pretsMap.getKeyAt(selectedIndex);
	}

	public void addPret(String label, Double montant) {
		Double currentMontant = (Double) pretsMap.getValueForKey(label);
		Double newMontant = new Double(currentMontant.doubleValue()
				+ montant.doubleValue());
		pretsMap.put(label, newMontant);

	}

	public void subtractPret(String label, Double montant) {
		Double currentMontant = (Double) pretsMap.getValueForKey(label);
		Double newMontant = new Double(currentMontant.doubleValue()
				- montant.doubleValue());
		pretsMap.put(label, newMontant);

	}

}
