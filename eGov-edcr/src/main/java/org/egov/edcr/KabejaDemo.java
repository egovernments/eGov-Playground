
package org.egov.edcr;


import java.awt.Polygon;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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

public class KabejaDemo {

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
          
            
            // getBlocks(doc);
            

            Iterator dxfLayerIterator = doc.getDXFLayerIterator();

            //iterate each layer
            while (dxfLayerIterator.hasNext()) {

                DXFLayer dxfLayer = (DXFLayer) dxfLayerIterator.next();

                String name = dxfLayer.getName();
                if (!name.equalsIgnoreCase("Defpoints")) {
                    //Mtext Entity
                    List mTextEntities = dxfLayer.getDXFEntities(DXFConstants.ENTITY_TYPE_MTEXT);
                    if(mTextEntities!=null)
                    for (Object dxfEntity : mTextEntities) {
                        DXFEntity dxfEntitySingle = (DXFEntity) dxfEntity;

                        System.out.println("LayerName = " + dxfEntitySingle.getLayerName() +
                                " , Entity Type = " + DXFConstants.ENTITY_TYPE_MTEXT +
                                " , Object Name = " + ((DXFMText) dxfEntitySingle).getText() +
                                "  , Color = " + dxfEntitySingle.getColor());
                    }

                    System.out.println("*********************************************************************************");
                    System.out.printf("%n");
                    
                    List dxfLineEntities = dxfLayer.getDXFEntities(DXFConstants.ENTITY_TYPE_LINE);
                    if(dxfLineEntities!=null)
                    for(int i=0;i<dxfLineEntities.size();i++)
                    {
                    	DXFLine l=(DXFLine)dxfLineEntities.get(i);
                    	System.out.println("Line Properties");
                    	System.out.println("Color:"+l.getColor() +", Length:"+l.getLength()+", LayerName:"+l.getLayerName()+", RGBCOLOR:"+l.getColorRGB());
                    }
                    
                    //LWPOLYLINE
                    List dxfPolyLineEntities = dxfLayer.getDXFEntities(DXFConstants.ENTITY_TYPE_LWPOLYLINE);
                    if(dxfPolyLineEntities!=null)
                    for (Object dxfEntity : dxfPolyLineEntities) {
                        int i = 0;
                        int noOfVertices;
                        Double secondSmallestY;
                        Double minimumHeight;

                        ArrayList x = new ArrayList();
                        ArrayList y = new ArrayList();

                        Polygon polygon = new Polygon();


                        DXFLWPolyline dxflwPolyline = (DXFLWPolyline) dxfEntity;
                        Iterator vertexIterator = dxflwPolyline.getVertexIterator();

                        System.out.println("LayerName = " + dxflwPolyline.getLayerName() + " , " +
                                "Entity Type = " + DXFConstants.ENTITY_TYPE_LWPOLYLINE +
                                ", PolyLine ID = " + dxflwPolyline.getID() + " ," +
                                " Closed Polygon = " + dxflwPolyline.isClosed() + "  , " +
                                "Color =  " + dxflwPolyline.getColor());

                        System.out.println("Coordinates : ");

                        //Vertex and coordinates of Polyline
                        while (vertexIterator.hasNext()) {

                            DXFVertex dxfVertex = (DXFVertex) vertexIterator.next();
                            Point point = dxfVertex.getPoint();

                            System.out.println("X" + i + " = " + point.getX() +
                                    ", Y" + i + " = " + point.getY() +
                                    ", Z" + i + " = " + point.getZ());

                            //values needed to calculate area
                            x.add(point.getX());
                            y.add(point.getY());

                            polygon.addPoint((int) point.getX(), (int) point.getY());

                            i++;
                        }

                        noOfVertices = i;

                        double polygonArea = polygonArea(x, y, noOfVertices);

                        //Get max and min of x, y coordinates
                        Double maxX = (Double) Collections.max(x);
                        Double minX = (Double) Collections.min(x);

                        Double maxY = (Double) Collections.max(y);
                        Double minY = (Double) Collections.min(y);

                        //calculate width and height of polygon
                        Double width = maxX - minX;
                        Double height = maxY - minY;

                        /*Get second minimum y coordinate*/
                        //Remove duplicate y coordinates by converting to hashset
                        Set<Double> hashsetList = new HashSet<Double>(y);

                        //Convert hashset to ArrayList
                        ArrayList<Double> distinctList = new ArrayList<Double>(hashsetList);

                        //Sort ArrayList in ascending order
                        Collections.sort(distinctList);

                        /*If condition used because distinct list may contain only one value in the list ,
                        else distinct list  has multiple values we will be using else condition */
                        if (distinctList.size() > 0 && distinctList.size() == 1) {
                            secondSmallestY = distinctList.get(0);
                            minimumHeight = secondSmallestY - minY;
                        } else {
                            secondSmallestY = distinctList.get(1);
                            minimumHeight = secondSmallestY - minY;
                        }

                        System.out.println("Polygon Area = " + polygonArea + " ," +
                                " Width of polygon = " + width + " , Height of polygon = " + height +
                                " , Minimum Height = " + minimumHeight);

                        System.out.printf("%n");

                    }
                }

            }

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

