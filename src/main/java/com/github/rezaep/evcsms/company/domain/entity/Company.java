package com.github.rezaep.evcsms.company.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.rezaep.evcsms.station.domain.entity.Station;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.exsio.nestedj.model.NestedNode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "company")
@SequenceGenerator(name = "company_seq", allocationSize = 1)
@JsonIgnoreProperties(
        value = {"treeLeft", "treeRight", "treeLevel"},
        allowGetters = true
)
public class Company implements NestedNode<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_seq")
    private Long id;
    @NotNull
    private String name;

    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "tree_left", nullable = false)
    private Long treeLeft;
    @Column(name = "tree_right", nullable = false)
    private Long treeRight;
    @Column(name = "tree_level", nullable = false)
    private Long treeLevel;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Station> stations;

    public Company(String name) {
        this.name = name;
    }
}