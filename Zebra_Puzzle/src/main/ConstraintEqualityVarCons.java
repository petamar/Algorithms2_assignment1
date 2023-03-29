package main;

public class ConstraintEqualityVarCons extends Constraint{
    Variable v1;
    Integer i1;

    public ConstraintEqualityVarCons(Variable v1, Integer i1) {
        this.v1 = v1;
        this.i1 = i1;
    }

    public String toString() {
        return String.valueOf(v1.name) + " = " + String.valueOf(i1);
    }

    protected boolean isSatisfied() {
        //is true when variable domain contains i1
        String currentDom = String.valueOf(v1.getDomain());
        return currentDom.contains(i1.toString());
    }

    protected void reduce() {
        //reduce domain to that one domain number
        int[] newDomainInt = new int[1];
        newDomainInt[0] = i1;
        Domain newDomain = new Domain(newDomainInt);
            v1.setDomain(newDomain);
    }
}
