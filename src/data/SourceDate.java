package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.supercsv.io.CsvListReader;
import org.supercsv.prefs.CsvPreference;

import similiarity.Distance;
import similiarity.EuclideanDistance;
import clustering.hierarchial.Dendrogram;
import clustering.hierarchial.SingleLinkAlgorithm;
import clustering.model.Attribute;
import clustering.model.DataPoint;

public class SourceDate {
	private static String[] allAvaiableAttributeNames = {
		"Age",	"IncomeRange", "Education", "Skills", "Social", "isPaid"
	};
	private static String fileName1 = "C:/myData/clusteringSF.dat";

	public static SourceDateSet createDataset() {
		return createDataset(allAvaiableAttributeNames);
	}

	/*
	 * 根据给定的属性名，创建数据集合
	 */
	public static SourceDateSet createDataset(String[] attrNames) {
		validateAttrNames(attrNames, allAvaiableAttributeNames);	//检查属性名是否合法
		DataPoint[] allData = loadDataFromFile(fileName1, attrNames);
		Distance dist = new EuclideanDistance();
		SourceDateSet sDataset = new SourceDateSet(allData, dist);
		return sDataset;
	}

	private static DataPoint[] loadDataFromFile(String fileName, String[] attrNames) {
		List<DataPoint> allData = new ArrayList<DataPoint>();
        CsvListReader csvReader = null;
        try {
            csvReader = new CsvListReader(
                    new BufferedReader(new FileReader(fileName)), 
                    CsvPreference.EXCEL_PREFERENCE);

            // Load all available headers from CSV file
            String[] csvHeaders = csvReader.getCSVHeader(true);
            
            // Map attribute names to field IDs from CSV file using header names
            int[] attrFieldIndexes = new int[attrNames.length];
            for(int i = 0; i < attrFieldIndexes.length; i++) {
                String header = attrNames[i];
                int csvHeaderId = -1;
                for(int j = 0; j < csvHeaders.length; j++) {
                    if( header.equalsIgnoreCase(csvHeaders[j]) ) {
                        csvHeaderId = j;
                        break;
                    }
                }
                // If there is no header found it means we have wrong attribute
                // name or wrong file.
                if( csvHeaderId == -1 ) {
                    throw new IllegalStateException("Attribute name mismatch. " + 
                            "Failed to find attribute name: '" + header + 
                            "' among cvs file headers. All available headers: " + 
                            Arrays.toString(csvHeaders));
                }
                else {
                    attrFieldIndexes[i] = csvHeaderId;
                }
            }

            // Read file and include only selected attributes
            List<String> line = null;
            while((line = csvReader.read()) != null) {
                try {
                    String label = line.get(0);
                    Attribute[] attributes = new Attribute[attrNames.length];
                    for(int i = 0, n = attrNames.length; i < n; i++) {
                        int attrFieldIndex = attrFieldIndexes[i];
                        String value = line.get(attrFieldIndex);
                        attributes[i] = new Attribute(attrNames[i], Double.valueOf(value));
                    }
                    DataPoint dataPoint = new DataPoint(label, attributes);
                    allData.add(dataPoint);
                }
                catch(Exception e) {
                    throw new RuntimeException("Error while reading line: '" + line + "'", e);
                }
            }
            
        } 
        catch(IOException e) {
            throw new RuntimeException(
                    "Error while reading SF data from csv file: '" + fileName + "'. ", e);
        }
        finally {
          try {
              if( csvReader != null ) {
                  csvReader.close();
              }
          }
          catch(IOException e) {
              e.printStackTrace();
          }
        }
        
        System.out.println("From file: " + fileName);
        System.out.println("Using attribute names: " + Arrays.toString(attrNames));
        System.out.println("Loaded " + allData.size() + " data points.");
        
        return allData.toArray(new DataPoint[allData.size()]);
	}

	private static void validateAttrNames(String[] actualAttrNames,
			String[] validAttrNames) {
		List<String> validNames = Arrays.asList(validAttrNames);
		for(String actualAttrName : actualAttrNames) {
			if(!validNames.contains(actualAttrName)) {
				throw new IllegalArgumentException("无效的属性名：" + actualAttrName);
			}
		}
	}
	
	public static void main(String[] args) {

        // Creates dataset that uses all available attributes
        SourceDateSet ds = SourceDate.createDataset();
        
        // Creates dataset that uses only a subset of available attributes        
        //SFDataset ds = SFData.createDataset(new String[] {"IncomeRange", "Age"});
        //SFDataset ds = SFData.createDataset(new String[] {"Age"});
        
        ds.printDistanceMatrix();

        Dendrogram dnd = null;

// Uncomment one of these two run clustering
        
//        // Run Single Link Clustering
        SingleLinkAlgorithm sla = new SingleLinkAlgorithm(ds.getData(), ds.getAdjacencyMatrix());
        dnd = sla.cluster();
        dnd.printAll();

//        // Run MST Single Link Clustering
//        MSTSingleLinkAlgorithm msla = new MSTSingleLinkAlgorithm(ds.getData(), ds.getDistanceMatrix());
//        dnd = msla.cluster();
//        dnd.print();
        
//        // Run Average Link Clustering
//        AverageLinkAlgorithm ala = new AverageLinkAlgorithm(ds.getData(), ds.getDistanceMatrix());
//        dnd = ala.cluster();
//        dnd.print();

//        double T = 5.0;

        //NearestNeighborAlgorithm nna = new NearestNeighborAlgorithm(ds.getData(), ds.getAdjacencyMatrix(), 5.0);
        //nna.run();
    }

}
