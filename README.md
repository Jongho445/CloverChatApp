# CloverChatApp

### TODO

#### 웹소켓 커넥션 자동 끊김 이슈 해결하기

- 내용: 오랜 시간동안 웹소켓 session의 connection이 사용되지 않으면 자동적으로 session의 connection이 끊어지게 됨.
- 이로인해 발생할 수 있는 장애 시나리오: 긴 시간동안 채팅방을 사용하지 않음 -> 채팅 메시지 session(ChatMessageSession)의 connection이 끊김 -> 그러나 session의 connection만 끊겼지 session 자체가 null이 된 것은 아니라 다시 해당 채팅방에 들어가도 session이 다시 connect되지 않는 문제가 발생함(이는 ChatRoomDetailFragment.initChatMessageSession()에서 return 탈출로 인해 로직이 생략되기 때문)
    - 해당 시나리오의 장애는 다른 채팅방에 들어갔다가 되돌아오면 해결됨. 하지만 이는 임시방편일뿐임.
    - 이러한 종류의 이슈는 ChatMessageSession 뿐만 아니라 ChatUserSession에서도 발생함. 그러나 ChatRoomSession은 해당 세션이 null이어도 매번 세션을 disconnect한 뒤 다시 connect하므로 해당 이슈가 발생하지 않음(ChatRoomListFragment.initWebSocketSession() 내부 구현 참조).
- Solution 1: 웹소켓 세션이 연결되면 주기적으로 ping을 보내 웹소켓 세션의 연결이 자동적으로 끊기지 않도록 한다.
- Solution 2: ChatRoomDetailFragment.initChatMessageSession()에서 return 탈출 로직을 제거한다.
    - 그러나 위 방법을 적용시 채팅 유저 리스트에서 채팅방으로 진입할때마다 세션이 새로 연결되어서 채팅 유저 리스트의 UI가 깜빡이는 이슈가 생기기에 기각.