public with sharing class TypeMethods {
    {
        Type t1 = Account.class;
        Type t2 = Type.forName('Account');
        System.assert(t1.equals(t2));
    }
}