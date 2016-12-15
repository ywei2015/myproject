package sail.beans.exception;


 /**
 * <p>类说明：</p>
 *    
 *    
 * <p>Copyright: Copyright (c) 2008</p>
 *    
 * @author mark
 * 2007-12-12
 *
 * @version 1.0
 */ 
 


@SuppressWarnings("serial")
public class BusinessException extends RuntimeException {
    /**
     * 根据错误代码将所有的错误相关信息维护进数据库，然后，通过日志文件记录下来。
     * 对于正式发行版本而言，也可以将所有错误记入数据库。
     */

    private String _errCode = null;
    private String _errInfo = null;
    private String _runtimeErrInfo = null;
    private boolean isLogged = false;    
    
	public BusinessException() {
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public String get_errCode() {
		return _errCode;
	}

	public void set_errCode(String code) {
		_errCode = code;
	}

	public String get_errInfo() {
		return _errInfo;
	}

	public void set_errInfo(String info) {
		_errInfo = info;
	}

	public String get_runtimeErrInfo() {
		return _runtimeErrInfo;
	}

	public void set_runtimeErrInfo(String errInfo) {
		_runtimeErrInfo = errInfo;
	}
	
//    public void printStackTrace(PrintWriter s) { 
//        synchronized (s) {
//            s.println(this);
//            StackTraceElement[] trace = getOurStackTrace();
//            for (int i=0; i < trace.length; i++)
//                s.println("\tat " + trace[i]);
//
//            Throwable ourCause = getCause();
//            if (ourCause != null)
//                ourCause.printStackTraceAsCause(s, trace);
//        }
//    }	
//
//    private StackTraceElement[] stackTrace;
//    
//    private synchronized StackTraceElement[] getOurStackTrace() {
//        // Initialize stack trace if this is the first call to this method
//        if (stackTrace == null) {
//            int depth = getStackTraceDepth();
//            stackTrace = new StackTraceElement[depth];
//            for (int i=0; i < depth; i++)
//                stackTrace[i] = getStackTraceElement(i);
//        }
//        return stackTrace;
//    }
//
//    private native int getStackTraceDepth();
//    
//    private native StackTraceElement getStackTraceElement(int index);    
//    
//    /**
//     * Print our stack trace as a cause for the specified stack trace.
//     */
//    public void printStackTraceAsCause(PrintStream s,
//                                        StackTraceElement[] causedTrace)
//    {
//        // assert Thread.holdsLock(s);
//
//        // Compute number of frames in common between this and caused
//        StackTraceElement[] trace = getOurStackTrace();
//        int m = trace.length-1, n = causedTrace.length-1;
//        while (m >= 0 && n >=0 && trace[m].equals(causedTrace[n])) {
//            m--; n--;
//        }
//        int framesInCommon = trace.length - 1 - m;
//
//        s.println("Caused by: " + this);
//        for (int i=0; i <= m; i++)
//            s.println("\tat " + trace[i]);
//        if (framesInCommon != 0)
//            s.println("\t... " + framesInCommon + " more");
//
//        // Recurse if we have a cause
//        Throwable ourCause = getCause();
//        if (ourCause != null)
//            ourCause.printStackTraceAsCause(s, trace);
//    }    
//
//    private Throwable cause = this;
//    
//    public Throwable getCause() {
//        return (cause==this ? null : cause);
//    }
}
