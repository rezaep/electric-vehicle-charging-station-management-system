package com.github.rezaep.evcsms.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.locationtech.jts.geom.Point;

import static org.assertj.core.api.Assertions.assertThat;

public class GeoUtilsTest {
    @ParameterizedTest
    @CsvSource({"35.696898, 51.401939",
            "35.694110, 51.410788",
            "35.703032, 51.397040",
            "35.724218, 51.355748",
            "35.704147, 51.416975"})
    public void shouldCreatePointConvertLatitudeAndLongitudeToPoint(double latitude, double longitude) {
        Point point = GeoUtils.createPoint(latitude, longitude);

        assertThat(point.getX()).isEqualTo(longitude);
        assertThat(point.getY()).isEqualTo(latitude);
    }
}
