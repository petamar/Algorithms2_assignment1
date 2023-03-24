package main;

import java.util.ArrayList;

public class ConstraintEqualityVarVar extends Constraint {

    Variable v1, v2;

    public ConstraintEqualityVarVar(Variable v1, Variable v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public String toString() {
        return String.valueOf(v1.name) + " = " + String.valueOf(v2.name);
    }

    protected boolean isSatisfied() {
        //true if they have at least one domain value in common
        Domain domainOne = v1.getDomain();
        String domainTwo = String.valueOf(v2.getDomain());
        for(int i = 0; i < domainOne.vals.length; i++){
            if(domainTwo.contains(String.valueOf(domainOne.vals[i]))){
                return true;
            }
        }
        return false;
    }

    protected void reduce() {
        //remove all domain values that are not shared
        String domainOne = String.valueOf(v1.getDomain());
        String domainTwo = String.valueOf(v2.getDomain());
        String[] domainNum = domainOne.split("");
        ArrayList<Integer> tempDomain = new ArrayList<Integer>();

        for(int i = 1; i < domainOne.length() - 1; i++){
            if(domainTwo.contains(domainNum[i])){
                tempDomain.add(Integer.parseInt(domainNum[i]));
            }
        }

        int[] newDomainInt = new int[tempDomain.size()];
        for(int i = 0; i < tempDomain.size(); i++){
            newDomainInt[i] = tempDomain.get(i);
        }
        Domain newDomain = new Domain(newDomainInt);
        //if(!newDomain.isEmpty()) {
            v1.setDomain(newDomain);
            v2.setDomain(newDomain);
        //}

    }

}