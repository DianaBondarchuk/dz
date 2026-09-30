import java.util.*;

class Fine {
    private String type;
    private double amount;

    public Fine(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() { return type; }
    public double getAmount() { return amount; }

    @Override
    public String toString() {
        return "{Тип: '" + type + "', Сума: " + amount + " грн}";
    }
}

class Person {
    private String id;
    private String name;
    private String city;
    private List<Fine> fines;

    public Person(String id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.fines = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCity() { return city; }
    public List<Fine> getFines() { return fines; }

    public void setName(String name) { this.name = name; }
    public void setCity(String city) { this.city = city; }

    public void addFine(Fine fine) { fines.add(fine); }

    @Override
    public String toString() {
        return "ІПН: " + id + " | ПІБ: " + name + " | Місто: " + city + " | Штрафи: " + fines;
    }
}

public class TaxInspectionDatabase {
    private Map<String, Person> database = new TreeMap<>();

    public void printAll() {
        System.out.println("\n=== ПОВНА БАЗА ДАНИХ ===");
        database.values().forEach(System.out::println);
    }

    public void printById(String id) {
        System.out.println("\n=== ДАНІ ЗА ІПН: " + id + " ===");
        Person person = database.get(id);
        if (person != null) System.out.println(person);
        else System.out.println("Запис не знайдено.");
    }

    public void printByFineType(String type) {
        System.out.println("\n=== ШТРАФИ ЗА ТИПОМ: " + type + " ===");
        for (Person p : database.values()) {
            for (Fine f : p.getFines()) {
                if (f.getType().equalsIgnoreCase(type)) {
                    System.out.println(p.getName() + " (" + p.getId() + ") -> " + f);
                }
            }
        }
    }

    public void printByCity(String city) {
        System.out.println("\n=== ЖИТЕЛІ МІСТА: " + city + " ===");
        database.values().stream()
                .filter(p -> p.getCity().equalsIgnoreCase(city))
                .forEach(System.out::println);
    }

    public void addPerson(Person person) {
        database.put(person.getId(), person);
    }

    public void addFineToPerson(String id, Fine fine) {
        Person person = database.get(id);
        if (person != null) {
            person.addFine(fine);
        } else {
            System.out.println("Людину з ІПН " + id + " не знайдено.");
        }
    }

    public void removeFine(String id, int fineIndex) {
        Person person = database.get(id);
        if (person != null && fineIndex >= 0 && fineIndex < person.getFines().size()) {
            person.getFines().remove(fineIndex);
        }
    }

    public void updatePersonInfo(String id, String newName, String newCity) {
        Person person = database.get(id);
        if (person != null) {
            person.setName(newName);
            person.setCity(newCity);
        }
    }

    public static void main(String[] args) {
        TaxInspectionDatabase db = new TaxInspectionDatabase();

        Person p1 = new Person("1234567890", "Іванов І.І.", "Київ");
        Person p2 = new Person("0987654321", "Петров П.П.", "Львів");

        db.addPerson(p1);
        db.addPerson(p2);

        db.addFineToPerson("1234567890", new Fine("Перевищення швидкості", 340));
        db.addFineToPerson("1234567890", new Fine("Паркування у неупорядкованому місці", 680));
        db.addFineToPerson("0987654321", new Fine("Перевищення швидкості", 340));

        db.printAll();
        db.printById("1234567890");
        db.printByFineType("Перевищення швидкості");
        db.printByCity("Київ");
    }
}
