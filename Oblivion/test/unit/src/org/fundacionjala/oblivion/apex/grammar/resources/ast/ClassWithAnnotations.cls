@isTest
global class M {
    protected @TestVisible Integer property {
        get { return 0; }
        set { property = value; }
    }

    public virtual override testMethod void testMe() {

    }
}