public class TestClass {
    
    public void foo(integer arg1, double arg2) {

    }

    public void foo(integer arg1, TestClass arg2) {

    }

    public void foo(TestClass arg) {

    }

    public integer foo(integer arg) {
        return arg;
    }

    public void test() {
        foo(1, 1.0);
        foo(foo(2), 2.1);
        foo(this);
        foo(3, this);
    }
}