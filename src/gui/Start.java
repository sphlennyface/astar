package gui;




import java.util.LinkedList;
import java.util.Comparator;

import java.util.ArrayList;
import java.util.Collections;



public class Start {

	private boolean visited[][];
	private ArrayList closed = new ArrayList();    
	private SortedList open = new SortedList();
	private Node[][] nodes;
	private Heuristic heuristic;
	private boolean allowDiagMovement;
	private int mapX, mapY;
	private int maxSearchDistance, maxDepth;

	//            public static void main(String[] args) {
		//                    Main main = new Main();        
		//                    Path m8 = new Path();
	//                    main.drawWorld2();
	//                    m8 = main.findPath(1,1,6,7);
	//                   
	//                    m8.drawWorld();
	//            }


	public void drawWorld2() {
		int hue[][] = new int[10][10];

		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				if (Interfejs.world[i][j] == 1)
					hue[i][j] = 1;
				else
					hue[i][j] = 0;

		int z = 0;
		boolean done = false;
		for (int i = 0; i < 11; i++) {
			System.out.printf(z + " ");
			z++;
			if (!done) {
				z--;
				done = true;
			}


		}
		System.out.printf("\n");
		for (int i = 0; i < 10; i++){
			for (int j = 0; j < 10; j++) {
				if (j == 0)
					System.out.printf(i + " ");
				System.out.printf(hue[j][i] + " ");
			}
			System.out.printf("\n");
		}
	}

	public void drawWorld() {
		//                    for (int i = 0; i < mapX; i++) {
		//                            for (int j = 0; j < mapY; j++) {
		//                            	if(Interfejs._tiles[i][j]
		//                            }
		//                            System.out.printf("\n");
		//                    }
	}

	public void createWorld() {
		//                    for (int i = 0; i < mapX; i++)
		//                            for (int j = 0; j < mapY; j++)
		//                            	Interfejs.world[i][j] = 1;
		//                   
		//                    for (int i = 1; i <= 8; i++)
		//                            for (int j = 1; j <= 8; j++)
		//                            	Interfejs.world[i][j] = 0;    
		//                   
		//                    int i = 6;
		//                    for (int j = 3; j<= 7; j++) {
		//                    	Interfejs.world [i][j] = 1;
		//                    }                   
		//Interfejs.update();

	}

	public class Node  {
		/** The x coordinate of the node */
		private int x;
		/** The y coordinate of the node */
		private int y;
		/** The path cost for this node */
		private float cost;
		/** The parent of this node, how we reached it in the search */
		private Node parent;
		/** The heuristic cost of this node */
		private float heuristic;
		/** The search depth of this node */
		private int depth;

		/**
		 * Create a new node
		 *
		 * @param x The x coordinate of the node
		 * @param y The y coordinate of the node
		 */
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}

		/**
		 * Set the parent of this node
		 *
		 * @param parent The parent node which lead us to this node
		 * @return The depth we have no reached in searching
		 */
		public int setParent(Node parent) {
			depth = parent.depth + 1;
			this.parent = parent;

			return depth;
		}

