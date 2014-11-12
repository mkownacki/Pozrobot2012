package pl.pozrobot.snake;

public class GraphTraverse 
{
	public static void main (String[] args) 
	{
		Graph g = new Graph();
		g.removeNode(13);
		g.removeNode(16);
		g.removeNode(27);
		g.getLongestPath(15);
		g.getObstacles();
		g.printObstacles();
		System.out.print("|");
		g.printLongestPath();
	}
}

class Graph 
{
	private int ROWS = 6;
	private int COLUMNS = 6;
	private int DESIRED_LENGTH = 33;
	private int NODES = COLUMNS*ROWS;
	private int visited[] = new int[NODES];
	private int currentPath[] = new int[NODES];
	private int currentPathLength = 0;
	private int longestPath[] = new int[NODES];
	private int longestPathLength = 0;
	private int graph[][] = new int[NODES][NODES];
	private int[] obstacles = new int[3];
		
	Graph () 
	{
		for (int i=0; i < visited.length; i++) 
		{
			visited[i] = 0;
		}
		
		createGraph();
	}
	
	void removeNode (int node) 
	{
		for (int i = 0; i < NODES; i++) 
		{
			graph[node][i] = 0;
			graph[i][node] = 0;
		}
	}
	
	void printLongestPath() 
	{
		if (longestPathLength > 0) 
		{
				System.out.print((int)(longestPath[0]+1)); 
				for (int i = 1; i < longestPathLength; i++) 
				{ 
					System.out.print("," + (int)(longestPath[i]+1)); 
				}
		}
	}
	
	void getObstacles () 
	{
		boolean obstacle = true;
		int currentObstacle = 0;
		for (int i = 0; i < NODES; i++) 
		{
			for (int j = 0; j < NODES; j++) 
			{
				if (graph[i][j] == 1) 
				{ 
					obstacle = false; 
				}
			}
			
			if (obstacle) { 
				obstacles[currentObstacle] = i; 
				currentObstacle += 1; 
			} else { 
				obstacle = true; 
			}
		}
	}
	
	void printObstacles () 
	{
		for (int i = 0; i < obstacles.length-1; i++) 
		{
			System.out.print(obstacles[i]+1 + ",");
		}
		
		System.out.print(obstacles[obstacles.length-1]+1);
	}
	
	void printGraph () 
	{
		boolean obstacle = true;
		int currentColumn = 0;
		
		for (int i = 0; i < graph.length; i++) 
		{
			for (int j = 0; j < NODES; j++) 
			{
				if (graph[i][j] == 1) 
				{ 
					obstacle = false; 
				}
			}
			
			if (obstacle) { 
				System.out.print("X"); 
			} else { 
				System.out.print("-"); 
			}
			
			obstacle = true;
			currentColumn += 1;
			if (currentColumn == COLUMNS) { currentColumn = 0; System.out.print("\n"); }
		}
	}
	
	int getNodeX (int node) 
	{
		return node % COLUMNS;
	}
	
	int getNodeY (int node) 
	{
		return node / COLUMNS;
	}
	
	void createGraph() 
	{		
		for (int i = 0; i < NODES; i++) 
		{ 
			if ((getNodeX(i) % COLUMNS < COLUMNS-1) && (i+1 < NODES)) 
			{ 
				graph[i][i+1] = 1; graph[i+1][i] = 1; 
			}
			
			if ((getNodeX(i) % COLUMNS > 0) && (i-1 >= 0)) 
			{ 
				graph[i][i-1] = 1; graph[i-1][i] = 1; 
			}
			
			if ((getNodeY(i) / COLUMNS < COLUMNS-1) && (i+COLUMNS < NODES)) 
			{ 
				graph[i][i+COLUMNS] = 1; graph[i+COLUMNS][i] = 1; 
			} 
			
			if ((getNodeY(i) / COLUMNS > 0) && (i-COLUMNS >= 0)) 
			{ 
				graph[i][i-COLUMNS] = 1; graph[i-COLUMNS][i] = 1; 
			}
		}
	}
	
	void printGraphArray() 
	{
		for (int i = 0; i < NODES; i++) 
		{ 
			for (int j = 0; j < NODES; j++) 
			{ 
				System.out.print(graph[i][j]); 
			}
			
		System.out.print("\n"); 
		}
	}
	
	void getLongestPath (int node) 
	{
		boolean endPath = true;
		
		visited[node] = 1;
		currentPath[currentPathLength] = node;
		currentPathLength += 1;
		
		for (int i = 0; i < NODES; i++) 
		{
			if ((graph[node][i] == 1) && (visited[i] == 0)) 
			{ 
				endPath = false;
				if (longestPathLength >= DESIRED_LENGTH) return;
				getLongestPath(i); 
			}
		}
	
		if (endPath == true) 
		{
			if (currentPathLength > longestPathLength) 
			{
				for (int i = 0; i < currentPathLength; i++) 
				{
					longestPath[i] = currentPath[i];
				}
				
				longestPathLength = currentPathLength;
			}
		}
		
		visited[node] = 0;
		currentPathLength -= 1;
	}
}