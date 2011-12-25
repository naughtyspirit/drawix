package com.naughtyspirit.drawix.activity;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;


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

  private class Vertex {
    float x, y;
  }

  private class CustomRenderer implements GLSurfaceView.Renderer {

    public List<Vertex> vertexList = new ArrayList<Vertex>();

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



      if (vertexList.size() == 3) {
        Log.d("About to draw", "Drawingg...");
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(3 * 2 * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer vertices = byteBuffer.asFloatBuffer();
        float[] coordinates = new float[vertexList.size() * 2];
        int index = 0;
        for (int i = 0; i < vertexList.size(); i++, index += 2) {
          coordinates[index] = vertexList.get(i).x;
          coordinates[index + 1] = vertexList.get(i).y;
        }
        for (int i = 0; i < coordinates.length; i++) {
          Log.d("Coordinate " + i, coordinates[i] + "");
        }
        vertices.put(coordinates);
//        vertices.put(new float[]{
//                0.0f, 0.0f,
//                319.0f, 20.0f,
//                160.0f, 479.0f});
        vertices.flip();

        glMatrixMode(GL10.GL_MODELVIEW);
        glLoadIdentity();
//        glTranslatef(10, 10, 0);
//        glRotatef(45, 10, 10, 1);
        glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);


        glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
      }
    }
  }

  private CustomRenderer drawingSurfaceRenderer = new CustomRenderer();

  private DrawingSurface view;

  private class DrawingSurface extends GLSurfaceView {

    public DrawingSurface(Context context) {
      super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
      switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
          if (drawingSurfaceRenderer.vertexList.size() > 3) {
            return true;
          }
          Vertex vertex = new Vertex();
          vertex.x = event.getX();
          vertex.y = event.getY();
          drawingSurfaceRenderer.vertexList.add(vertex);
          if (drawingSurfaceRenderer.vertexList.size() == 3) {
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