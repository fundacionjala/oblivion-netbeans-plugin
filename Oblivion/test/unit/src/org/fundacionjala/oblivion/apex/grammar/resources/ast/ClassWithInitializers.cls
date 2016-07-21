public class A {

	public String myString = 'I am a String';
	public Integer myInteger = someFunction();
	public Integer[] myArray = new Integer[]{11, 25, 300};
	public A firstA = new A();
	public A secondA = new A(arg1, arg2);
	public List<B> firstList = new List<B>();
	public List<B> secondList = new List<B>{ b1, b2, b3 };
	public Set<C> mySet = new Set<C>{ c1, c2, c3 };
	public Map<K, V> myMap = new Map<K, V> { k1 => v1, k2 => v2, k3 => v3 };
    
}