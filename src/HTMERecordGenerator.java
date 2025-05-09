import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HTMERecordGenerator {
    private String programName;
    private int startAddress;
    private int programLength;
    private List<String> textRecords;
    private int currentTextRecordStart;
    private StringBuilder currentTextRecord;
    private int currentTextRecordLength;
    private final int MAX_TEXT_RECORD_LENGTH = 30; // 60 chars in hex (30 bytes)

    public HTMERecordGenerator() {
        textRecords = new ArrayList<>();
        currentTextRecord = new StringBuilder();
        currentTextRecordLength = 0;
    }

    public void processFile(String filename) throws IOException {
        List<String> inputLines = readInputFile(filename);
        processInput(inputLines);
    }

    private List<String> readInputFile(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    public void processInput(List<String> inputLines) {
        // Parse the input lines
        boolean firstLine = true;
        int lastAddress = startAddress;

        for (String line : inputLines) {
            // Skip empty lines
            if (line.trim().isEmpty()) continue;

            // Split line into parts (handle tabs and multiple spaces)
            String[] parts = line.split("\\s+");

            if (firstLine && parts.length >= 4 && parts[2].equals("START")) {
                // Handle header line
                programName = parts[1];
                startAddress = Integer.parseInt(parts[3], 16);
                firstLine = false;
                lastAddress = startAddress;
                continue;
            }

            if (parts.length >= 2 && parts[1].equals("END")) {
                // Handle end line - we'll process this later
                continue;
            }

            // Process regular instruction lines
            if (parts.length >= 1) {
                String addressStr = parts[0];
                String objectCode = "";

                // Find object code (last field if it's hex)
                for (int i = parts.length - 1; i >= 0; i--) {
                    if (parts[i].matches("[0-9A-Fa-f]{2,}")) {
                        objectCode = parts[i];
                        break;
                    }
                }

                if (!objectCode.isEmpty()) {
                    int address = Integer.parseInt(addressStr, 16);
                    lastAddress = address + (objectCode.length() / 2);
                    addToTextRecord(addressStr, objectCode);
                }
            }
        }

        // Finish any remaining text record
        if (currentTextRecordLength > 0) {
            textRecords.add(createTextRecord(currentTextRecordStart, currentTextRecord.toString()));
        }

        // Calculate program length
        programLength = lastAddress - startAddress;
    }

    private void addToTextRecord(String addressStr, String objectCode) {
        int address = Integer.parseInt(addressStr, 16);
        int objectCodeLength = objectCode.length() / 2; // each byte is 2 hex chars

        if (currentTextRecordLength == 0) {
            // Start a new text record
            currentTextRecordStart = address;
            currentTextRecord = new StringBuilder(objectCode);
            currentTextRecordLength = objectCodeLength;
        } else if (currentTextRecordLength + objectCodeLength <= MAX_TEXT_RECORD_LENGTH &&
                address == (currentTextRecordStart + currentTextRecordLength)) {
            // Add to current text record
            currentTextRecord.append(objectCode);
            currentTextRecordLength += objectCodeLength;
        } else {
            // Finish current record and start a new one
            textRecords.add(createTextRecord(currentTextRecordStart, currentTextRecord.toString()));
            currentTextRecordStart = address;
            currentTextRecord = new StringBuilder(objectCode);
            currentTextRecordLength = objectCodeLength;
        }
    }

    private String createTextRecord(int startAddress, String objectCodes) {
        String length = String.format("%02X", objectCodes.length() / 2);
        return String.format("T%06X^%s^%s", startAddress, length, objectCodes);
    }

    public String generateHeaderRecord() {
        return String.format("H%-6s^%06X^%06X", programName, startAddress, programLength);
    }

    public List<String> getTextRecords() {
        return textRecords;
    }

    public String generateEndRecord() {
        return String.format("E^%06X", startAddress);
    }

    public void generateHTMERecords(String inputFilename, String outputFilename) throws IOException {
        processFile(inputFilename);

        try (java.io.PrintWriter out = new java.io.PrintWriter(outputFilename)) {
            out.println(generateHeaderRecord());
            for (String textRecord : getTextRecords()) {
                out.println(textRecord);
            }
            out.println(generateEndRecord());
        }
    }
}