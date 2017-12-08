package application.route;

@SuppressWarnings("rawtypes")
public class Entry implements Comparable {
	 private int key;
	 private int value;

	 public Entry(int k, int v) {
		 key = k;
		 value = v;
	 }
	 
	 public int getKey() {
		 return this.key;
	 }
	 
	 public int getValue() {
		 return this.value;
	 }

	@Override
	public int compareTo(Object o) {
		if (this.key == ((Entry) o).getKey()) {
			 return 0;
		 } else if (this.getKey()>((Entry) o).getKey()) {
			 return 1;
		 }
		 	return -1; 
	}
	 
}
