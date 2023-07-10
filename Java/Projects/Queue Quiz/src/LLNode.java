
/**
 * 
 */

/**
 * @author Jerry Heuring
 * 
 * Linked List Node Class Version 0.  
 * It contains access methods and constructors.
 */
public class LLNode {
	protected Object contents;
	protected LLNode next;

	/**
	 * 
	 */
	public LLNode() {
		// TODO Auto-generated constructor stub
		this(null, null);
	}

	public LLNode(Object info) {
		this(info, null);
	}

	public LLNode(Object info, LLNode next) {
		this.contents = info;
		this.next = next;
	}

	protected LLNode getNext() {
		return next;
	}

	protected void setNext(LLNode next) {
		this.next = next;
	}

	public Object getContents() {
		return contents;
	}

	public void setContents(Object contents)
	{
		this.contents = contents;
	}

	public String toString() {
		String sum = "";
		
		//System.out.println(next.next);
		while (next != null) {
			sum += next.getContents();
			
			next = next.next;
		}
		return sum;
}
	}
