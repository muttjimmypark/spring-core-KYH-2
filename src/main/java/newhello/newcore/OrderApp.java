package newhello.newcore;

import newhello.newcore.member.Grade;
import newhello.newcore.member.Member;
import newhello.newcore.member.MemberService;
import newhello.newcore.member.MemberServiceImpl;
import newhello.newcore.order.Order;
import newhello.newcore.order.OrderService;
import newhello.newcore.order.OrderServiceImpl;

public class OrderApp {

    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        OrderService orderService = new OrderServiceImpl();

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = " + order);
    }
}
