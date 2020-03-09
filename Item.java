import java.util.ArrayList;

public class Item implements Comparable<Item> {
	private String category, item_name, image_path;
	private double price;

	Item(String category, String item_info) {
		ArrayList<StringBuilder> split = new ArrayList<StringBuilder>();
		split.add(new StringBuilder());

		for (int x = 1; x < item_info.length(); x++) {
			if (item_info.charAt(x) != ',')
				split.get(split.size() - 1).append(item_info.charAt(x));
			else
				split.add(new StringBuilder());
		}

		item_name = split.get(0).toString();
		price = Double.parseDouble(split.get(1).toString());
		image_path = split.get(2).toString();
		this.category = category;
	}

	public String toString() {
		String temp = item_name + " at " + category + " " + "costs " + price + " --- " + image_path;
		return temp;
	}

	public void getInfo() {
		System.out.println(item_name + price + " ");
	}

	public String getCategory() {
		return category;
	}

	public String getItem_name() {
		return item_name;
	}

	public String getImage_path() {
		return image_path;
	}

	public double getPrice() {
		return price;
	}

	@Override
	public int compareTo(Item arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
