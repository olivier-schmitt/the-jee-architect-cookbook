/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thejeearchitectcookbook.camel.earthquake;

import java.io.Serializable;
import java.util.Date;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

/**
 *
 * @author oschmitt
 */
@CsvRecord( separator = "\"?,\"?",skipFirstLine=true)
public class EarthquakeInfos implements Serializable {
    
	@DataField(pos = 1)
    private String src;
	
	@DataField(pos = 2)
    private String eqid;
	
	@DataField(pos = 3)
    private String version;
	
    @DataField(pos = 4)
    private String datetime;
    
	@DataField(pos = 5)
    private String lat;
	
	@DataField(pos = 6)
    private String lon;
    
    @DataField(pos = 7)
    private String magnitude;
    
	@DataField(pos = 8)
    private String depth;
	
	@DataField(pos = 9)
    private String nst;
	
    @DataField(pos = 10)
    private String place;
    



    public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getEqid() {
		return eqid;
	}

	public void setEqid(String eqid) {
		this.eqid = eqid;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	public String getNst() {
		return nst;
	}

	public void setNst(String nst) {
		this.nst = nst;
	}

	public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}