package cn.LoveUO.util;

import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.beans.TypeMismatchException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
/**
 * 自定义编辑器
 * @author asus
 */
public class MyEditor extends PropertiesEditor {
    @Override
    public void setAsText(String source) throws IllegalArgumentException {

        SimpleDateFormat sdf=getDate(source);
        Date date=null;
        try {
            date = sdf.parse(source);
            setValue(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private SimpleDateFormat getDate(String source) {
        SimpleDateFormat sdf=null;
        if (Pattern.matches("\\d{4}/\\d{2}/\\d{2}",source)){
            sdf=new SimpleDateFormat("yyyy/MM/dd");
        }else if (Pattern.matches("\\d{4}-\\d{2}-\\d{2}",source)){
            sdf=new SimpleDateFormat("yyyy-MM-dd");
        }else if(Pattern.matches("\\d{4}\\d{2}\\d{2}",source)){
            sdf=new SimpleDateFormat("yyyyMMdd");
        }else {
            throw  new TypeMismatchException("",Date.class);
        }
        return  sdf;
    }
}
