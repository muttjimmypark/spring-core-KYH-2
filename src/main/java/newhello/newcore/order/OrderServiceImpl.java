package newhello.newcore.order;

import lombok.RequiredArgsConstructor;
import newhello.newcore.annotation.MainDiscountPolicy;
import newhello.newcore.discount.DiscountPolicy;
import newhello.newcore.discount.FixDiscountPolicy;
import newhello.newcore.discount.RateDiscountPolicy;
import newhello.newcore.member.Member;
import newhello.newcore.member.MemberRepository;
import newhello.newcore.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    //    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    //lombok 적용으로 주석화
//    @Autowired
//    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy rateDiscountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = rateDiscountPolicy;

//        //조회할 타입의 bean이 여러개면, 필드명을 통해 특정할 수 있다
//    }

//    @Autowired
//    public OrderServiceImpl(MemberRepository memberRepository, @Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//
//        //Qualifier라는 애노테이션으로 주입할 정책을 구분할수 있다
//    }

//    @Autowired
//    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//
//        //구현체중에 @Primary 애노테이션 붙은걸 우선 주입 : 제일 많이 쓰임
//        // 우선권은 @Qualifier가 @Primary 보다 앞선다.
//    }

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;

        //@Qualifier를 사용하면 파라미터가 String이므로 컴파일단에서 오타를 파악할수 없음
        //애노테이션을 직접 만듬으로 해결할수 있다
    }

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
