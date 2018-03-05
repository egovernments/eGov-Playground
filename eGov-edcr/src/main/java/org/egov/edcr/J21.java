
package org.egov.edcr;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.egov.edcr.math.Polygon;
import org.egov.edcr.math.Ray;
import org.egov.edcr.math.RayCast;
import org.kabeja.dxf.DXFDocument;
import org.kabeja.dxf.DXFLWPolyline;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.dxf.DXFVertex;
import org.kabeja.dxf.helpers.Point;
import org.kabeja.math.MathUtils;
import org.kabeja.parser.DXFParser;
import org.kabeja.parser.Parser;
import org.kabeja.parser.ParserBuilder;
import org.springframework.util.ResourceUtils;

public class J21 {
	private static final String VERT_CLEAR_OHE = "VERT_CLEAR_OHE";
	private static final String REAR_YARD = "REAR_YARD";
	private static final String BUILDING_FOOT_PRINT = "BUILDING_FOOTPRINT";
	private static final String SIDE_YARD_2 = "SIDE_YARD_2";
	private static final String SIDE_YARD_1 = "SIDE_YARD_1";
	private static final String FRONT_YARD = "FRONT_YARD";
	private static final String NOTIFIED_ROADS = "NOTIFIED_ROADS";
	private static final String NON_NOTIFIED_ROAD = "NON_NOTIFIED_ROAD";
	private static final String HORIZ_CLEAR_OHE2 = "HORIZ_CLEAR_OHE";
	private static final String PLOT_BOUNDARY = "PLOT_BOUNDARY";
	private static final String VOLTAGE = "VOLTAGE";
	private static Integer FLOOR_COLOUR_CODE = 10;
	private static String LAYER_NAME_WASTE_DISPOSAL = "WASTE_DISPOSAL";
	private static String CRZ_ZONE = "CRZ_ZONE";
	private static final int DECIMALDIGITS = 10;
	private static HashMap<String, HashMap<String, Object>> reportOutput = new HashMap<String, HashMap<String, Object>>();
    private static HashMap<String, String> errors = new HashMap<String, String>();
    private static HashMap<String, String> generalInformation = new HashMap<String, String>();



