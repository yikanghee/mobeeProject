//package movies.repository;
//
//import lombok.extern.slf4j.Slf4j;
//import movies.domain.ChatRoom;
//import movies.dto.ChatMessageDTO;
//import movies.service.SubscriberService;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.listener.ChannelTopic;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import java.util.*;
//
//@Slf4j
//@Component
//public class ChatRepository {
//
//    /** 채팅방(Topic)에 발행되는 메시지를 처리할 Listner **/
//    private final RedisMessageListenerContainer redisMessageListener;
//
//    /** Subscriver 전용 서비스**/
//    private final SubscriberService subscriberService;
//
//    /** Redis 로직 처리 **/
//    private static final String CHAT_ROOMS = "CHAT_ROOM";
//
//    private final RedisTemplate redisTemplate;
//    private final RedisTemplate<String, String> redisDB;
//    private HashOperations<String, String, ChatRoom> opsHashChatRoom;
//
//    /**
//     * 채팅방의 대화 메시지를 발행하기 위한 redis topic 정보.
//     * 서버별로 채팅방에 매치되는 topic정보를 Map에 넣어 roomId로 찾을수 있도록 한다.
//     */
//    private Map<String, ChannelTopic> topics = new HashMap<>();
//
//    public ChatRepository(RedisMessageListenerContainer redisMessageListener, @Lazy SubscriberService subscriberService, RedisTemplate redisTemplate, RedisTemplate<String, String> redisDB) {
//        this.redisMessageListener = redisMessageListener;
//        this.subscriberService = subscriberService;
//        this.redisTemplate = redisTemplate;
//        this.redisDB = redisDB;
//    }
//
//
//    /** @PostConstruct는 의존성 주입이 이루어진 후 초기화를 수행하는 메서드이다 **/
//    @PostConstruct
//    private void init(){
//        /** 1. redisTemplate에서 operation 받기 **/
//        opsHashChatRoom = redisTemplate.opsForHash();
//    }
//
//    /** hashmap을 다루는 key에 해당하는 value값 모두 가져오기
//     * CHAT_ROOM 을 key로하는 hashmap에
//     * 모든 value(chatRoom 객체)를 받아온다.
//     */
//    public List<ChatRoom> findAllRoom() {
//        return opsHashChatRoom.values(CHAT_ROOMS);
//    }
//
//    /** 값 가져오기
//     * CHAT_ROOM 을 key로하는 hashmap에
//     * roomId를 key로 하는 value(chatRoom 객체)를 받아온다.
//     */
//    public ChatRoom findRoomById(String id) {
//        return opsHashChatRoom.get(CHAT_ROOMS, id);
//    }
//
//    Random random = new Random();
//
//    /**
//     * 채팅방 생성
//     */
//    public ChatRoom createChatRoom(String name){
//        ChatRoom chatRoom = new ChatRoom();
//
//        String UU = UUID.randomUUID().toString();
//        String random = UU.substring(15);
//
//        chatRoom.setId(1l);
//        chatRoom.setRoomId(random);
//        chatRoom.setName(name);
//
//        /** (1) 값넣기
//         * CHAT_ROOM 을 key로하는 hashmap에
//         * chatRoom 객체의 room id를 key로
//         * chatRoom 객체를 value로 저장한다 **/
//        opsHashChatRoom.put(CHAT_ROOMS, chatRoom.getRoomId(),chatRoom);
//
//        return chatRoom;
//    }
//
//
//    /**
//     * 1. 채팅방 만들고
//     * 2. 해당 채팅방에 통신을 담당할 리스너 설정
//     */
//    public void enterChatRoom(String roomId){
//        /** 1. 동일한 채팅방 있는지 검사 **/
//        ChannelTopic topic = topics.get(roomId);
//        if(topic == null){
//            /** 2. 없으면 새로운 채팅방 만들어서 저장 **/
//            topic = new ChannelTopic(roomId);
//            topics.put(roomId,topic);
//        }
//        /** 3. 해당 채팅방(topic)의 통신을 담당 할 리스너(subscriberService) 등록 **/
//        redisMessageListener.addMessageListener(subscriberService, topic);
//    }
//
//
//    // 대화내용 저장
//    public void insertChat(ChatMessageDTO chatMessageDTO) throws Exception {
//
//        log.info("채팅 저장 레파지토리 시작");
//
//        // 대화방 키
//        String roomKey = chatMessageDTO.getKey();
//
//        redisDB.setKeySerializer(new StringRedisSerializer());
//        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatMessageDTO.class));
//
//        redisDB.opsForList().rightPush(roomKey, String.valueOf(chatMessageDTO));
//
//        log.info("채팅 저장 레파지토리 종료");
//    }
//
//
//    /**
//     * 채팅 방 목록(topics)에서 roomId에 해당하는 채팅방 이름 넘기기
//     */
//    public ChannelTopic getTopic(String roomId){
//        return topics.get(roomId);
//    }
//
//
//
//}