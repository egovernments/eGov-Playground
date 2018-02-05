
package org.egov.edcr;


import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

          //  getHeightOfTheBuilding(doc);
            Util util= new Util();
         //   System.out.println("Total Floors " + util.getFloorCountExcludingCeller(doc,FLOOR_COLOUR_CODE));
            
            
            //Rule 23 A  
            Map<String, String> planInfoProperties = util.getPlanInfoProperties(doc);
            System.out.println("\n####  Rule 23, 4 ####");
            if(planInfoProperties.get(CRZ_ZONE)!=null)
	            {
            	if(planInfoProperties.get(CRZ_ZONE).equalsIgnoreCase("YES"))
	          	  System.err.println("SITE MARKED UNDER CRZ ZONE. CHECK NOC DURING DOCUMENT SCRUTINY."); 
            	else
            		System.err.println("SITE NOT MARKED UNDER CRZ ZONE."); 
            	
	          }else
	          {
	        	  System.err.println("CRZ ZONE DETAILS NOT MENTIONED"); //TODO: CHECK IS IT MANDATORY.
	          }
            
            validateVoltageLineFromOHEL(doc, util);
            
          for(int i=1;i<1500;i++)
          {}
           //rule 26
          
			List<DXFLWPolyline> nonNotifiedRoad = util.getPolyLinesByLayer(doc, "Non notified road");
			List<DXFLWPolyline> notifiedRoad = util.getPolyLinesByLayer(doc, "Notified roads");

			DXFLine line = util.getSingleLineByLayer(doc, "Shortest Distance to road");
			System.out.println("\n####  Rule 26 ####");
			System.out.println("Shortest Distance to road : " + line.getLength());
			
			//line.getBounds().debug();
			if (nonNotifiedRoad.size() > 0) {
				if (line != null && line.getLength() < 2) {
					System.err.println("Shortest Distance to road condition violated for non notified roads");
				} else {
					System.out.println("Shortest Distance to road condition is accepted for non notified roads.");
				}
			}

			if (notifiedRoad.size() > 0) {
				if (line != null && line.getLength() < 3) {
					System.err.println("Shortest Distance to road condition violated for notified roads");
				} else {
					System.out.println("Shortest Distance to road condition is accepted for notified roads.");
				}
			}
          
          //rule 26a
          List<DXFLWPolyline> wasterDisposalPolyLines= util.getPolyLinesByLayer(doc, LAYER_NAME_WASTE_DISPOSAL);
          System.out.println("\n####  Rule 26 A ####"); 
          if(wasterDisposalPolyLines.size()>0) //Mean, they defined waster disposal.
          {
          	  System.out.println("Waste disposal difined in the diagram."); 
          }else
          {
        	  System.err.println("Waste disposal not defined.Application not accepted"); //TODO: CHECK IS IT MANDATORY.
          }
          
          DXFLWPolyline rearYard=null;
          //Rule 60
          System.out.println("\n####  Rule 60 ####");
  
          List<DXFLWPolyline> plotBoundary= util.getPolyLinesByLayer(doc, "Plot boundary");
          if(plotBoundary.size()==1)
          {
        	 
			rearYard = plotBoundary.get(0);

        	  System.out.println(" Total Area in yards "+ util.getPolyLineArea(rearYard));
          }
                  // System.out.println("Plot Area="+planInfoProperties.get("PLOT_AREA"));

                    if((util.getPolyLineArea(rearYard).compareTo(BigDecimal.valueOf(125))>0))
                    {
                    	 System.err.println("Plot are less than 125 m2 is violated");
                    }else
                    {
                    	 System.out.println("Plot are less than 125 m2 is accepted");
                    }
                    
          //rule 61
          System.out.println("\n####  Rule 61 ####");
          
          System.out.println("Total Floors " + util.getFloorCountExcludingCeller(doc,FLOOR_COLOUR_CODE));
          
         if(util.getFloorCountExcludingCeller(doc,FLOOR_COLOUR_CODE)>3)
          {
        	System.err.println("Total number of floors violated");  
          }
          else
          {
        	  System.out.println("Total number of floors accepted");  
          }
          
          DXFLWPolyline frontYard=null; 
         
          List<DXFLWPolyline> frontYardLines= util.getPolyLinesByLayer(doc, "Front yard");
          if(frontYardLines.size()==1)
          {
        	 
			frontYard = frontYardLines.get(0);
        	/*  System.out.println(frontYard.getColumns());
        	  System.out.println(frontYard.getBounds().getMaximumY());
        	  System.out.println(frontYard.getBounds().getMinimumY());*/
        	
		
			
			
			System.out.println("\n####  Rule 62, 1a ####");
			
			BigDecimal polyLineArea = util.getPolyLineArea(frontYard);
			BigDecimal mean = polyLineArea.divide(BigDecimal.valueOf(frontYard.getBounds().getWidth()),5,RoundingMode.HALF_UP);
			System.out.println("Area of the Front yard : "+polyLineArea);
			System.out.println("Front yard Mean : "+mean);
			if(mean.compareTo(BigDecimal.valueOf(1.2)) < 0)
			{
				System.err.println("Front yard Mean Distance rule violated");
			}
        	System.out.println("Front yard distance "+Math.abs(frontYard.getBounds().getMaximumY()-frontYard.getBounds().getMinimumY()));
          }
          
         BigDecimal sideYard1Mean=BigDecimal.ZERO;
         BigDecimal sideYard2Mean=BigDecimal.ZERO;
          
          List<DXFLWPolyline> side1= util.getPolyLinesByLayer(doc, "Side yard 1");
          if(side1.size()==1)
          {
        	 
			rearYard = side1.get(0);
        	/*  System.out.println(frontYard.getColumns());
        	  System.out.println(frontYard.getBounds().getMaximumY());
        	  System.out.println(frontYard.getBounds().getMinimumY());
        	*/ 
			System.out.println("\n####  Rule 62, (2) ####");
			BigDecimal polyLineArea = util.getPolyLineArea(rearYard);
			BigDecimal mean = polyLineArea.divide(BigDecimal.valueOf(rearYard.getBounds().getHeight()),5,RoundingMode.HALF_UP);
			System.out.println("Area of the Side yard : "+polyLineArea);
			System.out.println("Side yard Mean : "+mean);
			
			sideYard1Mean=mean;
        	  System.out.println("Side yard 1 distance "+Math.abs(rearYard.getBounds().getMaximumX()-rearYard.getBounds().getMinimumX()));
          }
          List<DXFLWPolyline> side2= util.getPolyLinesByLayer(doc, "Side yard 2");
          if(side2.size()==1)
          {
        	 
			rearYard = side2.get(0);
        	/*  System.out.println(frontYard.getColumns());
        	  System.out.println(frontYard.getBounds().getMaximumY());
        	  System.out.println(frontYard.getBounds().getMinimumY());
        	*/ 
			System.out.println("\n####  Rule 62, (2) ####");
			
			BigDecimal polyLineArea = util.getPolyLineArea(rearYard);
			BigDecimal mean= polyLineArea.divide(BigDecimal.valueOf(rearYard.getBounds().getHeight()),5,RoundingMode.HALF_UP);
			System.out.println("Area of the Side yard 2 : "+polyLineArea);
			System.out.println("Side yard 2 Mean : "+mean);
			sideYard2Mean=mean;
        	  System.out.println("Side yard 2 distance "+Math.abs(rearYard.getBounds().getMaximumX()-rearYard.getBounds().getMinimumX()));
          }
          
          
          List<DXFLWPolyline> rearYards= util.getPolyLinesByLayer(doc, "Rear yard");
          if(rearYards.size()==1)
          {
        	 
			rearYard = rearYards.get(0);
        	/*  System.out.println(frontYard.getColumns());
        	  System.out.println(frontYard.getBounds().getMaximumY());
        	  System.out.println(frontYard.getBounds().getMinimumY());
        	*/ 
			System.out.println("\n####  Rule 62, (3) ####");
			BigDecimal polyLineArea = util.getPolyLineArea(rearYard);
			BigDecimal mean = polyLineArea.divide(BigDecimal.valueOf(rearYard.getBounds().getWidth()),5,RoundingMode.HALF_UP);
			System.out.println("Area of the Rear yard : "+polyLineArea);
			System.out.println("Rear yard Mean : "+mean);
			if(mean.compareTo(BigDecimal.valueOf(1)) < 0)
			{
				System.err.println("Rear yard Mean Distance rule violated");
			}
			
        	  System.out.println("Rear yard distance "+Math.abs(rearYard.getBounds().getMaximumY()-rearYard.getBounds().getMinimumY()));
          }
          
          
            
         
            
          } catch (Exception e) {
            e.printStackTrace();
        }

    }
