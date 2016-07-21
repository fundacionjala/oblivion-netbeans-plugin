public with sharing class WithVariablesAndMethods {

    public Integer variable1;
    public String variable2;
    public Boolean variable3;

    WithVariablesAndMethods() {
        variable1 = 0;
        variable2 = '';
        variable3 = true;
    }

    public String method1() {
        Integer aux;
        String text;
        return variable2;
    }

    public Boolean method2() {
        String aux;
        String text;
        return variable3;
    }
    
    public Boolean method2(Integer a) {
        String aux;
        String text;
        return variable3;
    }

    public Integer method3() {
        Boolean aux;
        String text;
        return variable1;
    }
}