package soon.PTCMR_Back.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import soon.PTCMR_Back.domain.member.service.MemberService;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

}
