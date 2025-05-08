import java.io.*;

public class LocationHandler {
    private static String startAddress;
    private static int currentAddress;

    private LocationHandler() {
        // Private constructor prevents instantiation
    }
    public static void getFirstAddress() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("intermediate.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Trim the line and split by any amount of space
                String[] tokens = line.trim().split("\\s+");

                for (int i = 0; i < tokens.length; i++) {
                    if (tokens[i].equalsIgnoreCase("START")) {
                        if (i + 1 < tokens.length) {
                            startAddress = tokens[i + 1];
                            currentAddress = Integer.parseInt(startAddress, 16);  // Support hex
                            System.out.println("Start Address Found: " + startAddress);
                            return;
                        }
                    }
                }
            }
        }
    }
    public static int LocationCounter(String menmonic){
        int instLen = InstructionHandler.getInstructionLength(menmonic);
        int prevAddress = currentAddress;
        currentAddress += instLen;
        System.out.printf("%04X\t%-8s %-8s %-8s\n", prevAddress, "LABEL", menmonic, "OPERAND");
        return prevAddress;
    }

}
