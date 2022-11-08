package newhello.newcore;

import newhello.newcore.member.Grade;
import newhello.newcore.member.Member;
import newhello.newcore.member.MemberService;
import newhello.newcore.member.MemberServiceImpl;

public class MemberApp {

    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
//        MemberService memberService = new MemberServiceImpl();
        MemberService memberService = appConfig.memberService();
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member);
        System.out.println("find Member = " + findMember);
    }
}
