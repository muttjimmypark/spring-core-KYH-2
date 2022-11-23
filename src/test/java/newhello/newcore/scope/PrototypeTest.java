package newhello.newcore.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class PrototypeTest {

    /**
     * 프로토타입 스코프는 bean 생성~의존관계주입~초기화까지만 스프링 컨테이너가 관리하기 때문에
     * 종료콜백이 호출되지 않는것을 확인할 수 있다.
     *
     * 초기화까지 한 bean을 클라이언트에게 return하기 때문에
     * 컨테이너가 관리하지 않으며
     * 조회 이후 종료메서드를 비롯한 다른 어떤 호출이 필요하다면 클라이언트가 해야된다.
     */

    @Test
    void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        System.out.println("find pb1");
        PrototypeBean pb1 = ac.getBean(PrototypeBean.class);
        System.out.println("find pb2");
        PrototypeBean pb2 = ac.getBean(PrototypeBean.class);

        System.out.println("pb1 = " + pb1);
        System.out.println("pb2 = " + pb2);
        assertThat(pb1).isNotSameAs(pb2);

        ac.close();
    }

    @Scope("prototype")
    static class PrototypeBean {
        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
