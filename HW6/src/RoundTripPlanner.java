
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoundTripPlanner {
	// user inputs for the source and destination
	private final int startCityIndex;
	private final int endCityIndex;

	// Graph created using the following vertices and edges
	private WeightedGraph<String> flightNetwork;

	// array of vertices
	private final String[] cities;
	// array of weighted edges [source][dest][weight]
	private int[][] connections;

	// forward and return route cities lists and cost of trip
	private List<String> forwardRoute;
	private double forwardRouteCost;
	private List<String> returnRoute;
	private double returnRouteCost;

	/*
	 * Constructor:
	 * - Assigns class variables
	 * - Invokes generateRoundTrip() method
	 */
	public RoundTripPlanner(String[] cities, int[][] connections, int startCityIndex, int endCityIndex) {
		// TO DO
		this.cities = cities;

		this.connections = connections;

		this.startCityIndex = startCityIndex;

		this.endCityIndex = endCityIndex;

		generateRoundTrip();

	}


	/*
	 * Round trip generator:
	 * - Creates flight network graph
	 * - Updates forward trip path variable and forward trip cost
	 * - Performs necessary actions for return trip planning
	 * - Updates return trip path variable and return trip cost
	 */
	public void generateRoundTrip()  {


            // graph created
		   flightNetwork = new WeightedGraph<>(cities, connections);


		   // forward trip
		forwardRoute  = flightNetwork.getShortestPath(startCityIndex).getPath(endCityIndex);
		forwardRouteCost = flightNetwork.getShortestPath(startCityIndex).getCost(endCityIndex);

		//getting the edges of the cites who are connected to the route
		int[] forwardPath = new int[this.forwardRoute.size()];
		for (int i = 0; i < forwardRoute.size(); i++) {
			for (int j = 0; j < cities.length; j++) {
				if (cities[j] == forwardRoute.get(i)) {
					forwardPath[i] = j;
					break;
				}
			}
		}
				//set edge weight to Max_Value to indicate to take another path
		for (int i = forwardPath.length - 1; i > 0; i--) {
			for (int j = 0; j < connections.length; j++) {
				if (connections[j][0] == forwardPath[i] && connections[j][1] == forwardPath[i - 1]) {
					connections[j][2] = Integer.MAX_VALUE;
				}
			}
		}
			//graph updated
		flightNetwork = new WeightedGraph<>(cities, connections);

		  // return trip
		returnRoute = flightNetwork.getShortestPath(endCityIndex).getPath(startCityIndex);
		returnRouteCost = flightNetwork.getShortestPath(endCityIndex).getCost(startCityIndex);


	}


	/*
	 * Trip viewer:
	 * - prints forward trip in the format:
	 * "Forward trip from A to B: A –> P –> Q –> R –> B"
	 * - prints return trip in the same format:
	 * "Return trip from B to A: B –> S –> T –> U –> A"
	 * - prints the costs for the forward trip, return trip, and total trip in the format:
	 *  "Forward route cost: 200.0"
	 *  "Return route cost: 300.0"
	 *  "Total trip cost: 500.0"
	 */
	public void printRoundTrip() {


		System.out.print("Forward trip from "+flightNetwork.getVertex(startCityIndex)+" to "+flightNetwork.getVertex(endCityIndex)+": ");

		for(int i = 0; i < getForwardRoute().size(); i++) {
			System.out.print(getForwardRoute().get(i));
			if( i != getForwardRoute().size() - 1){
				System.out.print(" -> ");
			} else if (i == getForwardRoute().size() -1) {
				System.out.println();
			}
		}

		System.out.print("Return trip from "+flightNetwork.getVertex(endCityIndex)+" to "+flightNetwork.getVertex(startCityIndex)+": ");

		for(int i = 0; i < getReturnRoute().size(); i++) {
			System.out.print(getReturnRoute().get(i));
			if( i != getReturnRoute().size() - 1){
				System.out.print(" -> ");
			} else if (i == getReturnRoute().size() -1) {
				System.out.println();
			}
		}


		System.out.println("Forward route cost: "+getForwardRouteCost());
		System.out.println("Return route cost: "+getReturnRouteCost());
		System.out.println("Total trip cost: "+(getForwardRouteCost()+getReturnRouteCost()));

		// TO DO
	}

	// Returns the forwardRoute class variable
	public List<String> getForwardRoute() {
		return forwardRoute;
	}

	// Returns the returnRoute class variable
	public List<String> getReturnRoute() {
		return returnRoute;
	}

	// Returns the forwardRouteCost class variable
	public double getForwardRouteCost() {
		return forwardRouteCost;
	}

	// Returns the returnRouteCost class variable
	public double getReturnRouteCost() {
		return returnRouteCost;
	}



}
