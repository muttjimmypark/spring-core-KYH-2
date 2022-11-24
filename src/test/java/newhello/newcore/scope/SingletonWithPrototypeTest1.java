package newhello.newcore.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest1 {

    /**
     * getbean()으로 하나씩 뽑아낼때마다 새로운 bean을 얻게되므로
     * 별개의 동작이 이루어진다.
     * 대조군격 테스트
     */
    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean pb1 = ac.getBean(PrototypeBean.class);
        pb1.addCount();
        assertThat(pb1.getCount()).isEqualTo(1);

        PrototypeBean pb2 = ac.getBean(PrototypeBean.class);
        pb2.addCount();
        assertThat(pb2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class, ClientBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean1.logic();
//        assertThat(count2).isEqualTo(2);
        assertThat(count2).isEqualTo(1);
    }

    /**
     * 생성시점에 싱글톤빈으로 의존관계 주입된 프로토타입빈은 주입시점에 들어가게됨
     * 하지만 프로토타입빈을 처음 설계했을때 이런걸 의도하지 않았을것
     */
//    static class ClientBean {
//        private final PrototypeBean prototypeBean;
//
//        @Autowired
//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }
//
//        public int logic() {
//            prototypeBean.addCount();
//            return prototypeBean.getCount();
//        }
//    }

    /**
     * Provider라는 기능을 사용해서
     * 메서드가 필요로하는 시점마다 프로토타입빈을 생성해서 갖다쓸수 있다.
     * 의존성주입 DI와 반대되는 의존성탐색 DL (dependency lookup)이 특정상황에서 수행됨.
     */
    static class ClientBean {
        @Autowired //필드주입
        private ObjectProvider<PrototypeBean> prototypeBeanProvider;

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    /**
     * spring 기능이 아닌 java표준 Provider를 사용할수 있다.
     * implementation 'javax.inject:javax.inject:1' 라이브러리를 빌드에 추가해야 한다
     * 기능이 get() 하나다
     */
//    static class ClientBean {
//        @Autowired //필드주입
//        private Provider<PrototypeBean> prototypeBeanProvider;
//
//        public int logic() {
//            PrototypeBean prototypeBean = prototypeBeanProvider.get();
//            prototypeBean.addCount();
//            return prototypeBean.getCount();
//        }
//    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
