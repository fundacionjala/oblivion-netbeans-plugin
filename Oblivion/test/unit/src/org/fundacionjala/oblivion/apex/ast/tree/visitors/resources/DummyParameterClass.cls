

public class SimpleClass {

    private Integer code;
    private String name;
    private String lastName;


    public String getFullName() {
         String full = name + ' ' + lastName;
         return full;
    }

    public void updateName(String newName) {
          name = newName;
    }
}