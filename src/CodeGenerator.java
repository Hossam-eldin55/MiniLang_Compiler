import java.util.*;

/**
 * Machine Code Generator.
 * Translates TAC instructions into a simple register-based assembly
 * resembling a minimal RISC-like ISA.
 *
 * Registers: R0..R7 (general purpose), with a simple spill-to-memory scheme.
 * Memory model: named variables live at symbolic addresses [varName].
 */
public class CodeGenerator {

    // ─── Machine Instruction ────────────────────────────────────────────────────

    public static class MachineInstr {
        public final String text;
        public MachineInstr(String text) { this.text = text; }
        @Override public String toString() { return text; }
    }

    // ─── State ──────────────────────────────────────────────────────────────────

    private final List<MachineInstr> code    = new ArrayList<>();
    private final Map<String, String> regMap = new LinkedHashMap<>(); // var/temp -> register
    private final String[] registers = {"R0","R1","R2","R3","R4","R5","R6","R7"};
    private final Set<String> usedRegs      = new LinkedHashSet<>();
    private final Deque<String> freeRegs    = new ArrayDeque<>(Arrays.asList(registers));
    private int spillCount = 0;

    // ─── Public API ─────────────────────────────────────────────────────────────

    public List<MachineInstr> generate(List<IRGenerator.Instruction> ir) {
        emit("; ===== Generated Machine Code =====");
        emit(".text");
        emit(".start MAIN");
        emit("MAIN:");

        for (IRGenerator.Instruction instr : ir) {
            translateInstruction(instr);
        }

        emit("HALT");
        return code;
    }

    public void printCode() {
        System.out.println("[CodeGen] Machine Code:");
        for (MachineInstr mi : code) {
            System.out.println(mi);
        }
    }

    public List<MachineInstr> getCode() { return code; }

    // ─── Translation ────────────────────────────────────────────────────────────

    private void translateInstruction(IRGenerator.Instruction instr) {
        emit("; TAC: " + instr);   // emit original TAC as comment
        switch (instr.op) {

            case "COPY": {
                String src  = resolveOperand(instr.arg1);
                String dest = allocReg(instr.result);
                if (isImmediate(instr.arg1)) {
                    emit("    MOV " + dest + ", #" + instr.arg1);
                } else {
                    emit("    MOV " + dest + ", " + src);
                }
                store(instr.result, dest);
                break;
            }

            case "+": case "-": case "*": case "/": case "%": {
                String r1   = load(instr.arg1);
                String r2   = load(instr.arg2);
                String dest = allocReg(instr.result);
                String mnem = arithmeticMnemonic(instr.op);
                emit("    " + mnem + " " + dest + ", " + r1 + ", " + r2);
                store(instr.result, dest);
                break;
            }

            case "==": case "!=": case "<": case ">": case "<=": case ">=": {
                String r1   = load(instr.arg1);
                String r2   = load(instr.arg2);
                String dest = allocReg(instr.result);
                emit("    CMP " + r1 + ", " + r2);
                emit("    SET" + condMnemonic(instr.op) + " " + dest);
                store(instr.result, dest);
                break;
            }

            case "&&": {
                String r1   = load(instr.arg1);
                String r2   = load(instr.arg2);
                String dest = allocReg(instr.result);
                emit("    AND " + dest + ", " + r1 + ", " + r2);
                store(instr.result, dest);
                break;
            }

            case "||": {
                String r1   = load(instr.arg1);
                String r2   = load(instr.arg2);
                String dest = allocReg(instr.result);
                emit("    OR " + dest + ", " + r1 + ", " + r2);
                store(instr.result, dest);
                break;
            }

            case "NOT": {
                String r1   = load(instr.arg1);
                String dest = allocReg(instr.result);
                emit("    NOT " + dest + ", " + r1);
                store(instr.result, dest);
                break;
            }

            case "NEG": {
                String r1   = load(instr.arg1);
                String dest = allocReg(instr.result);
                emit("    NEG " + dest + ", " + r1);
                store(instr.result, dest);
                break;
            }

            case "IF": {
                String r1 = load(instr.arg1);
                emit("    CMP " + r1 + ", #0");
                emit("    JNE " + instr.arg2);
                break;
            }

            case "IFNOT": {
                String r1 = load(instr.arg1);
                emit("    CMP " + r1 + ", #0");
                emit("    JEQ " + instr.arg2);
                break;
            }

            case "GOTO": {
                emit("    JMP " + instr.arg1);
                break;
            }

            case "LABEL": {
                emit(instr.arg1 + ":");
                break;
            }

            case "RETURN": {
                if (instr.arg1 != null) {
                    String r = load(instr.arg1);
                    emit("    MOV R0, " + r);   // return value convention: R0
                }
                emit("    RET");
                break;
            }

            default:
                emit("    ; [UNHANDLED] " + instr);
        }
    }

    // ─── Register allocation (trivial: spill when full) ─────────────────────────

    private String allocReg(String name) {
        if (name == null) return registers[0];
        if (regMap.containsKey(name)) return regMap.get(name);
        if (!freeRegs.isEmpty()) {
            String reg = freeRegs.poll();
            usedRegs.add(reg);
            regMap.put(name, reg);
            return reg;
        }
        // Spill oldest register
        String spilled = usedRegs.iterator().next();
        usedRegs.remove(spilled);
        String reg = regMap.remove(spilled);
        // Actually spill: store to memory
        emit("    STR " + reg + ", [" + spilled + "]   ; spill");
        spillCount++;
        regMap.put(name, reg);
        usedRegs.add(reg);
        return reg;
    }

    private String load(String name) {
        if (name == null) return "R0";
        if (isImmediate(name)) {
            // Load immediate into a fresh temp register
            String temp = allocReg("__imm_" + name);
            emit("    MOV " + temp + ", #" + name);
            return temp;
        }
        if (regMap.containsKey(name)) return regMap.get(name);
        // Not in a register — load from memory
        String reg = allocReg(name);
        emit("    LDR " + reg + ", [" + name + "]   ; load from memory");
        return reg;
    }

    private void store(String name, String reg) {
        // Already in register — nothing to do for temps; for named vars emit STORE comment
        if (name != null && !name.startsWith("t")) {
            emit("    STR " + reg + ", [" + name + "]");
        }
    }

    private String resolveOperand(String op) {
        if (op == null) return "R0";
        if (regMap.containsKey(op)) return regMap.get(op);
        return op;
    }

    private boolean isImmediate(String s) {
        if (s == null) return false;
        try { Integer.parseInt(s); return true; } catch (NumberFormatException e) {}
        try { Double.parseDouble(s); return true; } catch (NumberFormatException e) {}
        if (s.equals("0") || s.equals("1")) return true;
        return false;
    }

    private String arithmeticMnemonic(String op) {
        switch (op) {
            case "+": return "ADD";
            case "-": return "SUB";
            case "*": return "MUL";
            case "/": return "DIV";
            case "%": return "MOD";
            default:  return "???";
        }
    }

    private String condMnemonic(String op) {
        switch (op) {
            case "==": return "EQ";
            case "!=": return "NE";
            case "<":  return "LT";
            case ">":  return "GT";
            case "<=": return "LE";
            case ">=": return "GE";
            default:   return "??";
        }
    }

    private void emit(String line) {
        code.add(new MachineInstr(line));
    }
}
