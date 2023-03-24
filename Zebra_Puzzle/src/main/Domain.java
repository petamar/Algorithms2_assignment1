package main;

public class Domain {

    int[] vals;


    public Domain(int[] vals) {
        this.vals = vals;
    }


    public Domain(Domain d2) {
        //TODO make a copy of the domain from what d2 contains
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

    /**
     * @return
     */
    public String toString() {
        String result  = "{";
        for (int i = 0; i < vals.length; i++)
            result += vals[i];
        result += "}";
        return result;
    }

    /**
     * @return
     */
    private Domain[] split() {
        return (new Domain[2]);
    }

    /**
     * @return
     */
    public boolean isEmpty() {
       return(vals.length == 0);
    }

    /**
     * @return
     */
    private boolean equals(Domain d2) {
        return true;
    }

    /**
     * @return
     */
    public boolean  isReducedToOnlyOneValue() {
        return (vals.length == 1);
    }



}