public with sharing class FutureAnnotation{
    @future 
    static void myMethod(String a, Integer i) {
        System.debug('Method called with: ' + a + ' and ' + i);
        // Perform long-running code
    }
}