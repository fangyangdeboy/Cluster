package data;

import java.util.Arrays;

import similiarity.Distance;
import clustering.model.DataPoint;

public class SourceDateSet {
	private DataPoint[] data;
	private Distance distance;
	private double[][] adjacencyMatrix;
	
	public SourceDateSet(DataPoint[] data, Distance distance) {
		this.data = data;
		this.distance = distance;
		this.adjacencyMatrix = calculateAdjacencyMatrix();
	}
	
	public DataPoint[] getData() {
		return data;
	}
	
	public double[][] getAdjacencyMatrix() {
		return adjacencyMatrix;
	}

	private double[][] calculateAdjacencyMatrix() {
        int n = data.length;
        double[][] adjMatrix = new double[n][n];
        
        DataPoint x = null;
        DataPoint y = null;
        
        for(int i = 0; i < n; i++) {
            x = data[i];
            for(int j = i + 1; j < n; j++) {
                y = data[j];
                adjMatrix[i][j] = distance.getDistance(
                        x.getNumericAttrValues(), y.getNumericAttrValues());
                adjMatrix[j][i] = adjMatrix[i][j];
            }
            adjMatrix[i][i] = 0.0;
        }
        
        return adjMatrix;
    }
    
    public void printDistanceMatrix() {
        for(int i = 0, n = adjacencyMatrix.length; i < n; i++) {
            System.out.println(Arrays.toString(adjacencyMatrix[i]));
        }
    }

}
