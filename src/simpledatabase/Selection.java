package simpledatabase;
import java.io.IOException;
import java.util.ArrayList;

public class Selection extends Operator{
	
	ArrayList<Attribute> attributeList;
	String whereTablePredicate;
	String whereAttributePredicate;
	String whereValuePredicate;
	Tuple next;
	int index = 0;
	Tuple result;
	boolean skipThis;
	int num;

	
	public Selection(Operator child, String whereTablePredicate, String whereAttributePredicate, String whereValuePredicate) {
		this.child = child;
		this.whereTablePredicate = whereTablePredicate;
		this.whereAttributePredicate = whereAttributePredicate;
		this.whereValuePredicate = whereValuePredicate;
		attributeList = new ArrayList<Attribute>();
		
		next = child.next();
		attributeList = child.getAttributeList();
		num = attributeList.size();
		for (index = 0; index < num; index++){
			if (whereAttributePredicate.equals(next.getAttributeName(index))){
				break;
			}
		}
	}
	
	
	/**
     * Get the tuple which match to the where condition
     * @return the tuple
     */
	@Override
	public Tuple next(){
		//if the table is not the table want to be searched,
		//return null
		if (index == num){
			result = next;
			next = child.next();
		}
		else{
		//if this tuple is null, return null
		//no element was selected
			skipThis = true;
			while(skipThis){
				if (next==null){
					result = null;
					skipThis = false;
				}
				else{
					//if this tuple is not null, judge it
					if (next.getAttributeValue(index).equals(whereValuePredicate)){
						result = next;
						skipThis = false;
					}
				}
				next = child.next();
			}
		}
		return result;
	}
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return the attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		
		return(child.getAttributeList());
	}

	
}