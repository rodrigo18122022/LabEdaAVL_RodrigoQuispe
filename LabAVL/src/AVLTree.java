import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.ArrayList;
import java.util.List;

public class AVLTree<E extends Comparable<E>> extends BST<E> {
    class NodeAVL extends Node<E> {
        protected int fb;

        public NodeAVL(E data) {
            super(data);
            this.fb = 0;
        }

        public String toString() {
            return super.toString() + "(" + this.fb + ")";
        }
    }

    private boolean fl;

    public AVLTree() {
        super();
    }

    public void insert(E x) throws ItemDuplicated {
        try {
            this.fl = false;
            this.setRoot(insertRecAVL(x, (NodeAVL) this.getRoot()));
        } catch (ItemNoFound e) {
            this.fl = true;
            this.setRoot(new NodeAVL(x));
        }
    }

    private NodeAVL insertRecAVL(E x, NodeAVL actual) throws ItemDuplicated {
        NodeAVL res = actual;
        if (actual == null) {
            this.fl = true;
            res = new NodeAVL(x);
        } else {
            int resC = actual.getData().compareTo(x);
            if (resC == 0)
                throw new ItemDuplicated("El dato " + x + " ya fue insertado antes...");
            if (resC < 0) {
                res.setRight(insertRecAVL(x, (NodeAVL) actual.getRight()));
                if (fl) {
                    switch (res.fb) {
                        case -1:
                            res.fb = 0;
                            this.fl = false;
                            break;
                        case 0:
                            res.fb = 1;
                            this.fl = true;
                            break;
                        case 1:
                            res = balanceToLeft(res);
                            this.fl = false;
                            break;
                    }
                }
            } else {
                res.setLeft(insertRecAVL(x, (NodeAVL) actual.getLeft()));
                if (fl) {
                    switch (res.fb) {
                        case 1:
                            res.fb = 0;
                            this.fl = false;
                            break;
                        case 0:
                            res.fb = -1;
                            this.fl = true;
                            break;
                        case -1:
                            res = balanceToRight(res);
                            this.fl = false;
                            break;
                    }
                }
            }
        }
        return res;
    }

    private NodeAVL balanceToLeft(NodeAVL node) {
        NodeAVL son = (NodeAVL) node.getRight();
        switch (son.fb) {
            case 1:
                node.fb = 0;
                son.fb = 0;
                node = rotateSL(node);
                break;
            case -1:
                NodeAVL grandSon = (NodeAVL) son.getLeft();
                switch (grandSon.fb) {
                    case -1:
                        node.fb = 0;
                        son.fb = 1;
                        break;
                    case 0:
                        node.fb = 0;
                        son.fb = 0;
                        break;
                    case 1:
                        node.fb = 1;
                        son.fb = 0;
                        break;
                }
                grandSon.fb = 0;
                node.setRight(rotateSR(son));
                node = rotateSL(node);
                break;
        }
        return node;
    }

    private NodeAVL balanceToRight(NodeAVL node) {
        NodeAVL son = (NodeAVL) node.getLeft();
        switch (son.fb) {
            case -1:
                node.fb = 0;
                son.fb = 0;
                node = rotateSR(node);
                break;
            case 1:
                NodeAVL grandSon = (NodeAVL) son.getRight();
                switch (grandSon.fb) {
                    case 1:
                        node.fb = 0;
                        son.fb = -1;
                        break;
                    case 0:
                        node.fb = 0;
                        son.fb = 0;
                        break;
                    case -1:
                        node.fb = -1;
                        son.fb = 0;
                        break;
                }
                grandSon.fb = 0;
                node.setLeft(rotateSL(son));
                node = rotateSR(node);
                break;
        }
        return node;
    }

    private NodeAVL rotateSL(NodeAVL node) {
        NodeAVL son = (NodeAVL) node.getRight();
        node.setRight(son.getLeft());
        son.setLeft(node);
        node = son;
        return node;
    }

    private NodeAVL rotateSR(NodeAVL node) {
        NodeAVL son = (NodeAVL) node.getLeft();
        node.setLeft(son.getRight());
        son.setRight(node);
        node = son;
        return node;
    }

    public void inOrden() {
        try {
            if (this.isEmpty())
                System.out.println("Arbol esta vacio....");
            else
                inOrden((NodeAVL) this.getRoot());
            System.out.println();
        } catch (ItemNoFound e) {
            System.out.println(e.getMessage());
        }
    }

