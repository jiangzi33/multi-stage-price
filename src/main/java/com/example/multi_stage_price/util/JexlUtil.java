package com.example.multi_stage_price.util;

import com.example.multi_stage_price.entity.TotalDuration;
import org.apache.commons.jexl3.*;

import java.beans.Expression;

public class JexlUtil {
    public static int getStage(String rule, int totalDuration){
        JexlEngine jexl = new JexlBuilder().create();
        JexlExpression e = jexl.createExpression(rule);
        JexlContext jc = new MapContext();
        jc.set("totalDuration",totalDuration);

        Object o = e.evaluate(jc);
        return (int) o;
    }

    public static int getAmount(String rule, int totalDuration){
        JexlEngine jexl = new JexlBuilder().create();
        JexlExpression e = jexl.createExpression(rule);
        JexlContext jc = new MapContext();
        jc.set("totalDuration",totalDuration);

        Object o = e.evaluate(jc);
        return (int) o;
    }

}
