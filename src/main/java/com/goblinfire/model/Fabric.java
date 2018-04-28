package com.goblinfire.model;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "FABRIC")
@Getter @Setter @NoArgsConstructor
public class Fabric {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PROTECTED)
    private Long _id;

    private String _name;
    private String _material;
    private float _length;
    private float _width;
    private String _season;
    private boolean _isLeftover;
    private boolean _isAllUsed;
    private String _brand;
    private String _location;
    private String _store;
    private String _era;
    private float _pricePerMeter;

    public Fabric(Long id, String name, String material, float length){
        this._id = id;
        this._name = name;
        this._material = material;
        this._length = length;
    }


    public Fabric(Long id, String name, String material, float length, float width, String season, boolean isLeftover,
                  boolean isAllUsed, String brand, String location, String store, String era, float pricePerMeter){
        this._id = id;
        this._name = name;
        this._material = material;
        this._length = length;
        this._width = width;
        this._season = season;
        this._isLeftover = isLeftover;
        this._isAllUsed = isAllUsed;
        this._brand = brand;
        this._location = location;
        this._store = store;
        this._era = era;
        this._pricePerMeter = pricePerMeter;
    }
}
