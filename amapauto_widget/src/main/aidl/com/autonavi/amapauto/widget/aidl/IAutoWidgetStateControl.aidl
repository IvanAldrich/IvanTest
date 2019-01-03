// IAutoWidgetStateControl.aidl
package com.autonavi.amapauto.widget.aidl;

// Widget相关状态监控
// 部分系统需要在widget显示时阻塞其显示
interface IAutoWidgetStateControl {

    /**
     * 第三方是否允许widget显示
     */
    boolean shouldInteruptWidgetDisplay();
}
