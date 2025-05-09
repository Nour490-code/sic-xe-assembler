import java.io.*;
import java.util.*;

public class SymbolTableGenerator {

    private final Map<String, String> symbolTable = new LinkedHashMap<>();

    public void generateSymbolTable(String inputFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");

                // Skip if line has less than 3 parts (not meaningful)
                if (parts.length < 3) continue;

                String address = parts[0];
                String secondField = parts[1];
                String opcode = parts[2];

                // If opcode is START, skip the line (program name line)
                if (opcode.equalsIgnoreCase("START")) {
                    continue;
                }

                // If second field is NOT a known opcode, treat it as a label
                if (!InstructionHandler.isInstruction(secondField)) {
                    symbolTable.put(secondField, address);
                }
            }
        }
    }

    public void writeToFile(String outputFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("===============\nSYMBOL\tADDRESS\n===============\n");
            for (Map.Entry<String, String> entry : symbolTable.entrySet()) {
                writer.write(entry.getKey() + "\t" + entry.getValue() + "\n");
            }
        }
    }

    public Map<String, String> getSymbolTable() {
        return symbolTable;
    }
}
