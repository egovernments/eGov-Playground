
package org.egov.edcr;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kabeja.dxf.Bounds;
import org.kabeja.dxf.DXFDocument;
import org.kabeja.dxf.DXFLWPolyline;
import org.kabeja.dxf.DXFLine;
import org.kabeja.parser.DXFParser;
import org.kabeja.parser.Parser;
import org.kabeja.parser.ParserBuilder;
import org.springframework.util.ResourceUtils;

public class J21 {
	private static Integer FLOOR_COLOUR_CODE = 10;
	private static String LAYER_NAME_WASTE_DISPOSAL="Waste disposal";
	private static String CRZ_ZONE="CRZ_ZONE";
    public static void main(String[] args) {

        Parser parser = ParserBuilder.createDefaultParser();

        try {
            //File Path
            File file = ResourceUtils.getFile("classpath:dxf/kj21.dxf");
            String path = file.getPath();
           

            //Parse DXF File
            parser.parse(path, DXFParser.DEFAULT_ENCODING);

            //Extract DXF Data
            DXFDocument doc = parser.getDocument();

            getHeightOfTheBuilding(doc);
            Util util= new Util();
            System.out.println("Total Floors " + util.getFloorCountExcludingCeller(doc,FLOOR_COLOUR_CODE));
            
            List<DXFLWPolyline> wasterDisposalPolyLines= util.getPolyLinesByLayer(doc, LAYER_NAME_WASTE_DISPOSAL);
            
            if(wasterDisposalPolyLines.size()>0) //Mean, they defined waster disposal.
            {
            	  System.out.println("Waste disposal difined in the diagram."); 
            }else
            {
          	  System.err.println("Waste disposal not defined.Application not accepted"); //TODO: CHECK IS IT MANDATORY.
            }
           
        
       
            //rule 26
          DXFLine line= util.getSingleLineByLayer(doc, "Shortest Distance to road");
          
          if(line !=null && line.getLength()< 3)
          {
        	  System.err.println("Shortest Distance to road condition violated");
          }else
          {
        	  System.out.println("Shortest Distance to road condition is accepted");
          }
          
          //rule 26a
          
          
          //rule 61
          
          System.out.println("  Total Floors " + util.getFloorCountExcludingCeller(doc,FLOOR_COLOUR_CODE));
          if(util.getFloorCountExcludingCeller(doc,FLOOR_COLOUR_CODE)>3)
          {
        	System.err.println("rule 61 total number of floors violated");  
          }
          else
          {
        	  System.err.println("rule 61 total number of floors accepted");  
          }
          
          DXFLWPolyline frontYard=null; 
          DXFLWPolyline rearYard=null;
          List<DXFLWPolyline> frontYardLines= util.getPolyLinesByLayer(doc, "Front yard");
          if(frontYardLines.size()==1)
          {
        	 
			frontYard = frontYardLines.get(0);
        	/*  System.out.println(frontYard.getColumns());
        	  System.out.println(frontYard.getBounds().getMaximumY());
        	  System.out.println(frontYard.getBounds().getMinimumY());
        	*/ 
        	  System.out.println("front yard distance"+Math.abs(frontYard.getBounds().getMaximumY()-frontYard.getBounds().getMinimumY()));
          }
          
          List<DXFLWPolyline> rearYards= util.getPolyLinesByLayer(doc, "Rear yard");
          if(rearYards.size()==1)
          {
        	 
			rearYard = rearYards.get(0);
        	/*  System.out.println(frontYard.getColumns());
        	  System.out.println(frontYard.getBounds().getMaximumY());
        	  System.out.println(frontYard.getBounds().getMinimumY());
        	*/ 
        	  System.out.println("rear yard distance"+Math.abs(rearYard.getBounds().getMaximumY()-rearYard.getBounds().getMinimumY()));
          }
          
          
          
          
            // getBlocks(doc);
            // rule 60 
            Map<String, String> planInfoProperties = util.getPlanInfoProperties(doc);
            System.out.println("Plot Area="+planInfoProperties.get("PLOT_AREA"));

            if(planInfoProperties.get(CRZ_ZONE)!=null)
	            {
            	if(planInfoProperties.get(CRZ_ZONE).equalsIgnoreCase("YES"))
	          	  System.out.println("SITE MARKED UNDER CRZ ZONE. CHECK NOC DURING DOCUMENT SCRUTINY."); 
            	else
            		System.out.println("SITE NOT MARKED UNDER CRZ ZONE."); 
            	
	          }else
	          {
	        	  System.err.println("CRZ ZONE DETAILS NOT MENTIONED"); //TODO: CHECK IS IT MANDATORY.
	          }
            

            if(Double.valueOf(planInfoProperties.get("PLOT_AREA"))>=125)
            {
            	 System.err.println("Plot are less than 125 m2 is violated");
            }else
            {
            	 System.out.println("Plot are less than 125 m2 is accepted");
            }
           
            List<DXFLWPolyline> polyLinesByLayer = util.getPolyLinesByLayer(doc, "Non notified road");
            DXFLWPolyline nonNotifiedRoad=    polyLinesByLayer.get(0);
            Bounds   roadBounds=   nonNotifiedRoad.getBounds();
            roadBounds.debug();
         
            frontYard.getBounds().debug();
            rearYard.getBounds().debug();
            
           
                

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

	private static void getBlocks(DXFDocument doc) {
		int block=0;
        while(doc.getDXFBlockIterator().hasNext() )
        {
		  block++;
		  doc.getDXFBlockIterator().next();
        }
        
        System.out.println("blocks"+block);
	}
    
    private static double  getHeightOfTheBuilding(DXFDocument doc)
    {
    	double height=0;
    	height= doc.getBounds().getHeight();
    	System.out.println("height"+height);
    	return height;
    	
    }


	
    private static double polygonArea(ArrayList<Double> x, ArrayList<Double> y, int numPoints) {

        double area = 0;         // Accumulates area in the loop
        int j = numPoints - 1;  // The last vertex is the 'previous' one to the first

        for (int i = 0; i < numPoints; i++) {
            area = area + (x.get(j) + x.get(i)) * (y.get(j) - y.get(i));
            j = i;  //j is previous vertex to i
        }

        return area / 2;
    }


}    

