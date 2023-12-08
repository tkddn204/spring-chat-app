package com.rightpair.exception;

public class ChatRoomNotFoundException extends BusinessException {
    private final static ErrorCode ERROR_CODE = ErrorCode.CHAT_ROOM_NOT_FOUND_ERROR;

    public ChatRoomNotFoundException() {
        super(ERROR_CODE);
    }
}
