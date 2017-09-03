import com.baidu.translate.demo.TransApi;

public class Main {

    // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    private static final String APP_ID = "20170903000080239";
    private static final String SECURITY_KEY = "iaHAFo0D7Wr1wNpS8ttZ";

    public static void main(String[] args) {
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);

        String query = "我想静静";
        System.out.println(api.getTransResult(query, "auto", "en"));
    }

}
