package test;

import clustering.hierarchial.Dendrogram;
import clustering.hierarchial.SingleLinkAlgorithm;
import clustering.model.DataPoint;
import data.SourceDate;
import data.SourceDateSet;
/*
 * ≤‚ ‘µ•¡¥Ω”À„∑®
 */
public class SingleLinkAlgorithmTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SourceDateSet ds = SourceDate.createDataset();
		DataPoint[] dps = ds.getData();
		double[][] adjMatrix = ds.getAdjacencyMatrix();
		SingleLinkAlgorithm sla = new SingleLinkAlgorithm(dps, adjMatrix);
		Dendrogram dendroSLA = sla.cluster();
		dendroSLA.print(4);
	}

}
