package test;

import clustering.hierarchial.AverageLinkAlgorithm;
import clustering.hierarchial.Dendrogram;
import clustering.hierarchial.SingleLinkAlgorithm;
import clustering.model.DataPoint;
import data.SourceDate;
import data.SourceDateSet;

public class AverageLinkAlgorithmTest {
	public static void main(String[] args) {
		SourceDateSet ds = SourceDate.createDataset();
		DataPoint[] dps = ds.getData();
		double[][] adjMatrix = ds.getAdjacencyMatrix();
		AverageLinkAlgorithm ala = new AverageLinkAlgorithm(dps, adjMatrix);
		Dendrogram den = ala.cluster();
		den.print(4);
	}
}
