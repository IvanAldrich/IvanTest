// IAmapAutoWidgetManager.aidl
package com.autonavi.amapauto.widget.aidl;

import com.autonavi.amapauto.widget.aidl.IAutoWidgetStateControl;

// widget三方控制逻辑实现
interface IAmapAutoWidgetManager {

    void startAutoWidget();

    void stopAutoWidget();

    void setWidgetStateControl(IAutoWidgetStateControl stateControl);
}
