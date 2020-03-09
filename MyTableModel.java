import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private Object[][] Table = null;
	private Object[] Header = null;

	MyTableModel(Object[][] Table, Object[] Header) {
		this.Table = Table;
		this.Header = Header;
	}

	private boolean isPositiveInt(String str) {
		if (str.length() == 0)
			return false;
		for (int x = 0; x < str.length(); x++)
			if ((str.charAt(x) >= '0' && str.charAt(x) <= '9') == false)
				return false;
		return true;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {

		if (columnIndex == 2)
			return true;
		return false;
	}

	public int getColumnCount() {

		return Header.length;

	}

	public int getRowCount() {

		return Table.length;

	}

	public Object getValueAt(int x, int y) {

		return Table[x][y];

	}

	public void setValueAt(Object obj, int x, int y) {
		if (y == 2) {
			if (obj.toString() == "" || isPositiveInt(obj.toString()) == false
					|| Integer.parseInt(obj.toString()) == 0) {
				Table[x][y] = "";
				return;
			}

		}

		Table[x][y] = obj;
	}

	public String getColumnName(int column) {
		return (String) Header[column];
	}

}
