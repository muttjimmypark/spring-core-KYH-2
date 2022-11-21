package newhello.newcore.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

//public class NetworkClient implements InitializingBean, DisposableBean {
public class NetworkClient {
    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
//        connect();
//        call("초기화 연결 메시지");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //서비스 시작시 호출
    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) {
        System.out.print("call: " + url);
        System.out.println(" message = " + message);
    }

    //서비스 종료시 호출
    public void disconnect() {
        System.out.println("close: " + url);
    }


    /**
     * 생명주기 콜백 적용방법 1 시작
     */
    // 생명주기 콜백을 위한 인터페이스 구체화 부분
//     코드가 스프링에 의존하게 된다는 단점
    // 스프링 초창기에 나온 방법이며, 근래는 이 대신 더 나은 방법을 사용한다
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("NetworkClient.afterPropertiesSet");
//        connect();
//        call("초기화 연결 메시지");
//    }
//
//    @Override
//    public void destroy() throws Exception {
//        System.out.println("NetworkClient.destroy");
//        disconnect();
//    }

    /**
     * 1 끝
     * 2 시작
     */

    // Bean등록시 설정정보를 입력해주는 방법
    // 해당 메서드들이 스프링에 의존하지 않게되며, 이름을 자유롭게 부여할수 있다.
    // 내가 코드에 손댈수없는 외부라이브러리에도 초기화-종료 메서드를 적용할수 있다.
    // destroyuMethod 속성은 default값으로 close나 shut을 받아줘서 (infferred 추론), 생략이 가능하다
    public void init() {
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    public void close() {
        System.out.println("NetworkClient.close");
        disconnect();
    }
}
