package q1.extract_method.refactored;

import java.util.List;




public class A {
	String name;
   Node m1(List<Node> nodes, String p) {
	   for (Node node : nodes)
	   {
		   contains(p);
	   }
      return null;
   }

   Edge m2(List<Edge> edgeList, String p) {
      for(Edge edge: edgeList)
      {
    	  contains(p);
      }
      return null;
   }

  void contains(String p)
  {
	  System.out.println(name.contains(p));
  }
}

class Node {
   String name;
   public boolean contains(String p) {
      return name.contains(p);
   }
}

class Edge {
   String name;

   public boolean contains(String p) {
       return name.contains(p);
   }
}