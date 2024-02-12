package org.example.youth_be.user.controller.spec;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.youth_be.artwork.enums.ArtworkMyPageType;
import org.example.youth_be.artwork.service.request.ArtworkPaginationRequest;
import org.example.youth_be.common.ApiTags;
import org.example.youth_be.common.PageResponse;
import org.example.youth_be.common.jwt.TokenClaim;
import org.example.youth_be.user.service.request.UserAdditionSignupRequest;
import org.example.youth_be.user.service.request.UserSignupRequest;
import org.example.youth_be.user.service.request.LinkRequest;
import org.example.youth_be.user.service.request.UserProfileUpdateRequest;
import org.example.youth_be.artwork.service.response.ArtworkResponse;
import org.example.youth_be.user.service.response.UserMyInformation;
import org.example.youth_be.user.service.response.UserProfileResponse;
import org.example.youth_be.user.service.response.UserSignUpResponse;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = ApiTags.USER)
public interface UserSpec {
    @Operation(description = "회원가입 API [값을 넣지 않으면 서버에서 임의로 넣습니다.]")
    Long signup(UserSignupRequest request);

    @Operation(description = "닉네임 중복 체크 API")
    void checkNicknameDuplicate(String nickname);

    @Operation(description = "유저 프로필 조회 API")
    UserProfileResponse getUserProfile(Long userId);

    @Operation(description = "유저 프로필 수정 API")
    void updateUserProfile(Long userId, UserProfileUpdateRequest request);

    @Operation(description = "유저 링크 생성 API")
    Long createUserLink(Long userId, LinkRequest request);

    @Operation(description = "유저 링크 삭제 API")
    void deleteUserLink(Long userId, Long linkId);

    @Operation(description = "유저의 작품 조회 API")
    PageResponse<ArtworkResponse> getUserArtworks(Long userId, ArtworkMyPageType type, ArtworkPaginationRequest request);

    @Operation(description = "유저 추가 회원 가입 api")
    UserSignUpResponse signUp(TokenClaim tokenClaim, UserAdditionSignupRequest request);
}
