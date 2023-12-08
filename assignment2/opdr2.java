import java.util.Scanner;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Node
{
    private String content;
    private Node left;
    private Node right;

    public Node (String newContent){
        this.content = newContent;
        this.left = null;
        this.right = null;
    }

    public String getContent(){
        return this.content; 
    }

    public Node getLeft(){
        return this.left;
    }

    public Node getRight(){
        return this.right;
    }

    public void setContent(String newContent){
        this.content = newContent;
    }

    public void setLeft(Node newLeft){
        this.left = newLeft;
    }

    public void setRight(Node newRight){
        this.right = newRight;
    }
}

class SyntaxTree
{
    public Node root;
    public String expr;
    public int index;

    public SyntaxTree (String newExpr){
        this.expr = newExpr;
        this.index = 0;
        this.root = parseExpression(newExpr);
    }

    public Node parseVariable (){
        StringBuilder var = new StringBuilder();
       
        while (index < expr.length() && Character.isLetterOrDigit(expr.charAt(index))){
            var.append(expr.charAt(index));
            index++;
        }
        
        expr = expr.substring(index);
        index = 0;
        Node varNode = new Node(var.toString());
        
        return varNode;
    }

    public Node parseLambda (){
        Node lambdaNode = new Node("\\");

        Node varNode =  parseVariable();
        if (varNode != null){
            lambdaNode.setLeft(varNode);
        }
        
        while (expr.charAt(index) == ' '){
            index++;
        }

        expr = expr.substring(index);
        index = 0;

        Node exprNode = parseExpression(expr);
        if (exprNode != null){
            lambdaNode.setRight(exprNode);
        }

        return lambdaNode;
    }
    
    public Node parseExpression (String newExpr){
        Node newRoot = new Node("");
        Character huidigeKar = newExpr.charAt(index);

        if (huidigeKar == '\\'){
            index++;
            expr = newExpr.substring(index); // verwerk \

            index = 0;
            newRoot = parseLambda();
        } else if (Character.isLetter(huidigeKar)) {
            newRoot = parseVariable();
        } else if (huidigeKar == '(') {
            index++;
            expr = newExpr.substring(index);

            index = 0;
            newRoot = parseExpression(expr);

            index++;
            expr = expr.substring(index);

            index = 0;

            System.out.println(expr);
        }
        
        if (index >= expr.length()){
            return newRoot;
        }

        huidigeKar = expr.charAt(index);
        
        if (huidigeKar == ' '){        
            Node connectorNode = new Node("+");
            Node leftNode = newRoot;
            
            expr = expr.substring(1);  // Spatie verwerken    
            
            Node rightNode = parseExpression(expr);
            
            if (leftNode != null){
                System.out.println();
                connectorNode.setLeft(leftNode);
            }

            if (rightNode != null){
                connectorNode.setRight(rightNode);
            }

            newRoot = connectorNode;
        }

        return newRoot;
    }

    public String getRootContent(){
        return root.getContent();
    }

    public void printTree(Node top){
        System.out.println(top.getContent());
        
        if(top.getLeft() != null){
            printTree(top.getLeft());
        }
        
        if (top.getRight() != null){
            printTree(top.getRight());
        }
    }

    public void betaReduction(Node root){
    if (root.getContent() == "\\") { // als lambda 
        Node varNode = root.getLeft();
        if (varNode.getContent().equals("x")) { // als linker kind matched met bound variable 
            root.setLeft(root.getRight());
            if (root.getRight() != null) {
                root.setContent(root.getRight().getContent());
            } else {
                root.setContent("");
            }
        }
    }
}
}

class Main 
{
    public static void main(String args[])
    {
        String input = "(\\x x) (\\y y)";  

        System.out.println(input);

        SyntaxTree boom = new SyntaxTree(input);

        if (boom.root != null){
            boom.printTree(boom.root);
        }

        boom.betaReduction(boom.root);
        if (boom.root != null){
            System.out.println("AST na BETA");
            boom.printTree(boom.root);
        }

    }
}
