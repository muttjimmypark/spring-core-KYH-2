package newhello.newcore;

import newhello.newcore.member.MemberRepository;
import newhello.newcore.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
) //@Component 애노테이션 붙은 bean을 전부 자동로드 (앞서 작성한 다른AppConfig와 충돌을 막는 한줄을 넣은것
public class AutoAppConfig {
    //자동로드 하니까 내용 쓸일이 없음

//    @Bean(name = "memoryMemberRepository")
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
    /**
     * 위와 같이 수동 빈 등록을 실시하면
     * 자동 빈 등록보다 우선권을 가진다
     * 실제 돌려보면 오버라이딩 메시지를 확인할 수 있다.
     * 최근 빌드의 스프링부트에서는 충돌오류가 나는것을 기본값으로 하도록 변경됐다
     */
}

/**
 * 컴포넌트 스캔은 @Component 뿐만 아니라 다음과 내용도 추가로 대상에 포함한다.
 * @Component : 컴포넌트 스캔에서 사용
 * @Controlller : 스프링 MVC 컨트롤러에서 사용
 * @Service : 스프링 비즈니스 로직에서 사용
 * @Repository : 스프링 데이터 접근 계층에서 사용
 * @Configuration : 스프링 설정 정보에서 사용
 *
 * 컴포넌트 스캔의 용도 뿐만 아니라 다음 애노테이션이 있으면 스프링은 부가 기능을 수행한다.
 * @Controller : 스프링 MVC 컨트롤러로 인식
 * @Repository : 스프링 데이터 접근 계층으로 인식하고, 데이터 계층의 예외를 스프링 예외로 변환해준다.
 * @Configuration : 앞서 보았듯이 스프링 설정 정보로 인식하고, 스프링 빈이 싱글톤을 유지하도록 추가 처리를 한다.
 * @Service : 특별한 처리를 하지 않는다. 대신 개발자들이 핵심 비즈니스 로직이 여기에 있겠구나 라고 비즈니스 계층을 인식하는데 도움이 된다.
 */