/*
 * This Java source file was generated by the Gradle 'init' task.
 */

package compdesignfinal;
import java.util.Scanner;

import com.google.common.collect.SortedMultiset;
public class App {
    public String getGreeting() {
        return "Hello World!";
    }
    //Instructions supported:
    //sllv, srlv, srav, add, addu, sub, subu, and, or, xor, nor, slt, sltu
    //addiu, addi, sltiu, slti, andi, ori, xori
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); 
        boolean goAgain = true; 
        System.out.println("Welcome to the Instruction to binary converter!"); 
        System.out.println("This was done by Noah Wenig for the final project in his computer design course.");
        while(goAgain){
        System.out.println("Enter the MIPS instruction you'd like to be converted to binary: ");
        
        String instruction = scanner.nextLine(); 
        System.out.println("instruction was " + instruction);
        //CHECK FOR CLASS 2 ONES FIRST (longer, if i check for and before andi, and i will never trigger)
        if(instruction.subSequence(0, 4).equals("addi")){
            String ret = handleClass2(instruction); 
            System.out.println(ret);
        }
        else if(instruction.subSequence(0, 4).equals("sllv") ||
            instruction.subSequence(0, 4).equals("srlv") ||
            instruction.subSequence(0, 4).equals("srav") ||
            instruction.subSequence(0, 4).equals("addu") ||
            instruction.subSequence(0, 3).equals("add") ||
            instruction.subSequence(0, 4).equals("subu") ||
            instruction.subSequence(0, 3).equals("sub") ||
            instruction.subSequence(0, 3).equals("and") ||
            instruction.subSequence(0, 2).equals("or") ||
            instruction.subSequence(0, 3).equals("xor") ||
            instruction.subSequence(0, 3).equals("nor") ||
            instruction.subSequence(0, 3).equals("slt") ||
            instruction.subSequence(0, 4).equals("sltu")){
            String ret = handleClass1(instruction); 
            System.out.println(ret); 
        }
            
        
    }
        scanner.close();
    }
    //class 1 instructions are what I'm calling R-type instructions
    //with no shift, and take 3 registers (rd, rs, rt)
    //all class 1 instructions: 
    //sllv, srlv, srav, add, addu, sub, subu, and, or, xor, nor, slt, sltu
    private static String handleClass1(String instruction){
        
        String[] registers = ((String) instruction.subSequence(4, instruction.length())).split(",");
        //^^ this is sketchy, but ends up working because of dollar signs (even with an or, 4th index will be the first number)
        for(int i=0; i< registers.length; i++){
            registers[i] = registers[i].trim(); 
            registers[i] = registers[i].replaceAll("[$,]", ""); 
        }
        StringBuilder binaryInstruction = new StringBuilder("000000"); //r type, funct code will be 0
        binaryInstruction.append(sNumtos5Bit(registers[1])); //rs
        binaryInstruction.append(sNumtos5Bit(registers[2])); //rt
        binaryInstruction.append(sNumtos5Bit(registers[0])); //rd
        binaryInstruction.append("00000"); //shamt 
        binaryInstruction.append(getFunctCodeClass1(instruction)); //funct  
        return binaryInstruction.toString();
    }
   //addiu, addi, sltiu, slti, andi, ori, xori
    public static String handleClass2(String instruction){
        //starts at index 5 to guarantee I get the number, but not the instruction at start
        //sometimes will start with space, dollar, or the number itself depending on instruction character count
        String[] parts = ((String) instruction.subSequence(5, instruction.length())).split(","); 
        for(int i=0; i< parts.length; i++){
           parts[i] =parts[i].trim(); 
           parts[i] = parts[i].replaceAll("[$,]", ""); 
        }
        StringBuilder binaryInstruction = new StringBuilder(); 
        binaryInstruction.append(getOpCodeClass2(instruction)); //op code
        binaryInstruction.append(sNumtos5Bit(parts[1]));    //rs
        binaryInstruction.append(sNumtos5Bit(parts[0]));    //rt 
        binaryInstruction.append(sNumtos16Bit(parts[2]));  //imm
        return binaryInstruction.toString(); 
    }
    //helper methods below:
    //takes a String represented a number in, and returns the 5 bit binary value of the number
