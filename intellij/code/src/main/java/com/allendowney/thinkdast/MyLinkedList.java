/**
 * 
 */
package com.allendowney.thinkdast;

import javax.jws.Oneway;
import java.util.*;

/**
 * @author downey
 * @param <E>
 *
 */
public class MyLinkedList<E> implements List<E> {

	/**
	 * Node is identical to ListNode from the example, but parameterized with T
	 *
	 * @author downey
	 *
	 */
	private class Node {
		public E data;
		public Node next;

		public Node(E data) {
			this.data = data;
			this.next = null;
		}
		@SuppressWarnings("unused")
		public Node(E data, Node next) {
			this.data = data;
			this.next = next;
		}
		public String toString() {
			return "Node(" + data.toString() + ")";
		}
	}

	private int size;            // keeps track of the number of elements
	private Node head;           // reference to the first node

	/**
	 *
	 */
	public MyLinkedList() {
		head = null;
		size = 0;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// run a few simple tests
		List<Integer> mll = new MyLinkedList<Integer>();
		mll.add(1);
		mll.add(2);
		mll.add(3);
		System.out.println(Arrays.toString(mll.toArray()) + " size = " + mll.size());

		mll.remove(new Integer(2));
		System.out.println(Arrays.toString(mll.toArray()) + " size = " + mll.size());
	}

	@Override
	public boolean add(E element) {
		if (head == null) {
			head = new Node(element);
		} else {
			Node node = head;
			// loop until the last node
			for ( ; node.next != null; node = node.next) {}
			node.next = new Node(element);
		}
		size++;
		return true;
	}

	//해당위치의 인덱스에 해당 요소 노드 추가
	@Override
	public void add(int index, E element) {
		//인덱스 범위를 벗어난경우 에러발생
//		if(index<0 || index > size) throw new IndexOutOfBoundsException();
//
//		//새로운 노드 생성
//		Node newNode = new Node(element);
//
//		// 인덱스가 0인 경우
//		if (index == 0) {
//			newNode.next = head;
//			head = newNode;
//			size++;
//			return;
//		}
//
//		// 이전 노드 찾기
//		Node prevNode = head;
//		for (int i = 0; i < index - 1; i++) {
//			prevNode = prevNode.next;
//		}
//
//		// 이전노드의 next에 newNode를 넣고,
//		// newNode의 next에 이전노드의 next를 넣기.
//		prevNode.next = newNode;
//		newNode.next = prevNode.next;
//
//		size++;

		//저자 코드. getNode 메서드 활용. 이전노드가 있는경우와 없는경우만 구분.
		// no need to check bounds; getNode does it.
		if (index == 0) {
			head = new Node(element, head);
		} else {
			Node node = getNode(index-1);
			node.next = new Node(element, node.next);
		}
		size++;
	}

	@Override
	public boolean addAll(Collection<? extends E> collection) {
		boolean flag = true;
		for (E element: collection) {
			flag &= add(element);
		}
		return flag;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> collection) {
		throw new UnsupportedOperationException();
	}

	// 상수시간처럼 보이지만, 요소의 갯수에 비례하여 가비지 컬렉터가 동작하므로 선형임. performance bug의 예.
	// 가비지 컬렉션처럼 뒷단에서 벌어지는 일에 주의.
	@Override
	public void clear() {
		head = null;
		size = 0;
	}

	@Override
	public boolean contains(Object obj) {
		return indexOf(obj) != -1;
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		for (Object obj: collection) {
			if (!contains(obj)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public E get(int index) {
		Node node = getNode(index);
		return node.data;
	}

	/** Returns the node at the given index.
	 * @param index
	 * @return
	 */
	private Node getNode(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		Node node = head;
		for (int i=0; i<index; i++) {
			node = node.next;
		}
		return node;
	}

	@Override
	public int indexOf(Object target) {
		//TODO: FILL THIS IN!
//		if(head == null) {
//			return -1;
//		}else {
//			int targetIndex = 0;
//			Node node = head;
//			for( ; node !=null; node = node.next) {
//				if(Objects.equals(target,node.data)){
//					return targetIndex;
//				}
//				targetIndex ++;
//			}
//			return -1;
//		}

//  저자 코드. size를 활용. 그리고 첫번째 노드도 굳이 확인안해도 될듯..
		Node node = head;
		for (int i=0; i<size; i++) {
			if (equals(target, node.data)) {
				return i;
			}
			node = node.next;
		}
		return -1;
	}

	/** Checks whether an element of the array is the target.
	 *
	 * Handles the special case that the target is null.
	 *
	 * @param target
	 * @param object
	 */
	private boolean equals(Object target, Object element) {
		if (target == null) {
			return element == null;
		} else {
			return target.equals(element);
		}
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<E> iterator() {
		E[] array = (E[]) toArray();
		return Arrays.asList(array).iterator();
	}

	@Override
	public int lastIndexOf(Object target) {
		Node node = head;
		int index = -1;
		for (int i=0; i<size; i++) {
			if (equals(target, node.data)) {
				index = i;
			}
			node = node.next;
		}
		return index;
	}

	@Override
	public ListIterator<E> listIterator() {
		return null;
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return null;
	}

	@Override
	public boolean remove(Object obj) {
		int index = indexOf(obj);
		if (index == -1) {
			return false;
		}
		remove(index);
		return true;
	}

	// 리스트에서 특정 위치의 요소를 제거하고, 그 요소를 반환
	@Override
	public E remove(int index) {
		//TODO: FILL THIS IN!

		// 인덱스 유효성 검사
//		if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
//
//		// index가 0인 경우
//		if (index == 0) {
//			Node targetNode = head;
//			head = head.next;
//			size--;
//			return targetNode.data;
//		}
//
//		// target노드의 next를 이전노드의 next에 넣기
//		Node prevNode = getNode(index - 1);
//		Node targetNode = prevNode.next;
//		prevNode.next = targetNode.next;
//
//		size--;
//
//		return targetNode.data;

		//저자 코드. 공통 작업을 밖으로 빼니까 코드가 간결.  
		E element = get(index);
		if (index == 0) {
			head = head.next;
		} else {
			Node node = getNode(index-1);
			node.next = node.next.next;
		}
		size--;
		return element;
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		boolean flag = true;
		for (Object obj: collection) {
			flag &= remove(obj);
		}
		return flag;
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E set(int index, E element) {
		Node node = getNode(index);
		E old = node.data;
		node.data = element;
		return old;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		if (fromIndex < 0 || toIndex >= size || fromIndex > toIndex) {
			throw new IndexOutOfBoundsException();
		}
		// TODO: classify this and improve it.
		int i = 0;
		MyLinkedList<E> list = new MyLinkedList<E>();
		for (Node node=head; node != null; node = node.next) {
			if (i >= fromIndex && i <= toIndex) {
				list.add(node.data);
			}
			i++;
		}
		return list;
	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		int i = 0;
		for (Node node=head; node != null; node = node.next) {
			// System.out.println(node);
			array[i] = node.data;
			i++;
		}
		return array;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}
}