    protected void inOrden(NodeAVL actual) {
        if (actual.getLeft() != null)
            inOrden((NodeAVL) actual.getLeft());
        System.out.print(actual + ", ");
        if (actual.getRight() != null)
            inOrden((NodeAVL) actual.getRight());
    }

    public E getMin() throws ItemNoFound {
        if (this.isEmpty())
            throw new ItemNoFound("El árbol está vacío...");
        return getMinRec((NodeAVL) this.getRoot()).getData();
    }

    private NodeAVL getMinRec(NodeAVL actual) {
        if (actual.getLeft() == null) {
            return actual;
        } else {
            return getMinRec((NodeAVL) actual.getLeft());
        }
    }

    public E getMax() throws ItemNoFound {
        if (this.isEmpty())
            throw new ItemNoFound("El árbol está vacío...");
        return getMaxRec((NodeAVL) this.getRoot()).getData();
    }

    private NodeAVL getMaxRec(NodeAVL actual) {
        if (actual.getRight() == null) {
            return actual;
        } else {
            return getMaxRec((NodeAVL) actual.getRight());
        }
    }

    public NodeAVL search(E x) throws ItemNoFound {
        return searchRec(x, (NodeAVL) this.getRoot());
    }

    private NodeAVL searchRec(E x, NodeAVL actual) throws ItemNoFound {
        if (actual == null) {
            throw new ItemNoFound("El dato " + x + " no se encuentra en el árbol...");
        } else {
            int resC = actual.getData().compareTo(x);
            if (resC == 0) {
                return actual;
            } else if (resC < 0) {
                return searchRec(x, (NodeAVL) actual.getRight());
            } else {
                return searchRec(x, (NodeAVL) actual.getLeft());
            }
        }
    }

    public NodeAVL parent(E x) throws ItemNoFound {
        if (this.isEmpty())
            throw new ItemNoFound("El árbol está vacío...");
        return parentRec(x, (NodeAVL) this.getRoot(), null);
    }

    private NodeAVL parentRec(E x, NodeAVL actual, NodeAVL parent) throws ItemNoFound {
        if (actual == null) {
            throw new ItemNoFound("El dato " + x + " no se encuentra en el árbol...");
        } else {
            int resC = actual.getData().compareTo(x);
            if (resC == 0) {
                return parent;
            } else if (resC < 0) {
                return parentRec(x, (NodeAVL) actual.getRight(), actual);
            } else {
                return parentRec(x, (NodeAVL) actual.getLeft(), actual);
            }
        }
    }

    public List<Node<E>> son(E x) throws ItemNoFound {
        NodeAVL node = search(x);
        List<Node<E>> sons = new ArrayList<>();
        if (node.getLeft() != null) {
            sons.add(node.getLeft());
        }
        if (node.getRight() != null) {
            sons.add(node.getRight());
        }
        return sons;
    }

    public void displayTree() {
        try {
            System.setProperty("org.graphstream.ui", "swing");

            Graph graph = new SingleGraph("AVL Tree");
            graph.setAttribute("ui.stylesheet", "node { text-size: 20px; text-color: red; }");

            NodeAVL root = (NodeAVL) this.getRoot();
            addNodesToGraph(graph, root);
            addEdgesToGraph(graph, root);
            graph.display();
        } catch (ItemNoFound e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void addNodesToGraph(Graph graph, NodeAVL node) {
        if (node == null)
            return;
        graph.addNode(node.getData().toString()).setAttribute("ui.label", node.getData().toString());
        addNodesToGraph(graph, (NodeAVL) node.getLeft());
        addNodesToGraph(graph, (NodeAVL) node.getRight());
    }

    private void addEdgesToGraph(Graph graph, NodeAVL node) {
        if (node == null)
            return;
        if (node.getLeft() != null) {
            graph.addEdge(node.getData().toString() + "-" + node.getLeft().getData().toString(),
                    node.getData().toString(), node.getLeft().getData().toString(), true);
            addEdgesToGraph(graph, (NodeAVL) node.getLeft());
        }
        if (node.getRight() != null) {
            graph.addEdge(node.getData().toString() + "-" + node.getRight().getData().toString(),
                    node.getData().toString(), node.getRight().getData().toString(), true);
            addEdgesToGraph(graph, (NodeAVL) node.getRight());
        }
    }
}
