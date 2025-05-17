import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        IntermediateFileGenerator test1 = new IntermediateFileGenerator("IN.txt");
        test1.ReadFile(test1.GetFileLines(),test1.GetScanner(),test1.GetInFile());
        test1.Split(test1.GetFileLines().elementAt(0));
        test1.GenerateOutFile();
        test1.PrintFileLinesVectorContents(test1.FileLines);
        System.out.println("End Of proccessing Intermediate File");
        //System.out.println(test1.FileLines.size());
        /////
        LocationHandler.getFirstAddress();
        passOneFileGenerator test3 = new passOneFileGenerator("Out.txt");
        test3.ReadFile();
        test3.Split(test1.GetFileLines().elementAt(0));
        test3.GenerateOutFile();
        //test3.PrintFileLinesVectorContents();
        System.out.println("End Of Adding Location Counter");

        ////////////////////////////////////////////////////////////
                SymbolTableGenerator generator = new SymbolTableGenerator();
        generator.generateSymbolTable("out_pass1.txt");
        generator.writeToFile("symbol_table.txt");
        System.out.println("Symbol table generated successfully in " + "outputFile");

        LocationHandler.getFirstAddress();
        ObjectCodeGenerator test2 = new ObjectCodeGenerator("out_pass1.txt");
        test2.ReadFile(test2.GetFileLines(),test2.GetScanner(),test2.GetInputFile());

        //System.out.println(test2.CalculateTA("10AC","0033","1009"));
        test2.ReadFromSymbolTableFile();
        test2.GenerateObjectCode();
        //  test2.PrintVector();
        test2.GenerateOutputFile();
//


        HTMERecordGenerator HTMERecordGenerator = new HTMERecordGenerator();
        HTMERecordGenerator.generateHTMERecords("Out_Pass2.txt", "HTME.txt");
        System.out.println("HTME records generated successfully in " + "outputFile");

        // Print the output to console as well
        System.out.println("\nGenerated Records:");
        System.out.println(HTMERecordGenerator.generateHeaderRecord());
        HTMERecordGenerator.getTextRecords().forEach(System.out::println);
        System.out.println(HTMERecordGenerator.generateEndRecord());
    }
}