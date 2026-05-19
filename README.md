# TinyLang Compiler — Project Documentation

## Overview

This project implements a **full compiler pipeline** for a small imperative language called **TinyLang** (also referred to as MiniLang internally). The compiler is written in **Java** and uses **ANTLR 4** for lexing and parsing. It processes a `.tiny` source file through seven sequential stages and produces:

- A **symbol table** (from semantic analysis)
- **Three-Address Code** (TAC) — saved as `.tac`
- **Assembly-like machine code** — saved as `.asm`
- An **AST visualization** file — saved as `_ast.dot` (renderable with Graphviz)

### Compiler Pipeline

```
Source (.tiny)
    │
    ▼
[Stage 1] Lexical Analysis      → TinyLexer  (ANTLR-generated)
    │
    ▼
[Stage 2] Syntax Analysis       → TinyParser (ANTLR-generated)
    │
    ▼
[Stage 3] AST Construction      → ASTVisitor + ASTNode hierarchy
    │
    ▼
[Stage 4] Semantic Analysis     → SemanticAnalyzer + SymbolTable
    │
    ▼
[Stage 5] IR Generation (TAC)   → IRGenerator → .tac file
    │
    ▼
[Stage 6] Machine Code Gen      → CodeGenerator → .asm file
    │
    ▼
[Stage 7] Visualization         → ASTVisualizer → _ast.dot file
```

### Source Files

| File | Role |
|---|---|
| `TinyLexer.g4` | ANTLR lexer grammar |
| `TinyParser.g4` | ANTLR parser grammar |
| `TinyLexer.java` | ANTLR-generated lexer |
| `TinyParser.java` | ANTLR-generated parser |
| `ASTNode.java` | AST node class hierarchy |
| `ASTVisitor.java` | Parse-tree → AST converter |
| `SymbolTable.java` | Scoped symbol table |
| `SemanticAnalyzer.java` | Type checking & scope analysis |
| `IRGenerator.java` | AST → Three-Address Code |
| `CodeGenerator.java` | TAC → Register-based assembly |
| `ASTVisualizer.java` | AST → Graphviz `.dot` file |
| `Main.java` | Compiler entry point |

---

## Stage 1 — Lexical Analysis

**File:** `TinyLexer.g4` → generates `TinyLexer.java`

The lexer converts raw source text into a flat stream of **tokens**. It is defined using an ANTLR 4 lexer grammar and recognizes the following token categories:

### Keywords
| Token | Lexeme |
|---|---|
| `IF` | `if` |
| `ELSE` | `else` |
| `WHILE` | `while` |
| `RETURN` | `return` |
| `TRUE` | `true` |
| `FALSE` | `false` |

### Operators
| Category | Tokens |
|---|---|
| Arithmetic | `+` `-` `*` `/` `%` |
| Comparison | `==` `!=` `<` `>` `<=` `>=` |
| Assignment | `=` `+=` `-=` `*=` `/=` |
| Logical | `&&` `\|\|` `!` |

### Punctuation
`(` `)` `{` `}` `;` `,`

### Literals
| Token | Pattern |
|---|---|
| `INT` | `[0-9]+` |
| `FLOAT` | `[0-9]+ '.' [0-9]+` |
| `STRING` | `"..."` (with escape support) |
| `BOOLEAN` | `true` or `false` |

### Identifiers
`ID` matches `[a-zA-Z_][a-zA-Z_0-9]*`

### Skipped Tokens
- **Line comments:** `// ...` → skipped
- **Block comments:** `/* ... */` → skipped
- **Whitespace:** spaces, tabs, newlines → skipped

### Error Token
Any unrecognized character is matched by the `Error` rule. The compiler detects these and aborts with a descriptive message.

### Example — Tokenizing `test.tiny`
```
x = 5;
if (x == 5) { y = x + 10; } else { y = 0; }
```
Produces tokens: `ID(x)`, `ASSIGN`, `INT(5)`, `SEMICOLON`, `IF`, `LPAREN`, `ID(x)`, `EQ`, `INT(5)`, `RPAREN`, `LBRACE`, …

---

## Stage 2 — Syntax Analysis

**File:** `TinyParser.g4` → generates `TinyParser.java`

The parser reads the token stream and verifies it conforms to the **grammar rules** of TinyLang. It builds a **parse tree** (also called a Concrete Syntax Tree, CST).

### Grammar Rules

```
program         → statement* EOF
statement       → ifStatement | whileStatement | assignment
                | returnStatement | block
ifStatement     → IF '(' expression ')' block ( ELSE block )?
whileStatement  → WHILE '(' expression ')' block
assignment      → ID '=' expression ';'
returnStatement → RETURN expression? ';'
block           → '{' statement* '}'
expression      → expression ('+' | '-' | '*' | '/' | '%') expression
                | expression ('==' | '!=' | '<' | '>' | '<=' | '>=') expression
                | expression ('&&' | '||') expression
                | '!' expression
                | '(' expression ')'
                | INT | FLOAT | STRING | BOOLEAN | ID
```

