public class myQueue<T>
{
	private LLNode head;
	private LLNode tail;

	public myQueue()
	{ // Constructor initializing queue.
		head = tail = null;
	}

	public Object dequeue()
	{
		if (head != null)
		{
			Object item = head.getContents();
			head = head.getNext();
			return item;
		}
		return null;
	}

	public void enqueue(Object item)
	{
		LLNode newNode = new LLNode(item);
		if (tail == null)

			head = newNode;
		else
			tail.setNext(newNode);
		tail = newNode;

	}

	public static void main(String[] args)
	{
		myQueue<String> queue = new <String>myQueue();

		String word1 = "1";
		String word2 = "2";
		queue.enqueue(word1);
		queue.enqueue(word2);
		
		System.out.println(queue.toString());

	}
}