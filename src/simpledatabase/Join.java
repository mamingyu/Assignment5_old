package simpledatabase;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Join extends Operator{

	private ArrayList<Attribute> newAttributeList = null;
	private String joinPredicate;
	ArrayList<Tuple> tuples1;
	Tuple next;
	int numRight;
	int numLeft;
	int indexLeft;
	int indexRight;
	int countNext = 0;
	int cntLeft = 0;
	int cntRight = 0;

	
	//Join Constructor, join fill
	public Join(Operator leftChild, Operator rightChild, String joinPredicate){
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.joinPredicate = joinPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuples1 = new ArrayList<Tuple>();
		
		//store all the tuples selected by leftChild in tuples1
		try{
			newAttributeList = leftChild.getAttributeList();
			numLeft = newAttributeList.size();
			int i = 0;
			while (i<numLeft){
				tuples1.add(leftChild.next());
				i++;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		

		//find the foreign key and the connection relationship
		try{
			newAttributeList = rightChild.getAttributeList();
			numRight = newAttributeList.size();
			next = rightChild.next();
			outerloop:
			for (indexLeft = 0; indexLeft < numLeft; indexLeft++){
				for (indexRight = 0; indexRight < numRight; indexRight++){
					if (next.getAttributeName(indexRight).equals(tuples1.get(0).getAttributeName(indexLeft))){
						break outerloop;
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
     * It is used to return a new tuple which is already joined by the common attribute
     * @return the new joined tuple
     */
	//The record after join with two tables
	@Override
	public Tuple next(){
		//pick tuples from leftChild, compare, merge and store in tuples1
		while(cntRight < numRight){
			if (next == null){
				return null;
			}
			while(cntLeft < numLeft){
				if (next.getAttributeValue(indexRight).equals(tuples1.get(cntLeft).getAttributeValue(indexLeft))){
					newAttributeList = next.getAttributeList();
					for (int i = 0; i < numLeft; i++){
						if (i != indexLeft)
							newAttributeList.add(tuples1.get(cntLeft).getAttributeList().get(i));
					}
					cntLeft++;
					return new Tuple(newAttributeList);
				}
				cntLeft++;
			}
			cntRight++;
			next = rightChild.next();
			cntLeft = 0;
		}
		return null;
	}
	
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		if(joinPredicate.isEmpty())
			return child.getAttributeList();
		else
			return(newAttributeList);
	}

}