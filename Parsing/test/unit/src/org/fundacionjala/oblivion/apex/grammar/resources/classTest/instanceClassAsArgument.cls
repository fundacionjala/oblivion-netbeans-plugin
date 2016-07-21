public class ClassA {
    public void methodA() {
        methodB(new ClassA());
    }

    public void methodB(ClassA a) {
    }
}
