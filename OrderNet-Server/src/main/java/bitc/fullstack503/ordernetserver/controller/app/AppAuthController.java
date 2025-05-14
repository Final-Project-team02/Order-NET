package bitc.fullstack503.ordernetserver.controller.app;


import bitc.fullstack503.ordernetserver.dto.LoginRequestDto;
import bitc.fullstack503.ordernetserver.dto.LoginResponseDto;
import bitc.fullstack503.ordernetserver.entity.UserAccount;
import bitc.fullstack503.ordernetserver.service.JwtTokenProvider;
import bitc.fullstack503.ordernetserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app")
public class AppAuthController {


    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    //    앱 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {

        // 로그 출력
        System.out.println("로그인 요청 아이디: " + loginRequestDto.getUserId());
        System.out.println("로그인 요청 비밀번호: " + loginRequestDto.getUserPw());
        System.out.println("로그인 요청 유저타입: " + loginRequestDto.getUserType());


        UserAccount user = userService.findByUserId(loginRequestDto.getUserId());

        // userType이 대리점일 때만 branchSupervisor 포함
        String branchSupervisor = user.getUserType().equals("대리점") ? user.getBranchSupervisor() : null;
        String branchName = user.getUserType().equals("대리점") ? user.getBranchName() : null;
        System.out.println("이름: " + branchSupervisor);
        System.out.println("이름: " + branchName);

        // userType이 물류센터일 때만 warehouseName 포함
        String warehouseName = user.getUserType().equals("물류센터") ? user.getWarehouseName() : null;
        System.out.println("이름: " + warehouseName);

//        if (user == null || !passwordEncoder.matches(loginRequestDto.getUserPw(), user.getUserPw()) || !user.getUserType().equals(loginRequestDto.getUserType())) {
        if (user == null || !loginRequestDto.getUserPw().equals(user.getUserPw()) || !user.getUserType().equals(loginRequestDto.getUserType())) {
            System.out.println("로그인 실패: 아이디/비밀번호/유저타입 불일치");
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String token = jwtTokenProvider.createToken(user.getUserId(), user.getUserType());
        return ResponseEntity.ok(new LoginResponseDto(token, user.getUserType(), user.getUserRefId(), branchSupervisor, branchName, warehouseName));
    }
}
