package org.egov.edcr;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.kabeja.dxf.DXFConstants;
import org.kabeja.dxf.DXFDocument;
import org.kabeja.dxf.DXFLWPolyline;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.dxf.DXFLine;
import org.kabeja.dxf.DXFPolyline;
import org.kabeja.dxf.DXFText;
import org.kabeja.dxf.DXFVertex;
import org.kabeja.dxf.helpers.Point;

public class Util {
	private static String FLOOR_NAME_PREFIX = "FLOOR_";
	private static final int DECIMALDIGITS = 10;

	public List<DXFLWPolyline> getPolyLinesByColor(DXFDocument dxfDocument, Integer colorCode) {

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

	public List<DXFLine> getLinesByColor(DXFDocument dxfDocument, Integer color) {

		List<DXFLine> lines = new ArrayList<>();

		Iterator dxfLayerIterator = dxfDocument.getDXFLayerIterator();

		while (dxfLayerIterator.hasNext()) {

			DXFLayer dxfLayer = (DXFLayer) dxfLayerIterator.next();

			List dxfPolyLineEntities = dxfLayer.getDXFEntities(DXFConstants.ENTITY_TYPE_LINE);

			if (null != dxfPolyLineEntities) {
				for (Object dxfEntity : dxfPolyLineEntities) {

					DXFLine line = (DXFLine) dxfEntity;

					if (color == line.getColor()) {
						lines.add(line);
					}

				}
			}
		}

		return lines;
	}

	public List<DXFLine> getLinesByLayer(DXFDocument dxfDocument, String name) {

		List<DXFLine> lines = new ArrayList<>();

		Iterator dxfLayerIterator = dxfDocument.getDXFLayerIterator();

		while (dxfLayerIterator.hasNext()) {

			DXFLayer dxfLayer = (DXFLayer) dxfLayerIterator.next();

			List dxfPolyLineEntities = dxfLayer.getDXFEntities(DXFConstants.ENTITY_TYPE_LINE);

			if (null != dxfPolyLineEntities) {
				for (Object dxfEntity : dxfPolyLineEntities) {

					DXFLine line = (DXFLine) dxfEntity;

					if (name == line.getLayerName()) {
						lines.add(line);
					}

				}
			}
		}

		return lines;
	}

	public DXFLine getSingleLineByLayer(DXFDocument dxfDocument, String name) {

		if(dxfDocument==null)
			return null;
		if(name==null)
			return null;
		
		List<DXFLine> lines = new ArrayList<>();

		Iterator dxfLayerIterator = dxfDocument.getDXFLayerIterator();

		while (dxfLayerIterator.hasNext()) {

			DXFLayer dxfLayer = (DXFLayer) dxfLayerIterator.next();

			List dxfLineEntities = dxfLayer.getDXFEntities(DXFConstants.ENTITY_TYPE_LINE);

			if (null != dxfLineEntities) {
				for (Object dxfEntity : dxfLineEntities) {

					DXFLine line = (DXFLine) dxfEntity;

					if (name.equalsIgnoreCase(line.getLayerName())) {
						lines.add(line);
					}

				}
			}
		}
		if (lines.size() == 1) {
			return lines.get(0);
		} else
			return null;

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

	protected BigDecimal getPolyLineArea(DXFPolyline dxfPolyline) {

		ArrayList x = new ArrayList();
		ArrayList y = new ArrayList();

		Iterator vertexIterator = dxfPolyline.getVertexIterator();

		// Vertex and coordinates of Polyline
		while (vertexIterator.hasNext()) {

			DXFVertex dxfVertex = (DXFVertex) vertexIterator.next();
			Point point = dxfVertex.getPoint();

			// values needed to calculate area
			x.add(point.getX());
			y.add(point.getY());

		}

		return polygonArea(x, y, dxfPolyline.getVertexCount());
	}

	// Using ShoeLace Formula to calculate area of polygon
	private BigDecimal polygonArea(ArrayList<Double> x, ArrayList<Double> y, int numPoints) {

		double area = 0; // Accumulates area in the loop
		int j = numPoints - 1; // The last vertex is the 'previous' one to the
								// first

		for (int i = 0; i < numPoints; i++) {
			area = area + (x.get(j) + x.get(i)) * (y.get(j) - y.get(i));
			j = i; // j is previous vertex to i
		}

		BigDecimal convertedArea = new BigDecimal(area / 2);

		return convertedArea.setScale(4, RoundingMode.HALF_UP).abs();

	}
	
	 public static	String	getMtextByLayerName(DXFDocument doc, String layerName)
		{
		 DXFLayer planInfoLayer = doc.getDXFLayer(layerName);
			List texts = planInfoLayer.getDXFEntities(DXFConstants.ENTITY_TYPE_MTEXT);
			String param="";
			DXFText	text=null;
			Iterator iterator = texts.iterator();
			
			while(iterator.hasNext())
			{
				text= (DXFText)iterator.next();
				if(text!=null && text.getText()!=null ){
					param=text.getText();
				/*if(new Float(param).isNaN())	
				{
					throw  new RuntimeException("Texts in the layer" + layerName +"Does not follow standard ");
				}*/
				
				param=param.replace("VOLTS", "").trim();
				}
			}
			return param;
		}
		
 public static	Map<String,String>	getPlanInfoProperties(DXFDocument doc)
	{
		
		DXFLayer planInfoLayer = doc.getDXFLayer("Plan info");
		List texts = planInfoLayer.getDXFEntities(DXFConstants.ENTITY_TYPE_MTEXT);
		String param="";
		DXFText	text=null;
		Map<String,String> planInfoProperties=new HashMap<>();
		Iterator iterator = texts.iterator();
		String[] split;
		String s="\\";
		while(iterator.hasNext())
		{
			text= (DXFText)iterator.next();
			
			param=text.getText();
			param=param.replace(s, "#");
		//	System.out.println(param);
			if(param.contains("#P"))
			{
			//	System.out.println("inside");
				split = param.split("#P");
			}
			else
			{
				split=new String [1];
				split[0]=param;
			}
			
			for (int j=0;j<split.length;j++)
			{
		 
			String[] data = split[j].split("=");
			if(data.length==2)
			{
			//	System.out.println(data[0]+"---"+data[1]);
				planInfoProperties.put(data[0], data[1]);
			}else
			{
				//throw new RuntimeException("Plan info sheet data not following standard '=' for " +param);
			}
			}
		}
		return planInfoProperties;
	
	}

	protected static int getTotalFloorCount(DXFDocument dxfDocument, Integer colorCode) {

		int i = 0;
		Iterator dxfLayerIterator = dxfDocument.getDXFLayerIterator();
		while (dxfLayerIterator.hasNext()) {

			DXFLayer dxfLayer = (DXFLayer) dxfLayerIterator.next();

			if ((colorCode != null && dxfLayer.getColor() == colorCode)
					|| dxfLayer.getName().startsWith(FLOOR_NAME_PREFIX)) {
				i++;
			}

		}

		return i;
	}

	protected static int getFloorCountExcludingCeller(DXFDocument dxfDocument, Integer colorCode) {
		int i = 0;
		Iterator dxfLayerIterator = dxfDocument.getDXFLayerIterator();
		while (dxfLayerIterator.hasNext()) {

			DXFLayer dxfLayer = (DXFLayer) dxfLayerIterator.next();

			if ((colorCode != null && dxfLayer.getColor() == colorCode)
					|| dxfLayer.getName().startsWith(FLOOR_NAME_PREFIX)) {
				try {

					if (colorCode != null && dxfLayer.getColor() == colorCode) {
						i++;
					} else {
						String[] floorName = dxfLayer.getName().split(FLOOR_NAME_PREFIX);
						if (floorName.length > 0 && floorName[1] != null && Integer.parseInt(floorName[1]) >= 0) {
							i++;
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
					// throw new RuntimeException("Floor number not in format");
					// //TODO: HANDLE THIS LATER
				}

			}

		}

		return i;
	}

	public boolean pointsEquals(Point point1, Point point) {
		BigDecimal px=BigDecimal.valueOf(point.getX()).setScale(DECIMALDIGITS, BigDecimal.ROUND_DOWN);
		BigDecimal py=BigDecimal.valueOf(point.getY()).setScale(DECIMALDIGITS, BigDecimal.ROUND_DOWN);
		BigDecimal p1x=BigDecimal.valueOf(point1.getX()).setScale(DECIMALDIGITS, BigDecimal.ROUND_DOWN);
		BigDecimal p1y=BigDecimal.valueOf(point1.getY()).setScale(DECIMALDIGITS, BigDecimal.ROUND_DOWN);
		if(px.compareTo(p1x)==0 && py.compareTo(p1y)==0)
			return true;
		else 
			return false;
	}

}