	public static void main(String[] args) {

		Parser parser = ParserBuilder.createDefaultParser();
		HashMap<String, Object> wasteDisposal = new HashMap<String, Object>();
		 wasteDisposal.put("WASTEDISPOSALNOTDEFINED",
                "Waster disposal not defined.");
			HashMap<String, Object> noticedroad = new HashMap<String, Object>();
			noticedroad.put("NOTIFIEDROAD",
	                "Notified road not defined.");
         reportOutput.put("RULE26", wasteDisposal);
         
         if(reportOutput.containsKey("RULE26"))
         {
             reportOutput.get("RULE26").put("RULE26", noticedroad);
         }
        // reportOutput.put("RULE26", noticedroad);
         System.out.println(reportOutput);
		try {
			// File Path
			File file = ResourceUtils.getFile("/home/mani/Desktop/BPA/kozhi/Sample_17-03-03-18.dxf");
			String path = file.getPath();
			
			
			

			// Parse DXF File
			parser.parse(path, DXFParser.DEFAULT_ENCODING);

			// Extract DXF Data
			DXFDocument doc = parser.getDocument();
			
		
			// getHeightOfTheBuilding(doc);
			Util util = new Util();
			
			
			
			
			List<DXFLWPolyline> residentialUnit= new ArrayList<DXFLWPolyline>();
			   Map<String,BigDecimal> unitWiseDeduction= new HashMap<String,BigDecimal>();
			   List<DXFLWPolyline> residentialUnitDeduction= new ArrayList<DXFLWPolyline>();
			   List<DXFLWPolyline> parking= new ArrayList<DXFLWPolyline>();
			   boolean layerPresent=true;
			
			  
			   layerPresent=doc.containsDXFLayer( "RESI_UNIT");
			    
			   if(layerPresent) {
			    List<DXFLWPolyline> bldgext = Util.getPolyLinesByLayer(doc, "RESI_UNIT");
				
		        if (!bldgext.isEmpty())
		            for (DXFLWPolyline pline : bldgext){
		            	 
		            	
		            	residentialUnit.add(pline);
		            	System.out.println("RESI_UNIT area " + Util.getPolyLineArea(pline));
		            }
			   }
			   layerPresent=doc.containsDXFLayer( "RESI_UNIT_DEDUCT");
			   if(layerPresent) {
		        List<DXFLWPolyline> bldDeduct = Util.getPolyLinesByLayer(doc, "RESI_UNIT_DEDUCT");
		        if (!bldDeduct.isEmpty())
		            for (DXFLWPolyline pline : bldDeduct){
		            	residentialUnitDeduction.add(pline);
		              	System.out.println("RESI_UNIT_DEDUCT area " + Util.getPolyLineArea(pline));
		            }
			   }
			   
			    Ray RAY_CASTING = new Ray(
                       new org.egov.edcr.math.Point(-1.123456789, -1.987654321));
			   int i=0;
			   for (DXFLWPolyline resUnit :residentialUnit )
			   {
				   i++;
				   double[][] pointsOfPlot = util.pointsOfPolygon(resUnit);
				   Iterator vertexIterator = resUnit.getVertexIterator();
				   List<org.egov.edcr.math.Point> points=new ArrayList<>();
				   while(vertexIterator.hasNext())
	            	 {
	            		 DXFVertex next =(DXFVertex) vertexIterator.next();
	            		 org.egov.edcr.math.Point p=new org.egov.edcr.math.Point(next.getX(), next.getY());
	            		 points.add(p);
	            	 }
				   
				   Polygon polygon=new Polygon(points);
				   
				  // System.out.println("resunit points----"+pointsOfPlot);
				   BigDecimal deduction=BigDecimal.ZERO;
				   for (DXFLWPolyline residentialDeduct: residentialUnitDeduction) {
					    boolean contains=false;
				        Iterator buildingIterator =residentialDeduct.getVertexIterator();
				        while (buildingIterator.hasNext()) {
			            DXFVertex dxfVertex = (DXFVertex) buildingIterator.next();
			            Point point = dxfVertex.getPoint();
			            org.egov.edcr.math.Point point1=new org.egov.edcr.math.Point(point.getX(), point.getY());
			           if( RAY_CASTING.contains(point1, polygon))
			           {
			        	   contains=true;
			           }
			            
			         //   System.out.println(point.getX()+","+point.getY());
				         /*   if (RayCast.contains(pointsOfPlot, new double[]{point.getX(), point.getY()}) == true) {
				            	contains=true;
				            }*/
				        }
				        if(contains)
				        {
				        	System.out.println("current deduct"+deduction+"    :add deduct for rest unit "+i+"area added"+Util.getPolyLineArea(residentialDeduct));
				        	deduction=deduction.add(Util.getPolyLineArea(residentialDeduct));
				        }
				        
			        }
				   unitWiseDeduction.put("resUnit"+i, deduction);
				   
				   
				  
				   
			   }
			   System.out.println(" dedutction for each key ----  " + unitWiseDeduction);
			   
			   		
			   
			   
			   
			   layerPresent=doc.containsDXFLayer( "PARKING_SLOT");

			   if(layerPresent) {
		        List<DXFLWPolyline> bldparking = Util.getPolyLinesByLayer(doc, "PARKING_SLOT");
		        if (!bldparking.isEmpty())
		            for (DXFLWPolyline pline : bldparking){
		            	parking.add(pline);
		            	System.out.println("parking " + Util.getPolyLineArea(pline));
		            }
			   }
		        	System.out.println("residentialUnit" + residentialUnit.size());
		        	System.out.println("residentialUnitDeduction" + residentialUnitDeduction.size());
		        	System.out.println("parking" + parking.size());

	        
	        
	        
			
			
			
			// System.out.println("Total Floors " +
			// util.getFloorCountExcludingCeller(doc,FLOOR_COLOUR_CODE));

			// Rule 23 A
		/*	Map<String, String> planInfoProperties = util.getPlanInfoProperties(doc);
			System.out.println("\n####  Rule 23, 4 ####");
			for (int i = 1; i < 100; i++) {
			}
			if (planInfoProperties.get(CRZ_ZONE) != null) {
				if (planInfoProperties.get(CRZ_ZONE).equalsIgnoreCase("YES"))
					System.err.println("SITE MARKED UNDER CRZ ZONE. CHECK NOC DURING DOCUMENT SCRUTINY.");
				else
					System.err.println("SITE NOT MARKED UNDER CRZ ZONE.");

			} else {
				System.err.println("CRZ ZONE DETAILS NOT MENTIONED"); // TODO:
																		// CHECK
																		// IS IT
																		// MANDATORY.
			}

			validateVoltageLineFromOHEL(doc, util);

			for (int i = 1; i < 1500; i++) {
			}
			// rule 26

			List<DXFLWPolyline> nonNotifiedRoad = util.getPolyLinesByLayer(doc, NON_NOTIFIED_ROAD);
			List<DXFLWPolyline> notifiedRoad = util.getPolyLinesByLayer(doc, NOTIFIED_ROADS);

			DXFLine line = util.getSingleLineByLayer(doc, "Shortest Distance to road");
			System.out.println("\n####  Rule 26 ####");
			if (line != null)
				System.out.println("Shortest Distance to road : " + line.getLength());
			else
				System.err.println("Shortest Distance to road not defined. ");

			// line.getBounds().debug();
			if (nonNotifiedRoad.size() > 0 && line != null ) {
				if ( line.getLength() < 2) {
					System.err.println("Shortest Distance to road condition violated for non notified roads");
				} else {
					System.out.println("Shortest Distance to road condition is accepted for non notified roads.");
				}
			}

			if (line != null && notifiedRoad.size() > 0) {
				if (line.getLength() < 3) {
					System.err.println("Shortest Distance to road condition violated for notified roads");
				} else {
					System.out.println("Shortest Distance to road condition is accepted for notified roads.");
				}
			}

			// rule 26a
			List<DXFLWPolyline> wasterDisposalPolyLines = util.getPolyLinesByLayer(doc, LAYER_NAME_WASTE_DISPOSAL);
			System.out.println("\n####  Rule 26 A ####");
			if (wasterDisposalPolyLines.size() > 0) // Mean, they defined waster
													// disposal.
			{
				System.out.println("Waste disposal difined in the diagram.");
			} else {
				System.err.println("Waste disposal not defined.Application not accepted"); // TODO:
																							// CHECK
																							// IS
																							// IT
																							// MANDATORY.
			}

			DXFLWPolyline rearYard = null;
			// Rule 60
			System.out.println("\n####  Rule 60 ####");

			List<DXFLWPolyline> plotBoundary = util.getPolyLinesByLayer(doc, PLOT_BOUNDARY);
			if (plotBoundary.size() == 1) {

				rearYard = plotBoundary.get(0);

				System.out.println("Total Plot Area in Mts " + util.getPolyLineArea(rearYard));
			}
			System.out.println("As per Plan info Plot Area is : " + planInfoProperties.get("PLOT_AREA"));

			if (planInfoProperties.get("PLOT_AREA") != null && util.getPolyLineArea(rearYard) != null
					&& (util.getPolyLineArea(rearYard)
							.compareTo(BigDecimal.valueOf(Float.valueOf(planInfoProperties.get("PLOT_AREA")))) > 0
							|| util.getPolyLineArea(rearYard).compareTo(
									BigDecimal.valueOf(Float.valueOf(planInfoProperties.get("PLOT_AREA")))) < 0))
				System.err.println("Plot are is not same as mentioned in plan info.");

			if ((util.getPolyLineArea(rearYard).compareTo(BigDecimal.valueOf(125)) > 0)) {
				System.err.println("Plot are less than 125 m2 is violated");
			} else {
				System.out.println("Plot are less than 125 m2 is accepted");
			}

			// rule 61
			System.out.println("\n####  Rule 61 ####");

			System.out.println("Total Floors " + util.getFloorCountExcludingCeller(doc, FLOOR_COLOUR_CODE));

			if (util.getFloorCountExcludingCeller(doc, FLOOR_COLOUR_CODE) > 3) {
				System.err.println("Total number of floors violated");
			} else {
				System.out.println("Total number of floors accepted");
			}

			DXFLWPolyline frontYard = null;

			List<DXFLWPolyline> frontYardLines = util.getPolyLinesByLayer(doc, FRONT_YARD);
			if (frontYardLines.size() == 1) {

				frontYard = frontYardLines.get(0);
				
				 * System.out.println(frontYard.getColumns());
				 * System.out.println(frontYard.getBounds().getMaximumY());
				 * System.out.println(frontYard.getBounds().getMinimumY());
				 

				System.out.println("\n####  Rule 62, 1a ####");

				BigDecimal polyLineArea = util.getPolyLineArea(frontYard);
				BigDecimal mean = polyLineArea.divide(BigDecimal.valueOf(frontYard.getBounds().getWidth()), 5,
						RoundingMode.HALF_UP);
				System.out.println("Area of the Front yard : " + polyLineArea);
				System.out.println("Front yard Mean : " + mean);
				 System.out.println("Front Yard Min Distance "+getYardMinDistance(doc, FRONT_YARD));
				if (mean.compareTo(BigDecimal.valueOf(1.2)) < 0) {
					System.err.println("Front yard Mean Distance rule violated");
				}
				 
			}

			BigDecimal sideYard1Mean = BigDecimal.ZERO;
			BigDecimal sideYard2Mean = BigDecimal.ZERO;

			List<DXFLWPolyline> side1 = util.getPolyLinesByLayer(doc, SIDE_YARD_1);
			if (side1.size() == 1) {

				rearYard = side1.get(0);
				System.out.println("Min Width: "
				+rearYard.getStartWidth()+"--Elevation---"+rearYard.getElevation()
				+"--"+rearYard.getContstantWidth()
				+"---"+rearYard.getPolyFaceMeshVertex(1)
				+"----"+read);
				
				
				 * System.out.println(frontYard.getColumns());
				 * System.out.println(frontYard.getBounds().getMaximumY());
				 * System.out.println(frontYard.getBounds().getMinimumY());
				 
				System.out.println("\n####  Rule 62, (2) ####");
				BigDecimal polyLineArea = util.getPolyLineArea(rearYard);
				BigDecimal mean = polyLineArea.divide(BigDecimal.valueOf(rearYard.getBounds().getHeight()), 5,
						RoundingMode.HALF_UP);
				System.out.println("Area of the Side yard : " + polyLineArea);
				System.out.println("Side yard Mean : " + mean);
				
				  Double sideyard1MinDistance = getYardMinDistance(doc,
				 SIDE_YARD_1); System.out.println(
				 "Side yard 1 Min Distance "+sideyard1MinDistance);
			

				sideYard1Mean = mean;
				 
			}
			List<DXFLWPolyline> side2 = util.getPolyLinesByLayer(doc, SIDE_YARD_2);
			if (side2.size() == 1) {

				rearYard = side2.get(0);
				
				 * System.out.println(frontYard.getColumns());
				 * System.out.println(frontYard.getBounds().getMaximumY());
				 * System.out.println(frontYard.getBounds().getMinimumY());
				 
				System.out.println("\n####  Rule 62, (2) ####");

				BigDecimal polyLineArea = util.getPolyLineArea(rearYard);
				BigDecimal mean = polyLineArea.divide(BigDecimal.valueOf(rearYard.getBounds().getHeight()), 5,
						RoundingMode.HALF_UP);
				System.out.println("Area of the Side yard 2 : " + polyLineArea);
				System.out.println("Side yard 2 Mean : " + mean);

				
				 Double sideyard2MinDistance = getYardMinDistance(doc,
				  SIDE_YARD_2); System.out.println(
				  "Side yard 2 Min Distance "+sideyard2MinDistance);
				 
				sideYard2Mean = mean;
				 
			}*/

		/*	List<DXFLWPolyline> rearYards = util.getPolyLinesByLayer(doc, REAR_YARD);
			if (rearYards.size() == 1) {

				rearYard = rearYards.get(0);
				
				 * System.out.println(frontYard.getColumns());
				 * System.out.println(frontYard.getBounds().getMaximumY());
				 * System.out.println(frontYard.getBounds().getMinimumY());
				 
				System.out.println("\n####  Rule 62, (3) ####");
				BigDecimal polyLineArea = util.getPolyLineArea(rearYard);
				BigDecimal mean = polyLineArea.divide(BigDecimal.valueOf(rearYard.getBounds().getWidth()), 5,
						RoundingMode.HALF_UP);
				System.out.println("Area of the Rear yard : " + polyLineArea);
				System.out.println("Rear yard Mean : " + mean);
				System.out.println("Rear Yard Min Distance " + getYardMinDistance(doc, REAR_YARD));
				if (mean.compareTo(BigDecimal.valueOf(1)) < 0) {
					System.err.println("Rear yard Mean Distance rule violated");
				}

				 
			}*/

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static Double getYardMinDistance(DXFDocument doc, String name) {
		Util util = new Util();
		List<DXFLWPolyline> polyLinesByLayer = util.getPolyLinesByLayer(doc, PLOT_BOUNDARY);
		DXFLWPolyline plotBoundary = polyLinesByLayer.get(0);

		List<DXFLWPolyline> polyLinesByLayer1 = util.getPolyLinesByLayer(doc, BUILDING_FOOT_PRINT);
		DXFLWPolyline buildFoorPrint = polyLinesByLayer1.get(0);
		// DXFLWPolyline buildFoorPrint1 = polyLinesByLayer1.get(1);
		int rows = buildFoorPrint.getRows();

		List<DXFLWPolyline> polyLinesByLayer2 = util.getPolyLinesByLayer(doc, name);
		DXFLWPolyline yard = polyLinesByLayer2.get(0);
	/*	System.out.println("vertex count"+yard.getVertexCount()+"and contains curve"+yard.isCurveFitVerticesAdded()); */
		/*System.out.println(name+" -isClosed :"+yard.isClosed());
		System.out.println("Plot - isClosed :"+plotBoundary.isClosed());
		System.out.println("Building -isClosed :"+buildFoorPrint.isClosed());*/

		Iterator vertexIterator = yard.getVertexIterator();
		List<Point> yardOutSidePoints = new ArrayList<>();
		List<Point> yardInSidePoints = new ArrayList<>();
		List<Double> distanceList = new ArrayList<>();
		int i = 0;
		Iterator plotBIterator1 = plotBoundary.getVertexIterator();
	int count=	plotBoundary.getVertexCount();
		double[][] shape = new double[count+1][2];
		while (plotBIterator1.hasNext()) {

			DXFVertex dxfVertex = (DXFVertex) plotBIterator1.next();
			Point point1 = dxfVertex.getPoint();
		
			shape[i][0] = point1.getX();
			shape[i][1] = point1.getY();

			//System.out.println(name+"===Shape=="+shape[i][0]+"--"+shape[i][1]);
			i++;

		}
		shape[i]=shape[0];
	//	System.out.println(name+"===Shape=="+shape[i][0]+"--"+shape[i][1]);
		
		
		
		DXFDocument doc1 = new DXFDocument();
		DXFLayer layer=new DXFLayer();
	 
		layer.addDXFEntity(plotBoundary);
		doc1.addDXFLayer(layer);
		doc1.toString();
		

//	System.out.println("flags"+	plotBoundary.getFlags());
		 
		while (vertexIterator.hasNext()) {
			DXFVertex next = (DXFVertex) vertexIterator.next();
			Point point = next.getPoint();
			// System.out.println("yard Point :"+point.getX()+","+point.getY());

			Iterator plotBIterator = plotBoundary.getVertexIterator();

			// Vertex and coordinates of Polyline
			outside: while (plotBIterator.hasNext()) {

				DXFVertex dxfVertex = (DXFVertex) plotBIterator.next();
				Point point1 = dxfVertex.getPoint();

				// System.out.println("Outside				 :"+point1.getX()+","+point1.getY());
				if (util.pointsEquals(point1,point)) {
					//System.out.println(name+" adding on points on a plot boundary Point ---"+point.getX()+","+point.getY());
					yardOutSidePoints.add(point);

					break outside;
				}
			}
		 	if(name.contains("side"))
			{
				if (RayCast.containsSide(shape, new double[] { point.getX(), point.getY() }) == true) {

					// System.out.println(yardOutSidePoints+"---"+!yardOutSidePoints.contains(point));

					if (!yardOutSidePoints.contains(point)) {
						// System.out.println("adding Point
						// :"+point.getX()+","+point.getY());
						yardOutSidePoints.add(point);
					}
				}
			}else
			{
			
			if (RayCast.contains(shape, new double[] { point.getX(), point.getY() }) == true) {

				// System.out.println(yardOutSidePoints+"---"+!yardOutSidePoints.contains(point));

				if (!yardOutSidePoints.contains(point)) {
					//System.out.println(name+" adding point on a   plot Boundary line using raycast---"+point.getX()+","+point.getY());
					yardOutSidePoints.add(point);
				}
			}
			}

			Iterator footPrintIterator = buildFoorPrint.getVertexIterator();

			// Vertex and coordinates of Polyline
			inside: while (footPrintIterator.hasNext()) {

				DXFVertex dxfVertex = (DXFVertex) footPrintIterator.next();
				Point point1 = dxfVertex.getPoint();
				// System.out.println("Foot Print  :"+point1.getX()+","+point1.getY());
				if (util.pointsEquals(point1,point)) {
					yardInSidePoints.add(point);
					// System.out.println("Inside	 :"+point.getX()+","+point.getY());
					break inside;
				}
			}

		}
		/*
		 * Iterator footPrintIterator = buildFoorPrint.getVertexIterator();
		 * while (footPrintIterator.hasNext()) {
		 * 
		 * DXFVertex dxfVertex = (DXFVertex) footPrintIterator.next(); Point
		 * point1 = dxfVertex.getPoint(); System.out.println("Foot Print :"
		 * +point1.getX()+","+point1.getY()); }
		 * 
		 * Iterator footPrintIterator1 = buildFoorPrint1.getVertexIterator();
		 * while (footPrintIterator1.hasNext()) {
		 * 
		 * DXFVertex dxfVertex = (DXFVertex) footPrintIterator1.next(); Point
		 * point1 = dxfVertex.getPoint(); System.out.println("Foot Print 1:"
		 * +point1.getX()+","+point1.getY());
		 * 
		 * }
		 */
		List<Point> toremove=new ArrayList<>();
		
		//System.out.println(name+"   Outside Points-------------");
		for(Point p:yardOutSidePoints)
		{
			for(Point p1:yardInSidePoints)
			{
				if(util.pointsEquals(p1,p))
				{
					toremove.add(p);
				}
			}
			//System.out.println(p.getX()+","+p.getY());
		}
		//System.out.println(name+"   Outside Points-------------");
		for(Point p:toremove)
		{
			yardOutSidePoints.remove(p);
			//System.out.println(name+"   remove Points-------------"+p.getX()+",,,,"+p.getY());	
		}
		
		
		for(Point p:yardOutSidePoints)
		{
			 
			//System.out.println(p.getX()+","+p.getY());
		}
		
		//System.out.println(name+"   Inside Points-------------");
		
		for(Point p:yardInSidePoints)
		{
			//System.out.println(p.getX()+","+p.getY());
		}
		
		List<Point> outsidePoints = findPointsOnPolylines(yardOutSidePoints);
		//System.out.println(outsidePoints.size());
		List<Point> insidePoints = findPointsOnPolylines(yardInSidePoints);
		//System.out.println(insidePoints.size());
		
		for (Point in : insidePoints) {
			 //System.out.println("Inside : "+in.getX()+","+in.getY());
			for (Point out : outsidePoints) {
			// System.out.println("Outside : "+out.getX()+","+out.getY());
				double distance = MathUtils.distance(in, out);
			//	 System.out.println("Distance : "+distance);
				distanceList.add(distance);

			}
		}
		
	/*	
		for (Point in : yardInSidePoints) {
			// System.out.println("Inside : "+in.getX()+","+in.getY());
			for (Point out : yardOutSidePoints) {
				// System.out.println("Outside : "+out.getX()+","+out.getY());
				double distance = MathUtils.distance(in, out);
				// System.out.println("Distance : "+distance);
				distanceList.add(distance);

			}
		}*/

		//System.out.println(distanceList);
		java.util.Collections.sort(distanceList);
		//System.out.println("the shortest Distance is " + distanceList.get(0));
		if (distanceList.size()>0)
			return distanceList.get(0);
		else return 0.0;

	}

	private static List<Point> findPointsOnPolylines(List<Point> yardInSidePoints) {
		Point old=null;
		Point first=null;
		Point point1=new Point();
		List<Point> myPoints=new ArrayList<>();
		
		for (Point in : yardInSidePoints) {
			{
				if(old==null)
				{
					old=in;
					first=in;
					continue;
				}
				if(first.equals(in))
				{
					continue;
				}
				
				//System.out.println("Points for line "+old.getX()+","+old.getY() +" And"+ in.getX()+","+in.getY());
				double distance = MathUtils.distance(old, in);
				//System.out.println("Distance"+distance);
				
				for(double j=.01;j<distance;j=j+.01)
				{
					 point1=new Point();
					double t=j/distance;
				point1.setX((1-t)*old.getX()+t*in.getX());
				point1.setY((1-t)*old.getY()+t*in.getY());
				myPoints.add(point1);
				//System.out.println(point1.getX()+"---"+point1.getY());
				}
				
				
				old=in;
			}
				
			}
		return myPoints;
	}

	// RULE 23 SUBRULE 5
	private static void validateVoltageLineFromOHEL(DXFDocument doc, Util util) {/*

		String voltage = util.getMtextByLayerName(doc, VOLTAGE);
		DXFLine horiz_clear_OHE = util.getSingleLineByLayer(doc, HORIZ_CLEAR_OHE2);
		DXFLine vert_clear_OHE = util.getSingleLineByLayer(doc, VERT_CLEAR_OHE);
		System.out.println("\n####  Rule 23, 5 ####");
		System.out.println("Voltage " + voltage);
		Float voltf=null;
		  try {
			voltf=Float.valueOf(voltage);
		} catch (NumberFormatException e) {
			System.err.println("Voltage contains non numeric value "+voltage+". Cannot validate voltage."); 
		}

		if (voltf!=null && voltf > 0) {

			if (horiz_clear_OHE != null && horiz_clear_OHE.getLength() > 0) {
				if (Float.valueOf(voltage) < 11000) {
					if (horiz_clear_OHE.getLength() < 1.2)
						System.err.println("Horizaontal distance from overhead line violating rules. Distance is "
								+ horiz_clear_OHE.getLength()); // TODO: CHECK
																// IS IT
																// MANDATORY.
					else {
						System.out.println("Horizaontal distance from overhead line " + horiz_clear_OHE.getLength());
					}
				} else if (Float.valueOf(voltage) >= 11000 && Float.valueOf(voltage) <= 33000) {
					if (horiz_clear_OHE.getLength() < 1.85)
						System.err.println("Horizaontal distance from overhead line violating rules. Distance is "
								+ horiz_clear_OHE.getLength()); // TODO: CHECK
																// IS IT
																// MANDATORY.
					else {
						System.out.println("Horizaontal distance from overhead line " + horiz_clear_OHE.getLength());
					}

				} else if (Float.valueOf(voltage) > 33000) {

					Double totalHorizontalOHE = 1.85 + 0.3 * (Math.ceil((Float.valueOf(voltage) - 33000) / 33000));
					if (horiz_clear_OHE.getLength() < totalHorizontalOHE)
						System.err.println("Horizaontal distance from overhead line violating rules. Distance is "
								+ horiz_clear_OHE.getLength()); // TODO: CHECK
																// IS IT
																// MANDATORY.
					else {
						System.out.println("Horizaontal distance from overhead line " + horiz_clear_OHE.getLength());
					}
				}

			} else if (vert_clear_OHE != null && vert_clear_OHE.getLength() > 0) {
				if (Float.valueOf(voltage) < 11000) {
					if (vert_clear_OHE.getLength() < 2.4)
						System.err.println("Vertical distance from overhead line violating rules. Distance is "
								+ vert_clear_OHE.getLength()); // TODO: CHECK IS
																// IT MANDATORY.
					else {
						System.out.println("Vertical distance from overhead line " + vert_clear_OHE.getLength());
					}
				} else if (Float.valueOf(voltage) >= 11000 && Float.valueOf(voltage) <= 33000) {
					if (vert_clear_OHE.getLength() < 3.7)
						System.err.println("Vertical distance from overhead line violating rules. Distance is "
								+ vert_clear_OHE.getLength()); // TODO: CHECK IS
																// IT MANDATORY.
					else {
						System.out.println("Vertical distance from overhead line " + vert_clear_OHE.getLength());
					}

				} else if (Float.valueOf(voltage) > 33000) {

					Double totalHorizontalOHE = 3.7 + 0.3 * (Math.ceil((Float.valueOf(voltage) - 33000) / 33000));

					if (vert_clear_OHE.getLength() < totalHorizontalOHE)
						System.err.println("Vertical distance from overhead line violating rules. Distance is "
								+ totalHorizontalOHE); // TODO: CHECK IS IT
														// MANDATORY.
					else {
						System.out.println("Vertical distance from overhead line " + vert_clear_OHE.getLength());
					}
				}

			} else
				System.err.println("Vertical distance/Horizontal distance from overhead line not defined.");

		} else
			System.out.println("Overhead electric voltage details not specified.");// TODO:
																					// CHECK
																					// MANDATORY
	*/}

	private static void getBlocks(DXFDocument doc) {
		int block = 0;
		while (doc.getDXFBlockIterator().hasNext()) {
			block++;
			doc.getDXFBlockIterator().next();
		}

		System.out.println("blocks" + block);
	}

	private static double getHeightOfTheBuilding(DXFDocument doc) {
		double height = 0;
		height = doc.getBounds().getHeight();
		System.out.println("height" + height);
		return height;

	}

	private static double polygonArea(ArrayList<Double> x, ArrayList<Double> y, int numPoints) {

		double area = 0; // Accumulates area in the loop
		int j = numPoints - 1; // The last vertex is the 'previous' one to the
								// first

		for (int i = 0; i < numPoints; i++) {
			area = area + (x.get(j) + x.get(i)) * (y.get(j) - y.get(i));
			j = i; // j is previous vertex to i
		}

		return area / 2;
	}

}