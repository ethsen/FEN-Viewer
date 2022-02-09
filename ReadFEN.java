/**
 * ReadFEN.java
 * A program that checks a FEN and is able to convert the FEN into a printable string.
 */

import java.util.Scanner; //imports Scanner tool to be able to receive any user input

public interface ReadFEN{ //I distinguished ReadFEN as an interface due to the fact that it needs to be implemented into ReadFENFromFile

    public static String convertFenToReadableString(String FEN){
        /**
         * This function converts the FEN into a printable String and returns it to user
         * @param String FEN, FEN that the program accepts to convert
         * 
         * @returns String Board, Printable string that could either be printed or written into another file
         */

        String Board = new String(); //Establishes new string to store the printable string

        for(int i = 0; i<FEN.length(); i++){
            /**
             * This for loop runs through the entirety of the FEN and checks if the character in position 'i' is a integer or a character and acts differently depending on the characters value.
             */
            Boolean characterInfo = Character.isDigit(FEN.charAt(i)); // Used to check if character in position 'i' is a integer or not

            if(characterInfo == true){ //If the character is indeed a number it runs through this code to add the necessary amount of '.' to represent each number

                int value = Character.getNumericValue(FEN.charAt(i)); // Establishes the value of the integer
                
                for(int j = 0; j<value ; j++){ //Using the value of the integer this for loop adds the necessary amount of '.' onto the new string 
                    Board += '.'; //Adds '.' to string

                }   
            }
            else{
                Board += FEN.charAt(i); //This runs if the character in position 'i' is a character. This adds the character to the string.
            }

        }

        return Board; //Returns the board to be printed or written into a file
    }

    public static String userInputCheckFEN(String FEN){
        /**
         * This function checks if the user input is a valid FEN.
         *@param String FEN, accepts the FEN to check if it is indeed valid
         *
         * @returns FEN, returns the FEN whether it be corrected or not
         */

        Scanner scan = new Scanner(System.in); //establishes scanner to take in user input
        int rowCount= 0; //Establishes row count variable
        Boolean rowInfo = false; //Establishes that each row should be treated as mistyped in the while loop
        int infoCount = 0; //Establishes variable to count the number of information within each row
        Boolean numberSeen = false; //Establishes a boolean that determines whether a integer value has been seen twice in a row.

        while(rowInfo == false){
            /**
             * This while loop will continue to run while the rowInfo is deemed false meaning the FEN is indeed invalid if it continues to run
             */

            numberSeen = false; //Establishes that the character seen in position 'i' is not an integer

            for(int i = 0; i < FEN.length(); i++){
                /**
                 * This for loop runs through the FEN and individually checks each character to see if the FEN is indeed valid
                 */
                Boolean characterInfo = Character.isDigit(FEN.charAt(i));// Establishes a boolean to see if the character seen is a number

                if(numberSeen && characterInfo ){ //If the number seen is true and the character seen is true the code now knows that there are two numbers in a row which would result in the rowInfo being false
                    rowInfo = false;
                }
                if(characterInfo){ //If the character is an integer this code runs to check if it is a valid integer, to establish that the number seen is an integer, and to add it to the infoCount if it is valid
                    numberSeen = true; //Establishes that the character seen is an integer

                    int a = Character.getNumericValue(FEN.charAt(i)); //Gets the integer value of the character

                    if(a >8 || a<0){ //Checks if the integer is greater than 8 (max row spaces) or less than 0 which would not make sense. If either of these is true then the rowInfo is set as false
                        rowInfo = false;   
                    }
                    else{
                        infoCount = infoCount + a; //If the integer is a valid integer it is added to the infoCount to keep track of the info in each row               
                    }
                }
                else{ //If the character is not an integer this code runs to check if it is a valid character and if the character is valid it updates the infoCount or updates the amount of rows
                    String ch = String.valueOf(FEN.charAt(i)); //Gets character value of the character in position 'i'
                    numberSeen = false; //Establishes that the character seen is not an integer
                    
                    if(FEN.charAt(i) == '/'){ //If the character is a '/' then this code updates the row count and checks if the infoCount divided by the rowCount is equal to 8. More about this below
                        rowCount = rowCount + 1; //rowCount is updated by adding 1 
                        if(infoCount / rowCount != 8){ //In theory once the code reaches this position the infoCount / rowCount should = 8 since each row would have a value of a multiple of 8
                            /**
                             * Example:
                             * infoCount would = 8 after going through the characters and once it reaches a '/' the rowCount is = to 1. This would mean that infoCount / rowCount would = 8
                             */
                            rowInfo = false; // If the infoCount / rowCount != 8 then the rowInfo would be false
                        }
                    }
                    else if(!ch.equalsIgnoreCase("p") && !ch.equalsIgnoreCase("n")  && !ch.equalsIgnoreCase("b")  && !ch.equalsIgnoreCase("r") && !ch.equalsIgnoreCase("q") && !ch.equalsIgnoreCase("k")){
                        /**
                         * This if statement is used to check if the character at position 'i' is in fact a valid character that represents a chess piece
                         */
                        rowInfo = false; //If the character is not one of these characters then the rowInfo would be false
                        
                    }                   
                    else{
                        infoCount = infoCount + 1; //If the character is a valid one then 1 is added to infoCount since each letter represents 1 space on the board
                    }                      
                }
            }
            if(infoCount == 64 && rowCount == 8){ //This if statement is used to check if the infoCount and rowCount are both equal to the correct values
                rowInfo = true; //If it is then the rowInfo would be true which would cause the FEN to break out of this while loop
            }
            if(rowInfo == false){ //This if statement is used if rowInfo is false. This prompts the user for a new FEN to check
                System.out.println("Invalid FEN. Please enter a valid FEN.");
                rowCount = 0; //re-establishes rowCount
                infoCount = 0;//re-establishes infoCount
                FEN = scan.next();// Prompts the user for a new FEN         
            }   
        }

        return FEN; //returns valid FEN
    }