		/**
		 * @see Comparable#compareTo(Object)
		 */


	}





	public Start() {
		mapX = 10;
		mapY = 10;
		heuristic = new Heuristic();
		visited = new boolean[mapX][mapY];
		//clearVisited();
		nodes = new Node[mapX][mapY];
		maxSearchDistance = 1000;
		allowDiagMovement = true;
		for (int x=0;x<mapX;x++) {
			for (int y=0;y<mapY;y++) {
				nodes[x][y] = new Node(x,y);
			}
		}

		//createWorld();
		//drawWorld();

	}

	public void clearVisited() {
		for (int i = 0; i < mapX; i++)
			for (int j = 0; j < mapY; j++)
				Interfejs.world[i][j] = 0;
	}


	public float getCost(int x, int y, int tx, int ty) {           
		float dx = tx - x;
		float dy = ty - y;

		float result = (float) (Math.sqrt((dx*dx)+(dy*dy)));

		return result;
	}


	public float getTileCost(int sx, int sy, int tx, int ty) {
		return 1;
	}

	public void pathFinderVisited(int x, int y) {
		visited[x][y] = true;
	}

	public boolean hasVisited(int x, int y) {
		return visited[x][y];
	}

	public Node grabFirst() {
		Node result = (Node) open.first();

		for (int i = 0; i < open.size(); i++)
		{
			Node newOne = (Node) open.get(i);
			if (newOne.cost < result.cost)
				result = newOne;                               
		}

		System.out.printf("CUSZ, WYGLADA NA TO ZE X " + result.x + " Y " + result.y + " KOSZTOWO: " + result.cost + " TO NAJFAJNIEJSZY Z ZIOMECZKÓW\nPOZOSTALI KOLEDZY: " );
		for (int i = 0; i < open.size(); i++) {
			Node herpes = (Node) open.get(i);
			if (herpes != result)
				System.out.printf("X" + herpes.x + "Y" + herpes.y + "(" + herpes.cost + ") " );
		}

		System.out.printf("\n");
		return result;


	}


	public Path findPath(int sx, int sy, int tx, int ty) throws InterruptedException {

		System.out.printf("MAM DOJSC Z JEBANEGO X " + sx + " Y " + sy + " DO X " + tx + " Y " + ty + "\n");
		open.clear();
		closed.clear();
		nodes[sx][sy].depth = 0;
		nodes[sx][sy].parent = null;
		open.add(nodes[sx][sy]);

		while (open.size() != 0) {
			Node current = grabFirst();
//			Interfejs.world[current.y][current.x] = 4;

			removeFromOpen(current);
			addToClosed(current);

			System.out.printf("ZAJMUJE SIE JEBANYM TILEM X " + current.x + " Y " + current.y + "\n");
			// PATH FOUND
			if (inClosedList(nodes[tx][ty])) {
				break;
			}

			for (int i = -1; i <= 1; i++)
				for (int j = -1; j<= 1; j++) {



					int newX, newY;
					newX = current.x + i;
					newY = current.y + j;  

					if ((newX == 0) && (newY == 0)) {
						continue;
					}

					if (!isValidLocation(newX,newY) || inClosedList(nodes[newX][newY]))
						continue;



					if (!inOpenList(nodes[newX][newY])) {
						addToOpen(nodes[newX][newY]);
						nodes[newX][newY].parent = current;
						/* G SCORE */           nodes[newX][newY].depth = nodes[newX][newY].parent.depth + 1;
						/* H SCORE */           nodes[newX][newY].heuristic = heuristic.getCost(newX, newY, tx, ty);
						/* F SCORE */           nodes[newX][newY].cost = nodes[newX][newY].depth + nodes[newX][newY].heuristic;


					}

					if (inOpenList(nodes[newX][newY])) {
						if (nodes[newX][newY].depth < current.depth) {
							System.out.printf("O KURWA! JEST KANDYDAT! JEGO X TO " + current.x + " A Y " + current.y + "\n");
							nodes[newX][newY].parent = current;
							/* G SCORE */                   nodes[newX][newY].depth = nodes[newX][newY].parent.depth + 1;
							/* H SCORE */                   nodes[newX][newY].heuristic = heuristic.getCost(newX, newY, tx, ty);
							/* F SCORE */                   nodes[newX][newY].cost = nodes[newX][newY].depth + nodes[newX][newY].heuristic;
							//open.sort();
						}



					}



					//

				}                      
		}

		if (nodes[tx][ty].parent == null) {
			System.out.printf("NIEZ£A ZE MNIE KURWA, NIE ODNALAZLEM SCIEZKI xD"); 

			return null;
		}
		Path path = new Path();
		Node target = nodes[tx][ty];
		while (target != nodes[sx][sy]) {
			path.prependStep(target.x, target.y);
			target = target.parent;
		}
		path.prependStep(sx,sy);
		Interfejs.update();
		Thread.sleep(100);

		return path;




	}



	public Node getFirstInOpen() {
		return (Node) open.first();
	}


	public void addToOpen(Node node) {
		open.add(node);
	}

	public boolean inOpenList(Node node) {
		return open.contains(node);
	}


	public void removeFromOpen(Node node) {
		open.remove(node);
	}


	public void addToClosed(Node node) {
		closed.add(node);
	}


	public boolean inClosedList(Node node) {
		return closed.contains(node);
	}


	public void removeFromClosed(Node node) {
		closed.remove(node);
	}



	/*
            public Path findPath(int sx, int sy, int tx, int ty) {
                    nodes[sx][sy].cost = 0;
                    nodes[sx][sy].depth = 0;
                    closed.clear();
                    open.clear();
                    open.add(nodes[sx][sy]);

                    nodes[tx][ty].parent = null;

                    int maxDepth = 0;
                    while ((maxDepth < maxSearchDistance) && (open.size() != 0)) {
                            // pull out the first node in our open list, this is determined to

                            // be the most likely to be the next step based on our heuristic

                            Node current = getFirstInOpen();
                            if (current == nodes[tx][ty]) {
                                    break;
                            }

                            removeFromOpen(current);
                            addToClosed(current);

                            // search through all the neighbours of the current node evaluating

                            // them as next steps

                            for (int x=-1;x<2;x++) {
                                    for (int y=-1;y<2;y++) {
                                            // not a neighbour, its the current tile

                                            if ((x == 0) && (y == 0)) {
                                                    continue;
                                            }

                                            // if we're not allowing diaganol movement then only

                                            // one of x or y can be set

                                            if (!allowDiagMovement) {
                                                    if ((x != 0) && (y != 0)) {
                                                            continue;
                                                    }
                                            }

                                            // determine the location of the neighbour and evaluate it

                                            int xp = x + current.x;
                                            int yp = y + current.y;

                                            if (isValidLocation(sx,sy,xp,yp)) {
                                                    // the cost to get to this node is cost the current plus the movement

                                                    // cost to reach this node. Note that the heursitic value is only used

                                                    // in the sorted open list

                                                    float nextStepCost = current.cost + getTileCost(current.x, current.y, xp, yp);
                                                    Node neighbour = nodes[xp][yp];
                                                    pathFinderVisited(xp, yp);

                                                    // if the new cost we've determined for this node is lower than

                                                    // it has been previously makes sure the node hasn'e've
                                                    // determined that there might have been a better path to get to

                                                    // this node so it needs to be re-evaluated

                                                    if (nextStepCost < neighbour.cost) {
                                                            if (inOpenList(neighbour)) {
                                                                    removeFromOpen(neighbour);
                                                            }
                                                            if (inClosedList(neighbour)) {
                                                                    removeFromClosed(neighbour);
                                                            }
                                                    }

                                                    // if the node hasn't already been processed and discarded then

                                                    // reset it's cost to our current cost and add it as a next possible

                                                    // step (i.e. to the open list)

                                                    if (!inOpenList(neighbour) && !(inClosedList(neighbour))) {
                                                            neighbour.cost = nextStepCost;
                                                            neighbour.heuristic = heuristic.getCost(xp, yp, tx, ty);
                                                            System.out.printf(xp + " " + yp + "\n");
                                                            maxDepth = Math.max(maxDepth, neighbour.setParent(current));
                                                            addToOpen(neighbour);
                                                    }
                                            }
                                    }
                            }      

                    }

                            if (nodes[tx][ty].parent == null) {
                                    return null;
                            }


                            Path path = new Path();
                            Node target = nodes[tx][ty];
                            while (target != nodes[sx][sy]) {
                                    path.prependStep(target.x, target.y);
                                    target = target.parent;
                            }
                            path.prependStep(sx,sy);

                            // thats it, we have our path

                            return path;


            }*/

	protected boolean isValidLocation(int x, int y) {
		if (x < 0 || y < 0 || x > mapX || y > mapY) {
			System.out.printf("PRZEKROCZENIE MAPKI M8 \n" );
			return false;
		}
		else if (Interfejs.world[x][y] == 1) {
			System.out.printf("PO X " + x + " Y " + y + " CHODZIC NI CHUJA :(\n" );
			return false;
		}
		System.out.printf("PRAWILNIE! PRZEJDZIEMY SIE PO X " + x + " Y " + y + "!\n" );
		return true;
	}




	private class Checker implements Comparator<Object> {
		@Override
		public int compare(Object o1, Object o2) {
			Node o1_ = (Node) o1;
			Node o2_ = (Node) o2;

			float co1 = o1_.cost;
			float co2 = o2_.cost;

			if (co1 < co2)
				return -1;
			else if (co1 > co2)
				return 1;
			else
				return 0;


		}

	}



	private class SortedList {
		private ArrayList list = new ArrayList();

		public Object first() {
			return list.get(0);
		}

		public void clear() {
			list.clear();
		}

		public Object get(int index) {
			return list.get(index);
		}

		public void add(Object o) {
			list.add(o);
			Collections.sort(list, new Checker());
		}

		public void sort() {
			Collections.sort(list, new Checker());
		}

		public void remove(Object o) {
			list.remove(o);
		}

		public int size() {
			return list.size();
		}

		public boolean contains(Object o) {
			return list.contains(o);
		}
	}





}

