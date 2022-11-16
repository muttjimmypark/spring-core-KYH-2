package newhello.newcore.order;

import newhello.newcore.discount.FixDiscountPolicy;
import newhello.newcore.member.Grade;
import newhello.newcore.member.Member;
import newhello.newcore.member.MemberRepository;
import newhello.newcore.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    @Test
    void createOrder() {
        MemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "name", Grade.VIP));

        //스프링에 의존하지않는 순수자바 작성 단계에서 테스트가 돌아갈수 있다.
        //의존관계를 생성자주입형태로 만드는것이 아래줄 작성때 단순컴파일에러만 발생시킬수 있다.
        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy());
        Order order = orderService.createOrder(1L, "itemA", 10000);
        assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}