package by.gradomski.threads.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TruckParser {
    private static Logger logger = LogManager.getLogger();
    private static final String lineDelim = ";";
    private static final String paramDelim = " ";

    public List<int[]> parse(String string){
        List<int[]> resultList = new ArrayList<>();
        String[] trucks = string.split(lineDelim);
        for(String s : trucks){
            logger.info("string from file:" + s);
            String[] stringParameters = s.split(paramDelim);
            int[] intParameters = new int[stringParameters.length];
            for(int i = 0; i < intParameters.length; i++){
                intParameters[i] = Integer.parseInt(stringParameters[i]);
            }
            resultList.add(intParameters);
        }
        return resultList;
    }
}
