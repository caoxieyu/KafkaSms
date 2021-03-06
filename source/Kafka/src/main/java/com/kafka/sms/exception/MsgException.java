/***
 * @pName proback
 * @name FinanceException
 * @user DF
 * @date 2018/8/5
 * @desc
 */
package com.kafka.sms.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/***
 * 普通消息异常类
 */
public class MsgException extends RuntimeException {
    private String msg;

    public String getMsg() {
        return msg;
    }

    /**
     * Constructs a new runtime exceptionAsString with the specified cause and a
     * detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>).  This constructor is useful for runtime exceptions
     * that are little more than wrappers for other throwables.
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method).  (A <tt>null</tt> value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     * @since 1.4
     */
    public MsgException(Throwable cause, String msg) {
        super(cause);
        this.msg = msg;
    }

    /**
     * Constructs a new runtime exceptionAsString with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public MsgException(String msg) {
        this.msg = msg;
    }

}
