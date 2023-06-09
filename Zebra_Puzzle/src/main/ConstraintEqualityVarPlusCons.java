package main;

import jdk.jshell.ImportSnippet;

import java.util.ArrayList;

public class ConstraintEqualityVarPlusCons extends Constraint{

    Variable v1, v2;
    Integer i1;

    public ConstraintEqualityVarPlusCons(Variable v1, Variable v2, Integer i1) {
        this.v1 = v1;
        this.v2 = v2;
        this.i1 = i1;
    }

    public String toString() {
        return String.valueOf(v1.name) + " = " + String.valueOf(v2.name) + " + " + String.valueOf(i1);
    }

    protected boolean isSatisfied() {
        //true if there exists a in α’s domain and b in β’s domain such that a = b + c
        //get values into array list for easier manipulation
        String domainOne = String.valueOf(v1.getDomain());
        Domain domainTwo = v2.getDomain();
        Integer[] resultInt = new Integer[domainTwo.vals.length * 2];
        for(int i = 0; i < domainTwo.vals.length ; i++){
            resultInt[i] = domainTwo.vals[i] + i1;
            resultInt[i] = domainTwo.vals[i] - i1;
            if(domainOne.contains(String.valueOf(resultInt[i]))) return true;
        }
        return false;
    }

    protected void reduce() {
        Domain domainOne = v1.getDomain();
        Domain domainTwo = v2.getDomain();
        //array for storing numbers after b + c operation
        ArrayList<Integer> resultInt = new ArrayList<Integer>();
        ArrayList<Integer> finalResultInt = new ArrayList<Integer>();

        for(int i = 0; i < domainTwo.vals.length; i++){
            resultInt.add(domainTwo.vals[i] + i1);
            resultInt.add(domainTwo.vals[i] - i1);
        }
        for(int i = 0; i < domainOne.vals.length; i++){
            if(resultInt.contains(domainOne.vals[i])){
                finalResultInt.add(domainOne.vals[i]);
            }
        }
        //set first domain
        int[] newDomainOneInt = new int[finalResultInt.size()];
        for(int i = 0; i < finalResultInt.size(); i++){
            newDomainOneInt[i] = finalResultInt.get(i);
        }
        Domain newDomainOne = new Domain(newDomainOneInt);
        //if(!newDomainOne.isEmpty()) {
            v1.setDomain(newDomainOne);
        //}

        //array for storing numbers after a-c operation
        ArrayList<Integer> resultIntTwo = new ArrayList<Integer>();
        ArrayList<Integer> finalResultIntTwo = new ArrayList<Integer>();

        for(int i = 0; i < domainOne.vals.length; i++){
            resultIntTwo.add(domainOne.vals[i] - i1);
            resultIntTwo.add(domainOne.vals[i] + i1);
        }
        for(int i = 0; i < domainTwo.vals.length; i++){
            if(resultIntTwo.contains(domainTwo.vals[i])){
                finalResultIntTwo.add(domainTwo.vals[i]);
            }
        }
        //set second domain
        int[] newDomainTwoInt = new int[finalResultIntTwo.size()];
        for(int i = 0; i < finalResultIntTwo.size(); i++){
            int temp = finalResultIntTwo.get(i);
            newDomainTwoInt[i]  = temp;
        }
        Domain newDomainTwo = new Domain(newDomainTwoInt);
        //if(!newDomainTwo.isEmpty()) {
            v2.setDomain(newDomainTwo);
        //}

    }

}
