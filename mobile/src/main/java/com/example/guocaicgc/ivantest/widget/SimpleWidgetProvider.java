package com.example.guocaicgc.ivantest.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.guocaicgc.ivantest.R;

/**
 * Created by guocai.cgc on 2017/8/22.
 */
public class SimpleWidgetProvider extends AppWidgetProvider {
    private static final String ACTION_TOAST_CONTENT = "com.example.guocaicgc.ivantest.widget" +
            ".ACTION_TOAST_CONTENT";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        RemoteViews remoteViews =  new RemoteViews(context.getPackageName(), R.layout
                .widget_layout_simple);
        remoteViews.setTextViewText(R.id.tv_widget_content, "Widget Content");

        Intent intent = new Intent(ACTION_TOAST_CONTENT);
        intent.putExtra("widgetContent", "Widget Content on Clicked");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.tv_widget_content, pendingIntent);

        AppWidgetManager.getInstance(context).updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case ACTION_TOAST_CONTENT:
                Toast.makeText(context, intent.getStringExtra("widgetContent"), Toast.LENGTH_SHORT)
                        .show();
                break;
            default:
                break;
        }
        super.onReceive(context, intent);
    }
}
