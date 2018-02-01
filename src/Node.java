public class Node {
    Character letter;

    Node left;
    Node right;

    Node(char x){

        letter = x;
        left = null;
        right = null;

    }

    @Override
    public String toString() {
        return letter.toString();
    }
}
