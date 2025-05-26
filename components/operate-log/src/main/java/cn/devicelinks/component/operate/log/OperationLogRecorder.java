package cn.devicelinks.component.operate.log;

import cn.devicelinks.component.jackson.utils.JacksonUtils;
import cn.devicelinks.component.web.RequestContext;
import cn.devicelinks.component.web.RequestContextHolder;
import cn.devicelinks.component.web.utils.HttpRequestUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作日志记录器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OperationLogRecorder {

    private static final ThreadLocal<List<OperationLogObject>> contextHolder = new InheritableThreadLocal<>();

    /**
     * Records a successful operation log entry with the provided details and associates it with the current thread's context.
     * The method captures the object identifier, message, and activity data (if any), converting the activity data to JSON format.
     * Additional contextual information such as IP address, browser, and operating system will be automatically added based on the current request context.
     *
     * @param objectId     the unique identifier of the object associated with the operation
     * @param msg          a descriptive message detailing the operation performed
     * @param activityData optional data related to the operation, which will be serialized to JSON if provided
     */
    public static void success(String objectId, String msg, Object activityData) {
        // @formatter:off
        OperationLogObject successLog = new OperationLogObject()
                .setExecutionSucceed(true)
                .setObjectId(objectId)
                .setMsg(msg)
                .setActivateData(activityData != null ? JacksonUtils.objectToJson(activityData) : null)
                .setTime(LocalDateTime.now());
        // @formatter:on
        addOperationLogToLocalList(successLog);
    }

    public static void success(String objectId, Object activityData) {
        success(objectId, null, activityData);
    }

    /**
     * Records an error operation log entry with the provided details and associates it with the current thread's context.
     * The method captures the object identifier, message, activity data (if any), and the cause of the failure.
     * The activity data is serialized to JSON format if provided.
     * Additional contextual information such as IP address, browser, and operating system will be automatically added based on the current request context.
     *
     * @param objectId     the unique identifier of the object associated with the operation
     * @param msg          a descriptive message detailing the operation performed
     * @param activityData optional data related to the operation, which will be serialized to JSON if provided
     * @param throwable    the exception or error that caused the operation to fail
     */
    public static void error(String objectId, String msg, Object activityData, Throwable throwable) {
        // @formatter:off
        OperationLogObject successLog = new OperationLogObject()
                .setExecutionSucceed(false)
                .setObjectId(objectId)
                .setMsg(msg)
                .setActivateData(activityData != null ? JacksonUtils.objectToJson(activityData) : null)
                .setFailureCause(throwable)
                .setTime(LocalDateTime.now());
        // @formatter:on
        addOperationLogToLocalList(successLog);
    }

    public static void error(String objectId, Object activityData, Throwable throwable) {
        error(objectId, null, activityData, throwable);
    }

    /**
     * Get OperationLogObject List
     *
     * @return {@link OperationLogObject}
     */
    public static List<OperationLogObject> getLocalOperationLogList() {
        return contextHolder.get();
    }

    /**
     * Clear {@link OperationLogObject} List
     */
    public static void clear() {
        contextHolder.remove();
    }

    private static void addOperationLogToLocalList(OperationLogObject operationLogObject) {
        setAddition(operationLogObject);
        List<OperationLogObject> operationLogObjectList = contextHolder.get();
        if (operationLogObjectList == null) {
            operationLogObjectList = new ArrayList<>();
        }
        operationLogObjectList.add(operationLogObject);
        contextHolder.set(operationLogObjectList);
    }

    private static void setAddition(OperationLogObject operationLogObject) {
        RequestContext requestContext = RequestContextHolder.getContext();
        if (requestContext != null) {
            operationLogObject.setIpAddress(requestContext.getIp());
            operationLogObject.setOs(HttpRequestUtils.getOS(requestContext.getRequest()));
            operationLogObject.setBrowser(HttpRequestUtils.getBrowser(requestContext.getRequest()));
        }
    }
}
