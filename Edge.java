/**
 * Class for constructing edges.
 * @author Bruckmann C., Wagner R.
 *
 */
class Edge 
	{
		Vertex start;
		Vertex end;
		double weight;
		public Edge(Vertex start, Vertex end) 
		{

		}
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