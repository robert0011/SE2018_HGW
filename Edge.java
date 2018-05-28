/**
 * Class for constructing edges.
 * @author Bruckmann C., Wagner R.
 *
 */
class Edge
	{
		double weight;
		// normal constructor for unweighted edges
		public Edge(int start, int end) 
		{
			
		
		}
		// overload constructor for weighted edges
		public Edge(Vertex start, Vertex end, double weight) 
		{
			if(weight < 0)
			{
				this.weight = 0;
			}
			else
			{
				this.weight = weight;
			}
		}
	}