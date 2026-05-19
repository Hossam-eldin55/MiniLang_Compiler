import java.io.*;
import java.util.*;

/**
 * AST Visualizer — generates a Graphviz .dot file from the AST.
 * Run:  dot -Tpng ast.dot -o ast.png
 */
public class ASTVisualizer {

    private final StringBuilder dot = new StringBuilder();
    private int nodeCount = 0;

    public void visualize(ASTNode root, String outputPath) throws IOException {
        dot.append("digraph AST {\n");
        dot.append("    node [shape=box, fontname=\"Courier\", style=filled, fillcolor=lightblue];\n");
        dot.append("    edge [fontname=\"Courier\"];\n\n");

        walkNode(root, -1);

        dot.append("}\n");

        try (PrintWriter pw = new PrintWriter(new FileWriter(outputPath))) {
            pw.print(dot);
        }
        System.out.println("[Visualizer] AST written to: " + outputPath);
        System.out.println("[Visualizer] Run: dot -Tpng " + outputPath + " -o ast.png");
    }

    private int walkNode(ASTNode node, int parentId) {
        int myId = nodeCount++;

        if (node instanceof ASTNode.Program) {
            emitNode(myId, "Program", "lightyellow");
            for (ASTNode s : ((ASTNode.Program) node).statements)
                walkNode(s, myId);

        } else if (node instanceof ASTNode.Block) {
            emitNode(myId, "Block", "lightyellow");
            for (ASTNode s : ((ASTNode.Block) node).statements)
                walkNode(s, myId);

        } else if (node instanceof ASTNode.IfStatement) {
            ASTNode.IfStatement n = (ASTNode.IfStatement) node;
            emitNode(myId, "If", "lightcoral");
            int condId = walkNode(n.condition, myId);
            emitEdge(myId, condId, "cond");
            int thenId = walkNode(n.thenBlock, myId);
            emitEdge(myId, thenId, "then");
            if (n.elseBlock != null) {
                int elseId = walkNode(n.elseBlock, myId);
                emitEdge(myId, elseId, "else");
            }

        } else if (node instanceof ASTNode.WhileStatement) {
            ASTNode.WhileStatement n = (ASTNode.WhileStatement) node;
            emitNode(myId, "While", "lightcoral");
            int condId = walkNode(n.condition, myId);
            emitEdge(myId, condId, "cond");
            int bodyId = walkNode(n.body, myId);
            emitEdge(myId, bodyId, "body");

        } else if (node instanceof ASTNode.Assignment) {
            ASTNode.Assignment n = (ASTNode.Assignment) node;
            emitNode(myId, "Assign\\n" + n.variable, "lightgreen");
            int valId = walkNode(n.value, myId);
            emitEdge(myId, valId, "val");

        } else if (node instanceof ASTNode.ReturnStatement) {
            ASTNode.ReturnStatement n = (ASTNode.ReturnStatement) node;
            emitNode(myId, "Return", "lightcoral");
            if (n.value != null) {
                int valId = walkNode(n.value, myId);
                emitEdge(myId, valId, "val");
            }

        } else if (node instanceof ASTNode.BinaryOp) {
            ASTNode.BinaryOp n = (ASTNode.BinaryOp) node;
            emitNode(myId, "BinOp\\n" + escape(n.op), "lightblue");
            int leftId  = walkNode(n.left, myId);
            int rightId = walkNode(n.right, myId);
            emitEdge(myId, leftId, "L");
            emitEdge(myId, rightId, "R");

        } else if (node instanceof ASTNode.UnaryOp) {
            ASTNode.UnaryOp n = (ASTNode.UnaryOp) node;
            emitNode(myId, "UnaryOp\\n" + n.op, "lightblue");
            int opId = walkNode(n.operand, myId);
            emitEdge(myId, opId, "op");

        } else if (node instanceof ASTNode.IntLiteral) {
            emitNode(myId, "INT\\n" + ((ASTNode.IntLiteral) node).value, "white");
        } else if (node instanceof ASTNode.FloatLiteral) {
            emitNode(myId, "FLOAT\\n" + ((ASTNode.FloatLiteral) node).value, "white");
        } else if (node instanceof ASTNode.StringLiteral) {
            emitNode(myId, "STR\\n\\\"" + escape(((ASTNode.StringLiteral) node).value) + "\\\"", "white");
        } else if (node instanceof ASTNode.BoolLiteral) {
            emitNode(myId, "BOOL\\n" + ((ASTNode.BoolLiteral) node).value, "white");
        } else if (node instanceof ASTNode.Identifier) {
            emitNode(myId, "ID\\n" + ((ASTNode.Identifier) node).name, "lightyellow");
        }

        if (parentId >= 0) {
            emitEdge(parentId, myId, null);
        }
        return myId;
    }

    private void emitNode(int id, String label, String color) {
        dot.append("    n").append(id)
           .append(" [label=\"").append(label)
           .append("\", fillcolor=").append(color).append("];\n");
    }

    private void emitEdge(int from, int to, String label) {
        dot.append("    n").append(from).append(" -> n").append(to);
        if (label != null) dot.append(" [label=\"").append(label).append("\"]");
        dot.append(";\n");
    }

    private String escape(String s) {
        return s.replace("&", "&amp;").replace("<", "\\<").replace(">", "\\>")
                .replace("\"", "\\\"");
    }
}
