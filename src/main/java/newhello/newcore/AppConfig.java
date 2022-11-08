package newhello.newcore;

import newhello.newcore.discount.FixDiscountPolicy;
import newhello.newcore.member.MemberService;
import newhello.newcore.member.MemberServiceImpl;
import newhello.newcore.member.MemoryMemberRepository;
import newhello.newcore.order.OrderService;
import newhello.newcore.order.OrderServiceImpl;

public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }

}
