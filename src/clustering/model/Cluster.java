package clustering.model;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/*
 * 数据点的集合
 */
public class Cluster {
	private String label;
	private Set<DataPoint> elements;
	
	public Cluster(){
		init("");
	}
	public Cluster(String label){
		init(label);
	}
	public Cluster(String label, Collection<DataPoint> elements){
		init(label);
		for(DataPoint e : elements) {
			add(e);
		}
	}
	public Cluster(Cluster c1, Cluster c2) {		//创建一个包含提供的聚集的所有的元素的新聚集
		init("");
		add(c1);
		add(c2);
	}
	public Cluster(Collection<DataPoint> elements) {
		init("");
		for(DataPoint e : elements) {
			add(e);
		}
	}
	public Cluster(DataPoint element) {
		init("");
		add(element);
	}
	
	public String getLabel() {
		return label;
	}
	
	public int size() {
		return elements.size();
	}

	/*
	 * 通过添加新的元素修改已经存在的聚集
	 */
	public void add(DataPoint e) {	
		elements.add(e);
	}
	/*
	 * 将参数聚集中的所有元素添加到当前聚集中
	 */
	public void add(Cluster c) {
		for(DataPoint e : c.getElements()) {
			elements.add(e);
		}
	}
	
	public boolean contains(Cluster c) {
		boolean result = true;
		for(DataPoint e : c.getElements()) {
			if(!contains(e)) {
				result = false;
				break;
			}
		}
		return result;
	}
	
	public boolean contains(DataPoint e) {
		return elements.contains(e);
	}
		
	public Set<DataPoint> getElements() {
		return new LinkedHashSet<DataPoint>(elements);
	}
	
	public int getDimensionCount() {
		if(elements == null || elements.isEmpty()) {
			return 0;
		}
		return elements.iterator().next().getAttributeCount();
	}
	
	public Cluster copy() {
		Cluster copy = new Cluster();
		for(DataPoint e : this.getElements()) {
			copy.add(e);
		}
		return copy;
	}
	
	
	private void init(String label) {
		this.label = label;
		elements = new LinkedHashSet<DataPoint>();
	}
	
	public String getElementsAsString() {
		StringBuffer buf = new StringBuffer();
		for(DataPoint e : elements) {
			if(buf.length() > 0) {
				buf.append(", ");
			}
			buf.append(e.getLabel());
		}
		return "{" + buf.toString() + "}";
	}
	
	@Override
	public String toString() {
		return getElementsAsString();
	}
	@Override
	public boolean equals(Object obj) {
		if(this == obj) 
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		final Cluster other = (Cluster) obj;
		if(elements == null) {
			if(other.elements != null)
				return false;
		} else if(!elements.equals(other.elements)) {
			return false;
		}
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elements == null)? 0 : elements.hashCode());
		return result;
	}
	

	
	
}
