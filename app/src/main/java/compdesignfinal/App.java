/*
 * This Java source file was generated by the Gradle 'init' task.
 */

package compdesignfinal;
import java.util.Scanner;
public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the Instruction to binary converter!"); 
        System.out.println("This was done by Noah Wenig for the final project in his computer design course.");
        System.out.println("Enter the MIPS instruction you'd like to be converted to binary: ");
        Scanner scanner = new Scanner(System.in); 
        String instruction = scanner.nextLine(); 
        System.out.println("instruction was " + instruction);
        if(instruction.subSequence(0, 4).equals("addu") ||instruction.subSequence(0, 4).equals("subu")){
            String ret = handleClass1(instruction); 
            System.out.println(ret);
        }else{
            System.out.println("doing something else");
        }
        scanner.close();
    }
    //class 1 instructions are what I'm calling R-type instructions
    //with no shift, and take 3 registers (rd, rs, rt)
    //all class 1 instructions: 
    //sllv, srlv, srav, add, addu, sub, subu, and, or, xor, nor, slt, sltu
    private static String handleClass1(String instruction){

        String[] registers = ((String) instruction.subSequence(4, instruction.length())).split(",");
        for(int i=0; i< registers.length; i++){
        registers[i] = registers[i].trim(); 
        registers[i] = registers[i].replaceAll("[$,]", ""); 
        System.out.println(registers[i]);
        }
        
        StringBuilder binaryInstruction = new StringBuilder("000000"); //r type, funct code will be 0
        binaryInstruction.append(sNumtos5Bit(registers[1])); 
        binaryInstruction.append(sNumtos5Bit(registers[2]));
        binaryInstruction.append(sNumtos5Bit(registers[0])); 
        binaryInstruction.append("00000"); //shamt is 0
        binaryInstruction.append(getFunctCodeClass1(instruction)); 

        // instruction will be in format of rd, rs, rt
        //binary will be in format op, rs, rt, rd, shamt, funct
        return binaryInstruction.toString();
    }
private static String sNumtos5Bit(String sNum){
    Integer numAsInt = Integer.parseInt(sNum); 
    String binaryString = String.format("%5s", Integer.toBinaryString(numAsInt)).replace(" ","0");
    //System.out.println(numAsBinary);
    System.out.println(binaryString);
    return binaryString; 
}
private static String sNumtos6Bit(String sNum){
    Integer numAsInt = Integer.parseInt(sNum); 
    String binaryString = String.format("%6s", Integer.toBinaryString(numAsInt)).replace(" ","0");
    //System.out.println(numAsBinary);
    System.out.println(binaryString);
    return binaryString; 
}
//handles funct codes for all class 1 instructions
//TODO: implement functionality for these:
//sllv, srlv, srav, add, sub, subu, and, or, xor, nor, slt, sltu
private static String getFunctCodeClass1(String instruction){
    if(instruction.subSequence(0, 4).equals("addu")){
        return sNumtos6Bit("33"); 
    }

    return ""; 
}
}
