package com.github.rezaep.evcsms.util;

import org.assertj.core.data.Offset;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;

import static org.assertj.core.api.Assertions.assertThat;

class GeoUtilsTest {
    @ParameterizedTest
    @CsvSource({"40.72309177, -73.84421522",
            "40.79411067, -73.81867946",
            "40.71758074, -73.9366077",
            "40.71353749, -73.93445616",
            "40.66677776, -73.97597938"})
    void shouldCreatePointConvertLatitudeAndLongitudeToPoint(double latitude, double longitude) {
        Point point = GeoUtils.createPoint(latitude, longitude);

        assertThat(point.getX()).isEqualTo(longitude);
        assertThat(point.getY()).isEqualTo(latitude);
    }

    @ParameterizedTest
    @CsvSource({"40.72309177, -73.84421522, 100",
            "40.79411067, -73.81867946, 1000",
            "40.71758074, -73.9366077, 500",
            "40.71353749, -73.93445616, 12300",
            "40.66677776, -73.97597938, 1000000"})
    void shouldCreatePointConvertLatitudeAndLongitudeToPoint(double latitude, double longitude, int radius) {
        Point center = GeoUtils.createPoint(latitude, longitude);
        Geometry area = GeoUtils.createCircle(center, radius);

        assertThat(area.getCentroid().getX()).isEqualTo(longitude, Offset.offset(0.00000001));
        assertThat(area.getCentroid().getY()).isEqualTo(latitude, Offset.offset(0.00000001));
    }
}