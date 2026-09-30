import java.util.*;

class WordEntry {
    private String word;
    private List<String> translations;
    private int requestCount;

    public WordEntry(String word, List<String> translations) {
        this.word = word;
        this.translations = new ArrayList<>(translations);
        this.requestCount = 0;
    }

    public String getWord() { return word; }
    public List<String> getTranslations() { return translations; }
    public int getRequestCount() { return requestCount; }
    public void incrementCount() { this.requestCount++; }

    public void addTranslation(String translation) {
        if (!translations.contains(translation)) translations.add(translation);
    }

    public void replaceTranslation(String oldT, String newT) {
        int idx = translations.indexOf(oldT);
        if (idx != -1) translations.set(idx, newT);
    }

    public void removeTranslation(String translation) {
        translations.remove(translation);
    }
}

public class Dictionary {
    private Map<String, WordEntry> dictionary = new HashMap<>();

    public void addWord(String word, List<String> translations) {
        dictionary.put(word.toLowerCase(), new WordEntry(word, translations));
    }

    public void replaceWord(String oldWord, String newWord) {
        WordEntry entry = dictionary.remove(oldWord.toLowerCase());
        if (entry != null) {
            dictionary.put(newWord.toLowerCase(), new WordEntry(newWord, entry.getTranslations()));
        }
    }

    public void removeWord(String word) {
        dictionary.remove(word.toLowerCase());
    }

    public WordEntry getWordInfo(String word) {
        WordEntry entry = dictionary.get(word.toLowerCase());
        if (entry != null) {
            entry.incrementCount();
        }
        return entry;
    }

    public void printTop10Popular() {
        System.out.println("\n--- Топ-10 найпопулярніших слів ---");
        dictionary.values().stream()
                .sorted(Comparator.comparingInt(WordEntry::getRequestCount).reversed())
                .limit(10)
                .forEach(e -> System.out.println(e.getWord() + " (звернень: " + e.getRequestCount() + ")"));
    }

    public void printTop10Unpopular() {
        System.out.println("\n--- Топ-10 найменш популярних слів ---");
        dictionary.values().stream()
                .sorted(Comparator.comparingInt(WordEntry::getRequestCount))
                .limit(10)
                .forEach(e -> System.out.println(e.getWord() + " (звернень: " + e.getRequestCount() + ")"));
    }

    public static void main(String[] args) {
        Dictionary dict = new Dictionary();
        dict.addWord("apple", Arrays.asList("яблуко"));
        dict.addWord("book", Arrays.asList("книга", "книжка"));
        dict.addWord("cat", Arrays.asList("кіт"));

        // Симуляція звернень
        dict.getWordInfo("apple");
        dict.getWordInfo("apple");
        dict.getWordInfo("book");

        System.out.println("=== СЛОВНИК (Завдання 2) ===");
        System.out.println("Переклад слова 'apple': " + dict.getWordInfo("apple").getTranslations());

        dict.printTop10Popular();
        dict.printTop10Unpopular();
    }
}