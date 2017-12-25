package com.autonavi.amapauto.component;
import com.autonavi.framework.component.AbstractComponent;;
import com.autonavi.framework.component.app.IServiceComponent;;
import com.autonavi.framework.component.AbstractAppLauncherComponent;;
import com.autonavi.framework.component.app.IAppLauncherComponent;;

public final class ComponentFactorytmp implements com.autonavi.framework.component.factory.IComponentFactory; {


@Override
public IAppLauncherComponent; createAppLauncherComponent(String componentId) {
switch(componentId) {
case "coreComponent" :
    AbstractAppLauncherComponent; coreComponent = new com.example.modulecore.component.CoreComponent();
    return coreComponent;
case "ivanComponentInModule" :
    AbstractAppLauncherComponent; ivanComponentInModule = new com.example.modulecomponent.IvanComponent();
    return ivanComponentInModule;
default:
    return null;
}
}

@Override
public IServiceComponent; createServiceComponent(String componentId) {
switch(componentId) {
default:
    return null;
}
}

@Override
public String[] getConfiguredComponents() {
String[] components = new String[2];
components[0] = "coreComponent";
components[1] = "ivanComponentInModule";
return components;
}

}