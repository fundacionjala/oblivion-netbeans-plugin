public with sharing class UseGetOnReturn  {
    public String method() {
        return (fieldMap.get(fieldName) == null)?'':fieldMap.get(fieldName);
    }
}