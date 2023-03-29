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
        boolean satisfied =!(domainTwo.equals(domainOne));
        return satisfied;
    }

    protected void reduce() {
        Domain domainOne = v1.getDomain();
        Domain domainTwo = v2.getDomain();
        if(domainTwo.vals.length == 1 && domainOne.vals.length > 1){
            for(int i =0; i < domainOne.vals.length; i++){
                if(domainOne.vals[i] == domainTwo.vals[0]){
                    domainOne.delete(i);
                }
            }
            v1.setDomain(domainOne);
        }
        else if(domainOne.vals.length == 1 && domainTwo.vals.length > 1){
            for(int i =0; i < domainTwo.vals.length; i++){
                if(domainTwo.vals[i] == domainOne.vals[0]){
                    domainTwo.delete(i);
                }
            }
            v2.setDomain(domainTwo);
        }
    }

}

