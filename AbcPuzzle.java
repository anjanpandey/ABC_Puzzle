/**________________________________________________________________________________________
 *
 * @author Anjan Pandey on 04/08/2015
 *
 * @title ABC Path Puzzle
 *
 * @submitted to Dr. C
 *
 * @discription This program solves the ABC Path puzzle with the help of recursion
 * and backtracking. Here we have 7*7 two dimensional array. In the surrounding of
 * this array, we have clues of alphabets which we obtain after reading the text 
 * file, and then we recursively fill the rest of the unfilled array from that clue so that
 * each letter is next to the previous letter either horizontally, vertically or diagonally.
 *__________________________________________________________________________________________
 */

import java.io.*;
import java.util.*;

public class AbcPuzzle
{

   private String[][] abcgrid;  //creating a two dimensional array called abcgrid 
   private int first=0;        //creating and intializing the position(row) of an A
   private int second=0;      //creating and intializing the position(column) of an A
   boolean recBrake = false; //creating and intializing the recursion braker
   private String letters = "BCDEFGHIJKLMNOPQRSTUVWXY";       //creating and intializing the to be added letter in an array
   private int index = letters.indexOf("B");                 // obtaining the location of B in letters
   private String alpha = letters.substring(index, index+1);// the variable that stores the letters
   
   /**
    Constructor that initializes the puzzle with the data stored in the file. It calls the method
    Reader in order to store the data in an array of 7*7. It also calls the solveAlphabets inorder
    to locate the position of A as in row and column, and pass B as a letter to be placed in an
    array called abcgrid. The letter B is passed as an index which is obtain from the instance variable 
    called letters. As we go on adding the letters, the feasible location keeps on updating from the 
    method solveAlphabets.  
    @param filename name of the input file
    */
    
   public AbcPuzzle(String filename)
   {
      abcgrid = new String[7][7];
      Reader(filename);
      solveAlphabets(index, first, second);
   }
   
   /**
    Reads the content of the file whose name is passed as argument. Stores each letter in the grid from
    B to Y, and at last store the letter A by looking at its position which is located at last in the
    file.  
    @param filename name of the file to be read 
    */
   
   public void Reader(String filename)
   {
      try
      {
         Scanner file, numScan; 
         file = new Scanner (new File(filename));
         int linecount = 0; // linecount is necessary inorder to keep track of the varying situation 
         while(file.hasNext())
         { 
            String letters = file.nextLine();
            String alphabet;
            String tempstore=null; //to store the second letter in the last column when the length of string is two
            for (int i = 0; i < letters.length(); i++)
            {
            
               alphabet = letters.substring(i, i+1);
             
               if (linecount < 7)
               {
                  abcgrid[linecount][i] = alphabet; 
               }
               if (letters.length() <= 3) //all the scanned strings are not of the same length 
               {
                  if (linecount == 7) //at linecount 7 we have two number with space in middle that gives us the loaction of A
                  {
                     numScan = new Scanner(letters);
                     first = numScan.nextInt();
                     second = numScan.nextInt();
                     abcgrid[first][second] = "A"; //places A in the grid based on the location first and second. 
                  }
                  else
                  { 
                     tempstore = abcgrid[linecount][1];
                     abcgrid[linecount][6] = tempstore;
                     abcgrid[linecount][1] = null; 
                  }
               }
            }
            linecount++;
         }
      }
      
      catch( IOException fileException) //if there is any exception while reading files, the try-catch catch catches it. 
      {
         System.out.println("File Not Found!!!");
      }
   }
   
   /**
    Displays the overall position of the letter after solving the abc path puzzle as a completed grid
    in the abcgrid. 
    */
    
   public void display()
   {
      for (int row=0; row <7; row++)
      {
         for (int col=0; col <7; col++)
         {
            System.out.print(abcgrid[row][col]+"   "+"");
         }
         System.out.println("");
      }
   }
   
   /**
    Returns the solution as a string representing the contents of the grid in row-major order. The string
    comprises of the solved letters without space in between in row-major base.
    @return a solved string in the row-major order
    */
   
   public String toString()
   {
      String rowmajor= "";
      for (int row=1; row < abcgrid.length-1; row++)
      {
         for (int col=1; col < abcgrid[row].length-1; col++)
         {
            rowmajor += abcgrid[row][col];
            
         }
      }
      return rowmajor;
   }
   
   /**
    Checks if the given letter's position is promising or not, that is, for any given letter, it checks if the
    next letter is feasible to place in the prior letters position either by vertically, horizentally or diagonally
    next to it, with the fact of the given clue. Firstly, the method checks if any postion is empty or not, if
    the position is empty, then it check if the letter to be added is present in any of the clue. If the clue
    is matched then the location is considerd to be promising. For the position where row and column are equal
    and there sum is equal to 6, it performs the diagonal test by checking clues in the diagonal also. If any 
    location is not found to be empty, the method does not considered the location promising. 
    @param passIndex passes the letter as its index
    @param row gives the location(row) to be fitted to place the letter
    @param col gives the location(column) to be fitted to place the letter
    @return true if the given position is promising, false otherwise    
    */
   
