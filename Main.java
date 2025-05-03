import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
    IntermediateFileGenerator test1 = new IntermediateFileGenerator("In.txt");
    test1.ReadFile();
    test1.Split(test1.GetFileLines().elementAt(0));
    test1.GenerateOutFile();
    test1.PrintFileLinesVectorContents();
        System.out.println(test1.FileLines.size());

    }
}