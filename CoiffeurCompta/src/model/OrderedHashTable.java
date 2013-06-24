package model;

import java.util.Hashtable;
import java.util.Vector;

public class OrderedHashTable {
	private Hashtable table;
	private Vector listKeys;

	public OrderedHashTable() {
		table = new Hashtable();
		listKeys = new Vector();
	}

	public void put(Object key, Object value) {
		table.put(key, value);
		if (!listKeys.contains(key)) {
			listKeys.addElement(key);
		}
	}

	public Object[] keys() {
		Object[] anArray = new Object[listKeys.size()];
		listKeys.copyInto(anArray);
		return anArray;
	}

	public Object getKeyAt(int index) {
		return listKeys.elementAt(index);
	}

	public Object getValueForKey(Object key) {
		return table.get(key);
	}

	public Object getValueAt(int index) {
		return table.get(listKeys.elementAt(index));
	}

	public int getValueCount() {
		return listKeys.size();
	}

	public void setAllValuesTo(Object o) {
		for (int i = 0; i < listKeys.size(); i++) {
			Object key = listKeys.elementAt(i);
			this.table.put(key, o);
		}
	}

}
