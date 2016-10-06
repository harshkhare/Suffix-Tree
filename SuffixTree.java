package test;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import node.Node;

public class SuffixTree {
    
    Node[] nodeVectorInit;
    int levelInit = 0, leafCnt=0;
    boolean compr=false;
    int comprLevel=0;
    String substr, newickFormat="";
    
    public Node build(String seq)
    {
        Node rootNode = new Node('r');
        int i;
        for(i=seq.length()-1;i>=0;i--)
        {
           substr = seq.substring(i);
           traceInto(rootNode, substr, levelInit);
        }
        return rootNode;
    }

    public void traceInto(Node baseNode, String subStr, int level)
    {
        int i, nodeVectorLength = 0;
        boolean traced = false;
        if(baseNode.nextNodes != null)
        {
            nodeVectorLength = baseNode.nextNodes.length;
        }
        
        for(i=0; i<nodeVectorLength; i++)
        {
            if(baseNode.nextNodes[i].value == subStr.charAt(level))
            {
                traced = true;
                level++;
                traceInto(baseNode.nextNodes[i],subStr,level);
                break;
            }
        }
        if(!traced)
        {
            baseNode.addNextNode(new Node(subStr.charAt(level)));
            level++;
            addAllNodes(baseNode.getLastNextNode(),subStr,level);
        }
    }
    
    public void addAllNodes(Node node,String subStr, int level)
    {
        
        while(level<subStr.length())
        {
            node = node.addNextNode(new Node(subStr.charAt(level)));
            level++;
        }
    }
    
    public void buildNewickFormat(Node baseNode)
    {
        int i=0, internalComprNodes = 0;
        
        for(i=0;i<baseNode.nextNodes.length;i++)
        {
            if(baseNode.nextNodes[i].nextNodes==null)
            {
                //leafNode
                newickFormat=newickFormat+baseNode.nextNodes[i].value+leafCnt+",";
                leafCnt++;
                compr=false;
                comprLevel = 0;
            }
            else
            {
                if(baseNode.nextNodes[i].nextNodes.length==1)
                {
                    //compress
                    newickFormat=newickFormat+baseNode.nextNodes[i].value; comprLevel++;
                    buildNewickFormat(baseNode.nextNodes[i]);
                     compr=true;
                }
                else
                {
                    if(compr)
                    {
                        //erase internal compressed node
                        newickFormat = newickFormat.substring(0, newickFormat.length()-comprLevel);
                        comprLevel=0;
                        internalComprNodes++;
                    }
                    //initiate branch
                    newickFormat=newickFormat+",(";
                    buildNewickFormat(baseNode.nextNodes[i]);
                    //close branch
                    newickFormat=newickFormat+"),";
                }
            }
        }
    }

    public String getNewickFormat(Node rootNode)
    {
        if(newickFormat=="")
        {
            buildNewickFormat(rootNode);
            newickFormat="("+newickFormat+");";
            newickFormat=newickFormat.replaceAll(",,",",");
            newickFormat=newickFormat.replaceAll(",\\)","\\)");
            newickFormat=newickFormat.replaceAll("\\(,","\\(");
        }
        return newickFormat;
    }
    public void writeTreeToFile(String path)
    {
        System.out.println(path);
        //if(path == null || path.matches())
        try
        {
            BufferedWriter br = new BufferedWriter(new FileWriter(path));
            br.write(this.newickFormat, 0,this.newickFormat.length());
            br.close();
            System.out.println("Newick format of the suffix tree is written to file:\n" + path);
        }
        catch (FileNotFoundException e1)
        {
            System.out.println("File not found.\n"+ e1);
        }
        catch(IOException e2)
        {
            System.out.println("IO exception.\n" + e2);
        }
    }
    public static void main(String args[])
    {
        SuffixTree t = new SuffixTree();
        System.out.println(t.getNewickFormat(t.build(":sdfkjaiosdjcsdufhzdi;fvaidfhasidfhadiofh")));
        t.writeTreeToFile("C:\\Harsh\\treeOut.tre");
    }
}
