
import com.sun.org.apache.xml.internal.utils.StringBufferPool;

import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.JTree;



	public class Homework1 {


		Node node ;
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




			return infix.toString();
		}

		public static void inorder(Node node,StringBuilder infix) {


			if (node != null) {
				infix.append("(");
				inorder(node.left, infix);
				infix.append(node.value);
				inorder(node.right, infix);
				infix.append(")");
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

		public static void calculate(Node node) {

			String text = infix(node);
			System.out.print(text + "=");
			String text1,text2,text3;

			int ans = 0 ;

			while(ishasoperater(text)) {

				for(int i=0;i<text.length();i++) {

					if(is_operater(text.charAt(i)) && text.charAt(i-2) == '(' && text.charAt(i+2) == ')' && !is_operater(text.charAt(i-1)) && !is_operater(text.charAt(i+1)) && text.length()>=5 && i>=2 && i<= text.length()-3) {

						if(text.charAt(i) == '+') ans = Integer.parseInt((String.valueOf(text.charAt(i-1))))+Integer.parseInt((String.valueOf(text.charAt(i+1))));
						if(text.charAt(i) == '-') ans = Integer.parseInt((String.valueOf(text.charAt(i-1))))-Integer.parseInt((String.valueOf(text.charAt(i+1))));
						if(text.charAt(i) == '*') ans = Integer.parseInt((String.valueOf(text.charAt(i-1))))*Integer.parseInt((String.valueOf(text.charAt(i+1))));
						if(text.charAt(i) == '/') ans = Integer.parseInt((String.valueOf(text.charAt(i-1))))/Integer.parseInt((String.valueOf(text.charAt(i+1))));



						text1 = text.substring(0, i-2);
						//System.out.print(text1);
						text2 = Integer.toString(ans);
						//System.out.print(text2);
						text3 = text.substring(i+3,text.length());
						//System.out.println(text3);

						text = text1 + text2 + text3;

					}

				}

				if(text.length() == 3) {
					if(text.charAt(1) == '+') ans = Integer.parseInt((String.valueOf(text.charAt(0))))+Integer.parseInt((String.valueOf(text.charAt(2))));
					if(text.charAt(1) == '-') ans = Integer.parseInt((String.valueOf(text.charAt(0))))-Integer.parseInt((String.valueOf(text.charAt(2))));
					if(text.charAt(1) == '*') ans = Integer.parseInt((String.valueOf(text.charAt(0))))*Integer.parseInt((String.valueOf(text.charAt(2))));
					if(text.charAt(1) == '/') ans = Integer.parseInt((String.valueOf(text.charAt(0))))/Integer.parseInt((String.valueOf(text.charAt(2))));

					text = Integer.toString(ans);
					System.out.println(text);


				}


			}



		}




		public static void main(String[] args) {
			// Begin of arguments input sample

			if (args.length > 0) {
				String input = args[0];
				if (input.equalsIgnoreCase("251-*32*+")) {
					System.out.println("(2*(5-1))+(3*2)=14");
				}
			}
			// End of arguments input sample

			// TODO: Implement your project here

			String number  = "251-*32*+";
			char [] letter = number.toCharArray();

			Node root = Stack(letter);

			calculate(root);

		}
	}