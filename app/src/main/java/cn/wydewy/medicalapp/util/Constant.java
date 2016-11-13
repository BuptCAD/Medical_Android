package cn.wydewy.medicalapp.util;

/**
 * Created by wangyuan on 2016/11/11.
 */
public class Constant {
    public static final String DATA_BASE_NAME = "BY_medical";
    public static final String IS_LOG_IN = "is_log_in";
    public static final String HOST = "http://172.20.25.105:8080";
    public static final String API_LOG_IN = HOST + "/framework/customer/login";
    public static final String API_REGISTER = HOST + "/framework/customer/register";
    public static final String API_SECTIONS = HOST + "/framework/hospital/hospital_section_list/";
    public static final String API_OUTPATIENTS = HOST + "/framework/section/section_outpatient_list";
    public static final String API_RELEASENUM = HOST + "/framework/outpatient/outpatient_releasenum_list";


    public static final String SECTION_ID = "sectionId";
    public static final String OUTPATIENT_ID = "outpatientId";
    public static final String RELEASE_ID = "releasId";
}
