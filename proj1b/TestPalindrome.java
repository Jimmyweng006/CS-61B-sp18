import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testPalindromeFalse() {
        assertFalse(palindrome.isPalindrome("cat"));
        assertFalse(palindrome.isPalindrome("dog"));
        assertFalse(palindrome.isPalindrome("doad"));

        OffByOne obo = new OffByOne();
        assertFalse(palindrome.isPalindrome("ac", obo));
        assertFalse(palindrome.isPalindrome("acca", obo));
    }

    @Test
    public void testPalindromeTrue() {
        assertTrue(palindrome.isPalindrome("aba"));
        assertTrue(palindrome.isPalindrome("bab"));

        OffByOne obo = new OffByOne();
        assertTrue(palindrome.isPalindrome("ab", obo));
        assertTrue(palindrome.isPalindrome("aabb", obo));
        assertTrue(palindrome.isPalindrome("%&", obo));
    }

    @Test
    public void testPalindromeEdgeCases() {
        assertFalse(palindrome.isPalindrome(null));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("aa"));
        assertTrue(palindrome.isPalindrome("aaa"));

        OffByOne obo = new OffByOne();
        assertFalse(palindrome.isPalindrome(null, obo));
        assertTrue(palindrome.isPalindrome("", obo));
        assertTrue(palindrome.isPalindrome("a", obo));
    }
}
