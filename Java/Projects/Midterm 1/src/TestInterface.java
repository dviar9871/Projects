
/*
 * @author Jerry Heuring
 *
 */
public interface TestInterface {
	/**
	 * This method adds the value to the list only if the 
	 * list does not already contain the value. If the list
	 * is full this method throws a StorageOverflowException.
	 *
	 * @param value  the value to add to the list. 
	 * @throws StorageOverflowException
	 */
	public void addValue(int value) throws StorageOverflowException;
	/**
	 * This method removes a value from the stored list if it 
	 * is present.  If the value is not present the list remains
	 * unchanged.
	 * 
	 * @param value  search for this value in this object and if 
	 *               found remove it.  
	 */
	public void removeValue(int value);
	/**
	 * This method should compute the sum of all items currently
	 * stored in this object and return it. 
	 * @return  the sum of all the values stored in this object. 
	 */
	public int sumOfValues();
	/**
	 * removes all values from this object.  
	 */
	public void clear();
	/**
	 * Checks if the current list is full or not. 
	 * @return true if the list in this object is full. false otherwise.
	 */
	public boolean isFull();
	/**
	 * Checks if the current list is empty or not.
	 * @return true if the list in this object is empty. false otherwise. 
	 */
	public boolean isEmpty();
}
