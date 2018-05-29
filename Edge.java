/**
 * Class for constructing edges.
 * @author Bruckmann C., Wagner R.
 *
 */
class Edge
	{
		int start;
		int end;
		double weight;
		// normal constructor for unweighted edges
		public Edge(int start, int end) 
		{
			this.start = start;
			this.end = end;
			weight = 1;
		
		}
		// overload constructor for weighted edges
		public Edge(int start, int end, double weight) 
		{
			this.start = start;
			this.end = end;
			
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