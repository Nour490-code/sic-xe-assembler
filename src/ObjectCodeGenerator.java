import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.Integer.toHexString;

public class ObjectCodeGenerator extends IntermediateFileGenerator {

    private String _InFileName = "out_pass1.txt";
    private String _SymbolTable = "symbol_table.txt";
    private String _OutFileName = "Out_Pass2.txt";
    private File Pass1Out = new File(_InFileName);
    private File Out = new File(_OutFileName);
    private File _SymbTable = new File(_SymbolTable);
    private Vector<String> _InFileData = new Vector();
    private Map<String,String  > Symbols = new HashMap<>();
    //private int InstructionFormatNumber ;

    Scanner input = new Scanner(Pass1Out);
    Scanner SympInput = new Scanner(_SymbTable);

    //Setters And Getters


    @Override
    public Vector<String> GetFileLines() {
        return _InFileData;
    }

    public Scanner GetScanner() {
        return input;
    }

    public File GetInputFile() {
        return Pass1Out;
    }

    //Constructor
    public ObjectCodeGenerator() throws FileNotFoundException {
        super();
        _InFileData = ReadFile(_InFileData, input, Pass1Out);

    }

    public ObjectCodeGenerator(String FileName) throws FileNotFoundException {
        super(FileName);
    }

    //Method to get the opCode of the instruction

    public void ReadFromSymbolTableFile()
    {
        String [] Parts1 = new String[2];
        String line ;
        if(_SymbTable.exists()) {
            while (SympInput.hasNextLine()) {

                    line = SympInput.nextLine();
                if (line.charAt(0) != '=')
                {
                    Parts1 = line.trim().split("\\s+");
                    Symbols.put(Parts1[0],Parts1[1]);
                   // System.out.println(Parts1[0] + " " + Parts1[1]);
                }
            }
        }
    }

    public String GetOpCode(String Menimonic) {
        Map<String, String> sicInstructions = new HashMap<>();
        sicInstructions.put("SSK", "EC");
        sicInstructions.put("STA", "0C");
        sicInstructions.put("STB", "78");
        sicInstructions.put("STCH", "54");
        sicInstructions.put("STF", "80");
        sicInstructions.put("STI", "D4");
        sicInstructions.put("STL", "14");
        sicInstructions.put("STS", "7C");
        sicInstructions.put("STSW", "E8");
        sicInstructions.put("STT", "84");
        sicInstructions.put("STX", "10");
        sicInstructions.put("SUB", "1C");
        sicInstructions.put("SUBF", "5C");
        sicInstructions.put("SUBR", "94");
        sicInstructions.put("SVC", "B0");
        sicInstructions.put("TD", "E0");
        sicInstructions.put("TIO", "F8");
        sicInstructions.put("TIX", "2C");
        sicInstructions.put("TIXR", "B8");
        sicInstructions.put("WD", "DC");
        sicInstructions.put("ADD", "18");
        sicInstructions.put("ADDF", "58");
        sicInstructions.put("ADDR", "90");
        sicInstructions.put("AND", "40");
        sicInstructions.put("COMP", "28");
        sicInstructions.put("COMPF", "88");
        sicInstructions.put("COMPR", "A0");
        sicInstructions.put("DIV", "24");
        sicInstructions.put("DIVF", "64");
        sicInstructions.put("DIVR", "9C");
        sicInstructions.put("FIX", "C4");
        sicInstructions.put("FLOAT", "C0");
        sicInstructions.put("HIO", "F4");
        sicInstructions.put("J", "3C");
        sicInstructions.put("JEQ", "30");
        sicInstructions.put("JGT", "34");
        sicInstructions.put("JLT", "38");
        sicInstructions.put("JSUB", "48");
        sicInstructions.put("LDA", "00");
        sicInstructions.put("LDB", "68");
        sicInstructions.put("LDCH", "50");
        sicInstructions.put("LDL", "08");
        sicInstructions.put("LDF", "6C");
        sicInstructions.put("LDS", "70");
        sicInstructions.put("LDT", "74");
        sicInstructions.put("LDX", "04");
        sicInstructions.put("LPS", "D0");
        sicInstructions.put("MUL", "20");
        sicInstructions.put("MULF", "60");
        sicInstructions.put("MULR", "98");
        sicInstructions.put("NORM", "C8");
        sicInstructions.put("OR", "44");
        sicInstructions.put("RD", "D8");
        sicInstructions.put("RMO", "AC");
        sicInstructions.put("RSUB", "4C");
        sicInstructions.put("SHIFTL", "A4");
        sicInstructions.put("SHIFTR", "A8");
        sicInstructions.put("SIO", "F0");
        return sicInstructions.get(Menimonic);
    }

    public String GetRegesterObcode(String RegisterName)
    {
        Map<String ,String> RegesterNo = new HashMap<>();
        RegesterNo.put("A","0");
        RegesterNo.put("X","1");
        RegesterNo.put("L","2");
        RegesterNo.put("PC","8");
        RegesterNo.put("SW","9");
        RegesterNo.put("B","3");
        RegesterNo.put("S","4");
        RegesterNo.put("T","5");
        RegesterNo.put("F","6");

        return RegesterNo.get(RegisterName);

    }

