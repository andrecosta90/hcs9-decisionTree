package input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Attribute {

	private Map<String, Integer> map;
	private String description;
	private ArrayList<String> values;

	public Attribute(String description) {
		super();

		this.map = new HashMap();

		this.description = description;
		this.values = new ArrayList<>();

	}

	@Override
	public String toString() {
		return description;
	}

	public void addValue(String value) {
		if (!this.map.containsKey(value)) {
			this.map.put(value, 1);
		}

		this.values.add(value);

	}

	public String getDescription() {
		return description;
	}

	public ArrayList<String> getValues() {
		return (ArrayList<String>) values.clone();
	}

	public Set<String> getPossibleValues() {
		return this.map.keySet();
	}

}
