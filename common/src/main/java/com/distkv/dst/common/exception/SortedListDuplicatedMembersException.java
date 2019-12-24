package com.distkv.dst.common.exception;

public class SortedListDuplicatedMembersException extends DstException {
	protected String errorCode = "008";

	protected String key;

	public SortedListDuplicatedMembersException(String key) {
		super(String.format("The SortedList %s which you putted into has" +
						" duplicated members.",  key));
	}

	public SortedListDuplicatedMembersException(String key, String typeCode) {
		super(String.format("The SortedList %s which you putted into has" +
						" duplicated members.",  key));
		this.errorCode = typeCode + this.errorCode;
	}

	public String getKey() {
		return key;
	}

	public String getErrorCode() {
		return errorCode;
	}
}