   public boolean promising(int passIndex, int row, int col)
   {  
      alpha =  letters.substring(passIndex, passIndex+1);
      boolean position= false;
      int sum = row+col;;
      if (abcgrid[row][col] == null)
      {
         if (abcgrid[row][0].equals(alpha) || abcgrid[row][6].equals(alpha)
         || abcgrid[0][col].equals(alpha) || abcgrid[6][col].equals(alpha))
         {
            position = true;
            
         }
         
         if (row == col)
         {
            if (abcgrid[0][0].equals(alpha) || abcgrid[0][6].equals(alpha) ||
            abcgrid[6][0].equals(alpha) || abcgrid[6][6].equals(alpha))
            {
               position = true;
            
            } 
         }
         
         if (sum == 6)
         {
            if (abcgrid[0][6].equals(alpha) || abcgrid[6][0].equals(alpha))
            {
               position = true;
            
            } 
         }
      } 
      return position;
      
   }
   
   /**
    Places the letter, if found promising, in the given location, and start solving for another letter by
    recursion and backtraking. There are eight promising location. The method calls promising for all of the
    given location. After considering the one location promising for the given letter, the method 
    recursively solve for all the other letters (can be imagine in the tree). If the location latter found
    to be misleading then the another promising loaction is considered (pruning the current path, and choosing
    the another path), and previous location is set to null. After all that backtracking and recursion, the 
    ultimate solution is obtained by braking down the recursion. 
    @param newLetter provides the index of the current letter
    @param row provides the location(row) suitable for that letter
    @param col provides the location(column) suitable for that letter   
    */
   public void solveAlphabets (int newLetter, int row, int col)
   {  
      if(newLetter == 24) //if happens to be the ultimate solution, the recursion brakes to indicate final letter is placed in the array
      {
         recBrake = true;
      }
      else
      {
         alpha =  letters.substring(newLetter, newLetter+1);
      
         if (promising(newLetter, row-1, col)) // checking if promising for the vertically upper position
         {  
         
            abcgrid[row-1][col] = alpha;      // placing that letter there
            solveAlphabets(newLetter+1,row-1,col ); // recursively solving all the other letters from there
            if(recBrake) 
            {
               return ;
            }
         
            abcgrid[row-1][col] = null;  //if infeasible, setting the location to the null
         
         }
         if (promising(newLetter, row-1, col-1)) //checking if promising for the upper left diagonal position  
         {
            abcgrid[row-1][col-1] = alpha;
            solveAlphabets(newLetter+1,row-1,col-1 );
            if(recBrake)
            {
               return ;
            }
         
            abcgrid[row-1][col-1] = null;
         }
         if (promising(newLetter,row-1, col+1)) //checking if promising for the upper right diagonal position
         {
            abcgrid[row-1][col+1] = alpha;
            solveAlphabets(newLetter+1,row-1,col+1 );
            if(recBrake)
            {
               return ;
            }
         
            abcgrid[row-1][col+1] = null;
         }
         if (promising(newLetter, row, col-1)) //checking if promising for the left horizontal position
         {
            abcgrid[row][col-1] = alpha;
            solveAlphabets(newLetter+1,row,col-1 );
            if(recBrake)
            {
               return ;
            }
         
            abcgrid[row][col-1] = null;
         }
         if (promising (newLetter, row, col+1)) //checking if promising for the right horizontal position
         {
            abcgrid[row][col+1] = alpha;
            solveAlphabets(newLetter+1,row,col+1 );
            if(recBrake)
            {
               return ;
            }
         
            abcgrid[row][col+1] = null;
         }
         if (promising(newLetter, row+1, col-1)) //checking if promising for the lower left diagonal position
         {
            abcgrid[row+1][col-1] = alpha;
            solveAlphabets(newLetter+1,row+1,col-1 );
            if(recBrake)
            {
               return ;
            }
         
            abcgrid[row+1][col-1] = null;
         }
         if (promising(newLetter, row+1, col)) //checking if promising for the lower vertical position
         {
            abcgrid[row+1][col] = alpha;
            solveAlphabets(newLetter+1,row+1,col );
            if(recBrake)
            {
               return ;
            }
            abcgrid[row+1][col] = null;
         }
         if (promising(newLetter, row+1, col+1)) //checking if promising for the lower right diagonal position
         {
            abcgrid[row+1][col+1] = alpha;
            solveAlphabets(newLetter+1,row+1,col+1 );
            if(recBrake)
            {
               return ;
            }
            abcgrid[row+1][col+1] = null;
         }
      }
   }
}