
import java.util.Stack;
public class Homework1 {

	public static  String number;
	public static void main(String[] args) {


		number  = args[0];
		char [] letter = number.toCharArray();

		Node root = Stack(letter);
		String get = infix(root);
		System.out.println(get+"="+calculate(root));

	}
	public static Node Stack(char letter []) {

		Stack<Node> data = new Stack();
		Node node,num1,num2;

		for(int i=0;i<letter.length;i++) {

			if(!is_operater(letter[i])) {

				node = new Node(letter[i]);
				data.push(node);

			}else {

				node = new Node(letter[i]);
				num2 = data.pop();
				num1 = data.pop();
				node.left = num1;
				node.right = num2;

				data.push(node);
			}

		}

		node = data.peek();
		return node;



	}
	public static String infix(Node node) {

		StringBuilder infix = new StringBuilder();
		inorder(node,infix);

		infix.deleteCharAt(0);
		infix.deleteCharAt(infix.length()-1);
		for(int i =1;i<infix.length()-1;i++) {

			if(is_number(infix.charAt(i))) {
				infix.deleteCharAt(i-1);

				if(i+1<= infix.length()){
					infix.deleteCharAt(i);
				}

			}


		}
//			System.out.println(infix.toString());




		return infix.toString();
	}

	public static void inorder(Node node,StringBuilder infix) {


		if (node != null) {
			infix.append('(');
			if (node.letter == '+') {
				inorder(node.left, infix);
				infix.append('+');
				inorder(node.right, infix);
			} else if (node.letter == '-') {
				inorder(node.left, infix);
				infix.append('-');
				inorder(node.right, infix);
			} else if (node.letter == '*') {
				inorder(node.left, infix);
				infix.append('*');
				inorder(node.right, infix);
			} else if (node.letter == '/') {
				inorder(node.left, infix);
				infix.append('/');
				inorder(node.right, infix);
			} else infix.append(node.letter);
			infix.append(')');


		}
	}

	public static boolean is_operater(char letter) {

		if(letter== '+' || letter=='-' || letter=='*' || letter=='/') {

			return true;
		}
		return false;

	}

	public static boolean is_number(char letter) {

		if(letter== '1' || letter=='2' || letter=='3' || letter=='4'|| letter=='5' || letter=='6' || letter=='7'|| letter=='8' || letter=='9' || letter=='0') {

			return true;
		}
		return false;

	}

	public static boolean ishasoperater(String text) {

		for(int i=0;i<text.length();i++) {

			if(is_operater(text.charAt(i))){
				return true;
			}

		}

		return false;
	}


	public static int calculate(Node node) {

		if(node != null){
			if(node.letter == '+') {
				return calculate(node.left) + calculate(node.right);
			}else if(node.letter == '-') {
				return calculate(node.left) - calculate(node.right);
			}else if(node.letter == '*') {
				return calculate(node.left) * calculate(node.right);
			}else if(node.letter == '/') {
				return calculate(node.left)  / calculate(node.right);
			}else return Integer.parseInt((String.valueOf(node.letter)));

		}return 0;


	}





}