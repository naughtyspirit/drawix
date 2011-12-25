package com.naughtyspirit.drawix.activity;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import com.naughtyspirit.drawix.primitive.Line;
import com.naughtyspirit.drawix.primitive.Rectangle;
import com.naughtyspirit.drawix.primitive.Vertex;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static android.opengl.GLES10.*;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 12/25/11
 */
public class DrawActivity extends Activity {

  private Rectangle rectangle = null;
  private Line line = null;

  private class CustomRenderer implements GLSurfaceView.Renderer {

    @Override
    public void onSurfaceCreated(GL10 openGl, EGLConfig eglConfig) {
      glEnableClientState(GL10.GL_VERTEX_ARRAY);
    }

    @Override
    public void onSurfaceChanged(GL10 openGl, int width, int height) {
      glViewport(0, 0, width, height);

      // center is top left
      glMatrixMode(GL10.GL_PROJECTION);
      glLoadIdentity();
      glOrthof(0, 320, 480, 0, 1, -1);
    }

    @Override
    public void onDrawFrame(GL10 openGl) {
      glClearColor(0, 0, 0, 1);
      glClear(GL10.GL_COLOR_BUFFER_BIT);
      if(line != null) {
        line.draw();
      }
//      if(rectangle != null) {
//        rectangle.draw();
//      }
    }
  }

  private CustomRenderer drawingSurfaceRenderer = new CustomRenderer();

  private DrawingSurface view;

  private class DrawingSurface extends GLSurfaceView {

    private Vertex a = null, b = null;

    public DrawingSurface(Context context) {
      super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
      switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
          if(a == null) {
            a = new Vertex(event.getX(), event.getY());
          } else if(b == null) {
            b = new Vertex(event.getX(), event.getY());
            line = new Line(a, b);
            requestRender();
          }
          break;
      }

      return true;
    }
  }

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
    view = new DrawingSurface(this);
    view.setRenderer(drawingSurfaceRenderer);
    view.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    setContentView(view);
  }

  @Override
  protected void onPause() {
    super.onPause();
    view.onPause();
  }

  @Override
  protected void onResume() {
    super.onResume();
    view.onResume();
  }
}