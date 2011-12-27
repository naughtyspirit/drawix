package com.naughtyspirit.drawix.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.*;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 27-12-2011
 */
public class ColorPickerDialog extends Dialog {

  public interface OnColorChangedListener {
    void colorChanged(int color);
  }

  private OnColorChangedListener listener;
  private int initialColor;

  private static class ColorPickerView extends View {

    private Paint paint;
    private Paint centerPaint;
    private final int[] colors;
    private OnColorChangedListener listener;

    ColorPickerView(Context c, OnColorChangedListener l, int color) {
      super(c);
      listener = l;
      colors = new int[] {
              0xFFFF0000, 0xFFFF00FF, 0xFF0000FF, 0xFF00FFFF, 0xFF00FF00,
              0xFFFFFF00, 0xFFFF0000
      };
      Shader s = new SweepGradient(0, 0, colors, null);

      paint = new Paint(Paint.ANTI_ALIAS_FLAG);
      paint.setShader(s);
      paint.setStyle(Paint.Style.STROKE);
      paint.setStrokeWidth(32);

      centerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
      centerPaint.setColor(color);
      centerPaint.setStrokeWidth(5);
    }

    private boolean trackingCenter;
    private boolean highlightCenter;

    @Override
    protected void onDraw(Canvas canvas) {
      float r = CENTER_X - paint.getStrokeWidth() * 0.5f;

      canvas.translate(CENTER_X, CENTER_X);

      canvas.drawOval(new RectF(-r, -r, r, r), paint);
      canvas.drawCircle(0, 0, CENTER_RADIUS, centerPaint);

      if (trackingCenter) {
        int c = centerPaint.getColor();
        centerPaint.setStyle(Paint.Style.STROKE);

        if (highlightCenter) {
          centerPaint.setAlpha(0xFF);
        } else {
          centerPaint.setAlpha(0x80);
        }
        canvas.drawCircle(0, 0,
                CENTER_RADIUS + centerPaint.getStrokeWidth(),
                centerPaint);

        centerPaint.setStyle(Paint.Style.FILL);
        centerPaint.setColor(c);
      }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      setMeasuredDimension(CENTER_X * 2, CENTER_Y * 2);
    }

    private static final int CENTER_X = 100;
    private static final int CENTER_Y = 100;
    private static final int CENTER_RADIUS = 32;

    private int floatToByte(float x) {
      int n = java.lang.Math.round(x);
      return n;
    }

    private int pinToByte(int n) {
      if (n < 0) {
        n = 0;
      } else if (n > 255) {
        n = 255;
      }
      return n;
    }

    private int ave(int s, int d, float p) {
      return s + java.lang.Math.round(p * (d - s));
    }

    private int interpColor(int colors[], float unit) {
      if (unit <= 0) {
        return colors[0];
      }
      if (unit >= 1) {
        return colors[colors.length - 1];
      }
      float p = unit * (colors.length - 1);
      int i = (int) p;
      p -= i;

      // now p is just the fractional part [0...1) and i is the index
      int c0 = colors[i];
      int c1 = colors[i + 1];
      int a = ave(Color.alpha(c0), Color.alpha(c1), p);
      int r = ave(Color.red(c0), Color.red(c1), p);
      int g = ave(Color.green(c0), Color.green(c1), p);
      int b = ave(Color.blue(c0), Color.blue(c1), p);

      return Color.argb(a, r, g, b);
    }

    private int rotateColor(int color, float rad) {
      float deg = rad * 180 / 3.1415927f;
      int r = Color.red(color);
      int g = Color.green(color);
      int b = Color.blue(color);

      ColorMatrix cm = new ColorMatrix();
      ColorMatrix tmp = new ColorMatrix();

      cm.setRGB2YUV();
      tmp.setRotate(0, deg);
      cm.postConcat(tmp);
      tmp.setYUV2RGB();
      cm.postConcat(tmp);

      final float[] a = cm.getArray();

      int ir = floatToByte(a[0] * r + a[1] * g + a[2] * b);
      int ig = floatToByte(a[5] * r + a[6] * g + a[7] * b);
      int ib = floatToByte(a[10] * r + a[11] * g + a[12] * b);

      return Color.argb(Color.alpha(color), pinToByte(ir),
              pinToByte(ig), pinToByte(ib));
    }

    private static final float PI = 3.1415926f;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
      float x = event.getX() - CENTER_X;
      float y = event.getY() - CENTER_Y;
      boolean inCenter = java.lang.Math.sqrt(x * x + y * y) <= CENTER_RADIUS;

      switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
          trackingCenter = inCenter;
          if (inCenter) {
            highlightCenter = true;
            invalidate();
            break;
          }
        case MotionEvent.ACTION_MOVE:
          if (trackingCenter) {
            if (highlightCenter != inCenter) {
              highlightCenter = inCenter;
              invalidate();
            }
          } else {
            float angle = (float) java.lang.Math.atan2(y, x);
            // need to turn angle [-PI ... PI] into unit [0....1]
            float unit = angle / (2 * PI);
            if (unit < 0) {
              unit += 1;
            }
            centerPaint.setColor(interpColor(colors, unit));
            invalidate();
          }
          break;
        case MotionEvent.ACTION_UP:
          if (trackingCenter) {
            if (inCenter) {
              listener.colorChanged(centerPaint.getColor());
            }
            trackingCenter = false;    // so we draw w/o halo
            invalidate();
          }
          break;
      }
      return true;
    }
  }

  public ColorPickerDialog(Context context,
                           OnColorChangedListener listener,
                           int initialColor) {
    super(context);

    this.listener = listener;
    this.initialColor = initialColor;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    OnColorChangedListener l = new OnColorChangedListener() {
      public void colorChanged(int color) {
        listener.colorChanged(color);
        dismiss();
      }
    };

    setContentView(new ColorPickerView(getContext(), l, initialColor));
    setTitle("Pick a Color");
  }
}
