package newhello.newcore.singleton;

import newhello.newcore.AppConfig;
import newhello.newcore.member.MemberRepository;
import newhello.newcore.member.MemberServiceImpl;
import newhello.newcore.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository mr1 = memberService.getMemberRepository();
        MemberRepository mr2 = orderService.getMemberRepository();

        assertThat(mr1)
                .isSameAs(mr2)
                .isSameAs(memberRepository);
        System.out.println("memberService -> memberRepository : " + mr1);
        System.out.println("orderService -> memberRepository " + mr2);
        System.out.println("memberRepository = " + memberRepository);
    }

    @Test
    void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean.getClass() = " + bean.getClass());
    }
}
