package org.egov.edcr;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.kabeja.dxf.DXFConstants;
import org.kabeja.dxf.DXFDocument;
import org.kabeja.dxf.DXFLWPolyline;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.dxf.DXFPolyline;
import org.kabeja.dxf.DXFVertex;
import org.kabeja.dxf.helpers.Point;

public class Util {

    public List<DXFLWPolyline> getPolyLinesByColor(DXFDocument dxfDocument,  Integer colorCode) {

        List<DXFLWPolyline> dxflwPolylines = new ArrayList<>();

        Iterator dxfLayerIterator = dxfDocument.getDXFLayerIterator();

        while (dxfLayerIterator.hasNext()) {

            DXFLayer dxfLayer = (DXFLayer) dxfLayerIterator.next();

            List dxfPolyLineEntities = dxfLayer.getDXFEntities(DXFConstants.ENTITY_TYPE_LWPOLYLINE);

            if (null != dxfPolyLineEntities) {
                for (Object dxfEntity : dxfPolyLineEntities) {

                    DXFLWPolyline dxflwPolyline = (DXFLWPolyline) dxfEntity;

                   
                        if (colorCode == dxflwPolyline.getColor()) {
                            dxflwPolylines.add(dxflwPolyline);
                        }
                    }
                }
            }
        

        return dxflwPolylines;
    }
    
    public List<DXFLWPolyline> getPolyLinesByColors(DXFDocument dxfDocument, List<Integer> colorCodes) {

        List<DXFLWPolyline> dxflwPolylines = new ArrayList<>();

        Iterator dxfLayerIterator = dxfDocument.getDXFLayerIterator();

        while (dxfLayerIterator.hasNext()) {

            DXFLayer dxfLayer = (DXFLayer) dxfLayerIterator.next();

            List dxfPolyLineEntities = dxfLayer.getDXFEntities(DXFConstants.ENTITY_TYPE_LWPOLYLINE);

            if (null != dxfPolyLineEntities) {
                for (Object dxfEntity : dxfPolyLineEntities) {

                    DXFLWPolyline dxflwPolyline = (DXFLWPolyline) dxfEntity;

                    for (int colorCode : colorCodes) {
                        if (colorCode == dxflwPolyline.getColor()) {
                            dxflwPolylines.add(dxflwPolyline);
                        }
                    }
                }
            }
        }

        return dxflwPolylines;
    }
    
    public List<DXFLWPolyline> getPolyLinesByLayer(DXFDocument dxfDocument, String name) {

        List<DXFLWPolyline> dxflwPolylines = new ArrayList<>();

        Iterator dxfLayerIterator = dxfDocument.getDXFLayerIterator();

        while (dxfLayerIterator.hasNext()) {

            DXFLayer dxfLayer = (DXFLayer) dxfLayerIterator.next();

            List dxfPolyLineEntities = dxfLayer.getDXFEntities(DXFConstants.ENTITY_TYPE_LWPOLYLINE);

            if (null != dxfPolyLineEntities) {
                for (Object dxfEntity : dxfPolyLineEntities) {

                    DXFLWPolyline dxflwPolyline = (DXFLWPolyline) dxfEntity;

                    
                        if (name.equalsIgnoreCase(dxflwPolyline.getLayerName())) {
                            dxflwPolylines.add(dxflwPolyline);
                       
                    }
                }
            }
        }

        return dxflwPolylines;
    }


    private BigDecimal getPolyLineArea(DXFPolyline dxfPolyline) {

        ArrayList x = new ArrayList();
        ArrayList y = new ArrayList();

        Iterator vertexIterator = dxfPolyline.getVertexIterator();

        //Vertex and coordinates of Polyline
        while (vertexIterator.hasNext()) {

            DXFVertex dxfVertex = (DXFVertex) vertexIterator.next();
            Point point = dxfVertex.getPoint();

            //values needed to calculate area
            x.add(point.getX());
            y.add(point.getY());

        }

        return polygonArea(x, y, dxfPolyline.getVertexCount());
    }

    //Using ShoeLace Formula to calculate area of polygon
    private BigDecimal polygonArea(ArrayList<Double> x, ArrayList<Double> y, int numPoints) {

        double area = 0;         // Accumulates area in the loop
        int j = numPoints - 1;  // The last vertex is the 'previous' one to the first

        for (int i = 0; i < numPoints; i++) {
            area = area + (x.get(j) + x.get(i)) * (y.get(j) - y.get(i));
            j = i;  //j is previous vertex to i
        }

        BigDecimal convertedArea = new BigDecimal(area / 2);

        return convertedArea.setScale(4, RoundingMode.HALF_UP).abs();

    }

}
