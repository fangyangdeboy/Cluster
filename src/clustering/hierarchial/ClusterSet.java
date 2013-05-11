package clustering.hierarchial;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import clustering.model.Cluster;
import clustering.model.DataPoint;
/*
 * 聚集的集合
 */
public class ClusterSet {
	private Set<Cluster> allClusters = new HashSet<Cluster>();

	public boolean add(Cluster c) {
		return allClusters.add(c);
	}

	public List<Cluster> getAllClusters() {
		return new ArrayList<Cluster>(allClusters);
	}

	public int size() {
		return allClusters.size();
	}

	public Cluster findClusterByElement(DataPoint e) {
		Cluster cluster = null;
		for(Cluster c : allClusters) {
			if(c.contains(e)) {
				cluster = c;
				break;
			}
		}
		return cluster;
	}

	public boolean remove(Cluster c) {
		return allClusters.remove(c);
	}

}
