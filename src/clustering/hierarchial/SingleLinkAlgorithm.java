package clustering.hierarchial;

import java.util.ArrayList;
import java.util.List;

import clustering.model.Cluster;
import clustering.model.DataPoint;

/*
 * ���۲�ξ����㷨--�������㷨
 * �������㷨��Ŀ���Ǿ����ܶ���ҳ�ͼ�е���ͨ������
 * �����������֮����һ���ߣ����㷨�ͻὫ���ߺϲ�����˱���Ϊ������
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
		for(DataPoint e : elements) {		//�����е�Ԫ����Ϊ���ڵ�
			Cluster c = new Cluster(e);
			initialClusters.add(c);
		}
		dnd.addLevel(String.valueOf(d), initialClusters);
		d = 1.0;
		int k = initialClusters.size();
		//��ʼ������ֱ��ֻʣ��һ�������������ݵ�ľ���
		while(k>1) {
			int oldK = k;
			List<Cluster> clusters = buildClusters(d);	//���඼����buildClusters�����з�����
			k = clusters.size();
			if(oldK != k) {
				dnd.addLevel(String.valueOf(d), clusters);
			}
			d = d + 1;	//ÿ�ε���ʱ������ķ�ֵ������һ����λ
		}
		return dnd;
	}

	/*
	 * �������㷨��ʵ��
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
