package com.allendowney.thinkdast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jfree.data.xy.XYSeries;

import com.allendowney.thinkdast.Profiler.Timeable;

/*
결론 : 접근은 ArrayList, 추가&삭제는 LinkedList good.
실행시간이 get과 set메서드에 의존한다면 ArrayList 클래스가 좋음.
실행시간이 시작이나 끝 근처에 요소를 추가하거나 삭제하는 연산에 의존한다면 LinkedList클래스가 좋음.

공간에 대해서도 주의. ArrayList에서 요소들은 한 덩어리의 메모리 안에 나란히 저장되어 거의 낭비되는 공간이 없고,
컴퓨터 하드웨어도 연속된 덩어리에서 종종 속도가 더 빠르다.

LinkedList에서 각 요소는 하나 또는 두개의 참조가 있는 노드가 필요. 참조는 공간을 차지하고,
메모리 여기저기에 노드가 흩어져 있으면 하드웨어의 효율이 떨어질 수 있다.
 */

public class ProfileListAdd {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//profileArrayListAddEnd();
		//profileArrayListAddBeginning();
		//profileLinkedListAddBeginning();
		profileLinkedListAddEnd();
	}

	/**
	 * Characterize the run time of adding to the end of an ArrayList
	 */
	public static void profileArrayListAddEnd() {
		Timeable timeable = new Timeable() {
			List<String> list;

			//ArrayList<String>을 초기화
			public void setup(int n) {
				list = new ArrayList<String>();
			}

			//리스트의 끝에 문자열을 n번 추가
			public void timeMe(int n) {
				for (int i=0; i<n; i++) {
					list.add("a string");
				}
			}
		};
		int startN = 4000;
		int endMillis = 1000;
		runProfiler("ArrayList add end", timeable, startN, endMillis);
	}
	
	/**
	 * Characterize the run time of adding to the beginning of an ArrayList
	 */
	public static void profileArrayListAddBeginning() {
		Timeable timeable = new Timeable() {
			List<String> list;

			//ArrayList<String>을 초기화
			public void setup(int n) {
				list = new ArrayList<String>();
			}

			//리스트의 시작 부분에 문자열을 추가
			public void timeMe(int n) {
				for (int i=0; i<n; i++) {
					list.add(0, "a string");
				}
			}
		};


		int startN = 4000; //리스트에 추가할 요소의 수
		int endMillis = 10000; //실행할 최대 시간(밀리초 단위)
		runProfiler("ArrayList add beginning", timeable, startN, endMillis);
	}

	/**
	 * Characterize the run time of adding to the beginning of a LinkedList
	 */
	public static void profileLinkedListAddBeginning() {
		Timeable timeable = new Timeable() {
			List<String> list;

			//LinkedList<String>을 초기화
			public void setup(int n) {
				list = new LinkedList<String>();
			}

			//리스트의 시작 부분에 문자열을 추가
			public void timeMe(int n) {
				for (int i=0; i<n; i++) {
					list.add(0, "a string");
				}
			}
		};
		int startN = 128000;
		int endMillis = 2000;
		runProfiler("LinkedList add beginning", timeable, startN, endMillis);
	}

	/**
	 * Characterize the run time of adding to the end of a LinkedList
	 */
	public static void profileLinkedListAddEnd() {
		Timeable timeable = new Timeable() {
			List<String> list;

			//LinkedList<String>을 초기화
			public void setup(int n) {
				list = new LinkedList<String>();
			}

			//리스트의 끝 부분에 문자열을 추가
			public void timeMe(int n) {
				for (int i=0; i<n; i++) {
					list.add("a string");
				}
			}
		};
		int startN = 64000;
		int endMillis = 1000;
		runProfiler("LinkedList add end", timeable, startN, endMillis);
	}

	/**
	 * Runs the profiles and displays results.
	 * 
	 * @param timeable
	 * @param startN
	 * @param endMillis
	 */

	//timeable 객체를 사용하여 프로파일링을 수행하고, 결과를 표시
	private static void runProfiler(String title, Timeable timeable, int startN, int endMillis) {
		//Profiler 객체를 생성하고, timingLoop 메서드를 사용하여 지정된 시간(endMillis) 동안 연산을 수행
		Profiler profiler = new Profiler(title, timeable);
		XYSeries series = profiler.timingLoop(startN, endMillis);
		//결과를 plotResults를 통해 표시
		profiler.plotResults(series);
	}
}