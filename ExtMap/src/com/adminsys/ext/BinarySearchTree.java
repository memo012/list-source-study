package com.adminsys.ext;

/**
 * @Author: qiang
 * @Description: 二叉搜索树
 * @Create: 2019-12-16 06-38
 **/

public class BinarySearchTree {

    /**
     *  元素
     */
    private int data;

    /**
     *  左子树
     */
    private BinarySearchTree left;

    /**
     *  右子树
     */
    private BinarySearchTree right;

    public BinarySearchTree(int data) {
        this.data = data;
    }

    /**
     *  添加数据
     * @param root
     * @param data
     */
    public void insertTree(BinarySearchTree root, int data){
        if(data > root.data){
            if (root.right == null) {
                root.right = new BinarySearchTree(data);
            }else{
                insertTree(root.right, data);
            }
        }else{
            if (root.left == null) {
                root.left = new BinarySearchTree(data);
            }else{
                insertTree(root.left, data);
            }
        }
    }

    public void searchTree(BinarySearchTree root){
        if(root != null){
            searchTree(root.left);
            System.out.println(root.data);
            searchTree(root.right);
        }
    }

    public static void main(String[] args) {
        int[] data = {3, 6, 5, 2, 1, 4};
        BinarySearchTree binarySearchTree = new BinarySearchTree(data[0]);
        for (int i = 1; i < data.length; i++) {
            binarySearchTree.insertTree(binarySearchTree, data[i]);
        }
        binarySearchTree.searchTree(binarySearchTree);

    }

}