    public void PrintVector()
    {
        for(int i = 0 ;i<_InFileData.size();i++)
            System.out.println(_InFileData.get(i));
    }

    public String GetFormat2ObjectCode(String [] Instruction)
    {
        String OpCode ;
        String r1;
        String r2;
        String binaryString = "101101101001"; // Example binary string

        // Convert binary string to decimal integer
        int decimalValue = Integer.parseInt(binaryString, 2);

        // Convert the decimal integer to a hexadecimal string
        String hexString = toHexString(decimalValue);
        if (Instruction[2].length() > 1)
        {
            r1 = GetRegesterObcode(Instruction[2].substring(0,1));
            r2 = GetRegesterObcode(Instruction[2].substring(2,3));
        }
        else
        {
            r1 = GetRegesterObcode(Instruction[2].substring(0,1));
            r2 = "0";
        }
        OpCode = GetOpCode(Instruction[1]);

        String ObjectCode = OpCode + r1 + r2 ;

        //System.out.println(ObjectCode);
        return ObjectCode;

    }

    public void AddObjectCodeToLine(String ObjectCode , int LineNumber)
    {



            String line = _InFileData.get(LineNumber);
            line = line + "      " + ObjectCode ;
            _InFileData.set(LineNumber,line);


    }

    //method to concatenate the first 6 bits of opcode with n,i flag bits
    public static String ConCatenateOpCodeAndINFlags(String opcodeHex, int n, int i) {

        if (opcodeHex != null) {
            int opcode = Integer.parseInt(opcodeHex, 16);
           // System.out.println(opcode);
            int result = ((opcode & 0xFC) | ((n << 1) | i));
            //System.out.println(result);
            return String.format("%02X", result);
        }
        else
            return "Menemonic not found";
    }
    public static String makeFlagsHex(char x, char b, char p, char e) {
        int xbpe = 0;
        xbpe |= (x == '1' ? 1 << 3 : 0);
        xbpe |= (b == '1' ? 1 << 2 : 0);
        xbpe |= (p == '1' ? 1 << 1 : 0);
        xbpe |= (e == '1' ? 1 : 0);
        return toHexString(xbpe).toUpperCase(); // Single hex digit for 4 bits
    }

    public String CalculateTA (String Pc,String Base ,String Ta) {
        //int Displacment = 0;
        if (Ta != null) {
            int PC = Integer.parseInt(Pc, 16);
            int BASE = Integer.parseInt(Base, 16);
            int TA = Integer.parseInt(Ta, 16);
            int Displacment = TA - PC;

            if (Displacment > 127 || Displacment < -128) {
                Displacment = TA - BASE;
            }
            String HexString = toHexString(Displacment);


            HexString = HexString.toUpperCase();
            int maskedNumber = Displacment & 0xFFF;
            HexString = String.format("%03X",maskedNumber);
            //System.out.println(HexString);
            return HexString;
        }
        return null;
    }
    public String GetForm3ObjectCode(String [] Instruction, String PC , String Base)
    {
        short  n ;
        short i ;
        char x;
        char b = '0';
        char p = '1' ;
        char e = '0' ;
        int Register_X = 50 ;
        String Displacement = "1";
        int TA = 0 ;
        String OpCode = new String();
        short AddressingMode = 0;
        String  ObjectCode = new String();
        String Operand = new String();
        String Menimonic = new String();
        if (Instruction.length > 3) {
            Operand = Instruction[3];
            Menimonic = Instruction[2];
        }
        else
        {
            Operand = Instruction[2];
            Menimonic = Instruction[1];
        }


            if(Operand.charAt(0) == '#')
            {
                i = '1';
                n = '0';
                AddressingMode = 2;
            }
            else if (Operand.charAt(0) == '@')
            {
                n = '1';
                i = '0';
                AddressingMode = 3;
            }
            else
            {
                n = '1';
                i = '1';
                AddressingMode = 1;
            }
            if(Operand.toUpperCase().endsWith(",X"))
            {
                x = '1';
                Operand = Operand.substring(0,Operand.length()-2);

            }

            else
                x = '0';




        switch (AddressingMode) {
            //Direct Addressing Mode
            case 1:

                Displacement = CalculateTA(PC, Base, Symbols.get(Operand));
//                System.out.println(Displacement);
//                System.out.println(PC);
//                System.out.println(Symbols.get(Operand));
                //System.out.println(Base);
                if (Long.parseLong(Displacement, 16) > 127 || Long.parseLong(Displacement, 16) < -128) {
                    b = '1';
                    p = '0';
                }
//                System.out.println(Symbols.get(Operand));
//                System.out.println(PC);
//                System.out.println(Base);

                break;
            //Immediate Addressing mode
            case 2:
                if (Character.isLetter(Operand.charAt(1))) {
                    Displacement = CalculateTA(PC, Base, Symbols.get(Operand.substring(1)));
                    if (Integer.parseInt(Displacement) > 127 || Integer.parseInt(Displacement) < -128) {
                        b = '1';
                        p = '0';
                    }
                } else {
                    int Dis = Integer.parseInt(Operand.substring(1));
                    Displacement = Integer.toString(Dis, 16);
                    System.out.println(Displacement);
                }

                break;
            //Indirect Addressing Mode
            case 3:

                Displacement = CalculateTA(PC, Base, Symbols.get(Operand.substring(1)));
                if ((int) Integer.parseInt(Displacement, 16) > 127 || (int) Integer.parseInt(Displacement, 16) < -128) {
                    b = '1';
                    p = '0';
                }

                break;
        }
                if (x == '1')
                {
                    TA = Integer.parseInt(Displacement,16);
                    int disp = Integer.parseInt(Displacement, 16);
                    if (disp >= 0x800) {           // 0x800 is 2048, the sign bit for 12 bits
                        disp = disp - 0x1000;      // 0x1000 is 4096, 2^12
                    }
                    int result = disp + Register_X;
                    Displacement = Integer.toHexString(result & 0xFFF);// Restrict back to 12 bits if needed
                    Displacement = String.format("%03X",result);
                }



        OpCode = ConCatenateOpCodeAndINFlags(GetOpCode(Menimonic) ,Character.getNumericValue(n),Character.getNumericValue(i))
        ;
        ObjectCode = OpCode +  makeFlagsHex(x,b,p,e) + Displacement ;
        return ObjectCode ;
    }

