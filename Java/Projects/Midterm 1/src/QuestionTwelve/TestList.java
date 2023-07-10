package QuestionTwelve;



public class TestList
{
	LLNode head;

	/**
	 * 
	 */
	public TestList()
	{
		// TODO Auto-generated constructor stub
		head = null;
	}

	protected void addToFront(String contents)
	{
		LLNode add = new LLNode(contents);
		LLNode curNode = head;
		if (head == null)
		{

			head = add;
			return;
		}
		add.next = head;
		head = add;
		
		}

	protected void addToFront(LLNode node) {
		
		LLNode curNode = head;
		if (head == null)
		{

			head = node;
			return;
		}
		node.next = head;
		head = node;
		
		
	}
	protected void addToBack(String content)
	{
		
		LLNode add = new LLNode(content);
		LLNode curNode = head;
		
		if (head == null)
		{

			head = add;
			return;
		}
		while (curNode.next != null)
		{
			
			curNode = curNode.next;
			
		}
		curNode.next = add;
		
		
	}

	protected void addToBack(LLNode node)
	{
		LLNode curNode = head;
		
		if (head == null)
		{
			
			head = node;
			return;
		}

		while (curNode.next != null)
		{
			curNode = curNode.next;
		}
	
		curNode.next = node;
		
		
	}

	protected void printAll()
	{
		LLNode curNode = head;
		while (curNode.next != null)
		{
			System.out.println(curNode.getContents());
			curNode = curNode.next;
		}
		System.out.println(curNode.getContents());

	}


}
