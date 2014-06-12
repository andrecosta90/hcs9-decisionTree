package core;

import java.util.HashMap;
import java.util.Map;

public class Instance {
	private Map<String, String> map = new HashMap();

	public Instance() {
		super();
		this.map = new HashMap();
	}

	public void add(String description, String value) {
		this.map.put(description, value);
	}

	public String getValue(String description) {
		return this.map.get(description);
	}
	
	@Override
	public String toString() {
		return map.toString();
	}

}
