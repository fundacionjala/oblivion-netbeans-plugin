public with sharing class IteratorWithGenericType {
    public Iterator<String> Iterator() {
        List<string> listX = new List<String> {'A','B','C'};
        return listX;
    }
    {
        List<string> listX = new List<String> {'A','B','C'};
        Iterator<String> iter = listX.iterator();
        while(iter.hasNext()) {
            String iterator = 'iterator';
            system.debug(iter.next());
        }
    }
}