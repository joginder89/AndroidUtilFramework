package in.jogindersharma.jsframeworkdemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import in.jogindersharma.jsutilsframework.colorpicker.ColorPickerView;
import in.jogindersharma.jsutilsframework.colorpicker.OnColorSelectedListener;
import in.jogindersharma.jsutilsframework.colorpicker.builder.ColorPickerClickListener;
import in.jogindersharma.jsutilsframework.colorpicker.builder.ColorPickerDialogBuilder;

public class ColorPickerActivity extends AppCompatActivity {

    LinearLayout llMain;
    AlertDialog colorDialog;
    String TAG = "ColorPickerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);

        initLayouts();
    }

    private void initLayouts() {

        llMain = (LinearLayout) findViewById(R.id.llMain);

        findViewById(R.id.bSelectColor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorPicker();
            }
        });

        initColorPickerDialog();
    }

    private void initColorPickerDialog() {
        colorDialog = ColorPickerDialogBuilder
                .with(this)
                .setTitle("Choose color")
                //.initialColor(currentBackgroundColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                        Log.e(TAG, "selectedColor 1: " + selectedColor);
                        //llMain.setBackgroundColor(selectedColor);
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        llMain.setBackgroundColor(selectedColor);
                        Log.e(TAG, "selectedColor 2: " + selectedColor);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build();
    }

    private void showColorPicker() {
        colorDialog.show();
    }
}
