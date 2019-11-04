import org.apache.commons.codec.digest.DigestUtils;

public class Test {
    public static void main(String[] args) {

        String sign = DigestUtils.md5Hex(DigestUtils.md5Hex("EWA2X36VO8UC7GYUBBPLEOVA5X13OVYI") + DigestUtils.md5Hex("fafafa" + "1.00" + "2" + "P201910232109024216828"));

        System.out.println(sign);
        //String sing = DigestUtils.md5Hex("EWA2X36VO8UC7GYUBBPLEOVA5X13OVYI")+DigestUtils.md5Hex("fafafa"+"zfb"+orderid));
    }
}
