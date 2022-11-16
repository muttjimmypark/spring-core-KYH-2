package newhello.newcore.order;

import lombok.RequiredArgsConstructor;
import newhello.newcore.discount.DiscountPolicy;
import newhello.newcore.discount.FixDiscountPolicy;
import newhello.newcore.discount.RateDiscountPolicy;
import newhello.newcore.member.Member;
import newhello.newcore.member.MemberRepository;
import newhello.newcore.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    //    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    //lombok 적용으로 주석화
//    @Autowired
//    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

    /**
     * 의존관계 주입 방법
     * 1. 생성자 주입 : 가장 권장됨. @Autowired 애노테이션 생략 가능
     * 2. 수정자 주입 : 의존관계에 선택성, 변경가능성이 있는 간헐적 경우에 사용됨
     * 3. 필드 주입 : @Autowired를 필드에 부여하면 돼서 제일 간편하지만, 스프링 개입없는 테스트 등에서 문제발생할 여지로 추천되지 않음.
     * 4. 일반 메서드 주입 : 주입기능을 하는 메서드를 별도 만드는 방법. 가능은 하다 정도만 알고 넘어가자. 안쓰인다.
     */

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //for test
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
