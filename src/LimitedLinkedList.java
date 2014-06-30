import java.util.Iterator;
class LimitedLinkedList<T> implements Iterable<T>
{
	public Node head, tail, current;
	public int count, limit;
	LimitedLinkedList(int l)
	{
		head = tail = null;
		count = 0;
		limit = l;
	}
	public boolean add(T t)
	{
		if(t == null)
			return false;
		Node add = new Node(t);
		if(head == null)
		{
			head = tail = add;
			count++;
			return true;
		}
		tail.setNext(add);
		tail = add;
		count++;
		if(count >= limit)
			pop();
		return true;
	}
	public void pop()
	{
		if(head != null && head.getNext() != null)
		{
			head = head.getNext();
			count--;
		}
	}
	public Node getHead() { return head; }
	public boolean isEmpty() { return count == 0; }
	public int getCount() { return count; }
	public Node head() { return head; }
	class Node
	{
		private T data;
		private Node next;
		Node(T d)
		{
			data = d;
		}
		Node getNext() { return next; }
		void setNext(Node n) { next = n; }
		T getData() { return data; }
	}
	public Iterator<T> iterator() { return new LimitedLinkedListIterator(); }
	class LimitedLinkedListIterator implements Iterator<T>
	{
		LimitedLinkedListIterator()
		{
			current = head;
		}
		public boolean hasNext() {
			return current != null && current.getNext() != null;
		}
		public T next() {
			T d = current.getData();
			current = current.getNext();
			return d;
		}
		public void remove() {
			
		}
	}
}