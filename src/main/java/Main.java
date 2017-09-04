import com.baidu.translate.TransApi;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

public class Main {

    // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
//    private static final String APP_ID = "";
//    private static final String SECURITY_KEY = "";

    public static void main(String[] args) {
//        TransApi api = new TransApi(APP_ID, SECURITY_KEY);
        String query = "我想静静";
        System.out.println(TransApi.trans2EN(query));

        System.out.println("res====");
        System.out.println(TransApi.trans2ENRes(query));
    }

}
