package input;

import io.ReaderClass;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import core.Instance;

public class Structure {

	private ArrayList<Attribute> attributes;
	private Attribute target;

	public Structure(String path) {
		String line = null;
		String[] attribs = null;

		this.attributes = new ArrayList<Attribute>();

		try {
			ReaderClass reader = new ReaderClass(path);
			line = reader.readLine();

			attribs = line.split(",");

			int size = attribs.length;
			for (int i = 0; i < size - 1; i++) {
				Attribute attr = new Attribute(attribs[i]);
				attributes.add(attr);
			}

			this.target = new Attribute(attribs[size - 1]);

			line = reader.readLine();
			while (line != null) {

				attribs = line.split(",");
				size = attribs.length;

				for (int i = 0; i < size - 1; i++) {
					this.attributes.get(i).addValue(attribs[i]);
				}

				this.target.addValue(attribs[size - 1]);

				// System.out.println(line);
				line = reader.readLine();
			}

			for (Attribute a : this.attributes) {
//				System.out.println(a.getPossibleValues());
			}
//			System.out.println("Target-> " + target.getPossibleValues());
//			System.out.println();

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public Structure() {
		this.attributes = new ArrayList<Attribute>();
	}

	public ArrayList<Attribute> getAttributes() {
		return (ArrayList<Attribute>) attributes.clone();
	}

	public void setTarget(Attribute target) {
		this.target = target;
	}

	public Attribute getTarget() {
		return target;
	}

	public Instance getInstance(int i) {

		Instance instance = new Instance();

		for (Attribute attribute : this.attributes) {
			ArrayList<String> values = attribute.getValues();

			try{
				instance.add(attribute.getDescription(), values.get(i));
			}catch(IndexOutOfBoundsException e){
//				e.printStackTrace();
			}

		}

		return instance;
	}

	public int getSize() {
		return target.getValues().size();
	}

}
