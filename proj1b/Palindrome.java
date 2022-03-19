import java.util.Objects;

public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        LinkedListDeque<Character> dq = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            dq.addLast(c);
        }

        return dq;
    }

    public boolean isPalindrome(String word) {
        if (Objects.isNull(word)) {
            return false;
        }

        Palindrome p = new Palindrome();
        Deque<Character> dq = p.wordToDeque(word);
//        while (!dq.isEmpty()) {
//            char first = dq.removeFirst();
//            char last = dq.isEmpty() ? first : dq.removeLast();
//
//            if (first != last) return false;
//        }
//
//        return true;

        return helper(dq);
    }

    private boolean helper(Deque<Character> dq) {
        if (dq.isEmpty()) {
            return true;
        }

        char first = dq.removeFirst();
        char last = dq.isEmpty() ? first : dq.removeLast();

        if (first != last) {
            return false;
        }

        return helper(dq);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word == null) {
            return false;
        }

        int size = word.length();
        for (int i = 0; i < size / 2; i++) {
            char first = word.charAt(i);
            char last = word.charAt(size - 1 - i);

            if (!cc.equalChars(first, last)) {
                return false;
            }
        }

        return true;
    }
}