    public static String fileCheckFEN(String FEN){
        /**
         * This checker is identical to the function above but it since it is used in th ReadFENFromFile the user will not be prompted for another file if the rowInfo is ever false.
         * @param String FEN, accepts the FEN and checks it
         * 
         * @return FEN, returns valid FEN
         */

        Scanner scan = new Scanner(System.in);
        int rowCount= 0;
        Boolean rowInfo = false;
        int infoCount = 0;
        Boolean numberSeen = false;

        while(rowInfo == false){
            numberSeen = false;
            for(int i = 0; i < FEN.length(); i++){
                Boolean characterInfo = Character.isDigit(FEN.charAt(i));                
                if(numberSeen == true && characterInfo == true){
                    rowInfo = false;
                }
                if(characterInfo == true ){
                    numberSeen = true;
                    int a = Character.getNumericValue(FEN.charAt(i));
                    if(a >8 || a<0){
                        rowInfo = false;   
                    }
                    else{
                        infoCount = infoCount + a;                      
                    }
                }
                else{
                    String ch = String.valueOf(FEN.charAt(i));
                    numberSeen = false;
                    if(FEN.charAt(i) == '/'){
                        rowCount = rowCount + 1;
                        if(infoCount / rowCount != 8){
                            rowInfo = false;  
                        }
                    }
                    else if(!ch.equalsIgnoreCase("p") && !ch.equalsIgnoreCase("n")  && !ch.equalsIgnoreCase("b")  && !ch.equalsIgnoreCase("r") && !ch.equalsIgnoreCase("q") && !ch.equalsIgnoreCase("k")){

                        rowInfo = false;
                        
                    }                   
                    else{
                        infoCount = infoCount + 1;
                    }                      
                }
            }
            if(infoCount == 64 && rowCount == 8){
                rowInfo = true;
            }
            if(rowInfo == false){//This is the only difference in code when comparing the function above and this one. The user will be told an FEN is invalid within the file and the code will end.
                System.out.println("Invalid FEN.");
                System.exit(1); //Ends the running of the code.          
            }   
        }

        return FEN;
    }

    public static void main(String[] args){
        /**
         * This simple main function prints the users valid FEN and calls all methods needed to recieve a valid FEN.
         */

        Scanner scan = new Scanner(System.in); //Establishes scanner to use to prompt user for FEN

        System.out.println("Please enter a FEN:"); //Prompts user for FEN

        String FEN = scan.next(); 

        FEN = userInputCheckFEN(FEN); //Checks if FEN is valid or not

        FEN = convertFenToReadableString(FEN); //Converts the FEN to a printable String 

        for(int i = 0; i< FEN.length(); i++){
            /**
             * This for loop takes in the valid FEN and prints it out
             */
            if(FEN.charAt(i) == '/'){ //If the character at position 'i' is a '/' then a new line is printed
                System.out.println();
            }
            else{
                System.out.print(FEN.charAt(i)); //Each character is printed out and shown to the user
            }
        }
    }
}
    