private static String sNumtos5Bit(String sNum){
    Integer numAsInt = Integer.parseInt(sNum); 
    String binaryString = String.format("%5s", Integer.toBinaryString(numAsInt)).replace(" ","0");;
    return binaryString; 
}
//takes a String represented a number in, and returns the 6 bit binary value of the number
private static String sNumtos6Bit(String sNum){
    Integer numAsInt = Integer.parseInt(sNum); 
    String binaryString = String.format("%6s", Integer.toBinaryString(numAsInt)).replace(" ","0");
    return binaryString; 
}

private static String sNumtos16Bit(String sNum){
    Integer numAsInt = Integer.parseInt(sNum); 
    String binaryString = String.format("%16s", Integer.toBinaryString(numAsInt)).replace(" ","0");
    return binaryString; 
}
//takes a class 1 instruction in, and returns the function code of it in binary 
private static String getFunctCodeClass1(String instruction){
     if(instruction.subSequence(0, 4).equals("sllv")){
        return sNumtos6Bit("4"); 
    }else if(instruction.subSequence(0, 4).equals("srlv")){
        return sNumtos6Bit("6"); 
    }else if(instruction.subSequence(0, 4).equals("srav")){
        return sNumtos6Bit("7"); 
    } else if(instruction.subSequence(0, 4).equals("addu")){
        return sNumtos6Bit("33"); 
    } else if(instruction.subSequence(0, 3).equals("add")){
        return sNumtos6Bit("32"); 
    } else if(instruction.subSequence(0, 4).equals("subu")){
        return sNumtos6Bit("35"); 
    } else if(instruction.subSequence(0, 3).equals("sub")){
        return sNumtos6Bit("34"); 
    } else if(instruction.subSequence(0, 3).equals("and")){
        return sNumtos6Bit("36"); 
    } else if(instruction.subSequence(0, 2).equals("or")){
        return sNumtos6Bit("37"); 
    } else if (instruction.subSequence(0, 3).equals("xor")){
        return sNumtos6Bit("38"); 
    } else if(instruction.subSequence(0, 3).equals("nor")){
        return sNumtos6Bit("39"); 
    } else if(instruction.subSequence(0, 4).equals("sltu")){
        return sNumtos6Bit("43"); 
    } else if(instruction.subSequence(0, 3).equals("slt")){
        return sNumtos6Bit("42"); 
    }else{
        System.out.println("Invalid class 1 instruction got here");
    return "0"; 
        }
    }
    //takes a class 2 instruction in, returns op code aqs a binary string
    //order of comparisons is wonky cuz of similar substrings
    //ex checking for addi before addiu means addiu would never trigger, would trigger as addi
    private static String getOpCodeClass2(String instruction){
        if(instruction.subSequence(0, 5).equals("addiu")){
            return sNumtos6Bit("9"); 
        }else if(instruction.subSequence(0, 4).equals("addi")){
            return sNumtos6Bit("8"); 
        } else if(instruction.subSequence(0, 5).equals("sltiu")){
            return sNumtos6Bit("11"); 
        } else if(instruction.subSequence(0, 4).equals("slti")){
            return sNumtos6Bit("10"); 
        } else if(instruction.subSequence(0, 4).equals("andi")){
            return sNumtos6Bit("12"); 
        } else if(instruction.subSequence(0, 3).equals("ori")){
            return sNumtos6Bit("13"); 
        } else if(instruction.subSequence(0, 4).equals("xori")){
            return sNumtos6Bit("14"); 
        } else{
            System.out.println("invalid type 2 instruction got into opCode function");
            return "0"; 
        }
    }
}
