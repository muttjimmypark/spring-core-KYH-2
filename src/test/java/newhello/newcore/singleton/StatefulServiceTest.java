package newhello.newcore.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

        StatefulService ss1 = ac.getBean(StatefulService.class);
        StatefulService ss2 = ac.getBean(StatefulService.class);

        //ThreadA : A가 10000원 주문
        ss1.order("userA", 10000);
        //ThreadB : B가 20000원 주문
        ss2.order("userB", 20000);

        //사용자A 주문금액 조회
        int priceA = ss1.getPrice();
        System.out.println("priceA = " + priceA);
        //A의 주문 처리 중간에 B의 주문이 들어갔으면 덮어씌어지는 문제발생
        // -> 무상태로 설계해야 한다 : 공유되지 않는 지역변수를 사용 (order()돌리면 처리결과가 리턴된다던지)
    }

    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}