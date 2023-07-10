package QuestionTwelve;

/**
 * 
 */

/**
 * @author Jerry Heuring
 *
 */
public class LLNode
{
	protected String contents; // the contents of this LLNode
	protected LLNode next; // Reference to next LLNode in list.

	/**
	 * The constructor -- The default constructor will build a node with a null
	 * reference for the string and a null reference for the next item in the list.
	 */
	public LLNode()
	{
		this(null, null);
	}

	/**
	 * Initializes this linked list node to the string given and sets the next
	 * reference to null.
	 * 
	 * @param contents The string to store in this LLNode
	 */
	public LLNode(String contents)
	{
		this(contents, null);
	}

	/**
	 * Initializes this linked list node to the string and LLNode reference passed
	 * in.
	 * 
	 * @param contents The string to store in this LLNode
	 * @param next     The reference to the next item in the List.
	 */
	public LLNode(String contents, LLNode next)
	{
		this.contents = contents;
		this.next = next;
	}

	/**
	 * @return the contents of this linked list node.
	 */
	protected String getContents()
	{
		return contents;
	}

	/**
	 * @param contents the string to store in this linked list node.
	 */
	protected void setContents(String contents)
	{
		this.contents = contents;
	}

	/**
	 * @return the reference to the next item in the Linked List
	 */
	protected LLNode getNext()
	{
		return next;
	}

	/**
	 * @param next the Node to set as the node to follow this item in the list.
	 */
	protected void setNext(LLNode next)
	{
		this.next = next;
	}

}
