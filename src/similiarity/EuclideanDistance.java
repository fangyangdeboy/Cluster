package similiarity;

/*
 * ≈∑º∏¿Ôµ√æ‡¿Î
 */
public class EuclideanDistance implements Distance{
	public EuclideanDistance() {}

	@Override
	public double getDistance(double[] x, double[] y) {
		double sumXY = 0.0;
		for(int i=0, n=x.length; i<n; i++) {
			sumXY += Math.pow(x[i]-y[i], 2);
		}
		return Math.sqrt(sumXY);
	}

}
