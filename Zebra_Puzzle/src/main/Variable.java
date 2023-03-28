package main;

public class Variable {

    String name;
    Domain d;

    public Variable(String name, Domain d) {
        this.name = name;
        this.d = new Domain(d);
    }

    public String toString() {
        return this.name + "= " + d;
    }

    public Domain getDomain(){
        return this.d;
    }

    public void setDomain(Domain newD){
        d = newD;
    }

    public String getName(){
        return name;
    }
    public void setName(String nameNew){
        name = nameNew;
    }



}