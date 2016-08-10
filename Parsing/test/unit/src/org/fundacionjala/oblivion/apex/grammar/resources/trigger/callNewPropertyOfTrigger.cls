public class MyClass {
    public void myMethod() {
	List<SObject> relatedObjects = Trigger.new;
	List<SObject> anotherObjects = System.Trigger.new;
	List<SObject> relatedObjects = !Trigger.isInsert ? Trigger.old : Trigger.new;
    }
}

