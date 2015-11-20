package simpledatabase;
import java.util.ArrayList;

public class Projection extends Operator{
	
	ArrayList<Attribute> newAttributeList;
	private String attributePredicate;
	private int index = 0;
	private Tuple result;
	private Tuple next;
	private String attributeName;
	private Type attributeType;
	private String attributeValue;


	public Projection(Operator child, String attributePredicate){
		
		this.attributePredicate = attributePredicate;
		this.child = child;
		ArrayList<Attribute> a = child.getAttributeList();
		int num = a.size();
		next = child.next();
		while(index<num){
			if (attributePredicate.equals(next.getAttributeName(index))){
				break;
			}
			index++;
		}
	}
	
	
	/**
     * Return the data of the selected attribute as tuple format
     * @return tuple
     */
	@Override
	public Tuple next(){
		if (next!=null){
			attributeName = next.getAttributeName(index);
			attributeValue = (String) next.getAttributeValue(index);
			attributeType = next.getAttributeType(index);
			result = new Tuple(attributeName, attributeType.toString(), attributeValue);
			result.setAttributeName();
			result.attribute.attributeType = attributeType;
			result.setAttributeValue();
			next = child.next();
			return result;
		}
		else return null;
	}
		

	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return child.getAttributeList();
	}

}