package com.github.rezaep.evcsms.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.util.GeometricShapeFactory;

public class GeoUtils {
    public static double convertMeterToRadius(double meter) {
        return meter * 0.00001 / 0.941;
    }

    public static Point createPoint(double latitude, double longitude) {
        return new GeometryFactory().createPoint(new Coordinate(longitude, latitude));
    }

    public static Geometry createCircle(Point center, double meter) {
        double radius = GeoUtils.convertMeterToRadius(meter);
        GeometricShapeFactory shapeFactory = new GeometricShapeFactory();
        shapeFactory.setNumPoints(32);
        shapeFactory.setCentre(center.getCoordinate());
        shapeFactory.setSize(radius * 2);
        return shapeFactory.createCircle();
    }
}
