package newhello.newcore.autowired;

import newhello.newcore.AutoAppConfig;
import newhello.newcore.discount.DiscountPolicy;
import newhello.newcore.member.Grade;
import newhello.newcore.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class AllBeanTest {

    @Test
    void findAllBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "userA", Grade.VIP);
        assertThat(discountService).isInstanceOf(DiscountService.class);

        int discountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");
        assertThat(discountPrice).isEqualTo(1000);

        discountPrice = discountService.discount(member, 20000, "rateDiscountPolicy");
        assertThat(discountPrice).isEqualTo(2000);
    }

    //Bean들 중에 DiscountPolicy로 작성된 것들을 Autowired하여 Map이나 List로 취합할 수 있다
    static class DiscountService {
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies;

        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }

        public int discount(Member member, int price, String policy) {
            DiscountPolicy discountPolicy = policyMap.get(policy);
            return discountPolicy.discount(member, price);
        }
    }
}
