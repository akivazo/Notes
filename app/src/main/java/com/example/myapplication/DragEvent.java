package com.example.myapplication;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DragEvent implements View.OnDragListener{
    private Runnable onDrop;
    View note_view;
    public DragEvent(Runnable onDrop, View note_view){
        this.onDrop = onDrop;
        this.note_view = note_view;
    }
    // This is the method that the system calls when it dispatches a drag event to the
    // listener.
    public boolean onDrag(View v, android.view.DragEvent event) {
        // Defines a variable to store the action type for the incoming event
        final int action = event.getAction();

        // Handles each of the expected events
        switch(action) {

            case android.view.DragEvent.ACTION_DRAG_STARTED:

                // Determines if this View can accept the dragged data
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {

                    // As an example of what your application might do,
                    // applies a blue color tint to the View to indicate that it can accept
                    // data.
                    setColor(v, Color.BLACK);
                    // Invalidate the view to force a redraw in the new tint
                    //v.invalidate();

                    // returns true to indicate that the View can accept the dragged data.
                    return true;

                }

                // Returns false. During the current drag and drop operation, this View will
                // not receive events again until ACTION_DRAG_ENDED is sent.
                return false;

            case android.view.DragEvent.ACTION_DRAG_ENTERED:

                // Applies a green tint to the View. Return true; the return value is ignored.

                setColor(v,Color.GREEN);

                // Invalidate the view to force a redraw in the new tint
                //v.invalidate();

                return true;

            case android.view.DragEvent.ACTION_DRAG_LOCATION:

                // Ignore the event
                return true;

            case android.view.DragEvent.ACTION_DRAG_EXITED:

                // Re-sets the color tint to blue. Returns true; the return value is ignored.
                setColor(v,Color.BLACK);

                // Invalidate the view to force a redraw in the new tint
                //v.invalidate();

                return true;

            case android.view.DragEvent.ACTION_DROP:
                note_view.setVisibility(View.VISIBLE);
                // Turns off any color tints
                setColor(v,Color.BLUE);
                onDrop.run();
                // Returns true. DragEvent.getResult() will return true.
                return true;
            case android.view.DragEvent.ACTION_DRAG_ENDED:
                setColor(v, Color.BLACK);
                if (!event.getResult())
                    note_view.setVisibility(View.VISIBLE);

                // returns true; the value is ignored.
                return true;

            // An unknown action type was received.
            default:
                Log.e("DragDrop Example","Unknown action type received by OnDragListener.");
                break;
        }

        return false;
    }
    protected void setColor(View view, int color){
        //  v.clearColorFilter();
        ((TextView)view).setTextColor(color);
    }
}
