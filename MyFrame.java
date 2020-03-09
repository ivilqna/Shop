import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class MyFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private HashMap<String, ArrayList<Item>> Map = new HashMap<String, ArrayList<Item>>();
	private HashMap<String, JTable> TablesMap = new HashMap<String, JTable>();

	private JPanel MAIN_PANEL = new JPanel(new CardLayout());

	private JPanel Page1 = new JPanel(new BorderLayout());

	private JPanel Page1_1 = new JPanel(new BorderLayout());
	private JPanel Page1_2 = new JPanel(new BorderLayout());
	private JPanel Page1_3 = new JPanel(new BorderLayout());
	private JPanel Page1_4 = new JPanel(new BorderLayout());
	private JPanel Page1_5 = new JPanel(new BorderLayout());
	private JPanel Page1_6 = new JPanel(new BorderLayout());
	private JPanel Page1_7 = new JPanel(new CardLayout());
	private JPanel Page1_8 = new JPanel(new CardLayout());
	private JPanel Page1_9 = new JPanel(new BorderLayout());
	private JPanel Page1_10 = new JPanel(new BorderLayout());
	private JLabel price = new JLabel("Price:");

	private JTree Tree = new JTree();
	private String TreeLocation = null;
	private JLabel info = new JLabel();

	private JTable CART_TABLE;

	final String[] columnNames = { "Item name", "Price","Quantity",  };

	private JPanel Page2 = new JPanel(new BorderLayout());
	{
		MAIN_PANEL.add(Page1, "Page1");
		MAIN_PANEL.add(Page2, "Page2");

		Page1.add(Page1_1, BorderLayout.WEST);
		Page1.add(Page1_2, BorderLayout.EAST);

		Page1_1.add(Page1_3, BorderLayout.NORTH);
		Page1_1.add(Page1_4, BorderLayout.SOUTH);

		Page1_2.add(Page1_5, BorderLayout.NORTH);
		Page1_2.add(Page1_6, BorderLayout.SOUTH);

		Page1_5.add(Page1_7, BorderLayout.WEST);
		Page1_5.add(Page1_8, BorderLayout.EAST);
		Page1_6.add(Page1_9, BorderLayout.WEST);

		Page1_4.setPreferredSize(
				new Dimension((int) (screenSize.getWidth() * 2 / 10), (int) (screenSize.getHeight() * 2.25 / 10)));
		Page1_9.setPreferredSize(
				new Dimension((int) (screenSize.getWidth() * 6 / 10), (int) (screenSize.getHeight() * 2.25 / 10)));

		Page1_3.setPreferredSize(
				new Dimension((int) (screenSize.getWidth() * 2 / 10), (int) (screenSize.getHeight() * 7 / 10)));
		Page1_7.setPreferredSize(
				new Dimension((int) (screenSize.getWidth() * 6 / 10), (int) (screenSize.getHeight() * 7 / 10)));
		Page1_8.setPreferredSize(
				new Dimension((int) (screenSize.getWidth() * 2 / 10), (int) (screenSize.getHeight() * 7 / 10)));

	}

	MyFrame() {

		setTitle("Lilly");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		this.add(Page1_10, BorderLayout.CENTER);
		this.setLayout(new BorderLayout());

		Page1_10.setLayout(new GridLayout(1, 1));
		Page1_10.add(price);

	}

	private void constructTables() throws IOException {

		// table create
		for (String str : Map.keySet()) {
			ArrayList<Item> items = Map.get(str);

			Collections.sort(items);

			Object[][] data = new Object[items.size()][columnNames.length];

			for (int x = 0; x < items.size(); x++) {
				Item i = items.get(x);
				BufferedImage image = null;
				image = ImageIO.read(getClass().getResourceAsStream(i.getImage_path()));

				JLabel picLabel = new JLabel(new ImageIcon(image));
				JPanel c = new JPanel();
				c.add(picLabel);
				c.setBackground(Color.white);

				Page1_8.add(c, i.getItem_name());

				data[x][0] = i.getItem_name();
				data[x][1] = i.getPrice();
				data[x][2] = "";
			}

			JPanel c = new JPanel();
			c.setOpaque(true);
			c.setBackground(Color.white);
			Page1_8.add(c, "");
			((CardLayout) Page1_8.getLayout()).show(Page1_8, "");
			JTable table = new JTable(new MyTableModel(data, columnNames));
			TablesMap.put(str, table);
			
			table.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent evt) {
					if (evt.getClickCount() == 2) {
						int row = table.rowAtPoint(evt.getPoint());
						((CardLayout) Page1_8.getLayout()).show(Page1_8, (String) table.getValueAt(row, 0));
					}
				}
			});

			JScrollPane TablePane = new JScrollPane(table);

			table.setAutoCreateRowSorter(true);

			Page1_7.add(TablePane, str);

		}

		JPanel e = new JPanel(new GridBagLayout());
		e.setBackground(Color.WHITE);
		e.setBorder(BorderFactory.createLineBorder(Color.black));
		e.add(new JLabel("<html><b><big>Welcome! <br> Please select a category. </big></b></html>"));

		Page1_7.add(e, "");
		((CardLayout) Page1_7.getLayout()).show(Page1_7, "");
	}

	public void construct() throws IOException, InterruptedException {

		add(MAIN_PANEL, BorderLayout.CENTER);

		// tree
		Tree = ConstructJtree();
		JScrollPane TreeScroll = new JScrollPane(Tree);
		Page1_3.add(TreeScroll, BorderLayout.CENTER);

		Tree.addTreeSelectionListener(new TreeSelectionListener() {
			DefaultMutableTreeNode lastSelected = null;
			CardLayout card = (CardLayout) Page1_7.getLayout();

			public void valueChanged(TreeSelectionEvent e) {

				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) Tree.getLastSelectedPathComponent();
				if (selectedNode != null && selectedNode.isLeaf() && selectedNode != lastSelected) {
					TreeLocation = selectedNode.getParent().toString() + "/" + selectedNode.toString();
					card.show(Page1_7, TreeLocation);
					lastSelected = selectedNode;
				}
			}
		});

		constructTables();

		// PAGE 2

		JButton Pay = new JButton("Pay Button");
		Pay.addActionListener(new ActionListener() {

			// INVOICE
			public void actionPerformed(ActionEvent arg0) {

				PrintWriter writer = null;
				try {
					writer = new PrintWriter("resource/INVOICE.txt", "UTF-8");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		setVisible(true);
	}

	// Add items from the Database
	public ArrayList<String> loadDatabase(String DatabaseName) throws IOException {
		ArrayList<String> Database = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(DatabaseName));

		String st;
		while ((st = br.readLine()) != null) {
			Database.add(st);
		}

		br.close();
		return Database;
	}

	// Link between Database and JTree
	public JTree ConstructJtree() throws IOException {

		ArrayList<String> Database = loadDatabase("./resource/online_shop_database.txt");
		DefaultMutableTreeNode shop = new DefaultMutableTreeNode("Lilly");
		Stack<DefaultMutableTreeNode> stack = new Stack<DefaultMutableTreeNode>();
		stack.add(shop);
		String category_name = null, subcategory_name = null;

		for (String str : Database) {
			for (int x = 0; x < str.length(); x++) {

				if (str.charAt(x) == '_' || str.charAt(x) == '.' || str.charAt(x) == '*') {
					if (x != 0)
						str = str.substring(x);

					break;
				}

			}

			if (str.length() == 0)
				continue;

			if (str.charAt(0) == '_') {
				while (stack.size() > 1)
					stack.pop();

				category_name = str.substring(1);
				DefaultMutableTreeNode category = new DefaultMutableTreeNode(category_name);
				stack.lastElement().add(category);
				stack.add(category);

			} else if (str.charAt(0) == '.') {
				subcategory_name = str.substring(1);
				stack.lastElement().add(new DefaultMutableTreeNode(subcategory_name));
			} else if (str.charAt(0) == '*') {
				String location = category_name + "/" + subcategory_name;

				if (Map.containsKey(location) == false)
					Map.put(location, new ArrayList<Item>());

				Map.get(location).add(new Item(location, str));
			}

		}

		JTree MyTree = new JTree(shop); // Design
		MyTree.setRootVisible(false);

		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) MyTree.getCellRenderer();
		renderer.setLeafIcon(null);
		renderer.setClosedIcon(null);
		renderer.setOpenIcon(null);
		return MyTree;
	}

}
