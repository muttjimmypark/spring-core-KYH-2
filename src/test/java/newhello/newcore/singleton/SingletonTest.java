package newhello.newcore.singleton;

import newhello.newcore.AppConfig;
import newhello.newcore.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class SingletonTest {
    @Test
    void pureContainer() {
        AppConfig appConfig = new AppConfig();

        //호출할때마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();
        MemberService memberService2 = appConfig.memberService();

        //참조값이 다름
        assertThat(memberService1).isNotSameAs(memberService2);
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);
    }

    @Test
    @DisplayName("singleton패턴을 적용한 객체 사용")
    void singletonServiceTest() {
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        assertThat(singletonService1).isSameAs(singletonService2);
        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);
    }

    @Test
    @DisplayName("스프링컨테이너는 싱글톤")
    void springContainer() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService ms1 = ac.getBean("memberService", MemberService.class);
        MemberService ms2 = ac.getBean("memberService", MemberService.class);

        assertThat(ms1).isSameAs(ms2);
        System.out.println("ms1 = " + ms1);
        System.out.println("ms2 = " + ms2);
    }
}
