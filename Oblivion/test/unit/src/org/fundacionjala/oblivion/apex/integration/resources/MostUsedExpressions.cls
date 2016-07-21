global without sharing class TestClass {

    private static TestClass mInstance = null;
    
    //Issue #46
    private final static object OBJECT_VALUE = 'ObjectTest';
    public static final object OBJECT_VALUE_2 = 'ObjectTest2';

    //Issue #46
    private blob mBlobVar;
    private boolean mBooleanVar;
    private date mDateVar;
    private datetime mDatetimeVar;
    private decimal mDecimalVar;
    private double mDoubleVar;
    private ID mIDVar;
    transient integer mIntegerVar;
    private long mLongVar;
    //Issue #46
    private object mObjectVar;
    private string mStringVar;
    private time mTimeVar;
    private List<integer> mIntegersList;
    private InnerClassA mInnerClass;
    
    public static TestClass getInstance() {
        if(mInstance == null) {
            mInstance = new TestClass();
        }
        
        return mInstance;
    }
    
    
    private TestClass() {
        this(2.5, 'StringTest');
        //Issue #46
        mBlobVar = blob.valueOf('BlobTest');
        mBooleanVar = true;
        //Issue #46
        mDateVar = date.newInstance(2015, 1, 1);
        mDatetimeVar = datetime.newInstance(2015, 2, 2, 2, 2, 2);
    }
    
    private TestClass(decimal decimalVar, string stringVar) {
        mDecimalVar = decimalVar;
        mDoubleVar = 3.14159;
        mIntegerVar = TestClass.getInteger();
        mIDVar = '00300000003T2PGAA0';
        mLongVar = 100000001L;
        mObjectVar = OBJECT_VALUE;
        mStringVar = stringVar + toString(DayOfWeek.FRIDAY) + 'ConcatTest';
        //Issue #46
        mTimeVar = time.newInstance(1, 2, 3, 4);
        mIntegersList = this.getIntegersList(10);
        mIntegerVar = getSumAllItems(mIntegersList);
        mDoubleVar = factorial(50);
        mInnerClass = new InnerClassB(this);
        InnerClassB innerClassB = (InnerClassB) mInnerClass;
        innerClassB.mTestClass = this;
        mInnerClass.start();
    }
    
    public string toString(DayofWeek day) {
        string dayToString = '';
        
        if(day==DayofWeek.MONDAY)
            dayToString = 'Monday';
        else if(day==DayofWeek.TUESDAY)
            dayToString = 'Tuesday';
        else if(day==DayofWeek.WEDNESDAY)
            dayToString = 'Wednesday';
        else if(day==DayofWeek.THURSDAY)
            dayToString = 'Thursday';
        else if(day==DayofWeek.FRIDAY)
            dayToString = 'Tuesday';
        else if(day==DayofWeek.SATURDAY)
            dayToString = 'Saturday';
        else
            dayToString = 'Sunday';
            
        return dayToString;
    }
    
    global TestClass getThisObject() {
        return this;
    }
    
    private static integer getInteger() {
        return 2 + 6 / 2;
    }
    
    private List<integer> getIntegersList(integer itemsNumber) {
        List<integer> integersList = new List<integer>();
        
        for(integer index = 0; index < itemsNumber; index++) {
            integersList.add(index);
        }
        
        return integersList;
    }
    
    private integer getSumAllItems(List<integer> integersList) {
        integer result = 0;
        
        for(integer item : integersList) {
            result += item;
        }
        
        return result;
    }
    
    private double factorial(integer n){
        if(n==0)
            return 1;
        else
            return n*(factorial(n-1));
    }
    
    public class MyException extends Exception{}
    
    public enum DayofWeek {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY
    }
    
    private interface IInnerInterfaceA {
    
    }
    
    private interface IInnerInterfaceB {
    
    }
    
    private interface IInnerInterfaceC extends IInnerInterfaceA {
    
    }
    
    private abstract class AbstractInnerClass {
        public abstract void initialize();
    }
    
    global virtual class InnerClassA extends AbstractInnerClass {
    
        protected decimal mDecimalVar;
        protected decimal[] mDecimalArray;
        
        public InnerClassA() {
            this(5.0);
        }
        
        public InnerClassA(decimal decimalVar) {
            mDecimalVar = decimalVar;
            mDecimalArray = new decimal[2];
        }
        
        public override void initialize() {
            mDecimalVar = 0;
        }
        
        public void start() {
            doSomething();
        }
        
        protected void doSomething() {
            mDecimalVar *= --mDecimalVar + mDecimalVar++ + ++mDecimalVar - 1;
            
            decimal[] decimalArray = new decimal[]{ 1, 2, 3, 4};
            try {
                if(mDecimalVar == 65) {
                    mDecimalArray[0] = mDecimalVar = mDecimalVar / 2;
                    mDecimalArray[1] = (decimalArray[0] + decimalArray[2]) / decimalArray[1] * decimalArray[3];
                }
                else if(mDecimalVar < 65) {
                    mDecimalArray[0] = 0;
                    mDecimalArray[1] = 1;
                }
                else if (mDecimalVar < 100) {
                    mDecimalArray[0] = -1 + mDecimalVar * 0.5;
                    mDecimalArray[1] = mDecimalVar / 2.1;
                }
                else {
                    throw new MyException('testing');
                }
            } catch (MyException ex) {
                mDecimalArray[0] = mDecimalVar = 1;
            } catch (Exception ex) {
                mDecimalArray[0] = mDecimalVar = 2;
            } finally {
                mDecimalArray[0] *= 2;
            }
        }
    }
    
    public with sharing class InnerClassB extends InnerClassA implements IInnerInterfaceB, IInnerInterfaceC {
    
        public TestClass mTestClass;
        public Map<integer,decimal> mDecimalMap;
    
        public InnerClassB(TestClass testClass) {
            super(5.0);
            this.mTestClass = testClass;
            this.mTestClass = this.mTestClass.getThisObject();
            mDecimalMap = getMap(30, true);
        }
        
        private boolean isInstanceOfInnerClassB() {
            InnerClassA aClass = this;
        
            if(aClass instanceof InnerClassB) {
                return true;
            }
            
            return false;
        }
        
        private Map<integer,decimal> getMap(integer itemsNumber, boolean duplicateItems) {
            Map<integer, decimal> decimalMap = new Map<integer, decimal>();
            
            if(itemsNumber >=1 && itemsNumber <= 100) {
                if(duplicateItems) itemsNumber *= 2;
                
                fillMap(decimalMap, itemsNumber);
            }
            
            return decimalMap;
        }
        
        private void fillMap(Map<integer, decimal> decimalMap, integer itemsNumber) {
            decimal value = 0;
            
            for(integer index = 1; index <= itemsNumber; index++) {
                value += (index <= itemsNumber / 2) ? index / 2 : index / 3 * -1;
                decimalMap.put(index, value);
            }
        }
    }
}