### Error Handling
A custom `BaseErrorListener` is attached to the parser. Each syntax error is printed with its **line number and column**. If any errors are detected, compilation is aborted before proceeding to further stages.

---

## Stage 3 — Abstract Syntax Tree (AST) Construction

**Files:** `ASTNode.java`, `ASTVisitor.java`

The raw ANTLR parse tree contains implementation details (parentheses, semicolons, etc.). The **AST** strips these away into a clean, semantic representation.

### ASTNode Hierarchy (`ASTNode.java`)

All nodes extend the abstract `ASTNode` class:

| Node Class | Fields | Description |
|---|---|---|
| `Program` | `List<ASTNode> statements` | Root of the AST |
| `Block` | `List<ASTNode> statements` | `{ ... }` group |
| `IfStatement` | `condition`, `thenBlock`, `elseBlock` | Conditional (else optional) |
| `WhileStatement` | `condition`, `body` | Loop |
| `Assignment` | `String variable`, `ASTNode value` | Variable assignment |
| `ReturnStatement` | `ASTNode value` (nullable) | Return statement |
| `BinaryOp` | `String op`, `left`, `right` | Arithmetic/logical expression |
| `UnaryOp` | `String op`, `operand` | Negation / NOT |
| `IntLiteral` | `int value` | Integer constant |
| `FloatLiteral` | `double value` | Float constant |
| `StringLiteral` | `String value` | String constant |
| `BoolLiteral` | `boolean value` | Boolean constant |
| `Identifier` | `String name` | Variable reference |

### ASTVisitor (`ASTVisitor.java`)

Extends `TinyParserBaseVisitor<ASTNode>`. Each `visitXxx()` method maps one grammar rule context to the corresponding `ASTNode` subclass:

- `visitProgram` → collects all statements into `ASTNode.Program`
- `visitIfStatement` → builds `ASTNode.IfStatement` with optional else
- `visitWhileStatement` → builds `ASTNode.WhileStatement`
- `visitAssignment` → reads the `ID` token and visits the expression
- `visitReturnStatement` → optional value expression
- `visitBlock` → collects inner statements
- `visitExpression` → dispatches to `BinaryOp`, `UnaryOp`, or a literal/identifier leaf

### Example AST for `test.tiny`

```
Program
├── Assign(x, Int(5))
└── If(BinOp(==, ID(x), Int(5)),
      Block [Assign(y, BinOp(+, ID(x), Int(10)))],
      Block [Assign(y, Int(0))]
   )
```

---

## Stage 4 — Semantic Analysis

**Files:** `SemanticAnalyzer.java`, `SymbolTable.java`

The semantic analyzer traverses the AST and enforces **language semantics** beyond syntax.

### SymbolTable (`SymbolTable.java`)

Implements a **scoped symbol table** with parent-chain lookup:

- **Types supported:** `INT`, `FLOAT`, `BOOL`, `STRING`, `UNKNOWN`
- Each `SymbolTable` has a unique `scopeId` and a reference to its `parent` scope
- `declare(name, type)` — adds a new symbol to the current scope
- `update(name, type)` — updates an existing symbol (walks up the scope chain); if the name is not found anywhere, it is **declared** in the current scope (implicit declaration on first assignment)
- `lookup(name)` — searches current scope then parent scopes; returns `null` if not found

### SemanticAnalyzer (`SemanticAnalyzer.java`)

Walks the AST with `analyzeNode()` and accumulates errors in a list. Key checks performed:

#### Scope Management
- A new child `SymbolTable` is created on entering each `Block` (`enterScope()`)
- It is discarded on exit (`exitScope()`), restoring the parent scope
- Variables declared inside a block are **not visible** outside it

#### Type Inference
Variables have their type **inferred** from their assigned value:
- `x = 5;` → `x` is typed `INT`
- `x = 3.14;` → `x` is typed `FLOAT`
- `x = "hi";` → `x` is typed `STRING`
- `x = true;` → `x` is typed `BOOL`

#### Type Checking Rules

| Check | Rule |
|---|---|
| **Arithmetic ops** (`+ - * / %`) | Both operands numeric; `+` allowed on strings (concatenation) |
| **Comparison ops** (`== != < > <= >=`) | Operands must be same type (int/float mix allowed) |
| **Logical ops** (`&& \|\|`) | Both operands must be `BOOL` |
| **Unary `!`** | Operand must be `BOOL` |
| **If/While condition** | Must be `BOOL` or `INT` |
| **Assignment type mismatch** | Error if existing type ≠ new type (int→float widening is allowed silently) |
| **Undeclared variable** | Error if identifier is used before any assignment |

