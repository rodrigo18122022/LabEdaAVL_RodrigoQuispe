public class BST<E extends Comparable<E>> {
    private Node<E> root;

    public void setRoot(Node<E> root) {
        this.root = root;
    }

    public Node<E> getRoot() throws ItemNoFound {
        if (this.root == null)
            throw new ItemNoFound("Arbol vacio...");
        return this.root;
    }

    public BST() {
        this.root = null;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

//    public E search(E x) throws ItemNoFound {
//        Node<E> resp = searchRec(x, this.root);
//        if (resp == null) {
//            throw new ItemNoFound("El dato " + x + "no se encuentra...");
//        }
//        return resp.getData();
//    }

//    public void insert(E x) throws ItemDuplicated {
//        this.root = insertRec(x, this.root);
//    }

    public Node<E> insertRec(E x, Node<E> actual) throws ItemDuplicated {
        Node<E> res = actual;
        if (actual == null) {
            res = new Node<E>(x);
        } else {
            int resC = actual.getData().compareTo(x);
            if (resC == 0) {
                throw new ItemDuplicated("El dato " + x + "ya fue agregado...");
            }
            if (resC < 0) {
                res.setRight(insertRec(x, actual.getRight()));
            } else {
                res.setLeft(insertRec(x, actual.getLeft()));
            }
        }
        return res;
    }

    protected Node<E> searchRec(E x, Node<E> actual) throws ItemNoFound {
        if (actual == null) {
            return null;
        } else {
            int resC = actual.getData().compareTo(x);
            if (resC < 0) {
                return searchRec(x, actual.getRight());
            } else if (resC > 0) {
                return searchRec(x, actual.getLeft());
            } else {
                return actual;
            }
        }
    }

    public void remove(E x) throws ItemNoFound {
        this.root = removeRec(x, this.root);
    }

    protected Node<E> removeRec(E x, Node<E> actual) throws ItemNoFound {
        Node<E> res = actual;
        if (actual == null) {
            throw new ItemNoFound("El dato " + x + "no se encuentra...");
        } else {
            int resC = actual.getData().compareTo(x);
            if (resC < 0) {
                res.setRight(removeRec(x, actual.getRight()));
            } else if (resC > 0) {
                res.setLeft(removeRec(x, actual.getLeft()));
            } else {
                if (actual.getLeft() != null && actual.getRight() != null) {
                    actual.setData(minRecover(actual.getRight()).getData());
                    actual.setRight(minRecover(actual.getRight()));
                } else {
                    res = (actual.getLeft() != null) ? actual.getLeft() : actual.getRight();
                }
            }
        }
        return res;
    }

    public void inOrden() {
        if (this.isEmpty())
            System.out.println("Arbol esta vacio...");
        else
            inOrden(this.root);
        System.out.println();
    }

    protected void inOrden(Node<E> actual) {
        if (actual.getLeft() != null)
            inOrden(actual.getLeft());
        System.out.println(actual.getData().toString() + ", ");
        if (actual.getRight() != null)
            inOrden(actual.getRight());
    }

    protected Node<E> minRemove(Node<E> actual) {
        if (actual.getLeft() != null) {
            actual.setLeft(minRemove(actual.getLeft()));
        } else {
            actual = actual.getRight();
        }
        return actual;
    }

    protected Node<E> minRecover(Node<E> actual) {
        if (actual.getLeft() != null) {
            return minRecover(actual.getLeft());
        } else {
            return actual;
        }
    }

    public String toString() {
        inOrden(this.root);
        return "";
    }
}