/**
 * Comments before the trigger should be supported
 */
trigger AccountTrigger on Account (after undelete, before insert){
	//inline comment
	public class MyTrigerClass {
		public void doNothing() { }
	}
	//inline comment
	throw new MyException();
	//inline comment
	return;
	//inline comment
	MyTrigerClass one = new MyTrigerClass();
	//inline comment
	one.doNothing();
	//inline comment
	
	/*
	 * MultiLine comment.
	 */
	
	//inline comment
	public interface MyInterface{ void implementMe(); }

    public void myMethod(){}
    private Integer mySecondMethod() { return null; }
}
/**
 * Comments after the trigger should be supported
 */