
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Graph
{
	HashMap<String, Vertex> vertices = new HashMap<String, Vertex>();
	ArrayList<Edge> edges = new ArrayList<Edge>();
	
	public boolean addVertex(String city_name,double lat,double lon)
	{
		if(!vertices.containsKey(city_name))
		{
			Vertex v1 = new Vertex(city_name,lat,lon);
			vertices.put(city_name, v1);
			return true;
		}
		else
		{
			System.out.println("Vertex not added! Possible duplicate city name");
			return false;
		}
	}
	public void addEdge(String vertex1, String vertex2, Integer weight)
	{
		if(!vertices.containsKey(vertex1) || !vertices.containsKey(vertex2))
		{
			System.out.println("One or more vertices have not been created");
		}
		else if(!isEdge(vertex1,vertex2))
		{
			Vertex v1=vertices.get(vertex1);
			Vertex v2=vertices.get(vertex2);
			
			Edge e = new Edge(v1,v2,weight);
			edges.add(e);
			v1.neighbourhood.add(v2);//??
			v2.neighbourhood.add(v1);
			
			/*for(int i=0;i<v1.neighbourhood.size();i++)
				System.out.println(v1.neighbourhood.get(i).city_name);
			*/
			
			Collections.sort(v1.neighbourhood);
			Collections.sort(v2.neighbourhood);
		}
		else
		{
			System.out.println("Edge already exists");
		}
	}
	public boolean isEdge(String vertex1, String vertex2)
	{
		Vertex v1 = vertices.get(vertex1);
		Vertex v2 = vertices.get(vertex2);
		int flag=0;
		for(int i=0;i<edges.size();i++)
		{
			
			Edge e=edges.get(i);
			flag=0;
			if((e.v1.city_name.equals(v1.city_name) || e.v1.city_name.equals(v2.city_name)) &&(e.v2.city_name.equals(v1.city_name) || e.v2.city_name.equals(v2.city_name)))
			{
				flag=1;
			}
		}
		if(flag==1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public int getEdgeWeight(String vertex1, String vertex2)
	{
		Vertex v1 = vertices.get(vertex1);
		Vertex v2 = vertices.get(vertex2);
		int flag=0;
		for(int i=0;i<edges.size();i++)
		{
			
			Edge e=edges.get(i);
			flag=0;
			if((e.v1.city_name.equals(v1.city_name) || e.v1.city_name.equals(v2.city_name)) &&(e.v2.city_name.equals(v1.city_name) || e.v2.city_name.equals(v2.city_name)))
			{
				return e.weight;
			}
		}
		return -1;
		
	}
	
	public List<Vertex> getNeighbours(String vertex)
	{
		Vertex v = vertices.get(vertex);
		return v.neighbourhood;
	}
	public void printVertex(String city_name)
	{
		if(vertices.containsKey(city_name))
		{
			Vertex v1=vertices.get(city_name);
			if(v1 != null)
			{
				System.out.println("Vertex: "+ city_name);	
				System.out.println("Neighbourhood:");
				for(int i=0;i<v1.neighbourhood.size();i++)
					System.out.println("("+v1.neighbourhood.get(i).city_name+","+v1.neighbourhood.get(i).city_name+")");
			}
		}
		else
		{
			System.out.println("Vertex "+city_name+" does not exist");	
		}
	}
	public void printEdges()
	{
		System.out.println("Printing edges: ");
		for(int i=0;i<edges.size();i++)
		{
			Edge e=edges.get(i);
			System.out.println("Edge "+i+" :");
			System.out.println("Vertices: ("+e.v1.city_name+","+e.v2.city_name+") Weight: "+e.weight);
		}
	}

}
