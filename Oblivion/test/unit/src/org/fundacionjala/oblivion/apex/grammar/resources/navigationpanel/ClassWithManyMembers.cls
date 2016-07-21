public class OuterClass {

    private static final Integer MY_INT;
    public static String sharedState;
    public static Integer getInt() { return MY_INT; }

    static {
        MY_INT = 2; 
    }

    private final String m; 

    public virtual interface MyInterface {

    }

    interface MySecondInterface extends MyInterface { 
        Integer method2(Integer i); 
    }

    public virtual class InnerClass implements MySecondInterface {
        
    }

    public abstract class AbstractChildClass extends InnerClass {

    }

    public class ConcreteChildClass extends AbstractChildClass {
                
    }

    private class AnotherChildClass extends InnerClass {
        AnotherChildClass(String s) {
            super(s);
        }
    }

    public virtual class MyException extends Exception {
        private Double d;

        MyException(Double d) {
            this.d = d;
        }

        private void doIt() {}
    }

    public abstract class MySecondException extends Exception implements MyInterface {
    }
}