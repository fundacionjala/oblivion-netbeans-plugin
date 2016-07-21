public class ClassA {
    public void aMethod() {
        new ClassB();
        new ClassB().a();
        new ClassC.ClassB();
        ClassB b = new ClassB();
        b = new ClassB();
        b = new ClassB().b();
        b = new ClassC.ClassB();
        b = new ClassD.ClassC.ClassB();
    }
}
