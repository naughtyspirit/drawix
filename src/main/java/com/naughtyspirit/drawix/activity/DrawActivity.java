package com.naughtyspirit.drawix.activity;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.*;
import com.naughtyspirit.drawix.R;
import com.naughtyspirit.drawix.primitive.*;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static android.opengl.GLES10.*;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 25-12-2011
 */
public class DrawActivity extends Activity {

  private final List<Vertex> selectedVertices = new ArrayList<Vertex>();

  private final List<BaseDrawablePrimitive> primitives = new LinkedList<BaseDrawablePrimitive>();

  private enum Primitives {RECTANGLE, LINE}

  ;

  private Primitives currentPrimitive = Primitives.RECTANGLE;

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.primitive_selection_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.rectangle:
        currentPrimitive = Primitives.RECTANGLE;
        return true;

      case R.id.line:
        currentPrimitive = Primitives.LINE;
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

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
      glOrthof(0, width, height, 0, 1, -1);
    }

    @Override
    public void onDrawFrame(GL10 openGl) {
      glClearColor(0, 0, 0, 1);
      glClear(GL10.GL_COLOR_BUFFER_BIT);

      new Circle(new Vertex(40, 40), 40).draw();
//      for (BaseDrawablePrimitive primitive : primitives) {
//        primitive.draw();
//      }

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
          Vertex selection = new Vertex(event.getX(), event.getY());
          selectedVertices.add(selection);
          if (selectedVertices.size() == 2) {
            if (currentPrimitive == Primitives.RECTANGLE) {
              primitives.add(new Rectangle(selectedVertices.get(0), selectedVertices.get(1)));
            } else {
              primitives.add(new Line(selectedVertices.get(0), selectedVertices.get(1)));
            }
            selectedVertices.clear();
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