//RULE 23 SUBRULE 5
	private static void validateVoltageLineFromOHEL(DXFDocument doc, Util util) {
		
			String voltage= util.getMtextByLayerName(doc, "Voltage");
            DXFLine horiz_clear_OHE= util.getSingleLineByLayer(doc, "Horiz_clear_OHE");
            DXFLine vert_clear_OHE= util.getSingleLineByLayer(doc, "Vert_clear_OHE");
            System.out.println("\n####  Rule 23, 5 ####");
	         if(Float.valueOf(voltage)>0){
	            	
	            if(horiz_clear_OHE.getLength()>0) {	
	            	if(Float.valueOf(voltage)<11000)
	            	{
	            		if(horiz_clear_OHE.getLength()<1.2)
	        	        	  System.err.println("Horizaontal distance from overhead line violating rules. Distance is "+horiz_clear_OHE.getLength()); //TODO: CHECK IS IT MANDATORY.
	              		else
	                      {
	                      	 System.out.println("Horizaontal distance from overhead line "+ horiz_clear_OHE.getLength());
	                      }
	            	}
	            	else if(Float.valueOf(voltage)>=11000 && Float.valueOf(voltage)<=33000)
	            	{
	            		if(horiz_clear_OHE.getLength()<1.85)
	      	        	  System.err.println("Horizaontal distance from overhead line violating rules. Distance is "+horiz_clear_OHE.getLength()); //TODO: CHECK IS IT MANDATORY.
	            		else
	                    {
	                    	 System.out.println("Horizaontal distance from overhead line "+ horiz_clear_OHE.getLength());
	                    }
	            			
	            	}else if(Float.valueOf(voltage)>33000)
	            	{
	            		
	            		Double totalHorizontalOHE= 1.85+ 0.3 * (Math.ceil((Float.valueOf(voltage)-33000)/33000));
	            		if(horiz_clear_OHE.getLength()<totalHorizontalOHE)
	        	        	  System.err.println("Horizaontal distance from overhead line violating rules. Distance is "+horiz_clear_OHE.getLength()); //TODO: CHECK IS IT MANDATORY.
	              		else
	                      {
	                      	 System.out.println("Horizaontal distance from overhead line "+ horiz_clear_OHE.getLength());
	                      }
	            	}
	            		
	            }else if(vert_clear_OHE.getLength()>0) {	
	            	if(Float.valueOf(voltage)<11000)
	            	{
	            		if(vert_clear_OHE.getLength()<2.4)
	        	        	  System.err.println("Vertical distance from overhead line violating rules. Distance is "+vert_clear_OHE.getLength()); //TODO: CHECK IS IT MANDATORY.
	              		else
	                      {
	                      	 System.out.println("Vertical distance from overhead line "+ vert_clear_OHE.getLength());
	                      }
	            	}
	            	else if(Float.valueOf(voltage)>=11000 && Float.valueOf(voltage)<=33000)
	            	{
	            		if(vert_clear_OHE.getLength()<3.7)
	      	        	  System.err.println("Vertical distance from overhead line violating rules. Distance is "+vert_clear_OHE.getLength()); //TODO: CHECK IS IT MANDATORY.
	            		else
	                    {
	                    	 System.out.println("Vertical distance from overhead line "+ vert_clear_OHE.getLength());
	                    }
	            			
	            	}else if(Float.valueOf(voltage)>33000)
	            	{
	            		
	            		Double totalHorizontalOHE= 3.7+ 0.3 * (Math.ceil((Float.valueOf(voltage)-33000)/33000));
	            		
	            		if(vert_clear_OHE.getLength()<totalHorizontalOHE)
	        	        	  System.err.println("Vertical distance from overhead line violating rules. Distance is "+totalHorizontalOHE); //TODO: CHECK IS IT MANDATORY.
	              		else
	                      {
	                      	 System.out.println("Vertical distance from overhead line "+ vert_clear_OHE.getLength());
	                      }
	            	}
	            		
	            }else
	          	  System.err.println("Vertical distance/Horizontal distance from overhead line not defined.");
	            
	             	
	           }else
		          	  System.out.println("Overhead electric voltage details not specified.");//TODO: CHECK MANDATORY
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

