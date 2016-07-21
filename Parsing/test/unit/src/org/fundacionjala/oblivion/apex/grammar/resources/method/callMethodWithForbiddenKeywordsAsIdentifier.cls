public class TestClass {
    
    public void class(integer arg1, double arg2) {

    }

    public void as(integer arg1, TestClass arg2) {

    }

    public void false(TestClass arg) {

    }

    public integer null(integer arg) {
        return arg;
    }

    public void test() {
        class(1, 1.0);
        as(foo(2), 2.1);
        false(this);
        null(3, this);
    }
}