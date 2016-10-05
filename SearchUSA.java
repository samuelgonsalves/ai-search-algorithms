import java.util.*;



import java.io.*;

public class SearchUSA
{
	static Graph g = new Graph();
	
	public static void main(String args[])
	{
		Scanner in = new Scanner(System.in);
		init();
		System.out.println("Enter the algorithm name (astar/greedy/uniform): ");
		String algorithm_name = in.next();
		System.out.println("Enter the source city name: ");
		String src = in.next();
		System.out.println("Enter the destination city name: ");
		String dest = in.next();
		
		if(algorithm_name.equals("astar"))
		{
			astar(src,dest);
		}
		else if(algorithm_name.equals("greedy"))
		{
			greedy(src,dest);
		}
		else if(algorithm_name.equals("uniform"))
		{
			uniform(src,dest);
		}
		else
		{
			System.out.println("Invalid search algorithm name");
		}	
		
		
	}


	public static void init()
	{
		
				
		String x;	
		String arr[];	
		
		try
		{				
			BufferedReader in1 = new BufferedReader(new FileReader("VertexInfo.txt"));
			BufferedReader in2 = new BufferedReader(new FileReader("EdgeInfo.txt"));
			
			while((x=in1.readLine())!=null)
			{
				arr=x.split(",");
				double a = Double.parseDouble(arr[1]);
				double b = Double.parseDouble(arr[2]);
				
				g.addVertex(arr[0],a,b);
			}
			
			while((x=in2.readLine())!=null)
			{
				arr=x.split(",");
				int weight = Integer.parseInt(arr[2]);
				g.addEdge(arr[0],arr[1],weight);
			}
		in1.close();
		in2.close();
			

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
	}
	
	public static void uniform(String start, String goal)
	{

		System.out.println("Algorithm: Uniform Cost Search\n");
		ArrayList<Vertex> closed_list = new ArrayList<Vertex>();
		
		Vertex start_vertex = g.vertices.get(start);
		Vertex goal_vertex = g.vertices.get(goal);
		
		Comparator<Path> c = new Path();
		PriorityQueue<Path> pq = new PriorityQueue<Path>(c);
		
		
		Path path = new Path(0);
		path.list.add(start_vertex);
		pq.add(path);
		
		while(!pq.isEmpty())
		{
			Path temp = pq.remove();
			Vertex v = temp.list.getLast();
			if(!closed_list.contains(v))
				closed_list.add(v);
			
			if(v.city_name.equals(goal_vertex.city_name))
			{
				System.out.print("Expanded nodes: \n");
				for(int a=0;a<closed_list.size();a++)
					System.out.print(closed_list.get(a).city_name+",");
					
				System.out.println("\n\nNumber of Expanded nodes: "+closed_list.size());
				temp.printPath();
				System.out.println("\nNumber of nodes in the solution path: "+temp.list.size());
				temp.printCost();
				
				break;
			}
	
			for(int i = 0;i < v.neighbourhood.size();i++)
			{
				Vertex neighbour = v.neighbourhood.get(i);
				if(!closed_list.contains(neighbour))
				{
					double cost = temp.cost + g.getEdgeWeight(neighbour.city_name, v.city_name);
					Path p = new Path(cost);
					
					if(temp.list.size() != 0)
						p.list.addAll(temp.getPath());
					
					if(!temp.list.contains(neighbour))
					{
						p.list.addLast(neighbour);
					}
					pq.add(p);	
				}
			}
			

			
		}
	}
	public static void astar(String start, String goal)
	{

		System.out.println("Algorithm: A*\n");
	
		ArrayList<Vertex> closed_list = new ArrayList<Vertex>();
		
		Vertex start_vertex = g.vertices.get(start);
		Vertex goal_vertex = g.vertices.get(goal);
		
		Comparator<Path> c = new Path();
		PriorityQueue<Path> pq = new PriorityQueue<Path>(c);

		Iterator<Map.Entry<String, Vertex>> x = g.vertices.entrySet().iterator();
		
		while(x.hasNext())
		{
			Map.Entry p= (Map.Entry) x.next();
			String x1=(String) p.getKey();
			Vertex t=g.vertices.get(x1);
			Vertex.heuristic(t, goal_vertex);
		}
		
		
		Path path = new Path(start_vertex.heuristic);
		path.list.add(start_vertex);
		pq.add(path);
		
		while(!pq.isEmpty())
		{
			Path temp = pq.remove();
			Vertex v = temp.list.getLast();
			if(!closed_list.contains(v))
				closed_list.add(v);
			
			if(v.city_name.equals(goal_vertex.city_name))
			{
				System.out.print("Expanded nodes: \n");
				for(int a=0;a<closed_list.size();a++)
					System.out.print(closed_list.get(a).city_name+",");
				System.out.println("\n\nNumber of Expanded nodes: "+closed_list.size());
				temp.printPath();
				System.out.println("\nNumber of nodes in the solution path: "+temp.list.size());
				temp.printCost_greedy();
				break;
			}
			for(int i = 0;i < v.neighbourhood.size();i++)
			{
				Vertex neighbour = v.neighbourhood.get(i);
				if(!closed_list.contains(neighbour))
				{
					double cost = temp.cost + g.getEdgeWeight(neighbour.city_name, v.city_name)+neighbour.heuristic;
					Path p = new Path(cost);
					
					if(temp.list.size() != 0)
						p.list.addAll(temp.getPath());
					
					if(!temp.list.contains(neighbour))
					{
						p.list.addLast(neighbour);
					}
					pq.add(p);	
				}
			}
			

			
		}
	}
	public static void greedy(String start, String goal)
	{
		
		System.out.println("Algorithm: Greedy Search\n");

		Vertex start_vertex = g.vertices.get(start);
		Vertex goal_vertex = g.vertices.get(goal);
		
		ArrayList<Vertex> closed_list = new ArrayList<Vertex>();
		
		
		Comparator<Path> c = new Path();
		PriorityQueue<Path> pq = new PriorityQueue<Path>(c);

		Iterator<Map.Entry<String, Vertex>> x = g.vertices.entrySet().iterator();
		
		while(x.hasNext())
		{
			Map.Entry p= (Map.Entry) x.next();
			String x1=(String) p.getKey();
			Vertex t=g.vertices.get(x1);
			Vertex.heuristic(t, goal_vertex);
		}
		
		Path path = new Path(start_vertex.heuristic);
		path.list.add(start_vertex);
		pq.add(path);
		
		while(!pq.isEmpty())
		{
			Path temp = pq.remove();
			Vertex v = temp.list.getLast();
		
		
			if(!closed_list.contains(v))		
				closed_list.add(v);
				
				if(v.city_name.equals(goal_vertex.city_name))
				{
					System.out.print("Expanded nodes: \n");
					for(int a=0;a<closed_list.size();a++)
						System.out.print(closed_list.get(a).city_name+",");
					System.out.println("\n\nNumber of Expanded nodes: "+closed_list.size());
					temp.printPath();
					System.out.println("\nNumber of nodes in the solution path: "+temp.list.size());
					temp.printCost_greedy();
					
					break;
				}
				int count =0;
				for(int i = 0;i < v.neighbourhood.size();i++)
				{
					Vertex neighbour = v.neighbourhood.get(i);
					if(!closed_list.contains(neighbour))
					{
						double cost = neighbour.heuristic;
						Path p = new Path(cost);
						
						if(temp.list.size() != 0)
							p.list.addAll(temp.getPath());
						
						if(!temp.list.contains(neighbour))
						{
							p.list.addLast(neighbour);
						}
						pq.add(p);	
					}
				}
			}
		}

}	
class Path implements Comparator<Path>
{
	LinkedList<Vertex> list;
	double cost;
	Path()
	{
		
	}
	Path(int cost)
	{
		list = new LinkedList<Vertex>();
		this.cost = cost;
	}
	Path(double cost)
	{
		list = new LinkedList<Vertex>();
		this.cost = cost;
	}
	
	
	public double getCost()
	{
		return cost;
	}
	public LinkedList<Vertex> getPath()
	{
		return list;
	}
	public void printPath()
	{
		System.out.print("\nNodes in solution path:\n");
		for(int i=0;i<list.size();i++)
			System.out.print(list.get(i).city_name + ",");
		System.out.println();
	}
	public void printCost()
	{
		System.out.println("\nDistance from "+list.getFirst().city_name +" to "+list.getLast().city_name+": "+(int)cost);
	}
	
	public void printCost_greedy()
	{
		int cost =0;
		for(int i=0;i<list.size()-1;i++)
		{
			int j=i+1;
			cost+=SearchUSA.g.getEdgeWeight(list.get(i).city_name, list.get(j).city_name);
		}
		System.out.println("\nDistance from "+list.getFirst().city_name +" to "+list.getLast().city_name+": "+cost+"\n");
	}
	@Override
	public int compare(Path o1, Path o2)
	{
		// TODO Auto-generated method stub
		if(o1.cost>o2.cost)
			return 1;
		else if(o1.cost<o2.cost)
			return -1;
		return 0;
	}
}
	


