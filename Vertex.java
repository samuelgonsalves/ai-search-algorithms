
import java.util.ArrayList;
import java.util.Comparator;

public class Vertex implements Comparable<Vertex>{
	String city_name;
	double lat,lon;
	double heuristic;
	ArrayList<Vertex> neighbourhood = new ArrayList<Vertex>();
	
	Vertex()
	{
		
	}
	public Vertex(String city_name, double lat,double lon)
	{
		this.city_name=city_name;
		this.lat = lat;
		this.lon = lon;
	}

	@Override
	public int compareTo(Vertex v)
	{
		// TODO Auto-generated method stub
		return this.city_name.compareTo(v.city_name);
	}

	public static void heuristic(Vertex v1, Vertex v2)
	{
		v1.heuristic=Math.sqrt( Math.pow((69.5*(v1.lat-v2.lat)), 2)+Math.pow(69.5*Math.cos(((v1.lat+v2.lat)/360*3.14)*(v1.lon-v2.lon)), 2));
	}
	
	/*
	 * 	@Override
	 * public int compare(Vertex v1, Vertex v2)
	{
		// TODO Auto-generated method stub
		if(( v1.g + v1.heuristic )>(v2.g+v2.heuristic))
			return 1;
		if((v1.g+v1.heuristic)<(v2.g+v2.heuristic))
			return -1;
		return 0;
	}
	*/
		
}
