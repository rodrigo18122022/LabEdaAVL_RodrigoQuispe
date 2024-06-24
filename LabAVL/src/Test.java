import java.util.List;

public class Test {
	public static void main(String[] args) {
		AVLTree<Character> avlTree = new AVLTree<>();

		String word = "EXAMPLE";
		for (char c : word.toCharArray()) {
			try {
				avlTree.insert(c);
			} catch (ItemDuplicated e) {
				System.out.println(e.getMessage());
			}
		}

		System.out.println("Árbol AVL en orden:");
		avlTree.inOrden();

		try {
			char min = avlTree.getMin();
			System.out.println("Mínimo valor en el árbol AVL: " + min);
		} catch (ItemNoFound e) {
			System.out.println(e.getMessage());
		}

		try {
			char max = avlTree.getMax();
			System.out.println("Máximo valor en el árbol AVL: " + max);
		} catch (ItemNoFound e) {
			System.out.println(e.getMessage());
		}

		try {
			char searchValue = 'A';
			Node<Character> foundNode = avlTree.search(searchValue);
			System.out.println("Nodo encontrado con valor " + searchValue + ": " + foundNode);
		} catch (ItemNoFound e) {
			System.out.println(e.getMessage());
		}

		try {
			char parentValue = 'X';
			Node<Character> parentNode = avlTree.parent(parentValue);
			if (parentNode != null) {
				System.out.println("Padre del nodo con valor " + parentValue + ": " + parentNode.getData());
			} else {
				System.out.println("El nodo con valor " + parentValue + " no tiene padre (es la raíz).");
			}
		} catch (ItemNoFound e) {
			System.out.println(e.getMessage());
		}

		try {
			char sonValue = 'E';
			List<Node<Character>> sons = avlTree.son(sonValue);
			System.out.print("Hijos del nodo con valor " + sonValue + ": ");
			for (Node<Character> son : sons) {
				System.out.print(son.getData() + ", ");
			}
			System.out.println();
		} catch (ItemNoFound e) {
			System.out.println(e.getMessage());
		}

		avlTree.displayTree();
	}
}
