package main;

import java.util.ArrayList;

public class ConstrainEqualityVPC extends Constraint{

    Variable v1, v2;

    public ConstrainEqualityVPC(Variable v1, Variable v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public String toString() {
        return String.valueOf(v1.name) + " = " + String.valueOf(v2.name) + " + 1";
    }

    protected boolean isSatisfied() {
        //true if they have at least one domain value in common
        Domain domainOne = v1.getDomain();
        String domainTwo = String.valueOf(v2.getDomain());
        for(int i = 0; i < domainOne.vals.length; i++){
            if(domainTwo.contains(String.valueOf(domainOne.vals[i] - 1))){
                return true;
            }
        }
        return false;
    }

    protected void reduce() {
        //remove all domains that dont satisfy a = b +1
        String domainTwoString = String.valueOf(v2.getDomain());
        Domain domainTwo = v2.getDomain();
        Domain domainOne = v1.getDomain();
        ArrayList<Integer> tempDomainOne = new ArrayList<Integer>();
        ArrayList<Integer> tempDomainTwo = new ArrayList<Integer>();


        for(int i = 0; i < domainOne.vals.length; i++){
           if(domainTwoString.contains(String.valueOf(domainOne.vals[i] - 1))){
               tempDomainOne.add(domainOne.vals[i]);
               tempDomainTwo.add(domainOne.vals[i]-1);
           }
        }

        int[] newDomainIntOne = new int[tempDomainOne.size()];
        for(int i = 0; i < tempDomainOne.size(); i++){
            newDomainIntOne[i] = tempDomainOne.get(i);
        }
        Domain newDomainOne = new Domain(newDomainIntOne);
        //if(!newDomainOne.isEmpty()) {
            v1.setDomain(newDomainOne);
        //}

        int[] newDomainIntTwo = new int[tempDomainTwo.size()];
        for(int i = 0; i < tempDomainTwo.size(); i++){
            newDomainIntTwo[i] = tempDomainTwo.get(i);
        }
        Domain newDomainTwo = new Domain(newDomainIntTwo);
        //if(!newDomainTwo.isEmpty()) {
            v2.setDomain(newDomainTwo);
        //}

    }

}
