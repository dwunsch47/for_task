import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Phonebook {

    SortedMap<String, String> nameToPhone = new TreeMap<>();
    Map<String, String> phoneToName = new HashMap<>(); // assume numbers also dont repeat

    public int add(String name, String number) {
        nameToPhone.put(name, number);
        phoneToName.put(number, name);
        return nameToPhone.size();
    }

    public String findByNumber(String number) {
        String name = null;
        if (phoneToName.containsKey(number)) {
            name = phoneToName.get(number);
        }
        return name;
    }

    public String findByName(String name) {
        String number = null;
        if (nameToPhone.containsKey(name)) {
            number = nameToPhone.get(name);
        }
        return number;
    }

    public void printAllNames() {
        System.out.println("");
    }
}
