
package org.egov.edcr;


import java.awt.Polygon;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kabeja.dxf.DXFConstants;
import org.kabeja.dxf.DXFDocument;
import org.kabeja.dxf.DXFEntity;
import org.kabeja.dxf.DXFLWPolyline;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.dxf.DXFLine;
import org.kabeja.dxf.DXFMText;
import org.kabeja.dxf.DXFVertex;
import org.kabeja.dxf.helpers.Point;
import org.kabeja.parser.DXFParser;
import org.kabeja.parser.Parser;
import org.kabeja.parser.ParserBuilder;
import org.springframework.util.ResourceUtils;

public class J21 {
	private static Integer FLOOR_COLOUR_CODE = 10;
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
            System.out.println("  Total Floors " + util.getFloorCountExcludingCeller(doc,FLOOR_COLOUR_CODE));
       
          DXFLine line= util.getSingleLineByLayer(doc, "Shortest Distance to road");
          
          if(line !=null && line.getLength()< 3)
          {
        	  System.err.println("Shortest Distance to road condition violated");
          }else
          {
        	  System.out.println(" Shortest Distance to road condition is accepted");
          }
            
          List<DXFLWPolyline> frontYardLines= util.getPolyLinesByLayer(doc, "Front yard");
          if(frontYardLines.size()==1)
          {
        	  DXFLWPolyline frontYard=  frontYardLines.get(0);
        	/*  System.out.println(frontYard.getColumns());
        	  System.out.println(frontYard.getBounds().getMaximumY());
        	  System.out.println(frontYard.getBounds().getMinimumY());
        	*/ 
        	  System.out.println("front yard distance"+Math.abs(frontYard.getBounds().getMaximumY()-frontYard.getBounds().getMinimumY()));
          }
          
          List<DXFLWPolyline> rearYards= util.getPolyLinesByLayer(doc, "Rear yard");
          if(rearYards.size()==1)
          {
        	  DXFLWPolyline frontYard=  rearYards.get(0);
        	/*  System.out.println(frontYard.getColumns());
        	  System.out.println(frontYard.getBounds().getMaximumY());
        	  System.out.println(frontYard.getBounds().getMinimumY());
        	*/ 
        	  System.out.println("rear yard distance"+Math.abs(frontYard.getBounds().getMaximumY()-frontYard.getBounds().getMinimumY()));
          }
            // getBlocks(doc);
            
            Map<String, String> planInfoProperties = util.getPlanInfoProperties(doc);
            System.out.println("Plot Area="+planInfoProperties.get("PLOT_AREA"));

            Iterator dxfLayerIterator = doc.getDXFLayerIterator();

                

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

