package clustering.hierarchial;

import java.util.ArrayList;
import java.util.List;

import clustering.model.Cluster;
import clustering.model.DataPoint;

/*
 * 凝聚层次聚类算法--单链接算法
 * 单链接算法的目的是尽可能多的找出图中的联通分量。
 * 如果两个聚类之间有一条边，该算法就会将两者合并，因此被称为单链接
 */
public class SingleLinkAlgorithm {
	private DataPoint[] elements;
	private double[][] a;
	
	public SingleLinkAlgorithm(DataPoint[] elements, double[][] adjacencyMatrix) {
		this.elements = elements;
		this.a = adjacencyMatrix;
	}
	
	public Dendrogram cluster() {
		Dendrogram dnd = new Dendrogram("Distance");
		double d = 0;
		List<Cluster> initialClusters = new ArrayList<Cluster>();
		for(DataPoint e : elements) {		//将所有的元素作为单节点
			Cluster c = new Cluster(e);
			initialClusters.add(c);
		}
		dnd.addLevel(String.valueOf(d), initialClusters);
		d = 1.0;
		int k = initialClusters.size();
		//开始迭代，直到只剩下一个包含所有数据点的聚类
		while(k>1) {
			int oldK = k;
			List<Cluster> clusters = buildClusters(d);	//聚类都是在buildClusters方法中发生的
			k = clusters.size();
			if(oldK != k) {
				dnd.addLevel(String.valueOf(d), clusters);
			}
			d = d + 1;	//每次迭代时，距离的阀值都增加一个单位
		}
		return dnd;
	}

	/*
	 * 单链接算法的实现
	 */
	private List<Cluster> buildClusters(double distanceThreshold) {
		boolean[] usedElementFlags = new boolean[elements.length];
		List<Cluster> clusters = new ArrayList<Cluster>();
		for(int i=0, n=a.length; i<n; i++){
			List<DataPoint> clusterPoints = new ArrayList<DataPoint>();
			for(int j=i, k=a.length; j<k; j++) {
				if(a[i][j]<=distanceThreshold && usedElementFlags[j] == false) {
					clusterPoints.add(elements[j]);
					usedElementFlags[j] = true;
				}
			}
			if(clusterPoints.size()>0) {
				Cluster c = new Cluster(clusterPoints);
				clusters.add(c);
			}
		}
		return clusters;
	}
	
	
	
	public static void main(String[] args) {
        //Define data
        DataPoint[] elements = new DataPoint[5];
        elements[0] = new DataPoint("A", new double[] {});
        elements[1] = new DataPoint("B", new double[] {});
        elements[2] = new DataPoint("C", new double[] {});
        elements[3] = new DataPoint("D", new double[] {});
        elements[4] = new DataPoint("E", new double[] {});

        double[][] a = new double[][] {
            {0,1,2,2,3},
            {1,0,2,4,3},
            {2,2,0,1,5},
            {2,4,1,0,3},
            {3,3,5,3,0}
        };
        
        SingleLinkAlgorithm ca = new SingleLinkAlgorithm(elements, a);
        Dendrogram dnd = ca.cluster();
        dnd.printAll();
        //dnd.print(3);
    }
	
	
}
