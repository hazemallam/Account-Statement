package com.nagarro.assignment.dto.enums;

import lombok.Getter;

@Getter
public enum ErrorCodes {
  INVALID_PARAMETER("CS_400", "invalid parameter try again with valid one");

  private final String code;
  private final String msg;

  ErrorCodes(String code, String msg ){
      this.code = code;
      this.msg = msg;
  }
}
