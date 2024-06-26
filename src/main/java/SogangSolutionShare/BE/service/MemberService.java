package SogangSolutionShare.BE.service;

import SogangSolutionShare.BE.domain.Member;
import SogangSolutionShare.BE.domain.dto.JoinDTO;
import SogangSolutionShare.BE.domain.dto.MemberDTO;
import SogangSolutionShare.BE.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;


    /*
     * 회원가입 비즈니스 로직
     */
    @Transactional
    public String createMember(JoinDTO joinDTO) {
        // 같은 로그인 아이디 혹은 같은 이메일을 가진 Member 이미 존재하면 예외처리
        if(memberRepository.findByEmail(joinDTO.getEmail()).isPresent()) {
            return "이미 가입된 이메일입니다.";
        }
        else if(memberRepository.findByLoginId(joinDTO.getLoginId()).isPresent()) {
            return "이미 가입된 로그인 아이디입니다.";
        }

        // Member 생성하고 저장
        Member createdMember = Member.builder()
                .loginId(joinDTO.getLoginId())
                .password(joinDTO.getPassword())
                .email(joinDTO.getEmail())
                .name(joinDTO.getName())
                .isAuthorized(joinDTO.getIsAuthorized())
                .build();

        log.info("Member created: {}", createdMember);

        memberRepository.save(createdMember);

        return "회원가입에 성공하셨습니다.";
    }


    public Member login(String loginId, String password) {
        Member member = memberRepository.findByLoginId(loginId).orElse(null);

        // 로그인 실패
        if(member == null || !password.equals(member.getPassword())) {
            return null;
        }
        // 로그인 성공
        return member;
    }

    @Transactional
    public String updateMember(Long memberId, MemberDTO memberDTO) {
        Optional<Member> member = memberRepository.findById(memberId);

        if(member.isEmpty()) {
            return "해당하는 회원이 존재하지 않습니다.";
        }

        member.get().setEmail(memberDTO.getEmail());
        member.get().setName(memberDTO.getName());

        log.info("Member updated: {}", member);

        return "회원 정보 수정에 성공하셨습니다.";
    }

    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    public List<MemberDTO> getMembers(){
        List<Member> memberList = memberRepository.findAll();
        return memberList.stream()
                .map(Member::toDTO)
                .collect(Collectors.toList());
    }


    public MemberDTO getMember(Long id) {
        Optional<Member> member = memberRepository.findById(id);

        return member.map(Member::toDTO).orElse(null);

    }

    public String updatePassword(Long id, String password) {
        Optional<Member> member = memberRepository.findById(id);

        if(member.isEmpty()) {
            return "해당하는 회원이 존재하지 않습니다.";
        }

        member.get().setPassword(password);

        log.info("Member updated: {}", member);

        return "비밀번호 수정에 성공하셨습니다.";
    }
}
