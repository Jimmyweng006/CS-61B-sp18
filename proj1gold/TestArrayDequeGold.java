import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestArrayDequeGold {
    ArrayDequeSolution<Integer> dqSol = new ArrayDequeSolution<>();
    StudentArrayDeque<Integer> dqStudent = new StudentArrayDeque<>();

    private static String processMessageParam(List<String> lists) {
        String ans = "";
        for (String s: lists) {
            ans += s;
            ans += "\n";
        }

        return ans;
    }

    @Test
    public void goldenTest() {
        Integer cur = 0;
        List<String> messageParam = new ArrayList<>();

        while (true) {
            Integer randomInt = StdRandom.uniform(4);
            if (randomInt.equals(0)) {
                dqSol.addFirst(cur);
                dqStudent.addFirst(cur);
                messageParam.add(String.format("addFirst(%s)", cur));
            } else if (randomInt.equals(1)) {
                dqSol.addLast(cur);
                dqStudent.addLast(cur);
                messageParam.add(String.format("addLast(%s)", cur));
            } else if (randomInt.equals(2) && !dqStudent.isEmpty()) {
                Integer removedSol = dqSol.removeFirst();
                Integer removedStudent = dqStudent.removeFirst();
                messageParam.add("removeFirst()");

                assertEquals(processMessageParam(messageParam), removedSol, removedStudent);
            } else if (randomInt.equals(3) && !dqStudent.isEmpty()) {
                Integer removedSol = dqSol.removeLast();
                Integer removedStudent = dqStudent.removeLast();
                messageParam.add("removeLast()");

                assertEquals(processMessageParam(messageParam), removedSol, removedStudent);
            }
            cur++;
        }
    }
}
