public class TestClass {

    private integer getInteger() {
        return 2;
    }
    
    public void test() {
        boolean testBool;
        integer testInteger;
        double testDouble;

        testBool = 2 == 2;
        testBool = ((2 == 2) == true);
        testInteger = 2 + 2;
        testDouble = (100 / testInteger) + 2.2;
        testDouble = testInteger * 2;
        integer testInteger2 = 2 + testInteger;
        testInteger2 = getInteger();
        testInteger2 = testInteger++;
        testInteger2 = --testInteger;
    }
}