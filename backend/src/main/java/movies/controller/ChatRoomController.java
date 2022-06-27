package movies.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import movies.Utils.RoomUtil;
import movies.config.JwtTokenProvider;
import movies.domain.ChatRoom;
import movies.repository.ChatRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
@Slf4j
public class ChatRoomController {

    private final ChatRepository chatRoomRepository;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * main Page 보여주기
     */
    @GetMapping("/room")
    public String rooms(Model model, HttpServletRequest servletRequest, HttpSession session) {

        String token = String.valueOf(servletRequest.getParameter("token"));

        String name = jwtTokenProvider.getAuthenticationUsername(token);

        log.info("아아아" + token);
        
        model.addAttribute("name", name);

        session.setAttribute("name", name);

        return "chat/room";
    }

    /**
     * 현재 있는 모든 채팅방 반환
     */
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return chatRoomRepository.findAllRoom();
    }

    /**
     * 채팅 룸 생성
     */
    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {

        return chatRoomRepository.createChatRoom(name);
    }

    /**
     * 채팅방 Page 리턴
     */
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {

        String roomKey = roomId;
        log.info("아ㅏ" + roomKey);
        if (RoomUtil.hasRoom(roomKey) == 0) {
            RoomUtil.setNewRoomInRoomCountMap(roomKey);
        }
        int memberCount = RoomUtil.getRoomMemberCount(roomKey);

        if (memberCount > 1) {
            log.info("접근 불가 사용자 초과");

            model.addAttribute("msg", "채팅방 인원이 가득 차 메인 화면으로 이동합니다");
            model.addAttribute("url", "http://mobee13.s3-website.ap-northeast-2.amazonaws.com/");
            return "chat/alert";
        }

        return "chat/roomdetail";
    }



    /**
     * 채팅방 번호로 찾기
     */
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {

        return chatRoomRepository.findRoomById(roomId);
    }
}