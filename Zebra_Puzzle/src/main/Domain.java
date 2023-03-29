package main;

public class Domain {

    int[] vals;


    public Domain(int[] vals) {
        this.vals = vals;
    }


    public Domain(Domain d2) {
        vals = new int[d2.vals.length];
        for(int i = 0; i < vals.length; i++)
            this.vals[i] = d2.vals[i];
    }

    public void delete(int index) {
        int[] newArr = new int[vals.length - 1]; // new array with one less element

        int j = 0; // index for new array
        for (int i = 0; i < vals.length; i++) {
            if (i != index) {
                newArr[j] = vals[i]; // copy element to new array
                j++; // increment index for new array
            }
        }

        this.vals = newArr;
    }

    public String toString() {
        String result  = "{";
        for (int i = 0; i < vals.length; i++)
            result += vals[i];
        result += "}";
        return result;
    }

    public Domain[] split() {
        int intDomain1[] = new int[vals.length/2];
        int intDomain2[] = new int[vals.length - (vals.length/2)];
        for(int q = 0; q < vals.length/2; q++){
            intDomain1[q] = vals[q];
        }
        for(int w = 0; w < vals.length - (vals.length/2); w++){
            intDomain2[w] = vals[w + (vals.length/2)];
        }
        Domain domain1 = new Domain(intDomain1);
        Domain domain2 = new Domain(intDomain2);
        Domain domains[] = new Domain[2];
        domains[0] = domain1;
        domains[1] = domain2;
        return domains;
    }

    public boolean isEmpty() {return(vals.length == 0);}

    private boolean equals(Domain d2) {
        return true;
    }

    public boolean  isReducedToOnlyOneValue() {
        return (vals.length == 1);
    }



}