public with sharing class UseSetAsAnClassMember {
    String a;
    UseSetAsAnClassMember() {
        String a = MyClass.method().set('myparameter');
    }
}