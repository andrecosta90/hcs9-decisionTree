package core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Node {

	private String label;
	private Map<String, Node> links;
	
	
	public Node() {
		super();
		this.label = null;
		this.links = new HashMap();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Map<String, Node> getLinks() {
		return links;
	}

	public void setLinks(Map<String, Node> links) {
		this.links = links;
	}

	public void makeLink(String pValue) {
		this.links.put(pValue, new Node());
	}
	
	public Node getNode(String link){
		return this.links.get(link);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getLabel();
	}

}
