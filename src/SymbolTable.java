import java.util.HashMap;
import java.util.Map;

/**
 * Symbol table with nested scope support.
 * Each scope has a reference to its parent scope for variable lookup.
 */
public class SymbolTable {

    public enum Type { INT, FLOAT, BOOL, STRING, UNKNOWN }

    public static class Symbol {
        public final String name;
        public Type type;

        public Symbol(String name, Type type) {
            this.name = name;
            this.type = type;
        }

        @Override
        public String toString() {
            return name + ":" + type;
        }
    }

    private final Map<String, Symbol> symbols = new HashMap<>();
    private final SymbolTable parent;
    private static int scopeCounter = 0;
    public final int scopeId;

    public SymbolTable(SymbolTable parent) {
        this.parent  = parent;
        this.scopeId = scopeCounter++;
    }

    /** Declare a new symbol in the CURRENT scope. */
    public void declare(String name, Type type) {
        symbols.put(name, new Symbol(name, type));
    }

    /** Update the type of an existing symbol (walks up scopes). */
    public void update(String name, Type type) {
        SymbolTable scope = findScope(name);
        if (scope != null) {
            scope.symbols.get(name).type = type;
        } else {
            // First assignment defines it
            declare(name, type);
        }
    }

    /** Look up a symbol, walking parent scopes. Returns null if not found. */
    public Symbol lookup(String name) {
        Symbol s = symbols.get(name);
        if (s != null) return s;
        if (parent != null) return parent.lookup(name);
        return null;
    }

    /** True if name is declared in this scope exactly (not parents). */
    public boolean isDeclaredLocally(String name) {
        return symbols.containsKey(name);
    }

    /** Find which scope owns this name. */
    private SymbolTable findScope(String name) {
        if (symbols.containsKey(name)) return this;
        if (parent != null) return parent.findScope(name);
        return null;
    }

    public SymbolTable getParent() { return parent; }

    public void printTable() {
        System.out.println("  Scope #" + scopeId + ": " + symbols);
    }
}
