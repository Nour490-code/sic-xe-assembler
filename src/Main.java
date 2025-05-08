import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        IntermediateFileGenerator test1 = new IntermediateFileGenerator("IN.txt");
        test1.ReadFile();
        test1.Split(test1.GetFileLines().elementAt(0));
        test1.GenerateOutFile();
        test1.PrintFileLinesVectorContents();
        System.out.println(test1.FileLines.size());

        LocationHandler.getFirstAddress();
        passOneFileGenerator test2 = new passOneFileGenerator("intermediate.txt");
        test2.ReadFile();
        test2.Split(test1.GetFileLines().elementAt(0));
        test2.GenerateOutFile();
        test2.PrintFileLinesVectorContents();
        System.out.println(test2.FileLines.size());

        SymbolTableGenerator generator = new SymbolTableGenerator();
        generator.generateSymbolTable("out_pass1.txt");
        generator.writeToFile("symbol_table.txt");
        System.out.println("Symbol table generated successfully in " + "outputFile");

    }
}