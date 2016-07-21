public class TestClass {

    private Integer value = 1;

    public Integer ReturnTestLiteral() {
        return 1;
    }

    public Integer ReturnTestVariable() {
        Integer a;
        a = 0;
        return a;
    }

    public Integer ReturnTestExpression1() {
        Integer a;
        a = 1;
        return 2 + a;
    }

    public Integer ReturnTestExpression2() {
        Integer a;
        a = 1;
        return 2 == a;
    }

    public Integer ReturnTestExpression3() {
        return this.value;
    }

    public Integer ReturnTestExpression4() {
        return this.ReturnTestLiteral();
    }

    public Integer ReturnTestExpression5() {
        Integer a;
        a = 1;
        return ++a;
    }

}