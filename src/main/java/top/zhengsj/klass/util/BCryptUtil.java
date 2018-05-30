package top.zhengsj.klass.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;

@Component
public class BCryptUtil {
    private static BCryptPasswordEncoder encoder;

    /**
     * 对字符串加密
     */
    public static String encode(String str) {
        return encoder.encode(str);
    }

    /**
     * 验证
     */
    public static boolean check(String plain, String hashed) {
        return encoder.matches(plain, hashed);
    }

    @PostConstruct
    public void setup() {
        encoder = new BCryptPasswordEncoder(5);
    }

    public static void main(String[] args) {
        encoder = new BCryptPasswordEncoder(5);

        System.out.println(encode("pass"));

//        String name = "test";
//        for (int i = 1; i < 4; i++) {
//            String test = name + i;
//            String pass = encode(test);
//            System.out.println(test + "," + pass);
//        }
    }
}
