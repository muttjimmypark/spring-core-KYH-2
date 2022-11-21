package newhello.newcore.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {
    @Test
    void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {

        @Bean
//        @Bean(initMethod = "init", destroyMethod = "close") //before inffered
//        @Bean(initMethod = "init") //방법2 inffered
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("https://hello.dev");
            return networkClient;

            /**
             * Bean은 객체 생성 후 의존관계가 주입되는 라이프사이클을 가진다
             * 그렇게 중요한 의존관계면 생성자로 넣어주도록 만드는게 좋겠지만
             * 완전한 SRP 구현을 위해 (생성자는 필수 파라미터 수신, 메모리 할당만)
             * 별도의 초기화 메서드를 구성해두는 것이 좋다. (객체 생성과 별개의 무거운 작업이다)
             *
             * 설정해둔 의존관계주입이 완료되면 Bean에게 콜백 메서드를 통해 초기화 하라는 시점을 알려주는 기능이 있다.
             * 마찬가지로 스프링컨테이너가 종료되기 직전에 소멸시점을 알려주는 콜백도 있다.
             *
             * 스프링컨테이너 생성 -> 스프링빈 생성 -> 의존관계주입 -> 초기화 콜백 -> 사용 -> 소멸전 콜백 -> 스프링 종료
             *
             * 데이터베이스 커넥션 풀, 네트워크 소켓 등
             * 애플리케이션 시작시점에 필요한 연결을 미리 해두고, 종료시점에 연결을 모두 종료해야하는 작업에 활용된다.
             */
        }
    }
}
