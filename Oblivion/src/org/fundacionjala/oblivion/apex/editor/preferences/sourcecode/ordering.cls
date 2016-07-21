public class ClassA {
    
    private static Nested instance;
    
    static {
        instance = new Nested();
    }
    
    public static Nested getInstance() {
        return instance;
    }

    public ClassA() {
    }
    
    private String name;

    {
       name = 'name';
    }
    
    public String getName() {
        return name;
    }

    private static class Nested {
    }

    private class innerClass {
    }
    
}
