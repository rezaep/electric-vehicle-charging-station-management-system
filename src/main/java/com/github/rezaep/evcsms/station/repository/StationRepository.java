package com.github.rezaep.evcsms.station.repository;

import com.github.rezaep.evcsms.station.domain.entity.Station;
import com.github.rezaep.evcsms.station.domain.model.StationWithDistanceModel;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface StationRepository extends JpaRepository<Station, Long> {
    //    @Query("from Station st where within(st.location, :area )=true order by distance(st.location, :center)")
    @Query("SELECT new com.github.rezaep.evcsms.station.domain.model.StationWithDistanceModel(" +
            "st.id, st.name, st.location, st.company.id, distance(st.location, :center)" +
            ") from Station st where within(st.location, :area)=true " +
            "and ((:companyIds) IS NULL OR (st.company.id in (:companyIds)))" +
            "order by distance(st.location, :center)")
    List<StationWithDistanceModel> findWithDistance(@Param("companyIds") Set<Long> companyIds
            , @Param("area") Geometry area, @Param("center") Geometry center);

//    @Query(value = "from Station st where distance(st.location, :point) < :distance ORDER BY distance(st.location, :point) ASC")
//    List<Station> findWithin(@Param("point") Point point, @Param("distance") double distance);

}
