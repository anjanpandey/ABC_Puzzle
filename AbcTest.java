
/**
   Main method to test the methods of the AbcPuzzle class.  
   
*/ 
public class AbcTest  
{
   public static void main(String[] args)
   {
      AbcPuzzle abc1 = new AbcPuzzle("abcpuzzle1.txt");
      
      abc1.display();
      if (abc1.toString().equals("VUSOPWTNRQXLMCAKYHDBJIGFE"))
         System.out.println("\nProblem 1 solved!!\n\n");
      AbcPuzzle abc2 = new AbcPuzzle("abcpuzzle2.txt");
      System.out.println("___________________________________________________");
      AbcPuzzle abc3 = new AbcPuzzle("test4.txt");
      abc3.display();
      System.out.println("___________________________________________________");
      abc2.display();
      if (abc2.toString().equals("WVRQOXSUPNYTGHMACFLIBEDKJ"))
         System.out.println("\nProblem 2 solved!!\n\n");
   }
}
