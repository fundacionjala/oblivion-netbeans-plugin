public with sharing class KeywordAfterAKeywordThis {
    String offset; 
    String without;
    KeywordAfterAKeywordThis(String offset, String without){
        this.offset = offset;
        this.without = without;
        method(this.without,this.offset);
    }
    public void method(String without,String offset) {
        this.offset = without + offset;
        this.without = offset + without;
    }
}