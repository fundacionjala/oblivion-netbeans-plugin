public class TestClass {

    private Integer myInt {
        get;
        set;
    }

    /*
    * class comment
    * multi-line
    */
    private class TestInnerClass{}

    //Comment for myString
    protected String myString = '';

    static { myString = 'a'; }
    
    @future
    public void doSomething() {
        Integer myInt2 = MY_INT + 2;

        if (myInt2 > 5) {
            return;
        }

        try {

        } catch (Exception ex) {
            
        } finally {

        }

        while(myInt2 > 0) {

        }

        for(Integer index = 0;index < 10; index++) {

        }

        do {

        } while(myInt2 < 10);
    }
}