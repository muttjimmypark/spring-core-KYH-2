package newhello.newcore;

import newhello.newcore.discount.DiscountPolicy;
import newhello.newcore.discount.FixDiscountPolicy;
import newhello.newcore.discount.RateDiscountPolicy;
import newhello.newcore.member.MemberRepository;
import newhello.newcore.member.MemberService;
import newhello.newcore.member.MemberServiceImpl;
import newhello.newcore.member.MemoryMemberRepository;
import newhello.newcore.order.OrderService;
import newhello.newcore.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//configuration 애노테이션이 다루는 bean들은 singleton 보장하는 cglib 기술이 적용된다
@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService() {
        System.out.println("AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        System.out.println("AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        System.out.println("AppConfig.discountPolicy");
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

}
