package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class ConstraintSolver {

    private Domain dom;
    public List<Variable> variableSet;
    public static List<Variable> solutionSet;
    private List<Constraint> constraintSet;

    static boolean solutionFound = false;



    public ConstraintSolver() {
        this.variableSet = new ArrayList<Variable>();
        this.constraintSet = new ArrayList<Constraint>();
        this.solutionSet = new ArrayList<Variable>();
    }

    public String toString() {
        //print variable
        for(int i = 0; i < variableSet.size(); i++){
            // System.out.println(variableSet.get(i));
        }
       // for(int i = 0; i < constraintSet.size(); i++)
        // System.out.println(constraintSet.get(i));
        return "";
    }

    private void parse(String fileName) {
        try {
            File inputFile = new File(fileName);
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();

                if(currentLine.startsWith("Domain-")) {
                    //this is our domain - i.e. a datastructure that contains values and can be updated, played with etc.
                    String s = currentLine.replace("Domain-","");
                    String[] array = s.split(",");
                    int[] vals = new int[array.length];
                    for(int i = 0; i < array.length; i++) {
                        vals[i] = Integer.parseInt(array[i]);
                    }
                    dom = new Domain(vals);
                } else if (currentLine.startsWith("Var-")) {
                    //this is the code for every variable (a name and a domain)
                    String s = currentLine.replace("Var-","");
                    Variable var = new Variable(s, dom);
                    variableSet.add(var);
                } else if (currentLine.startsWith("Cons-")) {
    //----------------------------------------------------------------------------------------
                    //this is the code for the constraints
                    String shortLine = currentLine.replace("Cons-","");
                    //create constrainSet
                    String[] forFirstLine = shortLine.split("\\(");
                    if(forFirstLine[0].equals("abs")) {
                        //if variable is next to other variable
                        String lineForAbs = forFirstLine[1].replace(")","");
                        lineForAbs = lineForAbs.replace("- ","");
                        lineForAbs = lineForAbs.replace("= ","");
                        String[] partsOfLineForAbs = lineForAbs.split(" ");
                        int matchingVar1 = 0;
                        int matchingVar2 = 0;
                        for(int i =0; i < variableSet.size(); i++) {
                            Variable currentVar = variableSet.get(i);
                            if (currentVar.name.equals(partsOfLineForAbs[0])) {
                                matchingVar1 = i;
                            }
                            if(currentVar.name.equals(partsOfLineForAbs[1])){
                                matchingVar2 = i;
                            }
                        }
                        ConstraintEqualityVarPlusCons cons = new ConstraintEqualityVarPlusCons(variableSet.get(matchingVar1),variableSet.get(matchingVar2), Integer.parseInt(partsOfLineForAbs[2]));
                        //add constrain into set
                        constraintSet.add(cons);

                    } else if(forFirstLine[0].equals("eqVPC")){
                        //if variable is on right from other variable
                        String lineForVPC = shortLine.replace("eqVPC(","");
                        lineForVPC = lineForVPC.replace("= ","");
                        lineForVPC = lineForVPC.replace("+ ","");
                        lineForVPC = lineForVPC.replace(")","");
                        String[] partsOfLineForVPC = lineForVPC.split(" ");
                        int matchingVar1 = 0;
                        int matchingVar2 = 0;
                        for(int i =0; i < variableSet.size(); i++) {
                            Variable currentVar = variableSet.get(i);
                            if (currentVar.name.equals(partsOfLineForVPC[0])) {
                                matchingVar1 = i;
                            }
                            if(currentVar.name.equals(partsOfLineForVPC[1])){
                                matchingVar2 = i;
                            }
                        }
                        ConstrainEqualityVPC cons = new ConstrainEqualityVPC(variableSet.get(matchingVar1),variableSet.get(matchingVar2),Integer.parseInt(partsOfLineForVPC[2])) ;
                        //add constrain into set
                        constraintSet.add(cons);

                    } else if (forFirstLine[0].equals("diff")) {
                        //if variable is not equal to other variable
                        String lineForDiff = shortLine.replace("diff(","");
                        lineForDiff= lineForDiff.replace(")","");
                        String[] partsOfLineForDiff = lineForDiff.split(",");
                        int matchingVar1 = 0;
                        int matchingVar2 = 0;
                        for(int i =0; i < variableSet.size(); i++) {
                            Variable currentVar = variableSet.get(i);
                            if (currentVar.name.equals(partsOfLineForDiff[0])) {
                                matchingVar1 = i;
                            }
                            if(currentVar.name.equals(partsOfLineForDiff[1])){
                                matchingVar2 = i;
                            }
                        }
                        ConstraintDifferenceVarVar cons = new ConstraintDifferenceVarVar(variableSet.get(matchingVar1),variableSet.get(matchingVar2)) ;
                        //add constrain into set
                        constraintSet.add(cons);

                    } else if (forFirstLine[0].equals("eqVC")) {
                        //if variable is equal to some integer as domain
                        String lineForeqVC = forFirstLine[1].replace(")","");
                        String[] partsOfLineForVC = lineForeqVC.split(" = ");
                        int matchingVar = 0;
                        for(int i =0; i < variableSet.size(); i++) {
                            Variable currentVar = variableSet.get(i);
                            if (currentVar.name.equals(partsOfLineForVC[0])) {
                                matchingVar = i;
                            }
                        }
                        ConstraintEqualityVarCons cons = new ConstraintEqualityVarCons(variableSet.get(matchingVar),Integer.parseInt(partsOfLineForVC[1])) ;
                        //add constrain into set
                        constraintSet.add(cons);
                    }
                    else if (forFirstLine[0].equals("eqVV")){
                        //if variable is equal to other variable
                        String lineForeqVV = forFirstLine[1].replace(")","");
                        String[] partsOfLineForAbs = lineForeqVV.split(" = ");
                        int matchingVar1 = 0;
                        int matchingVar2 = 0;

                        for(int i =0; i < variableSet.size(); i++) {
                            Variable currentVar = variableSet.get(i);
                            if (currentVar.name.equals(partsOfLineForAbs[0])) {
                                matchingVar1 = i;
                            }
                            if(currentVar.name.equals(partsOfLineForAbs[1])){
                                matchingVar2 = i;
                            }
                        }
                        ConstraintEqualityVarVar cons = new ConstraintEqualityVarVar(variableSet.get(matchingVar1),variableSet.get(matchingVar2)) ;
                        //add constrain into set
                        constraintSet.add(cons);
                    }
                };
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ConstraintSolver problem = new ConstraintSolver();
        problem.parse("data.txt");
        //create copy of variable set so it is not rewritten
        List<Variable> tempVariableSet = problem.createCopySet();
        //create tree and call tree to recursively find solution
        TheTree tree = new TheTree();
        tree.mainTree(tempVariableSet, problem);
        //print solution set as right result found
        for(int i = 0; i < solutionSet.size(); i++){
             System.out.println(solutionSet.get(i));
        }
    }

    public void reduce() {
        //reduce variable domains according to first set of constrains
            for (int i = 0; i < constraintSet.size(); i++) {
                if(constraintSet.get(i).isSatisfied()) {
                    constraintSet.get(i).reduce();
                }
            }
    }

    public List<Variable> reduceVarSet(List<Variable> passedSet){
        //copy values from passed set into variable set for constrain reduce
        for(int i = 0; i < passedSet.size(); i++){
            variableSet.get(i).setDomain(passedSet.get(i).getDomain());
        }
        checkReduceSame();
        return variableSet;
    }

    public void checkReduceSame(){
        //when compareReduce is false run the function
       while(!compareReduce()){
           compareReduce();
       }
    }

    public void getSolution(List<Variable> currentSet){
        boolean solved = true;
        for(int i = 0; i < currentSet.size(); i++){
            if(!currentSet.get(i).getDomain().isReducedToOnlyOneValue()){
                solved = false;
            }
        }
        for(int m =0; m < constraintSet.size(); m++){
            if(!constraintSet.get(m).isSatisfied()){
                solved = false;
            }
        }
        //if solved is true then we have solution
        if(solved == true){
            for(int p =0; p < currentSet.size(); p++){
                Domain savedDomain = currentSet.get(p).getDomain();
                String savedName = currentSet.get(p).getName();
                Variable savedVariable = new Variable(savedName, savedDomain);
                solutionSet.add(savedVariable);
            }
            solutionFound = true;
        }
    }

    public int smallestDomIndex(List<Variable> currentSet){
        //find smallest domain index in variable set
        int smallestDom = 5;
        int smallestIndex = 0;
        for(int i = 0; i < currentSet.size(); i++){
            if(currentSet.get(i).getDomain().vals.length != 1 && currentSet.get(i).getDomain().vals.length <  smallestDom){
                smallestDom = currentSet.get(i).getDomain().vals.length;
                smallestIndex = i;
            }
        }
        return smallestIndex;
    }

    public boolean compareReduce(){
        //save copy of variable list
        List<Variable> copyRecVarSet = new ArrayList<Variable>();
        for (int v = 0; v < variableSet.size(); v++) {
            Domain savedDomain = variableSet.get(v).getDomain();
            String savedName = variableSet.get(v).getName();
            Variable savedVariable = new Variable(savedName, savedDomain);
            copyRecVarSet.add(savedVariable);
        }
        reduce();
        //compare copy to new variable set and when its false it will run it again
        for(int i = 0; i < variableSet.size(); i++){
            if(!Arrays.equals(variableSet.get(i).getDomain().vals, copyRecVarSet.get(i).getDomain().vals)){
                return false;
            }
        }
        return true;
    }

    public List<Variable> createCopySet(){
        //creating new variables and domains copy, so it does not always point to same set
        List<Variable> tempVariableSet = new ArrayList<>();
        for (int v = 0; v < variableSet.size(); v++) {
            Domain savedDomain = variableSet.get(v).getDomain();
            String savedName = variableSet.get(v).getName();
            Variable savedVariable = new Variable(savedName, savedDomain);
            tempVariableSet.add(savedVariable);
        }
        return tempVariableSet;
    }
}
