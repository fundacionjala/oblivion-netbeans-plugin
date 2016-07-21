public with sharing class InstantiatingATypeBasedOnItsName {
    public static void invokeCustomImpl() {
        CustomImplementation__c cs = CustomImplementation__c.getInstance('Vehicle');
        Type t = Type.forName(cs.className__c);
    }
}