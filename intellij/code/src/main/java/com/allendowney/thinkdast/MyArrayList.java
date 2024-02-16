package com.allendowney.thinkdast;

import java.util.*;

/**
 * @author downey
 * @param <T>
 *
 */
public class MyArrayList<T> implements List<T> {
	int size;                    // keeps track of the number of elements
	private T[] array;           // stores the elements

	/**
	 *
	 */
	@SuppressWarnings("unchecked")
	public MyArrayList() {
		// You can't instantiate an array of T[], but you can instantiate an
		// array of Object and then typecast it.  Details at
		// http://www.ibm.com/developerworks/java/library/j-jtp01255/index.html
		array = (T[]) new Object[10];
		size = 0;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// run a few simple tests
		MyArrayList<Integer> mal = new MyArrayList<Integer>();
		mal.add(1);
		mal.add(2);
		mal.add(3);
		System.out.println(Arrays.toString(mal.toArray()) + " size = " + mal.size);

		mal.remove(new Integer(2));
		System.out.println(Arrays.toString(mal.toArray()) + " size = " + mal.size);
	}

	@Override
	public boolean add(T element) {
		// TODO: FILL THIS IN!
		if (size >= array.length) {
			//큰 배열을 만들고 요소를 복사
			T[] bigger = (T[]) new Object[array.length * 2];
			System.arraycopy(array, 0, bigger, 0, array.length);
			array = bigger;
		}
		array[size] = element;
		size++;
		return true;
	}

	@Override
	public void add(int index, T element) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		// add the element to get the resizing
		add(element);

		// shift the elements
		for (int i=size-1; i>index; i--) {
			array[i] = array[i-1];
		}
		// put the new one in the right place
		array[index] = element;
	}

	@Override
	public boolean addAll(Collection<? extends T> collection) {
		boolean flag = true;
		for (T element: collection) {
			flag &= add(element);
		}
		return flag;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		// note: this version does not actually null out the references
		// in the array, so it might delay garbage collection.
		size = 0;
	}

	@Override
	public boolean contains(Object obj) {
		return indexOf(obj) != -1;
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		for (Object element: collection) {
			if (!contains(element)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public T get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return array[index];
	}

	@Override
	public int indexOf(Object target) {
		// TODO: FILL THIS IN!
		for(int i=0;i<size;i++){
			if(Objects.equals(array[i],target)) return i;
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
	public Iterator<T> iterator() {
		// make a copy of the array
		T[] copy = Arrays.copyOf(array, size);
		// make a list and return an iterator
		return Arrays.asList(copy).iterator();
	}

	@Override
	public int lastIndexOf(Object target) {
		// see notes on indexOf
		for (int i = size-1; i>=0; i--) {
			if (equals(target, array[i])) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public ListIterator<T> listIterator() {
		// make a copy of the array
		T[] copy = Arrays.copyOf(array, size);
		// make a list and return an iterator
		return Arrays.asList(copy).listIterator();
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		// make a copy of the array
		T[] copy = Arrays.copyOf(array, size);
		// make a list and return an iterator
		return Arrays.asList(copy).listIterator(index);
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

	@Override
	public T remove(int index) {
		// TODO: FILL THIS IN!

		//배열 인덱스 범위를 벗어난경우
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		T removedElement = array[index];

		// 요소를 왼쪽으로 이동. 제일 끝값인 경우에는 알아서 실행 x ex)index가 2, size가 3인경우
		for (int i = index; i < size - 1; i++) {
			array[i] = array[i + 1];
		}
		//앞으로 다 땡겼으면 마지막 인덱스에는 null 대입
		array[size - 1] = null;
		size--;
		return removedElement;

		// 저자코드 인데, null 대입 x 그냥 size만 가지고 노네.. 메모리 누수 걱정없나..?
//		T element = get(index);
//		for (int i=index; i<size-1; i++) {
//			array[i] = array[i+1];
//		}
//		size--;
//		return element;
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
	public T set(int index, T element) {
		// TODO: FILL THIS IN!
		//1. 범위에 없는 인덱스인경우 에러발생시키기
//		//1-1. 해당 인덱스의 요소가 instanceof T 했을때.. --> 애초에 T타입으로 만드니까 상관 x 지네릭스가 런타임에러를 컴파일 타임에 잡아주잖아..
//		if(index < 0 ||index >= size) {
//			throw new IndexOutOfBoundsException();
//		}
//			//2. 해당 인덱스에 요소가 있는경우, 그걸 tmp에 저장해두고, 그 인덱스에 element넣고 tmp반환.
//		else if(array[index] != null) {
//			T tmp = array[index];
//			array[index] = element;
//			return tmp;
//		}else{
//			//3. 해당인덱스에 요소가 없는 경우 그냥 해당 인덱스에 element넣고 null반환
//			array[index] = element;
//			return null;
//		}

		//저자 코드. 기존 메서드를 활용하니 코드가 깔끔..
		// no need to check index; get will do it for us
		T old = get(index);
		array[index] = element;
		return old;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		if (fromIndex < 0 || toIndex >= size || fromIndex > toIndex) {
			throw new IndexOutOfBoundsException();
		}
		T[] copy = Arrays.copyOfRange(array, fromIndex, toIndex);
		return Arrays.asList(copy);
	}

	@Override
	public Object[] toArray() {
		return Arrays.copyOf(array, size);
	}

	@Override
	public <U> U[] toArray(U[] array) {
		throw new UnsupportedOperationException();
	}
}
