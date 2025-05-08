import java.util.*;

public class InstructionHandler {
    private String mnemonic,operand;
    private static Map<String, Integer> opcodeFormats = new HashMap<>();

    public InstructionHandler() {}
    // Define supported mnemonics with their formats
    static {
        opcodeFormats.put("START", 0);
        // Format 1
        opcodeFormats.put("FIX", 1);
        opcodeFormats.put("FLOAT", 1);
        opcodeFormats.put("HIO", 1);
        opcodeFormats.put("NORM", 1);
        opcodeFormats.put("SIO", 1);
        opcodeFormats.put("TIO", 1);

        // Format 2
        opcodeFormats.put("ADDR", 2);
        opcodeFormats.put("CLEAR", 2);
        opcodeFormats.put("COMPR", 2);
        opcodeFormats.put("DIVR", 2);
        opcodeFormats.put("MULR", 2);
        opcodeFormats.put("RMO", 2);
        opcodeFormats.put("SHIFTL", 2);
        opcodeFormats.put("SHIFTR", 2);
        opcodeFormats.put("SUBR", 2);
        opcodeFormats.put("SVC", 2);
        opcodeFormats.put("TIXR", 2);

        // Format 3/4
        List<String> format34Mnemonics = Arrays.asList(
                "ADD", "ADDF", "AND", "COMP", "COMPF", "DIV", "DIVF", "J", "JEQ", "JGT", "JLT",
                "JSUB", "LDA", "LDB", "LDCH", "LDF", "LDL", "LDS", "LDT", "LDX", "MUL", "MULF",
                "OR", "RD", "RSUB", "SSK", "STA", "STB", "STCH", "STF", "STI", "STL", "STS",
                "STSW", "STT", "STX", "SUB", "SUBF", "TD", "TIX", "WD"
        );

        format34Mnemonics.forEach(mnemonic -> opcodeFormats.put(mnemonic, 34));
    }

    public static int getInstructionLength(String mnemonic) {
        boolean isExtended = mnemonic.startsWith("+");
        String cleanMnemonic = (isExtended ? mnemonic.substring(1) : mnemonic).trim().toUpperCase();

        System.out.println("Looking up mnemonic: " + cleanMnemonic);
        System.out.println("Opcode map contains: " + opcodeFormats.keySet());

        if (!opcodeFormats.containsKey(cleanMnemonic)) {
            throw new IllegalArgumentException("Unknown mnemonic: " + mnemonic);
        }

        int format = opcodeFormats.get(cleanMnemonic);

        if (format == 0) return 0;
        if (format == 1) return 1;
        if (format == 2) return 2;
        if (format == 34) return isExtended ? 4 : 3;

        throw new IllegalArgumentException("Unrecognized format for mnemonic: " + mnemonic);
    }

    public static boolean isInstruction(String instructions){
        return opcodeFormats.containsKey(instructions);
    }



}
