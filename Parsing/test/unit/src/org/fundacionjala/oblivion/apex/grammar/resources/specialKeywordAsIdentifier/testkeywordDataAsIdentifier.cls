public with sharing class testkeywordDataAsIdentifier {
    public class Data {
        String data;
        Data dt;
        public Data(Data data) {
            this.data = data;
            String data;
            Data data;
            List<Object> list1 = [SELECT field1, field2 FROM abc OFFSET :data];
        }
    }
}