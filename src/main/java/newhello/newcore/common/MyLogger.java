package newhello.newcore.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
//@Scope("request")
/**
 * 프록시 객체를 생성시켜놓으니 컨트롤러에 이걸 주입시켜서 일단 bean이 생성되게 하고,
 * 실제 객체가 생성되는 시점에 CGLIB(바이트코드 조작 라이브러리)에서 바꿔치기(위임)를 해준다
 * Provider 대비, 리퀘스트스코프 자신에만 적용해놓으면 된다는 큰 장점이 있다
 */
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    /**
     * request web scope는 HTTP 요청당 하나씩 생성되고, HTTP 요청이 끝나는 시점에 소멸된다.
     * ㄴ> prototype scope와 달리, 종료도 컨테이너가 관리
     */

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "] [" + requestURL + "] " + message);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create: " + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close: " + this);
    }
}
