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
    private List<Variable> variableSet;

    private List<Constraint> constraintSet;

    public  ArrayList<ArrayList<Variable>> outer = new ArrayList<ArrayList<Variable>>();


    public ConstraintSolver() {
        this.variableSet = new ArrayList<Variable>();
        this.constraintSet = new ArrayList<Constraint>();
    }

    public String toString() {
        //print variable
        for(int i = 0; i < variableSet.size(); i++)
            System.out.println(variableSet.get(i));
       // for(int i = 0; i < constraintSet.size(); i++)
        // System.out.println(constraintSet.get(i));
        System.out.println("----------------------------------------------------------------------------------");

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
                    String[] partsOfLine = shortLine.split(" = ");
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
                        String lineForVPC = shortLine.replace("eqVPC(","");
                        lineForVPC = lineForVPC.replace(" + 1)","");
                        String[] partsOfLineForVPC = lineForVPC.split(" = ");
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
                        ConstrainEqualityVPC cons = new ConstrainEqualityVPC(variableSet.get(matchingVar1),variableSet.get(matchingVar2)) ;
                        //add constrain into set
                        constraintSet.add(cons);

                    } else if (forFirstLine[0].equals("diff")) {
                        //if variable is not equal to other variable
                        //if variable is equal to other variable
                        //find both variables in variable set
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
                        //if variable is equal to some integer
                        //find variable that matches the constraint
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
                        //find both variables in variable set
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
        problem.reduce();
        System.out.println(problem);
        System.out.println("----------------------------------------------------------------------------------");
    }

    public void reduce() {
            for (int i = 0; i < constraintSet.size(); i++) {
                if(constraintSet.get(i).isSatisfied()) {
                    constraintSet.get(i).reduce();
                }
            }
            for (int i = 0; i < constraintSet.size(); i++) {
                if(constraintSet.get(i).isSatisfied()) {
                    constraintSet.get(i).reduce();
                }
            }



            recursion(0);

    }


    public void recursion(int a){
        int count = 0;
        if(a < 24) {
                Domain newDomain = variableSet.get(a).getDomain();
                if (!newDomain.isEmpty()) {
                    for (int i = 0; i < variableSet.get(a).getDomain().vals.length; i++) {
                        if (!variableSet.get(a).getDomain().isEmpty()) {
                            int[] arrayD = new int[1];
                            arrayD[0] = variableSet.get(a).getDomain().vals[i];
                            Domain testD = new Domain(arrayD);
                            variableSet.get(a).setDomain(testD);
                            //update variables

                            if(count == 1) {
                                //nacitaj vsetky mozne veci z 0
                                for (int d = a+ 1; d < 24; d++) {
                                    Domain savedDomain = outer.get(0).get(d).getDomain();
                                    String savedName = outer.get(0).get(d).getName();
                                    Variable savedVariable = new Variable(savedName, savedDomain);
                                    variableSet.get(d).setDomain(savedDomain);

                                }
                            }

                           for (int m = 0; m < constraintSet.size(); m++) {
                                if (constraintSet.get(m).isSatisfied()) {
                                    constraintSet.get(m).reduce();
                                }
                            }

                            //update list
                            if(outer.size() < 25) {
                                outer.add(hopTemp());
                            }
                          // outer.get(a).


                            a++;
                            if (!isDomainEmpty()) {
                                recursion(a);
                                if(0 < a && a < 24) {
                                    Domain savedDomain = outer.get(a-1).get(a).getDomain();
                                    variableSet.get(a).setDomain(savedDomain);
                                }
                                count = 1;


                            } else {

                                variableSet = outer.get(a - 1);
                            }


                        }
                    }
                }
        }
    }


    public boolean isDomainEmpty(){
        for(int i =0; i < variableSet.size(); i++) {
            boolean tempBool = variableSet.get(i).getDomain().isEmpty();
            if(tempBool == true){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Variable> hopTemp(){
        ArrayList<Variable> tempVarSet = new ArrayList<>();

        for (int v = 0; v < variableSet.size(); v++) {
            Domain savedDomain = variableSet.get(v).getDomain();
            String savedName = variableSet.get(v).getName();
            Variable savedVariable = new Variable(savedName, savedDomain);
            tempVarSet.add(savedVariable);
        }
        return tempVarSet;
    }

}

         /*   int[] arrayD = new int[1];
            arrayD[0]= 3;
            Domain testD = new Domain(arrayD);
            if(!testD.isEmpty()) {
                variableSet.get(0).setDomain(testD);
            }
           */