package com.github.rezaep.evcsms.station.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.rezaep.evcsms.company.domain.entity.Company;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@Table(name = "station")
@SequenceGenerator(name = "station_seq", allocationSize = 1)
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "station_seq")
    private long id;
    @NotNull
    private String name;
    private Point location;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Company company;

    public Station(String name, Point location, Company company) {
        this.name = name;
        this.location = location;
        this.company = company;
    }
}
