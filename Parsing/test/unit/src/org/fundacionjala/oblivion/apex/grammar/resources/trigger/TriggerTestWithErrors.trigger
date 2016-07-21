//atleast one operation must be defined for the trigger
trigger AccountTrigger on Account (){
	//inline comment
	{
		//class declarations are not allowed in a inner block statement.
		public class MyTrigerClass {
			public void doNothing() { }
		}
	}
	//Can't return a value;
	return true;
}
