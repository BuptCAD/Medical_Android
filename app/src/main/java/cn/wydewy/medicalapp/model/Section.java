package cn.wydewy.medicalapp.model;

import org.litepal.crud.DataSupport;

/**
 * Created by wangyuan on 2016/11/11.
 */

public class Section extends DataSupport {
    private String sectionId;
    private String hosId;
    private String sectionCode;
    private String sectionName;

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getHosId() {
        return hosId;
    }

    public void setHosId(String hosId) {
        this.hosId = hosId;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionIntro() {
        return sectionIntro;
    }

    public void setSectionIntro(String sectionIntro) {
        this.sectionIntro = sectionIntro;
    }

    public String getSectionPic() {
        return sectionPic;
    }

    public void setSectionPic(String sectionPic) {
        this.sectionPic = sectionPic;
    }

    public String getSectionLoc() {
        return sectionLoc;
    }

    public void setSectionLoc(String sectionLoc) {
        this.sectionLoc = sectionLoc;
    }

    private String sectionIntro;
    private String sectionPic;
    private String sectionLoc;
}
