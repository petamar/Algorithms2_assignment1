package main;

import java.util.ArrayList;
import java.util.List;

public class TheTree {

    private class treeNode{
          treeNode left = null;
          treeNode right = null;
          private List<Variable> data = null;

          public treeNode(List<Variable> data){
              this.data = data;
          }
    }

    public void mainTree(List<Variable> currentSet, ConstraintSolver problem){
        //creates root and recursively creates new nodes
        recursiveTree(currentSet, problem);
    }

    public treeNode recursiveTree(List<Variable> currentSet, ConstraintSolver problem){
        List<Variable> data = problem.reduceVarSet(currentSet);
        problem.getSolution(data);
        treeNode currentNode = new treeNode(data);
        if(!ConstraintSolver.solutionFound) {
            //check if code needs to be reduced and if yes reduce it
            boolean needsSplitting = false;
            for (int t = 0; t < data.size(); t++) {
                if (data.get(t).getDomain().vals.length > 1) {
                    needsSplitting = true;
                }
            }
            if (needsSplitting == true) {
                //create copy of list, so it can be updated after recursion
                List<Variable> tempSet = new ArrayList<Variable>();
                for (int j = 0; j < data.size(); j++) {
                    Variable newVariable = new Variable(data.get(j).getName(), data.get(j).getDomain());
                    tempSet.add(newVariable);
                }
                //find the smallest domain and split it
                int smallestDomIndex = problem.smallestDomIndex(data);
                Domain[] splitedDomain = data.get(smallestDomIndex).getDomain().split();
                tempSet.get(smallestDomIndex).setDomain(splitedDomain[0]);
                //call recursion on left node of split domain
                currentNode.left = recursiveTree(tempSet, problem);
                //reduce the set and update tempSet with old domains from data
                data = problem.reduceVarSet(currentSet);
                for (int h = 0; h < tempSet.size(); h++) {
                    tempSet.get(h).setDomain(data.get(h).getDomain());
                }
                tempSet.get((smallestDomIndex)).setDomain(splitedDomain[1]);
                //call recursion on right node of split domain
                currentNode.right = recursiveTree(tempSet, problem);
            } 
        }
    return currentNode;
    }
}
