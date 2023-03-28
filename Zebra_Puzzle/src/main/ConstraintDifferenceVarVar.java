package main;

import java.util.ArrayList;
import java.util.List;

public class ConstraintDifferenceVarVar extends Constraint{

    Variable v1, v2;
    public List<Variable> variableSet;


    public ConstraintDifferenceVarVar(Variable v1, Variable v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public String toString() {
        return String.valueOf(v1.name) + " != " + String.valueOf(v2.name);
    }

    protected boolean isSatisfied() {
        //true if they don't have one last value in common
        String domainOne = String.valueOf(v1.getDomain());
        String domainTwo = String.valueOf(v2.getDomain());
        return !(domainTwo == domainOne);
    }

    protected void reduce() {
        //remove all domain values that are not shared
        String domainOne = String.valueOf(v1.getDomain());
        String domainTwo = String.valueOf(v2.getDomain());
        if(domainTwo.length() == 3){
            if(domainOne.length()-3 > -1) {
                int[] newDomainInt = new int[domainOne.length() - 3];
                String[] partsOfD2 = domainTwo.split("");
                String[] partsOfD1 = domainOne.split("");
                if (domainOne.contains(partsOfD2[1])) {
                    int newDomainIndex = 0;
                    for (int i = 1; i < domainOne.length() - 1; i++) {
                        if (!partsOfD2[1].equals(partsOfD1[i])) {
                            newDomainInt[newDomainIndex] = Integer.parseInt(partsOfD1[i]);
                            newDomainIndex++;
                        }
                    }
                    Domain newDomain = new Domain(newDomainInt);
                    //if(!newDomain.isEmpty()) {
                        v1.setDomain(newDomain);
                   // }
                }
            }
        }
        else if(domainOne.length() == 3) {
            if (domainTwo.length() - 3 > -1) {
                int[] newDomainInt = new int[domainTwo.length() - 3];
                String[] partsOfD2 = domainTwo.split("");
                String[] partsOfD1 = domainOne.split("");
                int newDomainIndex = 0;
                if (domainTwo.contains(partsOfD1[1])) {

                    for (int i = 1; i < domainTwo.length() - 1; i++) {
                        if (!partsOfD2[i].equals(partsOfD1[1])) {
                            newDomainInt[newDomainIndex] = Integer.parseInt(partsOfD2[i]);
                            newDomainIndex++;
                        }
                    }
                    Domain newDomain = new Domain(newDomainInt);
                    //if(!newDomain.isEmpty()) {
                        v2.setDomain(newDomain);
                    //}
                }
            }
        }
    }

}

