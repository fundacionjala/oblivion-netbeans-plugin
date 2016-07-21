public with sharing class SystemMethodWithBlock {
    SystemMethodWithBlock() {
        System.runAs(user) {
        //some code
        }
    }
}
