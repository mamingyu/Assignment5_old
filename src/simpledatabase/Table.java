package simpledatabase;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Table extends Operator{
	private BufferedReader br = null;
	private boolean getAttribute=false;
	private Tuple tuple;
	private String attributeName;
	private String attributeType;
	private String attributeValue;

	//Read the content of the table and store into different attribute(column)
	//Store all the attribute into a attributeList
	public Table(String from) {
		this.from = from;
		try{
			br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/datafile/"+from+".csv")));
			attributeName = br.readLine();
			attributeType = br.readLine();
			attributeValue = br.readLine();
			tuple = new Tuple(attributeName, attributeType, attributeValue);
			tuple.setAttributeName();
			tuple.setAttributeType();
			tuple.setAttributeValue();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
     * Create a new tuple and return the tuple to its parent.
     * Set the attribute list if you have not prepare the attribute list
     * @return the tuple
     */
	@Override
	public Tuple next(){
		if (attributeValue!=null && attributeValue.length()!=0){
			try {
				tuple = new Tuple(attributeName, attributeType, attributeValue);
				tuple.setAttributeName();
				tuple.setAttributeType();
				tuple.setAttributeValue();
				attributeValue = br.readLine();
				return tuple;
				}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		else return null;
	}
	

	/**
     * The function is used to get the attribute list of the tuple
     * @return the attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return tuple.getAttributeList();
	}
	
}