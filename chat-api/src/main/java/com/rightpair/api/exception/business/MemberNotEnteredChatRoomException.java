package com.rightpair.api.exception.business;

import com.rightpair.core.exception.BusinessException;
import com.rightpair.core.exception.ErrorCode;

public class MemberNotEnteredChatRoomException extends BusinessException {
    private final static ErrorCode ERROR_CODE = ErrorCode.MEMBER_NOT_ENTERED_CHAT_ROOM_ERROR;

    public MemberNotEnteredChatRoomException() {
        super(ERROR_CODE);
    }
}