#### Output
- `printErrors()` — lists all errors found or confirms "No errors"
- `printSymbolTable()` — prints the global scope's symbol map

### Example — Semantic output for `test.tiny`
```
[Semantic] No errors found.
[Semantic] Symbol Table (global scope):
  Scope #0: {x=x:INT, y=y:INT}
```

---

## Stage 5 — Intermediate Code Generation

**File:** `IRGenerator.java`  
**Output:** `.tac` file

Translates the AST into **Three-Address Code (TAC)**, a linear sequence of simple instructions each involving at most 3 operands.

### TAC Instruction Formats

| Format | Example | Meaning |
|---|---|---|
| `result = arg1 op arg2` | `t0 = x == 5` | Binary operation |
| `result = op arg1` | `t1 = !flag` | Unary operation |
| `result = arg1` | `x = 5` | Copy/assignment |
| `IF cond GOTO label` | `IF t0 GOTO L1` | Conditional jump (true) |
| `IFNOT cond GOTO label` | `IFNOT t0 GOTO L0` | Conditional jump (false) |
| `GOTO label` | `GOTO L1` | Unconditional jump |
| `LABEL label:` | `L0:` | Label definition |
| `RETURN value` | `RETURN t2` | Return statement |

### Temporaries and Labels
- **Temporaries** are named `t0`, `t1`, `t2`, … — generated by `newTemp()`
- **Labels** are named `L0`, `L1`, `L2`, … — generated by `newLabel()`

### Control Flow Translation

**If-Else:**
```
  <evaluate condition → cond>
  IFNOT cond GOTO L_else
  <then block>
  GOTO L_end
L_else:
  <else block>
L_end:
```

**While:**
```
L_start:
  <evaluate condition → cond>
  IFNOT cond GOTO L_end
  <body>
  GOTO L_start
L_end:
```

### Example TAC — `test.tiny`
```
    x = 5
    t0 = x == 5
    IFNOT t0 GOTO L0
    t1 = x + 10
    y = t1
    GOTO L1
L0:
    y = 0
L1:
```

---

## Stage 6 — Machine Code Generation

**File:** `CodeGenerator.java`  
**Output:** `.asm` file

Translates TAC instructions into a **register-based assembly** language resembling a minimal RISC-like ISA.

### ISA / Register Model
- **8 general-purpose registers:** `R0` through `R7`
- **Return value convention:** result placed in `R0`
- **Memory model:** named variables stored at symbolic addresses `[varName]`

### Register Allocator
Uses a simple **linear allocator with spilling**:
1. A `regMap` tracks which variable/temporary is currently in which register
2. `freeRegs` is a queue of available registers (`R0`–`R7`)
3. When all registers are occupied, the **oldest** allocated register is **spilled** to memory (`STR reg, [name]`) and reused
4. Variables are loaded from memory with `LDR reg, [name]` when needed

### Instruction Translation Table

| TAC operation | Machine instructions emitted |
|---|---|
| `COPY` (immediate) | `MOV Rdest, #value` + `STR Rdest, [var]` |
| `COPY` (register) | `MOV Rdest, Rsrc` + `STR Rdest, [var]` |
| `+` | `ADD Rdest, R1, R2` |
| `-` | `SUB Rdest, R1, R2` |
| `*` | `MUL Rdest, R1, R2` |
| `/` | `DIV Rdest, R1, R2` |
| `%` | `MOD Rdest, R1, R2` |
| `==` | `CMP R1, R2` + `SETEQ Rdest` |
| `!=` | `CMP R1, R2` + `SETNE Rdest` |
| `<` | `CMP R1, R2` + `SETLT Rdest` |
| `>` | `CMP R1, R2` + `SETGT Rdest` |
| `<=` | `CMP R1, R2` + `SETLE Rdest` |
| `>=` | `CMP R1, R2` + `SETGE Rdest` |
| `&&` | `AND Rdest, R1, R2` |
| `\|\|` | `OR Rdest, R1, R2` |
| `NOT` | `NOT Rdest, R1` |
| `NEG` | `NEG Rdest, R1` |
| `IF cond GOTO L` | `CMP Rcond, #0` + `JNE L` |
| `IFNOT cond GOTO L` | `CMP Rcond, #0` + `JEQ L` |
| `GOTO L` | `JMP L` |
| `LABEL L:` | `L:` |
| `RETURN val` | `MOV R0, Rval` + `RET` |

### Assembly Structure
Every generated file follows this structure:
```asm
; ===== Generated Machine Code =====
.text
.start MAIN
MAIN:
    ; TAC: <original TAC instruction>
    <machine instructions>
    ...
HALT
```

> Each TAC instruction is preserved as a **comment** above its machine code expansion, making the output traceable.

