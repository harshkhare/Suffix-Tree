package node;

public class Node {
    
    public char value;
    public Node[] nextNodes;

    public Node(char val)
    {
        value = val;
    }
    
    public Node addNextNode(Node nextNode)
    {
        int nextNodesLength = 0;
        if(nextNodes != null){nextNodesLength = nextNodes.length;}
        else{nextNodes = new Node[1];}
        
        Node[] tempNextNodes = new Node[nextNodesLength + 1];
        System.arraycopy(nextNodes, 0, tempNextNodes, 0, nextNodesLength);
        
        tempNextNodes[nextNodesLength] = nextNode;
        nextNodes = tempNextNodes;
//        nextNodes.add(nextNode);
        return nextNode;
    }
    
    public Node getLastNextNode()
    {
        return nextNodes[nextNodes.length - 1];
    }

}
