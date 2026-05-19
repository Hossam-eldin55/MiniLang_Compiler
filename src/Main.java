import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.*;
import java.nio.file.*;
import java.util.List;

/**
 * MiniLang / TinyLang Compiler — Main Entry Point
 *
 * Pipeline:
 *   1. Lexical Analysis   (ANTLR TinyLexer)
 *   2. Syntax Analysis    (ANTLR TinyParser)
 *   3. AST Construction   (ASTVisitor)
 *   4. Semantic Analysis  (SemanticAnalyzer)
 *   5. IR Generation      (IRGenerator  → TAC)
 *   6. Machine Code Gen   (CodeGenerator)
 *   7. Visualization      (ASTVisualizer → .dot file)
 *
 * Usage:
 *   java Main <source.tiny>
 *   java Main <source.tiny> --no-viz
 */
public class Main {

    public static void main(String[] args) throws Exception {

        // ── 0. Argument handling ────────────────────────────────────────────────
        String sourceFile = args.length > 0 ? args[0] : "test2.tiny";

        // ---------------------------------------------------------------

        boolean visualize = true;
        for (String a : args) if (a.equals("--no-viz")) visualize = false;

        String source;
        try {
            source = new String(Files.readAllBytes(Paths.get(sourceFile)));
        } catch (IOException e) {
            System.err.println("Cannot read file: " + sourceFile);
            System.exit(1);
            return;
        }

        System.out.println("========================================");
        System.out.println("  MiniLang Compiler");
        System.out.println("  Source: " + sourceFile);
        System.out.println("========================================\n");

        // ── 1. Lexical Analysis ─────────────────────────────────────────────────
        System.out.println(">>> Stage 1: Lexical Analysis");
        CharStream input     = CharStreams.fromString(source);
        TinyLexer  lexer     = new TinyLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        tokens.fill();

        boolean lexError = false;
        for (Token t : tokens.getTokens()) {
            if (t.getType() == Token.EOF) break;
            if (t.getType() == TinyLexer.Error) {
                System.err.println("  [Lexer] Unexpected character: '" + t.getText()
                        + "' at line " + t.getLine());
                lexError = true;
            }
        }
        if (lexError) { System.err.println("Lexer errors detected. Aborting."); System.exit(1); }
        System.out.println("  Tokens: " + tokens.size() + " (including EOF)");

        // ── 2. Syntax Analysis ──────────────────────────────────────────────────
        System.out.println("\n>>> Stage 2: Syntax Analysis");
        TinyParser parser = new TinyParser(tokens);

        // Attach custom error listener to count syntax errors
        final int[] syntaxErrors = {0};
        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?,?> rec, Object sym, int line, int col,
                                    String msg, RecognitionException e) {
                System.err.println("  [Parser] line " + line + ":" + col + " " + msg);
                syntaxErrors[0]++;
            }
        });

        TinyParser.ProgramContext parseTree = parser.program();
        if (syntaxErrors[0] > 0) {
            System.err.println("  " + syntaxErrors[0] + " syntax error(s). Aborting.");
            System.exit(1);
        }
        System.out.println("  Parse tree OK.");

        // ── 3. AST Construction ─────────────────────────────────────────────────
        System.out.println("\n>>> Stage 3: AST Construction");
        ASTVisitor astVisitor = new ASTVisitor();
        ASTNode ast = astVisitor.visit(parseTree);
        System.out.println("  AST root: " + ast.getClass().getSimpleName());

        // ── 4. Semantic Analysis ────────────────────────────────────────────────
        System.out.println("\n>>> Stage 4: Semantic Analysis");
        SemanticAnalyzer semantic = new SemanticAnalyzer();
        semantic.analyze(ast);
        semantic.printErrors();
        if (semantic.hasErrors()) {
            System.err.println("  Semantic errors detected. Aborting.");
            System.exit(1);
        }
        semantic.printSymbolTable();

        // ── 5. IR Generation ────────────────────────────────────────────────────
        System.out.println("\n>>> Stage 5: IR Generation (TAC)");
        IRGenerator irGen = new IRGenerator();
        List<IRGenerator.Instruction> ir = irGen.generate(ast);
        irGen.printIR();

        // Write TAC to file
        String tacFile = sourceFile.replaceAll("\\.tiny$", "") + ".tac";
        try (PrintWriter pw = new PrintWriter(new FileWriter(tacFile))) {
            for (IRGenerator.Instruction i : ir) pw.println(i);
        }
        System.out.println("  TAC written to: " + tacFile);

        // ── 6. Machine Code Generation ──────────────────────────────────────────
        System.out.println("\n>>> Stage 6: Machine Code Generation");
        CodeGenerator codeGen = new CodeGenerator();
        List<CodeGenerator.MachineInstr> machineCode = codeGen.generate(ir);
        codeGen.printCode();

        // Write machine code to file
        String asmFile = sourceFile.replaceAll("\\.tiny$", "") + ".asm";
        try (PrintWriter pw = new PrintWriter(new FileWriter(asmFile))) {
            for (CodeGenerator.MachineInstr mi : machineCode) pw.println(mi);
        }
        System.out.println("  Machine code written to: " + asmFile);

        // ── 7. Visualization ────────────────────────────────────────────────────
        if (visualize) {
            System.out.println("\n>>> Stage 7: AST Visualization");
            String dotFile = sourceFile.replaceAll("\\.tiny$", "") + "_ast.dot";
            ASTVisualizer viz = new ASTVisualizer();
            viz.visualize(ast, dotFile);
        }

        System.out.println("\n========================================");
        System.out.println("  Compilation successful!");
        System.out.println("========================================");
    }
}
