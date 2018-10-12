//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.coocaa.union.manager.exception;

public class BaseJSONException extends RuntimeException {
    private ErrorCodes error;
    private String[] replaces;

    public BaseJSONException(ErrorCodes error, String... replaces) {
        this.error = error;
        this.replaces = replaces;
    }

    public BaseJSONException(ErrorCodes error) {
        this.error = error;
    }

    public String[] getReplaces() {
        return this.replaces;
    }

    public ErrorCodes getError() {
        return this.error;
    }

    @Override
    public String getMessage() {
        String msg = error.getMsg();
        if(replaces != null && replaces.length > 0){
            msg = String.format(msg, replaces);
        }
        return msg;
    }
}
