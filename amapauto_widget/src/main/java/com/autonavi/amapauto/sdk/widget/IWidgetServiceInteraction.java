package com.autonavi.amapauto.sdk.widget;

/**
 * widget aidl 对应的能力抽象
 * Created by guocai.cgc on 2018/12/5.
 */

public interface IWidgetServiceInteraction {

    boolean startAutoWidget();

    boolean stopAutoWidget();

    boolean setWidgetStateControl(com.autonavi.amapauto.widget.aidl.IAutoWidgetStateControl
                                       stateControl);
}
