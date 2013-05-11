package clustering.hierarchial;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import clustering.model.Cluster;

/*
 * 用于封装层次聚类的基础类
 * 聚类中的基本数据结构：树状图
 * 可以将其视为一个有序的三元组：【d，k，{。。。}】，
 * d表示相似度阀值，k表示聚类的数量，第三个元素表示聚类的集合
 */
public class Dendrogram {
	/*
	 * 层次聚类
	 */
	private Map<Integer, ClusterSet> entryMap;
	private Map<Integer, String> levelLabels;
	private Integer nextLevel;
	private String levelLabelName;
	
	public Dendrogram(String levelLabelName) {
		entryMap = new LinkedHashMap<Integer, ClusterSet>();
		levelLabels = new LinkedHashMap<Integer, String>();
		nextLevel = 1;
		this.levelLabelName = levelLabelName;
	}
	
	public int addLevel(String label, Cluster cluster) {
		List<Cluster> values = new ArrayList<Cluster>();
		values.add(cluster);
		return addLevel(label, values);
	}

	/*
	 * 利用给定的聚集的拷贝创建一个新的树状图的层次
	 */
	public int addLevel(String label, Collection<Cluster> clusters) {
		ClusterSet clusterSet = new ClusterSet();
		for(Cluster c : clusters) {
			clusterSet.add(c.copy());		//在添加之前先复制聚类--随着时间的推移，聚类中的元素可能会发生变化，但是对于树状图，我们希望能保持当前的状态
		}
		int level = nextLevel;
		entryMap.put(level, clusterSet);
		levelLabels.put(level, label);
		nextLevel++;
		return level;
	}
	
	/*
	 * 替换制定层次的聚集，如果该层次不存在，它将被创建。
	 */
	public void setLevel(int level, String label, Collection<Cluster> clusters) {
		ClusterSet clusterSet = new ClusterSet();
		for(Cluster c : clusters) {
			clusterSet.add(c.copy());
		}
		System.out.println("设置聚集的层次为：" + level);
		entryMap.put(level, clusterSet);
		levelLabels.put(level, label);
		if(level >= nextLevel) {
			nextLevel = level + 1;
		}
	}

	public void printAll() {
		for(Map.Entry<Integer, ClusterSet> e : entryMap.entrySet()) {
			Integer level = e.getKey();
			print(level);
		}
	}

	public void print(int level) {
		String label = levelLabels.get(level);
		ClusterSet clusters = entryMap.get(level);
		System.out.println("Clusters for : level=" + level + ", " +
				levelLabelName + "=" + label);
		for(Cluster c : clusters.getAllClusters()) {
			if(c.getElements().size() >= 1) {
				//System.out.println("----------------------------------");
				System.out.print(c.getElementsAsString());
				System.out.println(" ,");
				//System.out.println("\n----------------------------------");
			}
		}
	}
	
	
	
}
