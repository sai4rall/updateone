import org.apache.hadoop.shaded.org.eclipse.jetty.websocket.common.frames.DataFrame;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;

import java.util.regex.Pattern;

public class testUtil {
    public static void main(String[] args) {
//        System.out.println(Pattern.matches("[a-zA-Z]{1}[0-9]{5}", "metro"));
        SparkSession session=SparkSession.builder().appName("Udmf-data-migration")
                .master("local[1]")
                .getOrCreate();

        Dataset df=session.read()
                .option("header", "true").csv("C:\\Users\\sai\\Downloads\\spark2\\updateone\\src\\main\\resources\\test.csv");
        df.show();
        Dataset df2=df.where(df.col("name").rlike("[a-zA-Z]{1}[0-9]{3}"));
        df2.show();

    }
}
