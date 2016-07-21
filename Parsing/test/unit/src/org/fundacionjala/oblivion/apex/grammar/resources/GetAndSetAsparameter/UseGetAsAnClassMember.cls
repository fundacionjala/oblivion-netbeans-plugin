public with sharing class UseGetAsAnClassMember {
    String a;
    UseGetAsAnClassMember() {
        String a = MyClass.method().get('myparameter');
    }
}