### Example Assembly — `test.tiny`
```asm
; ===== Generated Machine Code =====
.text
.start MAIN
MAIN:
; TAC:     x = 5
    MOV R0, #5
    STR R0, [x]
; TAC:     t0 = x == 5
    MOV R1, #5
    CMP R0, R1
    SETEQ R2
; TAC:     IFNOT t0 GOTO L0
    CMP R2, #0
    JEQ L0
; TAC:     t1 = x + 10
    MOV R3, #10
    ADD R4, R0, R3
; TAC:     y = t1
    MOV R5, R4
    STR R5, [y]
; TAC:     GOTO L1
    JMP L1
; TAC: L0:
L0:
; TAC:     y = 0
    MOV R5, #0
    STR R5, [y]
; TAC: L1:
L1:
HALT
```

---

## Stage 7 — AST Visualization

**File:** `ASTVisualizer.java`  
**Output:** `_ast.dot` file

Generates a **Graphviz DOT file** representing the AST graphically.

### Color Coding

| Node Type | Color |
|---|---|
| `Program`, `Block`, `Identifier` | Light yellow |
| `Assignment` | Light green |
| `IfStatement`, `WhileStatement`, `ReturnStatement` | Light coral (red) |
| `BinaryOp`, `UnaryOp` | Light blue |
| Literals (`INT`, `FLOAT`, `STRING`, `BOOL`) | White |

### Edges and Labels
Edges are labeled with their semantic role:
- `cond` — condition of an If or While
- `then` / `else` — branches of an If
- `body` — body of a While
- `val` — value in an Assignment or Return
- `L` / `R` — left and right operands of a BinaryOp
- `op` — operand of a UnaryOp

### Rendering the DOT File
```bash
dot -Tpng test_ast.dot -o test_ast.png
```

The `.dot` file can also be rendered with [Graphviz Online](https://dreampuf.github.io/GraphvizOnline/) or any compatible viewer.

### Example DOT — `test.tiny`
```dot
digraph AST {
    node [shape=box, fontname="Courier", style=filled, fillcolor=lightblue];
    n0 [label="Program", fillcolor=lightyellow];
    n1 [label="Assign\nx", fillcolor=lightgreen];
    n2 [label="INT\n5", fillcolor=white];
    n1 -> n2 [label="val"];
    n0 -> n1;
    n3 [label="If", fillcolor=lightcoral];
    n4 [label="BinOp\n==", fillcolor=lightblue];
    n5 [label="ID\nx", fillcolor=lightyellow];
    n6 [label="INT\n5", fillcolor=white];
    n4 -> n5 [label="L"];
    n4 -> n6 [label="R"];
    n3 -> n4 [label="cond"];
    ...
}
```

---

## Running the Compiler

### Prerequisites
- Java 11+
- ANTLR 4.13.2 JAR on the classpath (`.idea/libraries/antlr_4_13_2_complete.xml`)
- Graphviz (optional, for rendering `.dot` files)

### Usage
```bash
java Main <source.tiny>          # compile with visualization
java Main <source.tiny> --no-viz # compile without generating .dot file
```

### Default Behavior
If no file argument is provided, `test2.tiny` is used as the default source.

### Output Files Generated

| Output File | Content |
|---|---|
| `<name>.tac` | Three-Address Code (IR) |
| `<name>.asm` | Register-based assembly |
| `<name>_ast.dot` | AST Graphviz visualization |

---

## End-to-End Example

**Input (`test.tiny`):**
```
x = 5;
if (x == 5) {
    y = x + 10;
} else {
    y = 0;
}
```

**Stage 1 — Lexer:** Tokenizes into 20+ tokens (keywords, operators, identifiers, literals).

**Stage 2 — Parser:** Validates grammar, produces CST.

**Stage 3 — AST:**
```
Program
├── Assign(x, Int(5))
└── If(BinOp(==, ID(x), Int(5)),
      Block[Assign(y, BinOp(+, ID(x), Int(10)))],
      Block[Assign(y, Int(0))]
   )
```

**Stage 4 — Semantics:** No errors. Symbol table: `{x:INT, y:INT}`

**Stage 5 — TAC (`test.tac`):**
```
    x = 5
    t0 = x == 5
    IFNOT t0 GOTO L0
    t1 = x + 10
    y = t1
    GOTO L1
L0:
    y = 0
L1:
```

**Stage 6 — Assembly (`test.asm`):**
```asm
MAIN:
    MOV R0, #5
    STR R0, [x]
    MOV R1, #5
    CMP R0, R1
    SETEQ R2
    CMP R2, #0
    JEQ L0
    MOV R3, #10
    ADD R4, R0, R3
    MOV R5, R4
    STR R5, [y]
    JMP L1
L0:
    MOV R5, #0
    STR R5, [y]
L1:
HALT
```

**Stage 7 — Visualization:** `test_ast.dot` rendered as a color-coded graph.
