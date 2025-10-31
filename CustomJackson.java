import java.util.*;

public class CustomJackson {
    static class JsonObject{
        private Map<String, Object> children = new HashMap<>();
        public void put(String key, Object value){
            children.put(key, value);
        }
        public Object get(String key){
            return children.get(key);
        }

        // Recursive print method
        public void printJson(int indent){
            String ind = "  ".repeat(indent);
            System.out.println(ind + "{");
            for(String key : children.keySet()){
                System.out.print(ind + "  " + key + ": ");
                Object value = children.get(key);
                if(value instanceof JsonObject){
                    ((JsonObject)value).printJson(indent + 1);
                } else if(value instanceof List){
                    printList((List<?>)value, indent + 1);
                } else {
                    System.out.println(value);
                }
            }
            System.out.println(ind + "}");
        }

        private void printList(List<?> list, int indent){
            String ind = "  ".repeat(indent);
            System.out.println(ind + "[");
            for(Object item : list){
                if(item instanceof JsonObject){
                    ((JsonObject)item).printJson(indent + 1);
                } else if(item instanceof List){
                    printList((List<?>)item, indent + 1);
                } else {
                    System.out.println(ind + "  " + item);
                }
            }
            System.out.println(ind + "]");
        }
    }

    public static void main(String[] args) {
        JsonObject root = new JsonObject();

        // person object
        JsonObject person = new JsonObject();
        person.put("name","Alice");
        person.put("age",30);

        // deeper: address object inside person
        JsonObject address = new JsonObject();
        address.put("city","Bangalore");
        address.put("zip","560001");
        person.put("address",address); // attach address to person

        // attach person to root
        root.put("person",person);

        // === Recursive print ===
        root.printJson(0);
    }
}

// {
//   person:   {
//     address:     {
//       zip: 560001
//       city: Bangalore
//     }
//     name: Alice
//     age: 30
//   }
// }