package clustering.hierarchial;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import clustering.model.Cluster;

/*
 * ���ڷ�װ��ξ���Ļ�����
 * �����еĻ������ݽṹ����״ͼ
 * ���Խ�����Ϊһ���������Ԫ�飺��d��k��{������}����
 * d��ʾ���ƶȷ�ֵ��k��ʾ�����������������Ԫ�ر�ʾ����ļ���
 */
public class Dendrogram {
	/*
	 * ��ξ���
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
	 * ���ø����ľۼ��Ŀ�������һ���µ���״ͼ�Ĳ��
	 */
	public int addLevel(String label, Collection<Cluster> clusters) {
		ClusterSet clusterSet = new ClusterSet();
		for(Cluster c : clusters) {
			clusterSet.add(c.copy());		//�����֮ǰ�ȸ��ƾ���--����ʱ������ƣ������е�Ԫ�ؿ��ܻᷢ���仯�����Ƕ�����״ͼ������ϣ���ܱ��ֵ�ǰ��״̬
		}
		int level = nextLevel;
		entryMap.put(level, clusterSet);
		levelLabels.put(level, label);
		nextLevel++;
		return level;
	}
	
	/*
	 * �滻�ƶ���εľۼ�������ò�β����ڣ�������������
	 */
	public void setLevel(int level, String label, Collection<Cluster> clusters) {
		ClusterSet clusterSet = new ClusterSet();
		for(Cluster c : clusters) {
			clusterSet.add(c.copy());
		}
		System.out.println("���þۼ��Ĳ��Ϊ��" + level);
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
