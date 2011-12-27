package com.naughtyspirit.drawix.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import com.naughtyspirit.drawix.R;
import com.naughtyspirit.drawix.collision.BoundingShape;
import com.naughtyspirit.drawix.collision.HasBoundingShape;
import com.naughtyspirit.drawix.primitive.*;
import com.naughtyspirit.drawix.ui.ColorPickerDialog;

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
  private static final int PICK_COLOR_DIALOG = 1;

  private int currentColor = Color.WHITE;

  private enum Primitives {
    CIRCLE,
    RECTANGLE,
    LINE
  };

  private Primitives currentPrimitive = Primitives.RECTANGLE;

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.primitive_selection_menu, menu);
    return true;
  }

  @Override
  protected Dialog onCreateDialog(int id) {
    ColorPickerDialog dialog = new ColorPickerDialog(this, new ColorPickerDialog.OnColorChangedListener() {
      @Override
      public void colorChanged(int color) {
        currentColor = color;
      }
    }, Color.WHITE, Color.WHITE);
    return dialog;
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
      case R.id.circle:
        currentPrimitive = Primitives.CIRCLE;
        return true;
      case R.id.pick_color:
        showDialog(PICK_COLOR_DIALOG);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private class CustomRenderer implements GLSurfaceView.Renderer {

    @Override
    public void onSurfaceCreated(GL10 openGl, EGLConfig eglConfig) {
      glEnableClientState(GL10.GL_VERTEX_ARRAY);
      glEnable(GL_POINT_SMOOTH);
      glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);
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
//      glColor4f(Color.red(currentColor)/255, Color.green(currentColor)/255, Color.blue(currentColor)/255, 1);
      for (BaseDrawablePrimitive primitive : primitives) {
        primitive.draw();
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
          Vertex selection = new Vertex(event.getX(), event.getY());
          selectedVertices.add(selection);
          if (selectedVertices.size() == 2) {
            BaseDrawablePrimitive primitive;
            if (currentPrimitive == Primitives.RECTANGLE) {
              primitive = new Rectangle(selectedVertices.get(0), selectedVertices.get(1));
            } else if(currentPrimitive == Primitives.CIRCLE) {
              primitive = new Circle(selectedVertices.get(0), selectedVertices.get(0).distanceTo(selection));
            } else {
              primitive = new Line(selectedVertices.get(0), selectedVertices.get(1));
            }
            primitive.setColor(currentColor);
            primitives.add(primitive);
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