    public String GetForm4ObjectCode(String [] Instruction , String PC , String Base )
    {
        short  n = 1 ;
        short i = 1;
        char x;
        char b = '0';
        char p = '0' ;
        char e = '1' ;
        int Register_X = 50 ;
        String Displacement = "";
        int TA = 0 ;
        String OpCode = new String();
        short AddressingMode = 0;
        String  ObjectCode = new String();
        String Operand = new String();
        String Menimonic = new String();
        if (Instruction.length > 3) {
            Operand = Instruction[3];
            Menimonic = Instruction[2].substring(1);
        }
        else
        {
            Operand = Instruction[2];
            Menimonic = Instruction[1].substring(1);
        }


        if(Operand.charAt(0) == '#')
        {
            i = 1;
            n = 0;
            AddressingMode = 2;
        }

        if(Operand.toUpperCase().endsWith(",X"))
        {
            x = '1';
        }

        else
            x = '0';

            if (AddressingMode == 2) {

                int Dis = Integer.parseInt(Operand.substring(1));
                Displacement = Integer.toString(Dis, 16);
                System.out.println(Displacement);
            }
            else
            {

                Displacement = Symbols.get(Operand);
            }


        if (x == '1')
        {
            TA = Integer.parseInt(Displacement,16);
            int disp = Integer.parseInt(Displacement, 16);
            if (disp >= 0x800) {           // 0x800 is 2048, the sign bit for 12 bits
                disp = disp - 0x1000;      // 0x1000 is 4096, 2^12
            }
            int result = disp + Register_X;
            Displacement = Integer.toHexString(result & 0xFFF);// Restrict back to 12 bits if needed
            Displacement = String.format("%03X",result);
        }


        OpCode = ConCatenateOpCodeAndINFlags(GetOpCode(Menimonic) ,n,i)
        ;
        ObjectCode = OpCode +  makeFlagsHex(x,b,p,e) + Displacement ;
        return ObjectCode ;


    }

    public void GenerateObjectCode()
    {
        int InstructionFormatNumber ;
        InstructionHandler handler = new InstructionHandler();
        String[] parts ;
        String Menimonic ;
        int PC ;
        String  Base = "30" ;
        for(int i = 1 ;i<_InFileData.size();i++)
        {

            String line = _InFileData.get(i);
            parts = line.trim().split("\\s+");
            if(parts.length> 3)
            { InstructionFormatNumber = handler.getInstructionLength(parts[2]);
                Menimonic = parts[2];
            }
            else
            {
                InstructionFormatNumber = handler.getInstructionLength(parts[1]);
                Menimonic = parts[1];
            }
            PC = Integer.parseInt(parts[0],16) + InstructionFormatNumber;
            //System.out.println(InstructionFormatNumber);
            switch (InstructionFormatNumber)
            {
                case 1:
                    AddObjectCodeToLine(GetOpCode(Menimonic) ,i);
                    //System.out.println(_InFileData.get(i));
                    break;
                case 2:
                    AddObjectCodeToLine(GetFormat2ObjectCode(parts),i);
                    //System.out.println(_InFileData.get(i));
                    break;
                case 3:
                    AddObjectCodeToLine(GetForm3ObjectCode(parts,Integer.toString(PC,16),Base),i);
                    //System.out.println(_InFileData.get(i));
                    break;
                case 4:
                    AddObjectCodeToLine(GetForm4ObjectCode(parts,Integer.toString(PC,16),Base),i);


            }


        }
    }








}
